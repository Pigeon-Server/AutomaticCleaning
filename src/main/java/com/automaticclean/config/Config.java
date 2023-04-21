package com.automaticclean.config;

import com.automaticclean.interfaces.CleanType;
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
        @Expose
        private String beforeClean = "<气人姬> 注意：将在 %d 秒后清理实体";

        public String getBeforeClean() {
            return beforeClean;
        }

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

    public static class ItemClean implements CleanType {
        @Expose
        private String name = "掉落物清理";
        @Expose
        private boolean cleanEnable = true;
        @Expose
        private boolean whitelistMode = false;
        @Expose
        private Set<String> whitelist = Sets.newHashSet("minecraft:diamond", "minecraft:emerald");
        @Expose
        private Set<String> blacklist = Sets.newHashSet();
        @Expose
        private String beforeClean = "<气人姬> 注意：将在 %d 秒后清理掉落物";
        @Expose
        private String cleanComplete = "<气人姬> 本次清理了 %d 个掉落物";

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isWhitelistMode() {
            return whitelistMode;
        }

        @Override
        public void setWhitelistMode(boolean whitelistMode) {
            this.whitelistMode = whitelistMode;
        }

        @Override
        public Set<String> getWhitelist() {
            return whitelist;
        }

        @Override
        public Set<String> getBlacklist() {
            return blacklist;
        }

        @Override
        public String getBeforeClean() {
            return beforeClean;
        }

        @Override
        public String getCleanComplete() {
            return cleanComplete;
        }

        @Override
        public void addWhitelist(String name) {
            this.whitelist.add(name);
        }

        @Override
        public void delWhitelist(String name) {
            this.whitelist.remove(name);
        }

        @Override
        public void addBlacklist(String name) {
            this.blacklist.add(name);
        }

        @Override
        public void delBlacklist(String name) {
            this.blacklist.remove(name);
        }

        @Override
        public boolean isCleanEnable() {
            return cleanEnable;
        }

        @Override
        public void setCleanEnable(boolean cleanEnable) {
            this.cleanEnable = cleanEnable;
        }
    }

    public static class MonsterClean implements CleanType {
        @Expose
        private String name = "怪物清理";
        @Expose
        private boolean cleanEnable = false;
        @Expose
        private boolean whitelistMode = false;
        @Expose
        private Set<String> whitelist = Sets.newHashSet();
        @Expose
        private Set<String> blacklist = Sets.newHashSet();
        @Expose
        private String beforeClean = "<气人姬> 注意：将在 %d 秒后清理敌对生物";
        @Expose
        private String cleanComplete = "<气人姬> 本次清理了 %d 个敌对生物";

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Set<String> getWhitelist() {
            return whitelist;
        }

        @Override
        public Set<String> getBlacklist() {
            return blacklist;
        }

        @Override
        public boolean isWhitelistMode() {
            return whitelistMode;
        }

        @Override
        public void setWhitelistMode(boolean whitelistMode) {
            this.whitelistMode = whitelistMode;
        }

        @Override
        public void addWhitelist(String name) {
            this.whitelist.add(name);
        }

        @Override
        public void delWhitelist(String name) {
            this.whitelist.remove(name);
        }

        @Override
        public void addBlacklist(String name) {
            this.blacklist.add(name);
        }

        @Override
        public void delBlacklist(String name) {
            this.blacklist.remove(name);
        }

        @Override
        public boolean isCleanEnable() {
            return cleanEnable;
        }

        @Override
        public void setCleanEnable(boolean cleanEnable) {
            this.cleanEnable = cleanEnable;
        }

        @Override
        public String getBeforeClean() {
            return beforeClean;
        }

        @Override
        public String getCleanComplete() {
            return cleanComplete;
        }

    }

    public static class AnimalClean implements CleanType {
        @Expose
        private String name = "动物清理";
        @Expose
        private boolean cleanEnable = false;
        @Expose
        private boolean whitelistMode = false;
        @Expose
        private Set<String> whitelist = Sets.newHashSet();
        @Expose
        private Set<String> blacklist = Sets.newHashSet();
        @Expose
        private String beforeClean = "<气人姬> 注意：将在 %d 秒后清理动物";
        @Expose
        private String cleanComplete = "<气人姬> 本次清理了 %d 个动物";

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Set<String> getWhitelist() {
            return whitelist;
        }

        @Override
        public Set<String> getBlacklist() {
            return blacklist;
        }

        @Override
        public boolean isWhitelistMode() {
            return whitelistMode;
        }

        @Override
        public void setWhitelistMode(boolean whitelistMode) {
            this.whitelistMode = whitelistMode;
        }

        @Override
        public void addWhitelist(String name) {
            this.whitelist.add(name);
        }

        @Override
        public void delWhitelist(String name) {
            this.whitelist.remove(name);
        }

        @Override
        public void addBlacklist(String name) {
            this.blacklist.add(name);
        }

        @Override
        public void delBlacklist(String name) {
            this.blacklist.remove(name);
        }

        @Override
        public boolean isCleanEnable() {
            return cleanEnable;
        }

        @Override
        public void setCleanEnable(boolean cleanEnable) {
            this.cleanEnable = cleanEnable;
        }

        @Override
        public String getBeforeClean() {
            return beforeClean;
        }

        @Override
        public String getCleanComplete() {
            return cleanComplete;
        }
    }
}