package com.automaticclean.interfaces;

import net.minecraft.server.MinecraftServer;

public interface CallableWithServer {
    void callback(MinecraftServer server);
}
