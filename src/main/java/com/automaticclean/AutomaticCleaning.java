package com.automaticclean;

import com.automaticclean.utils.FileUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import static com.automaticclean.Definition.CONFIG_FOLDER;

@Mod("automatic_cleaning")
public class AutomaticCleaning
{
    public AutomaticCleaning() {
        CONFIG_FOLDER = FMLPaths.GAMEDIR.get().resolve("config/pigeon-server");
        FileUtils.checkFolder(CONFIG_FOLDER);
    }
}
