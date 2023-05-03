package com.automaticclean;

import com.automaticclean.config.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;
import java.util.Optional;

public class Definition {
    public static final String MOD_ID = "automatic_cleaning";
    public static final Logger LOGGER = LogManager.getLogger(Definition.MOD_ID);
    public static Path CONFIG_FOLDER;

    public static Config config;
    public static MinecraftServer SERVER = null;

    public static void sendMessageToAllPlayers(Component message, boolean actionBar) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList().getPlayers()
                .forEach(player -> player.displayClientMessage(message, actionBar)))).start();
    }

    public static void sendMessageToAllPlayers(String message) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList()
                .broadcastSystemMessage(Component.literal(message), false))).start();
    }

    public static void sendMessageToAllPlayers(MinecraftServer server, String message, Object... args) {
        new Thread(() -> Optional.ofNullable(server).ifPresent(server_ -> server_.getPlayerList()
                .broadcastSystemMessage(Component.literal(String.format(message, args)), false)))
                .start();
    }

    public static void sendMessageToAllPlayers(String message, Object... args) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList()
                .broadcastSystemMessage(Component.literal(String.format(message, args)), false)))
                .start();
    }

    public static Boolean cmdPermission(CommandSourceStack source, String permission, boolean admin) {
        if (admin) {
            return source.hasPermission(2);
        } else {
            return true;
        }
    }
}
