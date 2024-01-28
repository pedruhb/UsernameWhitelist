package com.pedruhb.usernamewhitelist.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.pedruhb.usernamewhitelist.main.Main;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

@SuppressWarnings("SameReturnValue")
public class WhitelistUsernameList {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("whitelistusernamelist")
                .requires(source -> source.hasPermission(4))
                .executes(WhitelistUsernameList::listWhitelist));
    }

    private static int listWhitelist(CommandContext<CommandSourceStack> context) {

        CommandSourceStack source = context.getSource();
        source.sendSuccess(new TextComponent("---------------------------"), true);
        source.sendSuccess(new TextComponent("-------- Whitelist --------"), true);
        source.sendSuccess(new TextComponent("-------- Size: " + Main.Users.size() + " --------"), true);

        for (String user : Main.Users) {
            source.sendSuccess(new TextComponent("User: " + user), true);
        }

        source.sendSuccess(new TextComponent("---------------------------"), true);

        return Command.SINGLE_SUCCESS;
    }

}