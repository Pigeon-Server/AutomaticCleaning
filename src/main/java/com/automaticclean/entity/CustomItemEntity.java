package com.automaticclean.entity;

import com.automaticclean.Definition;
import com.automaticclean.utils.WhitelistAndBlacklist;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;

public class CustomItemEntity {
    private final ResourceLocation registryName;

    public CustomItemEntity(ItemEntity entity) {
        this.registryName = Registry.ITEM.getKey(entity.getItem().getItem());
    }

    public boolean filtrate() {
        if (Definition.config.getItemsClean().isWhitelistMode()) {
            for (String s : Definition.config.getItemsClean().getWhitelist()) {
                return !WhitelistAndBlacklist.nameMatch(s, this.registryName);
            }
            return false;
        } else {
            for (String s : Definition.config.getItemsClean().getBlacklist()) {
                return WhitelistAndBlacklist.nameMatch(s, this.registryName);
            }
            return true;
        }
    }
}
