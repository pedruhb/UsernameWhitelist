package com.pedruhb.usernamewhitelist.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.pedruhb.usernamewhitelist.main.Main;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

@SuppressWarnings("SameReturnValue")
public class WhitelistUsernameAdd {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("whitelistusernameadd")
                .requires(source -> source.hasPermission(4))
                .then(Commands.argument("username", StringArgumentType.string())
                .executes(WhitelistUsernameAdd::addWhitelist)));
    }

    private static int addWhitelist(CommandContext<CommandSourceStack> context) {

        CommandSourceStack source = context.getSource();
        String username = StringArgumentType.getString(context, "username");

        if(Main.Users.contains(username)){
            source.sendSuccess(new TextComponent("User "+username+" is already on whitelist."), true);
            return Command.SINGLE_SUCCESS;
        }
        
        Main.Users.add(username.toLowerCase());
        Main.save();
        source.sendSuccess(new TextComponent("Added "+username+" to whitelist."), true);
        return Command.SINGLE_SUCCESS;

    }

}