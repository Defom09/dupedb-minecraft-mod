package net.defom.dupedbmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.defom.dupedbmod.command.DupeDBCommands;
import net.defom.dupedbmod.util.DupeDBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DupeDBModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("dupedbmod");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing DupeDB Mod");
        DupeDBManager.initialize();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                DupeDBCommands.register(dispatcher)
        );
        LOGGER.info("DupeDB Mod loaded successfully");
    }
}
