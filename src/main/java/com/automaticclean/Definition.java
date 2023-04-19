package com.automaticclean;

import com.automaticclean.config.Config;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class Definition {
    public static final String MOD_ID = "automatic_cleaning";

    public static final Logger LOGGER = LogManager.getLogger(Definition.MOD_ID);

    public static Path CONFIG_FOLDER;
    public static Config config;

    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();


    public static void sendMessageToAllPlayers(ITextComponent message, boolean actionBar) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList().getPlayers()
                .forEach(player -> player.displayClientMessage(message, actionBar)))).start();
    }

    public static void sendMessageToAllPlayers(ITextComponent message) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList().broadcastMessage(message, ChatType.SYSTEM, Util.NIL_UUID))).start();
    }

    public static void sendMessageToAllPlayers(String message) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList().broadcastMessage(new StringTextComponent(message), ChatType.SYSTEM, Util.NIL_UUID))).start();
    }

    public static void sendMessageToAllPlayers(MinecraftServer server, String message, Object... args) {
        new Thread(() -> Optional.ofNullable(server).ifPresent(server_ -> server_.getPlayerList()
                .broadcastMessage(new StringTextComponent(String.format(message, args)), ChatType.SYSTEM, Util.NIL_UUID)))
                .start();
    }

    public static void sendMessageToAllPlayers(String message, Object... args) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList()
                .broadcastMessage(new StringTextComponent(String.format(message, args)), ChatType.SYSTEM, Util.NIL_UUID)))
                .start();
    }

}
