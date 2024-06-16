package org.yusjade.welcomexuan.commands;

import org.bukkit.configuration.file.YamlConfiguration;
import org.yusjade.welcomexuan.WelcomeXuan;
import org.yusjade.welcomexuan.utils.WelcomeXuanUtils;

public class Set extends SubCommand {
  static WelcomeXuan welcomeXuan;
  static YamlConfiguration config;
  static {
    welcomeXuan = WelcomeXuanUtils.getInstance();
    config = WelcomeXuanUtils.get
  }
  @Override
  public void execute(String[] args) {
  }
}
