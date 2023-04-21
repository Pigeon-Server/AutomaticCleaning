package com.automaticclean.command;

import com.automaticclean.Definition;
import com.automaticclean.handler.ConfigHandler;
import com.automaticclean.handler.TimerHandler;
import com.automaticclean.interfaces.CleanType;
import com.automaticclean.timer.TimerExecute;
import com.automaticclean.utils.CleanTypeEnum;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
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
                                        .then(Commands.literal("enable")
                                                .then(Commands.literal("true")
                                                        .executes(context -> Command.editStatus(context, Definition.config.getItemsClean(), true)))
                                                .then(Commands.literal("false")
                                                        .executes(context -> Command.editStatus(context, Definition.config.getItemsClean(), false))))
                                        .then(Commands.literal("clean")
                                                .then(Commands.literal("now")
                                                        .executes(Command::cleanItems))
                                                .then(Commands.literal("after")
                                                        .then(Commands.argument("time", IntegerArgumentType.integer(0, 60))
                                                                .executes(context -> Command.cleanItems(context, true)))))
                                        .then(Commands.literal("whitelist")
                                                .then(Commands.literal("enable")
                                                        .executes(context -> Command.switchWhitelist(context, Definition.config.getItemsClean())))
                                                .then(Commands.literal("add")
                                                        .executes(context -> Command.addWhitelist(context, Definition.config.getItemsClean()))
                                                        .then(Commands.argument("name", StringArgumentType.string())
                                                                .executes(Command::addWhitelist)))
                                                .then(Commands.literal("del")
                                                        .executes(context -> Command.delWhitelist(context, Definition.config.getItemsClean()))
                                                        .then(Commands.argument("name", StringArgumentType.string())
                                                                .executes(Command::delWhitelist))))
                                        .then(Commands.literal("blacklist")
                                                .then(Commands.literal("enable")
                                                        .executes(context -> Command.switchBlacklist(context, Definition.config.getItemsClean())))
                                                .then(Commands.literal("add")
                                                        .executes(context -> Command.addBlacklist(context, Definition.config.getItemsClean()))
                                                        .then(Commands.argument("name", StringArgumentType.string())
                                                                .executes(Command::addBlacklist)))
                                                .then(Commands.literal("del")
                                                        .executes(context -> Command.delBlacklist(context, Definition.config.getItemsClean()))
                                                        .then(Commands.argument("name", StringArgumentType.string())
                                                                .executes(Command::delBlacklist)))))
                                .then(Commands.literal("animals")
                                        .then(Commands.literal("enable")
                                                .then(Commands.literal("true")
                                                        .executes(context -> Command.editStatus(context, Definition.config.getAnimalClean(), true)))
                                                .then(Commands.literal("false")
                                                        .executes(context -> Command.editStatus(context, Definition.config.getAnimalClean(), false))))
                                        .then(Commands.literal("clean")
                                                .then(Commands.literal("now")
                                                        .executes(Command::cleanAnimals))
                                                .then(Commands.literal("after")
                                                        .then(Commands.argument("time", IntegerArgumentType.integer(0, 60))
                                                                .executes(context -> Command.cleanAnimals(context, true)))))
                                        .then(Commands.literal("whitelist")
                                                .then(Commands.literal("enable")
                                                        .executes(context -> Command.switchWhitelist(context, Definition.config.getAnimalClean())))
                                                .then(Commands.literal("add")
                                                        .executes(context -> Command.addWhitelist(context, Definition.config.getAnimalClean())))
                                                .then(Commands.literal("del")
                                                        .executes(context -> Command.delWhitelist(context, Definition.config.getAnimalClean()))))
                                        .then(Commands.literal("blacklist")
                                                .then(Commands.literal("enable")
                                                        .executes(context -> Command.switchBlacklist(context, Definition.config.getAnimalClean())))
                                                .then(Commands.literal("add")
                                                        .executes(context -> Command.addBlacklist(context, Definition.config.getAnimalClean())))
                                                .then(Commands.literal("del")
                                                        .executes(context -> Command.delBlacklist(context, Definition.config.getAnimalClean())))))
                                .then(Commands.literal("monsters")
                                        .then(Commands.literal("enable")
                                                .then(Commands.literal("true")
                                                        .executes(context -> Command.editStatus(context, Definition.config.getMonsterClean(), true)))
                                                .then(Commands.literal("false")
                                                        .executes(context -> Command.editStatus(context, Definition.config.getMonsterClean(), false))))
                                        .then(Commands.literal("clean")
                                                .then(Commands.literal("now")
                                                        .executes(Command::cleanMonsters))
                                                .then(Commands.literal("after")
                                                        .then(Commands.argument("time", IntegerArgumentType.integer(0, 60))
                                                                .executes(context -> Command.cleanMonsters(context, true)))))
                                        .then(Commands.literal("whitelist")
                                                .then(Commands.literal("enable")
                                                        .executes(context -> Command.switchWhitelist(context, Definition.config.getMonsterClean())))
                                                .then(Commands.literal("add")
                                                        .executes(context -> Command.addWhitelist(context, Definition.config.getMonsterClean())))
                                                .then(Commands.literal("del")
                                                        .executes(context -> Command.delWhitelist(context, Definition.config.getMonsterClean()))))
                                        .then(Commands.literal("blacklist")
                                                .then(Commands.literal("enable")
                                                        .executes(context -> Command.switchBlacklist(context, Definition.config.getMonsterClean())))
                                                .then(Commands.literal("add")
                                                        .executes(context -> Command.addBlacklist(context, Definition.config.getMonsterClean())))
                                                .then(Commands.literal("del")
                                                        .executes(context -> Command.delBlacklist(context, Definition.config.getMonsterClean())))))
                                .then(Commands.literal("setting")
                                        .then(Commands.literal("interval")
                                                .then(Commands.argument("time", IntegerArgumentType.integer(0))
                                                        .executes(context -> {
                                                            Definition.config.getCommon().setInterval(context.getArgument("time", Integer.class));
                                                            ConfigHandler.onChange();
                                                            return 1;
                                                        })))
                                        .then(Commands.literal("reminderBefore")
                                                .then(Commands.argument("time", IntegerArgumentType.integer(0))
                                                        .executes(context -> {
                                                            Definition.config.getCommon().setReminderBefore(context.getArgument("time", Integer.class));
                                                            ConfigHandler.onChange();
                                                            return 1;
                                                        })))
                                        .then(Commands.literal("countdown")
                                                .then(Commands.argument("time", IntegerArgumentType.integer(0))
                                                        .executes(context -> {
                                                            Definition.config.getCommon().setCountdown(context.getArgument("time", Integer.class));
                                                            ConfigHandler.onChange();
                                                            return 1;
                                                        }))))
                                .then(Commands.literal("reload")
                                        .executes(context -> {
                                            TimerExecute.INSTANCE.stopTimer();
                                            ConfigHandler.load();
                                            TimerExecute.INSTANCE.startTimer();
                                            PlayerEntity player = context.getSource().getPlayerOrException();
                                            player.sendMessage(new StringTextComponent("重载完成"), Util.NIL_UUID);
                                            return 1;
                                        }))
                                .then(Commands.literal("clean")
                                        .executes(context -> {
                                            TimerExecute.INSTANCE.timer(context.getSource().getServer());
                                            return 1;
                                        }))
                        ));
    }

    private static int editStatus(CommandContext<CommandSource> context, CleanType cleanType, boolean status) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        cleanType.setCleanEnable(status);
        player.sendMessage(new StringTextComponent(cleanType.getName() + ": " + (status ? "开启" : "关闭")), Util.NIL_UUID);
        ConfigHandler.onChange();
        return 1;
    }

    private static int addWhitelist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null) {
            cleanType.addWhitelist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经添加到白名单"), Util.NIL_UUID);
        } else {
            player.sendMessage(new StringTextComponent("添加到白名单失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int addWhitelist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        Definition.config.getItemsClean().addWhitelist(context.getArgument("name", String.class));
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("已经添加到白名单"), Util.NIL_UUID);
        return 1;
    }

    private static int delWhitelist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null) {
            cleanType.delWhitelist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经从白名单移除"), Util.NIL_UUID);
        } else {
            player.sendMessage(new StringTextComponent("从白名单移除失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int delWhitelist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        Definition.config.getItemsClean().delWhitelist(context.getArgument("name", String.class));
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("已经从白名单移除"), Util.NIL_UUID);
        return 1;
    }

    private static int addBlacklist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null) {
            cleanType.addBlacklist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经添加到黑名单"), Util.NIL_UUID);
        } else {
            player.sendMessage(new StringTextComponent("添加到黑名单失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int addBlacklist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        Definition.config.getItemsClean().addBlacklist(context.getArgument("name", String.class));
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("已经添加到黑名单"), Util.NIL_UUID);
        return 1;
    }

    private static int delBlacklist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null) {
            cleanType.delBlacklist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经从黑名单移除"), Util.NIL_UUID);
        } else {
            player.sendMessage(new StringTextComponent("从黑名单移除失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int delBlacklist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        Definition.config.getItemsClean().delBlacklist(context.getArgument("name", String.class));
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("已经从黑名单移除"), Util.NIL_UUID);
        return 1;
    }

    private static int cleanItems(CommandContext<CommandSource> context) {
        Command.clean(context, CleanTypeEnum.ITEMS);
        return 1;
    }

    private static int cleanItems(CommandContext<CommandSource> context, boolean ignored_) throws CommandSyntaxException {
        if (TimerHandler.getCounter() != -1) {
            context.getSource().getPlayerOrException().sendMessage(new StringTextComponent("即将进行清理，请稍后"), Util.NIL_UUID);
            return 1;
        }
        TimerHandler.beginCountDown(server -> Command.clean(context, CleanTypeEnum.ITEMS), context.getArgument("time", Integer.class), Definition.config.getItemsClean());
        return 1;
    }

    private static int cleanAnimals(CommandContext<CommandSource> context) {
        Command.clean(context, CleanTypeEnum.ANIMALS);
        return 1;
    }

    private static int cleanAnimals(CommandContext<CommandSource> context, boolean ignored_) throws CommandSyntaxException {
        ServerWorld world = context.getSource().getLevel();
        if (TimerHandler.getCounter() != -1) {
            context.getSource().getPlayerOrException().sendMessage(new StringTextComponent("即将进行清理，请稍后"), Util.NIL_UUID);
            return 1;
        }
        TimerHandler.beginCountDown(server -> Command.clean(context, CleanTypeEnum.ANIMALS), context.getArgument("time", Integer.class), Definition.config.getAnimalClean());
        return 1;
    }

    private static int cleanMonsters(CommandContext<CommandSource> context) {
        Command.clean(context, CleanTypeEnum.MONSTERS);
        return 1;
    }

    private static int cleanMonsters(CommandContext<CommandSource> context, boolean ignored_) throws CommandSyntaxException {
        ServerWorld world = context.getSource().getLevel();
        if (TimerHandler.getCounter() != -1) {
            context.getSource().getPlayerOrException().sendMessage(new StringTextComponent("即将进行清理，请稍后"), Util.NIL_UUID);
            return 1;
        }
        TimerHandler.beginCountDown(server -> Command.clean(context, CleanTypeEnum.MONSTERS), context.getArgument("time", Integer.class), Definition.config.getMonsterClean());
        return 1;
    }

    private static void clean(CommandContext<CommandSource> context, CleanTypeEnum type) {
        int killCount = 0;
        Iterable<ServerWorld> worlds = context.getSource().getServer().getAllLevels();
        switch (type) {
            case ITEMS:
                for (ServerWorld world : worlds) {
                    killCount += TimerExecute.INSTANCE.cleanItems(world);
                }
                Definition.sendMessageToAllPlayers(Definition.config.getItemsClean().getCleanComplete(), killCount);
                break;
            case MONSTERS:
                for (ServerWorld world : worlds) {
                    killCount += TimerExecute.INSTANCE.cleanMonsters(world);
                }
                Definition.sendMessageToAllPlayers(Definition.config.getMonsterClean().getCleanComplete(), killCount);
                break;
            case ANIMALS:
                for (ServerWorld world : worlds) {
                    killCount += TimerExecute.INSTANCE.cleanAnimals(world);
                }
                Definition.sendMessageToAllPlayers(Definition.config.getAnimalClean().getCleanComplete(), killCount);
                break;
        }
    }

    private static int switchWhitelist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        cleanType.setWhitelistMode(true);
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("当前过滤模式为：白名单"), Util.NIL_UUID);
        return 1;
    }

    private static int switchBlacklist(CommandContext<CommandSource> context, CleanType cleanType) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        cleanType.setWhitelistMode(false);
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("当前过滤模式为：黑名单"), Util.NIL_UUID);
        return 1;
    }
}