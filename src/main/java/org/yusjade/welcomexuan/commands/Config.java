package org.yusjade.welcomexuan;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

class Config implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Plugin plugin = WelcomeXuan.getProvidingPlugin(WelcomeXuan.class);
    String head = "[WelcomeXuan]: ";
    sender.sendMessage(head + plugin.getConfig().getString("title.title"));
    sender.sendMessage(head + plugin.getConfig().getString("title.subtitle"));
    sender.sendMessage(head + plugin.getConfig().getInt("animation.fadeIn"));
    sender.sendMessage(head + plugin.getConfig().getInt("animation.stay"));
    sender.sendMessage(head + plugin.getConfig().getInt("animation.fadeOut"));
    return false;
  }
}