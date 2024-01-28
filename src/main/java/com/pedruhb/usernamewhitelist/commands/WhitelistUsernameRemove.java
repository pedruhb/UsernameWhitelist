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
public class WhitelistUsernameRemove {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("whitelistusernameremove")
                .requires(source -> source.hasPermission(4))
                .then(Commands.argument("username", StringArgumentType.string())
                .executes(WhitelistUsernameRemove::removeWhitelist)));
    }

    private static int removeWhitelist(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String username = StringArgumentType.getString(context, "username");

        if (!Main.Users.contains(username)) {
            source.sendSuccess(new TextComponent("User " + username + " is not on the whitelist."), true);
            return Command.SINGLE_SUCCESS;
        }

        Main.Users.remove(username);
        Main.save();
        source.sendSuccess(new TextComponent("Used " + username + " is removed from whitelist."), true);

        return Command.SINGLE_SUCCESS;
    }

}
