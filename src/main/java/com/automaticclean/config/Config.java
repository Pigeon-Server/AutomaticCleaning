package com.automaticclean.config;

import com.google.common.collect.Sets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class Config {
    @SerializedName("items_clean")
    private ItemClean itemsClean = new ItemClean();
    @SerializedName("monster_clean")
    private MonsterClean monsterClean = new MonsterClean();
    @SerializedName("animal_clean")
    private AnimalClean animalClean = new AnimalClean();
    @SerializedName("common")
    private Common common = new Common();

    public AnimalClean getAnimalClean() {
        return this.animalClean;
    }

    public MonsterClean getMonsterClean() {
        return this.monsterClean;
    }

    public ItemClean getItemsClean() {
        return this.itemsClean;
    }

    public Common getCommon() {
        return this.common;
    }

    public static class Common {
        @Expose
        private int interval = 1800;
        @Expose
        private int reminderBefore = 15;
        @Expose
        private int countdown = 5;

        public int getCountdown() {
            return countdown;
        }

        public void setCountdown(int countdown) {
            this.countdown = countdown;
        }

        public int getReminderBefore() {
            return reminderBefore;
        }

        public void setReminderBefore(int reminderBefore) {
            this.reminderBefore = reminderBefore;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }
    }

    public static class ItemClean {
        @Expose
        private boolean cleanItem = true;
        @Expose
        private boolean whitelistMode = false;
        @Expose
        private Set<String> itemEntitiesWhitelist = Sets.newHashSet("minecraft:diamond", "minecraft:emerald");
        @Expose
        private Set<String> itemEntitiesBlacklist = Sets.newHashSet();
        @Expose
        private String beforeCleanItem = "<气人姬> 注意：将在 %d 秒后清理掉落物";
        @Expose
        private String cleanItemComplete = "<气人姬> 本次清理了 %d 个掉落物";

        public boolean isWhitelistMode() {
            return whitelistMode;
        }

        public void setWhitelistMode(boolean whitelistMode) {
            this.whitelistMode = whitelistMode;
        }

        public Set<String> getItemEntitiesWhitelist() {
            return itemEntitiesWhitelist;
        }

        public Set<String> getItemEntitiesBlacklist() {
            return itemEntitiesBlacklist;
        }

        public String getBeforeCleanItem() {
            return beforeCleanItem;
        }

        public String getCleanItemComplete() {
            return cleanItemComplete;
        }

        public void addWhitelist(String name) {
            this.itemEntitiesWhitelist.add(name);
        }

        public void delWhitelist(String name) {
            this.itemEntitiesWhitelist.remove(name);
        }

        public void addBlacklist(String name) {
            this.itemEntitiesBlacklist.add(name);
        }

        public void delBlacklist(String name) {
            this.itemEntitiesBlacklist.remove(name);
        }

        public boolean isCleanItem() {
            return cleanItem;
        }

        public void setCleanItem(boolean cleanItem) {
            this.cleanItem = cleanItem;
        }
    }

    public static class MonsterClean {
        @Expose
        private boolean cleanMonster = false;
        @Expose
        private boolean whitelistMode = false;
        @Expose
        private Set<String> monsterEntitiesWhitelist = Sets.newHashSet("minecraft:diamond", "minecraft:emerald");
        @Expose
        private Set<String> monsterEntitiesBlacklist = Sets.newHashSet();
        @Expose
        private String beforeCleanMonster = "<气人姬> 注意：将在 %d 秒后清理敌对生物";
        @Expose
        private String cleanMonsterComplete = "<气人姬> 本次清理了 %d 个敌对生物";

        public Set<String> getMonsterEntitiesWhitelist() {
            return monsterEntitiesWhitelist;
        }

        public Set<String> getMonsterEntitiesBlacklist() {
            return monsterEntitiesBlacklist;
        }

        public boolean isWhitelistMode() {
            return whitelistMode;
        }

        public void setWhitelistMode(boolean whitelistMode) {
            this.whitelistMode = whitelistMode;
        }

        public void addWhitelist(String name) {
            this.monsterEntitiesWhitelist.add(name);
        }

        public void delWhitelist(String name) {
            this.monsterEntitiesWhitelist.remove(name);
        }

        public void addBlacklist(String name) {
            this.monsterEntitiesBlacklist.add(name);
        }

        public void delBlacklist(String name) {
            this.monsterEntitiesBlacklist.remove(name);
        }
        public boolean isCleanMonster() {
            return cleanMonster;
        }

        public void setCleanMonster(boolean cleanMonster) {
            this.cleanMonster = cleanMonster;
        }

        public String getBeforeCleanMonster() {
            return beforeCleanMonster;
        }

        public String getCleanMonsterComplete() {
            return cleanMonsterComplete;
        }
    }

    public static class AnimalClean {
        @Expose
        private boolean cleanAnimal = false;
        @Expose
        private boolean whitelistMode = false;
        @Expose
        private Set<String> animalEntitiesWhitelist = Sets.newHashSet("minecraft:diamond", "minecraft:emerald");
        @Expose
        private Set<String> animalEntitiesBlacklist = Sets.newHashSet();
        @Expose
        private String beforeCleanAnimals = "<气人姬> 注意：将在 %d 秒后清理动物";
        @Expose
        private String cleanAnimalsComplete = "<气人姬> 本次清理了 %d 个动物";

        public Set<String> getAnimalEntitiesWhitelist() {
            return animalEntitiesWhitelist;
        }

        public Set<String> getAnimalEntitiesBlacklist() {
            return animalEntitiesBlacklist;
        }

        public boolean isWhitelistMode() {
            return whitelistMode;
        }

        public void setWhitelistMode(boolean whitelistMode) {
            this.whitelistMode = whitelistMode;
        }

        public void addWhitelist(String name) {
            this.animalEntitiesWhitelist.add(name);
        }

        public void delWhitelist(String name) {
            this.animalEntitiesWhitelist.remove(name);
        }

        public void addBlacklist(String name) {
            this.animalEntitiesBlacklist.add(name);
        }

        public void delBlacklist(String name) {
            this.animalEntitiesBlacklist.remove(name);
        }
        public boolean isCleanAnimal() {
            return cleanAnimal;
        }

        public void setCleanAnimal(boolean cleanAnimal) {
            this.cleanAnimal = cleanAnimal;
        }

        public String getBeforeCleanAnimals() {
            return beforeCleanAnimals;
        }

        public String getCleanAnimalsComplete() {
            return cleanAnimalsComplete;
        }
    }
}