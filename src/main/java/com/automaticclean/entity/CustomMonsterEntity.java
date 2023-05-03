package com.automaticclean.entity;

import com.automaticclean.Definition;
import com.automaticclean.utils.WhitelistAndBlacklist;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;

public class CustomMonsterEntity {
    private final ResourceLocation registryName;

    public CustomMonsterEntity(Monster entity) {
        this.registryName = EntityType.getKey(entity.getType());
    }

    public boolean filtrate() {
        if (Definition.config.getMonsterClean().isWhitelistMode()) {
            for (String s : Definition.config.getMonsterClean().getWhitelist()) {
                return WhitelistAndBlacklist.nameMatch(s, this.registryName);
            }
            return false;
        } else {
            for (String s : Definition.config.getMonsterClean().getBlacklist()) {
                return !WhitelistAndBlacklist.nameMatch(s, this.registryName);
            }
            return true;
        }
    }
}
