package com.automaticclean.handler;

import com.automaticclean.Definition;
import com.automaticclean.timer.TimerExecute;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/9 14:50
 * Version: 1.0
 */
@Mod.EventBusSubscriber(modid = Definition.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {
    @SubscribeEvent
    public static void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        Definition.SERVER = event.getServer();
    }

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        Definition.config = ConfigHandler.load();
        TimerExecute.INSTANCE.startTimer();
    }

    @SubscribeEvent
    public static void onServerStopping(FMLServerStoppingEvent event) {
        TimerExecute.INSTANCE.stopTimer();
    }

    @SubscribeEvent
    public static void onServerStopped(FMLServerStoppedEvent event) {
        ConfigHandler.save(Definition.config);
    }


}