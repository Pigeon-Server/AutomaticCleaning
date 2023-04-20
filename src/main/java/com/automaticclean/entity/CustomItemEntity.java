package com.automaticclean.entity;

import com.automaticclean.Definition;
import com.automaticclean.utils.WhitelistAndBlacklist;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.ResourceLocation;

public class CustomItemEntity {
    private final ResourceLocation registryName;

    public CustomItemEntity(ItemEntity entity) {
        this.registryName = entity.getItem().getItem().getRegistryName();
    }

    public boolean filtrate() {
        if (Definition.config.getItemsClean().isWhitelistMode()) {
            for (String s : Definition.config.getItemsClean().getItemEntitiesWhitelist()) {
                return !WhitelistAndBlacklist.nameMatch(s, this.registryName);
            }
            return false;
        } else {
            for (String s : Definition.config.getItemsClean().getItemEntitiesBlacklist()) {
                return WhitelistAndBlacklist.nameMatch(s, this.registryName);
            }
            return true;
        }
    }
}
