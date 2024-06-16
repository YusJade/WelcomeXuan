package org.yusjade.welcomexuan.commands;

import org.bukkit.configuration.file.YamlConfiguration;
import org.yusjade.welcomexuan.WelcomeXuan;
import org.yusjade.welcomexuan.utils.WelcomeXuanUtils;

public class Set extends SubCommand {
  static WelcomeXuan welcomeXuan;
  static YamlConfiguration config;
  static {
    welcomeXuan = WelcomeXuanUtils.getInstance();
    config = WelcomeXuanUtils.getConfigFile();
  }
  @Override
  public void execute(String[] args) {
    if (args.length != 2) {
      return;
    }
    String key = args[0];
    String val = args[1];
    if (!config.contains(key)) {
      return;
    }
    if (config.isInt(key)) {
      config.set(key, Integer.parseInt(val));
    }
    if (config.isString(key)) {
      config.set(key, val);
    }
  }
}
