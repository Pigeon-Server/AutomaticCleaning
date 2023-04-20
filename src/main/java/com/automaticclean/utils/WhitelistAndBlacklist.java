package com.automaticclean.utils;

import net.minecraft.util.ResourceLocation;

public class WhitelistAndBlacklist {
    public static boolean nameMatch(String s, ResourceLocation registryName) {
        int index;
        if (s.equals(registryName.toString())) {
            return true;
        } else if ((index = s.indexOf('*')) != -1) {
            s = s.substring(0, index - 1);
            return registryName.getNamespace().equals(s);
        }
        return false;
    }
}
