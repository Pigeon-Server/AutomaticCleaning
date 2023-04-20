package com.automaticclean.timer;

import com.automaticclean.Definition;
import com.automaticclean.entity.CustomAnimalEntity;
import com.automaticclean.entity.CustomItemEntity;
import com.automaticclean.entity.CustomMonsterEntity;
import com.automaticclean.handler.TimerHandler;
import com.automaticclean.interfaces.CallableWithServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class TimerExecute implements CallableWithServer {
    public static final TimerExecute INSTANCE = new TimerExecute();
    private Timer timer;
    private TimerTask currentTask;

    private TimerExecute() {
    }

    public void startTimer() {
        if (this.timer != null) {
            this.stopTimer();
        }
        this.timer = new Timer();
        this.resetTimer();
    }

    public void stopTimer() {
        this.timer.cancel();
        this.timer = null;
    }

    public void resetTimer() {
        if (this.currentTask != null) {
            this.currentTask.cancel();
        }
        this.timer.purge();
        this.currentTask = new TimerTask() {
            public void run() {
                if (Definition.SERVER != null) {
                    Definition.SERVER.execute(TimerExecute.this::reminderTimer);
                }

                TimerExecute.this.timer.schedule(new TimerTask() {
                    public void run() {
                        if (Definition.SERVER != null) {
                            Definition.SERVER.execute(TimerExecute.this::startTimerTick);
                        }
                    }
                }, (long) (Definition.config.getCommon().getReminderBefore() - Definition.config.getCommon().getCountdown()) * 1000L);
            }
        };
        this.timer.schedule(this.currentTask, 0L, (long) Definition.config.getCommon().getInterval() * 1000L);
    }

    public void startTimerTick() {
        TimerHandler.beginCountDown(this);
    }

    public void reminderTimer() {
        Definition.sendMessageToAllPlayers(Definition.config.getItemsClean().getBeforeCleanItem(), Definition.config.getCommon().getReminderBefore());
    }

    public void timer(MinecraftServer server) {
        int killItemCount = 0;
        Iterable<ServerWorld> worlds = server.getAllLevels();

        for (ServerWorld world : worlds) {
            synchronized (world) {
                killItemCount += this.cleanItems(world);
            }
        }

        Definition.sendMessageToAllPlayers(server, Definition.config.getItemsClean().getCleanItemComplete(), killItemCount);
    }

    public int cleanItems(ServerWorld world) {
        return this.cleanEntity(world, entity -> entity instanceof ItemEntity, entity -> new CustomItemEntity((ItemEntity) entity).filtrate());
    }

    public int cleanMonsters(ServerWorld world){
        return this.cleanEntity(world, entity -> entity instanceof MonsterEntity, entity -> new CustomMonsterEntity((MonsterEntity) entity).filtrate());
    }

    public int cleanAnimals(ServerWorld world){
        return this.cleanEntity(world, entity -> entity instanceof AnimalEntity, entity -> new CustomAnimalEntity((AnimalEntity) entity).filtrate());
    }

    private int cleanEntity(ServerWorld world, Predicate<Entity> type, Predicate<Entity> additionalPredicate) {
        AtomicInteger amount = new AtomicInteger();

        StreamSupport.stream(world.getAllEntities().spliterator(), false)
                .filter(Objects::nonNull)
                .filter(entity -> !entity.hasCustomName())
                .filter(type)
                .filter(additionalPredicate)
                .forEach(
                        entity -> {
                            entity.remove();
                            if (entity instanceof ItemEntity) {
                                amount.getAndAdd(((ItemEntity) entity).getItem().getCount());
                            } else {
                                amount.getAndIncrement();
                            }
                        }
                );

        return amount.get();
    }

    @Override
    public void callback(MinecraftServer server) {
        this.timer(server);
    }
}