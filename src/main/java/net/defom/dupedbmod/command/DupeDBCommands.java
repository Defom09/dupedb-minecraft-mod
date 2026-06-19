package net.defom.dupedbmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.defom.dupedbmod.util.DupeDBManager;
import net.dupedb.api.model.*;
import net.dupedb.api.exception.*;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

/**
 * DupeDB in-game commands
 * /dupedb search <query> [page]
 * /dupedb info <exploit_id>
 * /dupedb stats
 * /dupedb whoami
 */
public class DupeDBCommands {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
                literal("dupedb")
                        .then(literal("search")
                                .then(argument("query", StringArgumentType.greedyString())
                                        .executes(ctx -> searchExploits(
                                                ctx.getSource(),
                                                StringArgumentType.getString(ctx, "query"),
                                                1
                                        ))
                                        .then(argument("page", IntegerArgumentType.integer(1))
                                                .executes(ctx -> searchExploits(
                                                        ctx.getSource(),
                                                        StringArgumentType.getString(ctx, "query"),
                                                        IntegerArgumentType.getInteger(ctx, "page")
                                                ))
                                        )
                                )
                        )
                        .then(literal("info")
                                .then(argument("exploit_id", StringArgumentType.word())
                                        .executes(ctx -> getExploitInfo(
                                                ctx.getSource(),
                                                StringArgumentType.getString(ctx, "exploit_id")
                                        ))
                                )
                        )
                        .then(literal("stats")
                                .executes(ctx -> getStats(ctx.getSource()))
                        )
                        .then(literal("whoami")
                                .executes(ctx -> checkAuth(ctx.getSource()))
                        )
        );
    }

    private static int searchExploits(FabricClientCommandSource source, String query, int page) {
        source.sendFeedback(Text.literal("§6Searching DupeDB for: §f" + query));

        DupeDBManager.asyncCall(() ->
                DupeDBManager.getClient().exploits().search(query, page)
        ).thenAccept(result -> {
            if (result.cards().isEmpty()) {
                source.sendFeedback(Text.literal("§cNo exploits found."));
                return;
            }

            source.sendFeedback(Text.literal("§6Found §e" + result.cards().size() + "§6 results (page " + page + "):"));
            for (ExploitCard card : result.cards()) {
                String statusColor = switch (card.status()) {
                    case "verified" -> "§a";
                    case "working" -> "§e";
                    case "patched" -> "§c";
                    default -> "§7";
                };
                source.sendFeedback(Text.literal(
                        statusColor + "  • " + card.name() + " §8[" + card.edition() + "] §7" + card.id()
                ));
            }
            source.sendFeedback(Text.literal("§6Use §f/dupedb info <id>§6 to view details"));
        }).exceptionally(e -> {
            if (e.getCause() instanceof AuthException) {
                source.sendError(Text.literal("§cAuthentication failed. Try /dupedb whoami to login."));
            } else if (e.getCause() instanceof RateLimitException) {
                source.sendError(Text.literal("§cRate limited. Try again later."));
            } else {
                source.sendError(Text.literal("§cError searching: " + e.getMessage()));
            }
            return null;
        });

        return 1;
    }

    private static int getExploitInfo(FabricClientCommandSource source, String exploitId) {
        source.sendFeedback(Text.literal("§6Fetching exploit details..."));

        DupeDBManager.asyncCall(() ->
                DupeDBManager.getClient().exploits().getById(exploitId)
        ).thenAccept(exploit -> {
            source.sendFeedback(Text.literal("§6════════════════════════════"));
            source.sendFeedback(Text.literal("§6" + exploit.name()));
            source.sendFeedback(Text.literal("§6════════════════════════════"));
            source.sendFeedback(Text.literal("§7Status: §f" + exploit.status()));
            source.sendFeedback(Text.literal("§7Edition: §f" + exploit.edition()));
            source.sendFeedback(Text.literal("§7Version: §f" + exploit.version()));
            source.sendFeedback(Text.literal("§7Type: §f" + exploit.type()));
            source.sendFeedback(Text.literal("§7Upvotes: §a" + exploit.votesCount()));
            if (exploit.description() != null && !exploit.description().isEmpty()) {
                String desc = exploit.description();
                source.sendFeedback(Text.literal("§7Description: §f" + desc.substring(0, Math.min(100, desc.length())) + "..."));
            }
        }).exceptionally(e -> {
            if (e.getCause() instanceof AuthException) {
                source.sendError(Text.literal("§cNot authenticated. Use /dupedb whoami to login first."));
            } else {
                source.sendError(Text.literal("§cError fetching exploit: " + e.getMessage()));
            }
            return null;
        });

        return 1;
    }

    private static int getStats(FabricClientCommandSource source) {
        source.sendFeedback(Text.literal("§6Fetching DupeDB statistics..."));

        DupeDBManager.asyncCall(() ->
                DupeDBManager.getClient().metadata().publicStats()
        ).thenAccept(stats -> {
            source.sendFeedback(Text.literal("§6════════════════════════════"));
            source.sendFeedback(Text.literal("§6DupeDB Statistics"));
            source.sendFeedback(Text.literal("§6════════════════════════════"));
            source.sendFeedback(Text.literal("§7Total Exploits: §f" + stats.totalExploits()));
            source.sendFeedback(Text.literal("§7Verified: §a" + stats.verifiedCount()));
            source.sendFeedback(Text.literal("§7Working: §e" + stats.workingCount()));
            source.sendFeedback(Text.literal("§7Patched: §c" + stats.patchedCount()));
        }).exceptionally(e -> {
            source.sendError(Text.literal("§cError fetching stats: " + e.getMessage()));
            return null;
        });

        return 1;
    }

    private static int checkAuth(FabricClientCommandSource source) {
        source.sendFeedback(Text.literal("§6Checking authentication..."));

        DupeDBManager.asyncCall(() ->
                DupeDBManager.getClient().user().me()
        ).thenAccept(user -> {
            source.sendFeedback(Text.literal("§aAuthenticated as: §f" + user.name()));
        }).exceptionally(e -> {
            if (e.getCause() instanceof AuthException) {
                source.sendFeedback(Text.literal("§cNot authenticated."));
                source.sendFeedback(Text.literal("§eA browser window will open on your next API call."));
            } else {
                source.sendError(Text.literal("§cError: " + e.getMessage()));
            }
            return null;
        });

        return 1;
    }
}
