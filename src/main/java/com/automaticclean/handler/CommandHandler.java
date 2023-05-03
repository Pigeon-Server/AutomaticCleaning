package com.automaticclean.handler;

import com.automaticclean.command.Command;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandHandler {
    @SubscribeEvent
    public static void registryCmd(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher());
    }
}