package com.automaticclean.handler;

import com.automaticclean.Definition;
import com.automaticclean.interfaces.CallableWithServer;
import com.automaticclean.interfaces.CleanType;
import com.automaticclean.timer.TimerExecute;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TimerHandler {
    private static int counter = -1;

    private static CallableWithServer callback = TimerExecute.INSTANCE;

    private static CleanType cleanType = null;

    public static void beginCountDown(CallableWithServer server) {
        callback = server;
        counter = Definition.config.getCommon().getCountdown() * 20;
        TimerHandler.cleanType = null;
    }

    public static void beginCountDown(CallableWithServer server, int setTime, CleanType cleanType) {
        callback = server;
        counter = setTime * 20;
        TimerHandler.cleanType = cleanType;
    }

    public static int getCounter() {
        return counter;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Optional.ofNullable(Definition.SERVER).ifPresent(server -> {
                if (counter >= 0) {
                    if (counter == 0) {
                        if (callback != null) {
                            callback.callback(server);
                        }
                        callback = null;
                        cleanType = null;
                        counter = -1;
                    } else {
                        if (counter / 20 == 60 && counter % 20 == 0) { // 60s
                            Definition.sendMessageToAllPlayers(server, (cleanType != null ? cleanType.getBeforeClean() : Definition.config.getCommon().getBeforeClean()), counter / 20);
                        } else if (counter / 20 == 30 && counter % 20 == 0) { // 30s
                            Definition.sendMessageToAllPlayers(server, (cleanType != null ? cleanType.getBeforeClean() : Definition.config.getCommon().getBeforeClean()), counter / 20);
                        } else if (counter / 20 == 15 && counter % 20 == 0) { // 15s
                            Definition.sendMessageToAllPlayers(server, (cleanType != null ? cleanType.getBeforeClean() : Definition.config.getCommon().getBeforeClean()), counter / 20);
                        } else if (counter / 20 == 10 && counter % 20 == 0) { // 10s
                            Definition.sendMessageToAllPlayers(server, (cleanType != null ? cleanType.getBeforeClean() : Definition.config.getCommon().getBeforeClean()), counter / 20);
                        } else if (counter / 20 <= 5 && counter % 20 == 0){
                            Definition.sendMessageToAllPlayers(server, (cleanType != null ? cleanType.getBeforeClean() : Definition.config.getCommon().getBeforeClean()), counter / 20);
                        }
                        --counter;
                    }
                }
            });
        }
    }
}
