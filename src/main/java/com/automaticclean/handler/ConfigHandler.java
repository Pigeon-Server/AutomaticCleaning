package com.automaticclean.handler;


import com.automaticclean.Definition;
import com.automaticclean.config.Config;
import com.automaticclean.utils.JsonFormat;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class ConfigHandler {
    private static final Gson GSON = new Gson();

    public static Config load() {
        Config config = new Config();

        if (!Definition.CONFIG_FOLDER.toFile().isDirectory()) {
            try {
                Files.createDirectories(Definition.CONFIG_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path configPath = Definition.CONFIG_FOLDER.resolve(Definition.MOD_ID + ".json");
        if (configPath.toFile().isFile()) {
            try {
                config = GSON.fromJson(FileUtils.readFileToString(configPath.toFile(), StandardCharsets.UTF_8),
                        Config.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileUtils.write(configPath.toFile(), JsonFormat.formatJson(GSON.toJson(config)), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return config;
    }

    public static void save(Config config) {
        if (!Definition.CONFIG_FOLDER.toFile().isDirectory()) {
            try {
                Files.createDirectories(Definition.CONFIG_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path configPath = Definition.CONFIG_FOLDER.resolve(Definition.MOD_ID + ".json");
        try {
            FileUtils.write(configPath.toFile(), JsonFormat.formatJson(GSON.toJson(config)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void onChange(){
        ConfigHandler.save(Definition.config);
        Definition.config = ConfigHandler.load();
    }
}