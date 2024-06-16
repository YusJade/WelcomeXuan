package org.yusjade.welcomexuan;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.yusjade.welcomexuan.commands.Set;
import org.yusjade.welcomexuan.commands.SubCommand;
import org.yusjade.welcomexuan.utils.WelcomeXuanUtils;

class MainCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    WelcomeXuan plugin = WelcomeXuanUtils.getInstance();
    String head = "[WelcomeXuan]: ";
    if (args.length == 1) {
      if (args[0].equals("reload")) {
        plugin.reloadConfig();
        sender.sendMessage(head + "重载配置文件 config.yml");
        WelcomeXuanUtils.printConfig();
      }
    }
    if (args.length == 4) {
      String[] subArgs = { args[2], args[3] };
      if (args[0].equals("config") && args[1].equals("set")) {
        SubCommand setCommand = new Set();
        setCommand.execute(subArgs);
      }
    }
//    if (args.length == 3 && args[0].equals("set")) {
//      if (args[1].equals("fadeIn")) {
//        plugin.setFadeIn(Integer.parseInt(args[2]));
//      }
//      if (args[1].equals("stay")) {
//        plugin.setStay(Integer.parseInt(args[2]));
//      }
//      if (args[1].equals("fadeOut")) {
//        plugin.setFadeOut(Integer.parseInt(args[2]));
//      }
//    }

//    sender.sendMessage(head + plugin.getTitle());
//    sender.sendMessage(head + plugin.getSubtitle());
//    sender.sendMessage(head + plugin.getFadeIn().toString());
//    sender.sendMessage(head + plugin.getStay().toString());
//    sender.sendMessage(head + plugin.getFadeOut().toString());
    return false;
  }
}