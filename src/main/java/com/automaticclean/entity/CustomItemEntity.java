package com.automaticclean.entity;

import com.automaticclean.Definition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.ResourceLocation;
public class CustomItemEntity {
    private final ItemEntity entity;
    private final ResourceLocation registryName;

    public CustomItemEntity(ItemEntity entity) {
        this.entity = entity;
        this.registryName = this.entity.getItem().getItem().getRegistryName();
    }

    /**
     * @return 白名单或者黑名单
     */
    public boolean filtrate() {
        if (Definition.config.getItemsClean().isWhitelistMode()) {
            // Whitelist
            for (String s : Definition.config.getItemsClean().getItemEntitiesWhitelist()) {
                if (itemMatch(s, this.registryName)) return true;
            }
            return false;
        } else {
            // Blacklist
            for (String s : Definition.config.getItemsClean().getItemEntitiesBlacklist()) {
                if (itemMatch(s, this.registryName)) return false;
            }
            return true;
        }
    }

    static boolean itemMatch(String s, ResourceLocation registryName) {
        int index;
        if (s.equals(registryName.toString())) {
            return true;
        } else if ((index = s.indexOf('*')) != -1) {
            s = s.substring(0, index - 1);
            return registryName.getNamespace().equals(s);
        }
        return false;
    }

    public Entity getEntity() {
        return entity;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }
}
