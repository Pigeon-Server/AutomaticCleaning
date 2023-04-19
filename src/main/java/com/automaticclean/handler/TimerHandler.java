package com.automaticclean.handler;

import com.automaticclean.Definition;
import com.automaticclean.timer.TimerExecute;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TimerHandler {
    private static int counter = -1;

    public static void beginCountDown() {
        counter = Definition.config.getCommon().getCountdown() * 20;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Optional.ofNullable(Definition.SERVER).ifPresent(server -> {
                if (counter >= 0) {
                    if (counter == 0) {
                        TimerExecute.INSTANCE.timer(server);
                        counter = -1;
                    } else {
                        if (counter % 20 == 0) {
                            Definition.sendMessageToAllPlayers(Definition.config.getItemsClean().getBeforeClean(), counter / 20);
                        }
                        --counter;
                    }
                }
            });
        }
    }
}
