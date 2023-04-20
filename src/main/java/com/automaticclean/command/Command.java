package com.automaticclean.command;

import com.automaticclean.Definition;
import com.automaticclean.handler.ConfigHandler;
import com.automaticclean.handler.TimerHandler;
import com.automaticclean.timer.TimerExecute;
import com.automaticclean.utils.CleanType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class Command {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("pigeon")
                        .requires(context -> context.hasPermission(3))
                        .then(Commands.literal("cleaning")
                                .then(Commands.literal("items")
                                        .then(Commands.literal("set")
                                                .then(Commands.literal("true")
                                                        .executes(context -> Command.editStatus(context, CleanType.ITEMS, true)))
                                                .then(Commands.literal("false")
                                                        .executes(context -> Command.editStatus(context, CleanType.ITEMS, false))))
                                        .then(Commands.literal("clean")
                                                .then(Commands.literal("now")
                                                        .executes(Command::cleanItems))
                                                .then(Commands.literal("after")
                                                        .then(Commands.argument("time", IntegerArgumentType.integer(0, 60))
                                                                .executes(context -> Command.cleanItems(context, true)))))
                                        .then(Commands.literal("whitelist")
                                                .then(Commands.literal("enable")
                                                        .executes(context -> Command.switchWhitelist(context, CleanType.ITEMS)))
                                                .then(Commands.literal("add")
                                                        .executes(context -> Command.addWhitelist(context, CleanType.ITEMS))
                                                        .then(Commands.argument("name", StringArgumentType.string())
                                                                .executes(context -> Command.addWhitelist(context, CleanType.ITEMS, true))))
                                                .then(Commands.literal("del")
                                                        .executes(context -> Command.delWhitelist(context, CleanType.ITEMS))
                                                        .then(Commands.argument("name", StringArgumentType.string())
                                                                .executes(context -> Command.delWhitelist(context, CleanType.ITEMS, true)))))
                                        .then(Commands.literal("blacklist")
                                                .then(Commands.literal("enable")
                                                        .executes(context -> Command.switchBlacklist(context, CleanType.ITEMS)))
                                                .then(Commands.literal("add")
                                                        .executes(context -> Command.addBlacklist(context, CleanType.ITEMS))
                                                        .then(Commands.argument("name", StringArgumentType.string())
                                                                .executes(context -> Command.addBlacklist(context, CleanType.ITEMS, true))))
                                                .then(Commands.literal("del")
                                                        .executes(context -> Command.delBlacklist(context, CleanType.ITEMS))
                                                        .then(Commands.argument("name", StringArgumentType.string())
                                                                .executes(context -> Command.delBlacklist(context, CleanType.ITEMS, true))))))
                                .then(Commands.literal("animals"))
                                .then(Commands.literal("monsters"))
                                .then(Commands.literal("setting"))
                        ));
    }

    private static int editStatus(CommandContext<CommandSource> context, CleanType cleanType, boolean status) {
        switch (cleanType) {
            case ITEMS:
                Definition.config.getItemsClean().setCleanItem(status);
                break;
            case ANIMALS:
                Definition.config.getAnimalClean().setCleanAnimal(status);
                break;
            case MONSTERS:
                Definition.config.getMonsterClean().setCleanMonster(status);
        }
        ConfigHandler.onChange();
        return 1;
    }

    private static int addWhitelist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null) {
            switch (cleanType) {
                case ITEMS:
                    Definition.config.getItemsClean().addWhitelist(itemStack.getItem().getRegistryName().toString());
                    break;
                case ANIMALS:
                    Definition.config.getAnimalClean().addWhitelist(itemStack.getItem().getRegistryName().toString());
                    break;
                case MONSTERS:
                    Definition.config.getMonsterClean().addWhitelist(itemStack.getItem().getRegistryName().toString());
            }
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经添加到白名单"), Util.NIL_UUID);
        } else {
            player.sendMessage(new StringTextComponent("添加到白名单失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int addWhitelist(CommandContext<CommandSource> context, CleanType cleanType, boolean ignored_) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        switch (cleanType) {
            case ITEMS:
                Definition.config.getItemsClean().addWhitelist(context.getArgument("name", String.class));
                break;
            case ANIMALS:
                Definition.config.getAnimalClean().addWhitelist(context.getArgument("name", String.class));
                break;
            case MONSTERS:
                Definition.config.getMonsterClean().addWhitelist(context.getArgument("name", String.class));
        }
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("已经添加到白名单"), Util.NIL_UUID);
        return 1;
    }

    private static int delWhitelist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null) {
            switch (cleanType) {
                case ITEMS:
                    Definition.config.getItemsClean().delWhitelist(itemStack.getItem().getRegistryName().toString());
                    break;
                case ANIMALS:
                    Definition.config.getAnimalClean().delWhitelist(itemStack.getItem().getRegistryName().toString());
                    break;
                case MONSTERS:
                    Definition.config.getMonsterClean().delWhitelist(itemStack.getItem().getRegistryName().toString());
            }
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经从白名单移除"), Util.NIL_UUID);
        } else {
            player.sendMessage(new StringTextComponent("从白名单移除失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int delWhitelist(CommandContext<CommandSource> context, CleanType cleanType, boolean ignored_) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        switch (cleanType) {
            case ITEMS:
                Definition.config.getItemsClean().delWhitelist(context.getArgument("name", String.class));
                break;
            case ANIMALS:
                Definition.config.getAnimalClean().delWhitelist(context.getArgument("name", String.class));
                break;
            case MONSTERS:
                Definition.config.getMonsterClean().delWhitelist(context.getArgument("name", String.class));
        }
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("已经从白名单移除"), Util.NIL_UUID);
        return 1;
    }

    private static int addBlacklist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null) {
            switch (cleanType) {
                case ITEMS:
                    Definition.config.getItemsClean().addBlacklist(itemStack.getItem().getRegistryName().toString());
                    break;
                case ANIMALS:
                    Definition.config.getAnimalClean().addBlacklist(itemStack.getItem().getRegistryName().toString());
                    break;
                case MONSTERS:
                    Definition.config.getMonsterClean().addBlacklist(itemStack.getItem().getRegistryName().toString());
            }
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经添加到黑名单"), Util.NIL_UUID);
        } else {
            player.sendMessage(new StringTextComponent("添加到黑名单失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int addBlacklist(CommandContext<CommandSource> context, CleanType cleanType, boolean ignored_) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        switch (cleanType) {
            case ITEMS:
                Definition.config.getItemsClean().addBlacklist(context.getArgument("name", String.class));
                break;
            case ANIMALS:
                Definition.config.getAnimalClean().addBlacklist(context.getArgument("name", String.class));
                break;
            case MONSTERS:
                Definition.config.getMonsterClean().addBlacklist(context.getArgument("name", String.class));
        }
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("已经添加到黑名单"), Util.NIL_UUID);
        return 1;
    }

    private static int delBlacklist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null) {
            switch (cleanType) {
                case ITEMS:
                    Definition.config.getItemsClean().delBlacklist(itemStack.getItem().getRegistryName().toString());
                    break;
                case ANIMALS:
                    Definition.config.getAnimalClean().delBlacklist(itemStack.getItem().getRegistryName().toString());
                    break;
                case MONSTERS:
                    Definition.config.getMonsterClean().delBlacklist(itemStack.getItem().getRegistryName().toString());
            }
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经从黑名单移除"), Util.NIL_UUID);
        } else {
            player.sendMessage(new StringTextComponent("从黑名单移除失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int delBlacklist(CommandContext<CommandSource> context, CleanType cleanType, boolean ignored_) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        switch (cleanType) {
            case ITEMS:
                Definition.config.getItemsClean().delBlacklist(context.getArgument("name", String.class));
                break;
            case ANIMALS:
                Definition.config.getAnimalClean().delBlacklist(context.getArgument("name", String.class));
                break;
            case MONSTERS:
                Definition.config.getMonsterClean().delBlacklist(context.getArgument("name", String.class));
        }
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("已经从黑名单移除"), Util.NIL_UUID);
        return 1;
    }

    private static int cleanAll(CommandContext<CommandSource> context) {
        Command.cleanItems(context);
        Command.cleanAnimals(context);
        Command.cleanMonsters(context);
        return 1;
    }

    private static int cleanItems(CommandContext<CommandSource> context) {
        ServerWorld world = context.getSource().getLevel();
        Definition.sendMessageToAllPlayers(Definition.config.getItemsClean().getCleanItemComplete(), TimerExecute.INSTANCE.cleanItems(world));
        return 1;
    }

    private static int cleanItems(CommandContext<CommandSource> context, boolean ignored_) throws CommandSyntaxException {
        ServerWorld world = context.getSource().getLevel();
        if (TimerHandler.getCounter() != -1) {
            context.getSource().getPlayerOrException().sendMessage(new StringTextComponent("即将进行定时清理，请稍后"), Util.NIL_UUID);
            return 1;
        }
        TimerHandler.beginCountDown(server -> Definition.sendMessageToAllPlayers(Definition.config.getItemsClean().getCleanItemComplete(), TimerExecute.INSTANCE.cleanItems(world)), context.getArgument("time", Integer.class));
        return 1;
    }

    private static int cleanAnimals(CommandContext<CommandSource> context) {
        ServerWorld world = context.getSource().getLevel();
        Definition.sendMessageToAllPlayers(Definition.config.getAnimalClean().getCleanAnimalsComplete(), TimerExecute.INSTANCE.cleanAnimals(world));
        return 1;
    }

    private static int cleanMonsters(CommandContext<CommandSource> context) {
        ServerWorld world = context.getSource().getLevel();
        Definition.sendMessageToAllPlayers(Definition.config.getMonsterClean().getCleanMonsterComplete(), TimerExecute.INSTANCE.cleanMonsters(world));
        return 1;
    }

    private static int switchWhitelist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        switch (cleanType) {
            case ITEMS:
                Definition.config.getItemsClean().setWhitelistMode(true);
                break;
            case ANIMALS:
                Definition.config.getAnimalClean().setWhitelistMode(true);
                break;
            case MONSTERS:
                Definition.config.getMonsterClean().setWhitelistMode(true);
        }
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("当前过滤模式为：白名单"), Util.NIL_UUID);
        return 1;
    }

    private static int switchBlacklist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        switch (cleanType) {
            case ITEMS:
                Definition.config.getItemsClean().setWhitelistMode(false);
                break;
            case ANIMALS:
                Definition.config.getAnimalClean().setWhitelistMode(false);
                break;
            case MONSTERS:
                Definition.config.getMonsterClean().setWhitelistMode(false);
        }
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("当前过滤模式为：黑名单"), Util.NIL_UUID);
        return 1;
    }
}