package net.defom.dupedbmod.util;

import net.dupedb.DupeDB;
import net.dupedb.DupeDBClient;
import net.dupedb.api.exception.DupeDBException;
import net.defom.dupedbmod.DupeDBModClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

/**
 * Manages DupeDB API client lifecycle and authentication.
 * Handles OAuth flow and token storage for Minecraft mod users.
 */
public class DupeDBManager {
    private static DupeDBClient client;
    private static final String CONFIG_DIR = "config/dupedbmod";
    private static final String TOKEN_FILE = CONFIG_DIR + "/dupedb-token.json";
    private static final String APP_ID = "dupedb-mod";
    private static final String REDIRECT_URI = "http://127.0.0.1:9876/callback";

    /**
     * Initialize the DupeDB client with OAuth authentication
     */
    public static void initialize() {
        try {
            // Create config directory if it doesn't exist
            Path configPath = Paths.get(CONFIG_DIR);
            if (!Files.exists(configPath)) {
                Files.createDirectories(configPath);
            }

            // Initialize OAuth client with token storage
            client = DupeDB.client()
                    .oauth(APP_ID, REDIRECT_URI)
                    .tokenStore(Paths.get(TOKEN_FILE))
                    .build();

            DupeDBModClient.LOGGER.info("DupeDB client initialized with OAuth");
        } catch (Exception e) {
            DupeDBModClient.LOGGER.error("Failed to initialize DupeDB client", e);
        }
    }

    /**
     * Get the DupeDB client instance
     */
    public static DupeDBClient getClient() {
        if (client == null) {
            initialize();
        }
        return client;
    }

    /**
     * Execute an async API call off the main thread
     */
    public static <T> CompletableFuture<T> asyncCall(APICall<T> call) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return call.execute();
            } catch (DupeDBException e) {
                DupeDBModClient.LOGGER.error("DupeDB API error", e);
                throw new RuntimeException(e);
            }
        });
    }

    @FunctionalInterface
    public interface APICall<T> {
        T execute() throws DupeDBException;
    }
}
