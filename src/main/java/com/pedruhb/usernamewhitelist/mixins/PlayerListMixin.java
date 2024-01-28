package com.pedruhb.usernamewhitelist.mixins;

import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import com.pedruhb.usernamewhitelist.main.Main;

import java.net.SocketAddress;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraftforge.network.ConnectionData;
import net.minecraftforge.network.NetworkHooks;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ PlayerList.class })
public class PlayerListMixin {

    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(at = { @At("HEAD") }, method = { "canPlayerLogin" }, cancellable = true)
    public void canPlayerLogin(SocketAddress socket, GameProfile profile, CallbackInfoReturnable<Component> info) {

        Connection con = (Connection) Main.PROFILES.get(socket);
        ConnectionData connection = NetworkHooks.getConnectionData(con);

        if (connection != null) {

            if (!Main.Users.contains(profile.getName().toLowerCase())) {
                info.setReturnValue(new TextComponent("You're not whitelisted!"));
            }

        }

    }

}
