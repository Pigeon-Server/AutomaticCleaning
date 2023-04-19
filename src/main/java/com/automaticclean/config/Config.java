package com.automaticclean.config;

import com.google.common.collect.Sets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class Config {
    @SerializedName("items_clean")
    private ItemClean itemsClean = new ItemClean();
    @SerializedName("common")
    private Common common = new Common();

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
        private boolean whitelistMode = false;
        @Expose
        private Set<String> itemEntitiesWhitelist = Sets.newHashSet("minecraft:diamond", "minecraft:emerald");
        @Expose
        private Set<String> itemEntitiesBlacklist = Sets.newHashSet();
        @Expose
        private String beforeClean = "<气人姬> 注意：将在 %d 秒后清理掉落物";
        @Expose
        private String cleanComplete = "<气人姬> 本次清理了 %d 个掉落物";

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

        public String getBeforeClean() {
            return beforeClean;
        }

        public void setBeforeClean(String beforeClean) {
            this.beforeClean = beforeClean;
        }

        public String getCleanComplete() {
            return cleanComplete;
        }

        public void setCleanComplete(String cleanComplete) {
            this.cleanComplete = cleanComplete;
        }

        public void addWhitelist(String name){
            this.itemEntitiesWhitelist.add(name);
        }

        public void delWhitelist(String name){
            this.itemEntitiesWhitelist.remove(name);
        }

        public void addBlacklist(String name){
            this.itemEntitiesBlacklist.add(name);
        }

        public void delBlacklist(String name){
            this.itemEntitiesBlacklist.remove(name);
        }
    }
}