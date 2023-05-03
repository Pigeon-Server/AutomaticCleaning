package com.automaticclean.entity;

import com.automaticclean.Definition;
import com.automaticclean.utils.WhitelistAndBlacklist;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;

public class CustomAnimalEntity {
    private final ResourceLocation registryName;

    public CustomAnimalEntity(Animal entity) {
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
