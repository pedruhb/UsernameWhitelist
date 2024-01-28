package com.pedruhb.usernamewhitelist.main;

import com.mojang.logging.LogUtils;
import com.pedruhb.usernamewhitelist.commands.WhitelistUsernameAdd;
import com.pedruhb.usernamewhitelist.commands.WhitelistUsernameList;
import com.pedruhb.usernamewhitelist.commands.WhitelistUsernameRemove;
import com.pedruhb.usernamewhitelist.main.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;

import net.minecraft.network.Connection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("usernamewhitelist")
@Mod.EventBusSubscriber(modid = "usernamewhitelist", bus = Mod.EventBusSubscriber.Bus.FORGE)

public class Main {

   private static final Logger LOGGER = LogUtils.getLogger();
   public static Map<SocketAddress, Connection> PROFILES = new HashMap<SocketAddress, Connection>();
   public static Set<String> Users = new HashSet<String>();

   public Main() {
      MinecraftForge.EVENT_BUS.register(this);
      Main.load();
   }

   @SubscribeEvent
   public static void registerCommands(RegisterCommandsEvent event) {
      WhitelistUsernameAdd.register(event.getDispatcher());
      WhitelistUsernameRemove.register(event.getDispatcher());
      WhitelistUsernameList.register(event.getDispatcher());
   }

   public static void load() {
      try {
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream("config/whitelistusername.data"));
         Users = (HashSet<String>) ois.readObject();
         ois.close();
      } catch (Exception e) {
         LOGGER.error("[UsernameWhitelist] Error getting data.", e);
      }
   }

   public static void save() {
      try {
         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("config/whitelistusername.data"));
         oos.writeObject(Users);
         oos.close();
      } catch (Exception e) {
         LOGGER.error("[UsernameWhitelist] Error saving data.", e);
      }
   }

}