package org.yusjade.welcomexuan;

import com.alibaba.fastjson.JSON;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerProfile;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;


//@Slf4j
public final class WelcomeXuan extends JavaPlugin implements Listener {

  private final HttpClient httpClient;
  private String title = "欢迎{{playerName}}";
  private String subtitle = "";
  private Integer fadeIn = 20;
  private Integer stay = 30;
  private Integer fadeOut = 10;

  public WelcomeXuan() {
    httpClient = HttpClient.newBuilder().build();
  }

  @Override
  public void onEnable() {
    System.out.println("[WelcomeXuan]: 加载成功");
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
    try {
      getCommand("welcomexuan").setExecutor(new org.yusjade.welcomexuan.Config());
      saveDefaultConfig();
      loadConfig();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public void loadConfig() {
    FileConfiguration config = getConfig();
    title = getConfig().getString("title.title");
    subtitle = getConfig().getString("title.subtitle");
    fadeIn = getConfig().getInt("animation.fadeIn");
    stay = getConfig().getInt("animation.stay");
    fadeOut = getConfig().getInt("animation.fadeOut");
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    String playerName = player.getName();
    PlayerProfile playerProfile = player.getPlayerProfile();
    UUID playerUUID = playerProfile.getUniqueId();
    System.out.println("尝试验明正身：" + playerName + "携带 uuid: " + playerUUID + "进入了游戏！");
    if (isPremium(playerName, playerUUID)) {
      System.out.println("认证通过，正版玩家 " + playerName + " 驾到！");
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        onlinePlayer.sendTitle(title.replace("{{playerName}}", playerName),
            subtitle,
            fadeIn,
            stay,
            fadeOut);
//        onlinePlayer.sendTitle(
//            ChatColor.YELLOW + "欢迎" + ChatColor.BLUE + "正版玩家 " + playerName + ChatColor.GREEN
//                + " 加入游戏！",
//            "全服公告", 10, 20, 10);
      }
    }
  }

  private boolean isPremium(String playerName, UUID playerUUID) {
    try {
      System.out.println("向Mojang申请获取uuid...");
//      log.info("向Mojang申请获取uuid...");
      HttpRequest httpRequest = HttpRequest.newBuilder()
          .uri(new URI(
              String.format("https://api.mojang.com/users/profiles/minecraft/%s", playerName)))
          .GET()
          .build();
      HttpResponse<String> httpResponse = httpClient.send(httpRequest,
          HttpResponse.BodyHandlers.ofString());
      JSONObject jsonObject = JSON.parseObject(httpResponse.body());
      String formattedUuidString = jsonObject.getString("id").replaceFirst(
          "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
          "$1-$2-$3-$4-$5"
      );
      UUID uuid = UUID.fromString(formattedUuidString);
      System.out.println("Mojang告诉我：" + playerName + "的 uuid 是: " + uuid);
//      log.info(String.format("对比UUID: [从服务器]%s : [从Mojang]%s", playerUUID, uuid));
      return uuid.equals(playerUUID);
    } catch (Exception e) {
      System.out.println(e.toString());
      return false;
    }
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
