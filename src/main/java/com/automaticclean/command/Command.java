package com.automaticclean.command;

import com.automaticclean.Definition;
import com.automaticclean.handler.ConfigHandler;
import com.automaticclean.timer.TimerExecute;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;

public class Command {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("pigeon")
                        .then(Commands.literal("clear")
                                .then(Commands.literal("now").executes(Command::cleanAll))
                                .then(Commands.literal("items").executes(Command::cleanItems))
                                .then(Commands.literal("animals").executes(Command::cleanAnimals))
                                .then(Commands.literal("monsters").executes(Command::cleanMonsters))
                                .then(Commands.literal("whitelist")
                                        .then(Commands.literal("switch").executes(Command::switchWhitelist))
                                        .then(Commands.literal("add").executes(Command::addWhitelist))
                                        .then(Commands.literal("del").executes(Command::delWhitelist)))
                                .then(Commands.literal("blacklist")
                                        .then(Commands.literal("switch").executes(Command::switchBlacklist))
                                        .then(Commands.literal("add").executes(Command::addBlacklist))
                                        .then(Commands.literal("del").executes(Command::delBlacklist)))
                        )
                        .requires(context -> context.hasPermission(3))
        );
    }

    private static int addWhitelist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null){
            Definition.config.getItemsClean().addWhitelist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经添加到白名单"), Util.NIL_UUID);
        }
        else {
            player.sendMessage(new StringTextComponent("添加到白名单失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int delWhitelist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null){
            Definition.config.getItemsClean().delWhitelist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经从白名单移除"), Util.NIL_UUID);
        }
        else {
            player.sendMessage(new StringTextComponent("从白名单移除失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int addBlacklist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null){
            Definition.config.getItemsClean().addBlacklist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经添加到黑名单"), Util.NIL_UUID);
        }
        else {
            player.sendMessage(new StringTextComponent("添加到黑名单失败"), Util.NIL_UUID);
        }
        return 1;
    }

    private static int delBlacklist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null){
            Definition.config.getItemsClean().delBlacklist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经从黑名单移除"), Util.NIL_UUID);
        }
        else {
            player.sendMessage(new StringTextComponent("从黑名单移除失败"), Util.NIL_UUID);
        }
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
        Definition.sendMessageToAllPlayers(Definition.config.getItemsClean().getCleanComplete(), TimerExecute.INSTANCE.cleanItems(world));
        return 1;
    }

    private static int cleanAnimals(CommandContext<CommandSource> context) {
        return 1;
    }

    private static int cleanMonsters(CommandContext<CommandSource> context) {
        return 1;
    }

    private static int switchWhitelist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        Definition.config.getItemsClean().setWhitelistMode(true);
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("当前过滤模式为：白名单"), Util.NIL_UUID);
        return 1;
    }

    private static int switchBlacklist(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        Definition.config.getItemsClean().setWhitelistMode(false);
        ConfigHandler.onChange();
        player.sendMessage(new StringTextComponent("当前过滤模式为：黑名单"), Util.NIL_UUID);
        return 1;
    }
}