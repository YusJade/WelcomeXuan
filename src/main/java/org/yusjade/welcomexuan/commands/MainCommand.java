package org.yusjade.welcomexuan;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

class MainCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    WelcomeXuan plugin = (WelcomeXuan) WelcomeXuan.getProvidingPlugin(WelcomeXuan.class);
    String head = "[WelcomeXuan]: ";
    if (args.length == 1) {
      if (args[0].equals("reload")) {
        plugin.reloadConfig();
        sender.sendMessage(head + "重载配置文件 config.yml");
      }
    }
    if (args.length == 3 && args[0].equals("set")) {
      if (args[1].equals("fadeIn")) {
        plugin.setFadeIn(Integer.parseInt(args[2]));
      }
      if (args[1].equals("stay")) {
        plugin.setFadeIn(Integer.parseInt(args[2]));
      }
      if (args[1].equals("fadeOut")) {
        plugin.setFadeIn(Integer.parseInt(args[2]));
      }
    }
    sender.sendMessage(head + plugin.getConfig().getString("title.title"));
    sender.sendMessage(head + plugin.getConfig().getString("title.subtitle"));
    sender.sendMessage(head + plugin.getConfig().getInt("animation.fadeIn"));
    sender.sendMessage(head + plugin.getConfig().getInt("animation.stay"));
    sender.sendMessage(head + plugin.getConfig().getInt("animation.fadeOut"));
    return false;
  }
}