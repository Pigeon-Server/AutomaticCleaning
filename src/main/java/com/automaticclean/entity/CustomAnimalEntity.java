package com.automaticclean.entity;

import com.automaticclean.Definition;
import com.automaticclean.utils.WhitelistAndBlacklist;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;

public class CustomAnimalEntity {
    private final ResourceLocation registryName;

    public CustomAnimalEntity(AnimalEntity entity) {
        this.registryName = EntityType.getKey(entity.getType());
    }

    public boolean filtrate() {
        if (Definition.config.getAnimalClean().isWhitelistMode()) {
            for (String s : Definition.config.getAnimalClean().getWhitelist()) {
                return WhitelistAndBlacklist.nameMatch(s, this.registryName);
            }
            return false;
        } else {
            for (String s : Definition.config.getAnimalClean().getBlacklist()) {
                return !WhitelistAndBlacklist.nameMatch(s, this.registryName);
            }
            return true;
        }
    }
}
