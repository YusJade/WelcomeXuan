package org.yusjade.welcomexuan.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.yusjade.welcomexuan.WelcomeXuan;

public class WelcomeXuanUtils {

  static public YamlConfiguration getConfigFile() {
    return YamlConfiguration.loadConfiguration(
        new InputStreamReader(Objects.requireNonNull(getInstance().getResource("config.yml")),
            StandardCharsets.UTF_8));
  }

  static public WelcomeXuan getInstance() {
    return (WelcomeXuan) WelcomeXuan.getProvidingPlugin(WelcomeXuan.class);
  }

  static public void printConfig() {
    InputStream configStream = null;
    InputStreamReader configIsr = null;
    BufferedReader configBfr = null;
    try {
      Plugin helloXuan = WelcomeXuan.getProvidingPlugin(WelcomeXuan.class);
      configStream = helloXuan.getResource("config.yml");
      configIsr = new InputStreamReader(configStream, StandardCharsets.UTF_8);
      configBfr = new BufferedReader(configIsr);
    } catch (Exception e) {
      System.out.println(e.toString());
    } finally {
      try {
        if (configStream != null) {
          configStream.close();
        }
        if (configIsr != null) {
          configIsr.close();
        }
        if (configBfr != null) {
          configBfr.close();
        }
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }
  }
}
