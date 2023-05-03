package com.automaticclean.handler;

import com.automaticclean.Definition;
import com.automaticclean.timer.TimerExecute;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Definition.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        Definition.SERVER = event.getServer();
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        Definition.config = ConfigHandler.load();
        TimerExecute.INSTANCE.startTimer();
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        TimerExecute.INSTANCE.stopTimer();
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ConfigHandler.save(Definition.config);
    }


}