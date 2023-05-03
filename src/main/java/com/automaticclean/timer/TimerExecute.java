package com.automaticclean.timer;

import com.automaticclean.Definition;
import com.automaticclean.entity.CustomAnimalEntity;
import com.automaticclean.entity.CustomItemEntity;
import com.automaticclean.entity.CustomMonsterEntity;
import com.automaticclean.handler.TimerHandler;
import com.automaticclean.interfaces.CallableWithServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;

import java.util.*;
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
        Definition.sendMessageToAllPlayers(Definition.config.getCommon().getBeforeClean(), Definition.config.getCommon().getReminderBefore());
    }

    public void timer(MinecraftServer server) {
        int killItemCount = 0;
        int killMonsterCount = 0;
        int killAnimalCount = 0;
        Iterable<ServerLevel> worlds = server.getAllLevels();

        for (ServerLevel world : worlds) {
            synchronized (world) {
                if (Definition.config.getItemsClean().isCleanEnable()) {
                    killItemCount += this.cleanItems(world);
                }
                if (Definition.config.getMonsterClean().isCleanEnable()) {
                    killMonsterCount += this.cleanMonsters(world);
                }
                if (Definition.config.getAnimalClean().isCleanEnable()) {
                    killAnimalCount += this.cleanAnimals(world);
                }
            }
        }
        if (Definition.config.getItemsClean().isCleanEnable()) {
            Definition.sendMessageToAllPlayers(server, Definition.config.getItemsClean().getCleanComplete(), killItemCount);
        }
        if (Definition.config.getMonsterClean().isCleanEnable()) {
            Definition.sendMessageToAllPlayers(server, Definition.config.getMonsterClean().getCleanComplete(), killMonsterCount);
        }
        if (Definition.config.getAnimalClean().isCleanEnable()) {
            Definition.sendMessageToAllPlayers(server, Definition.config.getAnimalClean().getCleanComplete(), killAnimalCount);
        }
    }

    public int cleanItems(ServerLevel world) {
        return this.cleanEntity(world, entity -> entity instanceof ItemEntity, entity -> new CustomItemEntity((ItemEntity) entity).filtrate());
    }

    public int cleanMonsters(ServerLevel world) {
        return this.cleanEntity(world, entity -> entity instanceof Monster, entity -> new CustomMonsterEntity((Monster) entity).filtrate());
    }

    public int cleanAnimals(ServerLevel world) {
        return this.cleanEntity(world, entity -> entity instanceof Animal, entity -> new CustomAnimalEntity((Animal) entity).filtrate());
    }

    private int cleanEntity(ServerLevel world, Predicate<Entity> type, Predicate<Entity> additionalPredicate) {
        AtomicInteger amount = new AtomicInteger();

        final var test = world.getAllEntities();
        List<Entity> entities = new ArrayList<>();
        for (var e : test){
            entities.add(e);
        }
        for (var entity : entities){
            if (entity != null && entity.getCustomName() == null && type.test(entity) && additionalPredicate.test(entity)) {
                entity.remove(Entity.RemovalReason.KILLED);
                if (entity instanceof ItemEntity) {
                    amount.getAndAdd(((ItemEntity) entity).getItem().getCount());
                } else {
                    amount.getAndIncrement();
                }
            }
        }
        return amount.get();
    }

    @Override
    public void callback(MinecraftServer server) {
        this.timer(server);
    }
}