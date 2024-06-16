package org.yusjade.welcomexuan;

import com.alibaba.fastjson.JSON;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerProfile;
import com.alibaba.fastjson.JSONObject;

@Setter
@Getter
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
      getCommand("welcomexuan").setExecutor(new org.yusjade.welcomexuan.MainCommand());
      saveDefaultConfig();
      loadConfig();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public void loadConfig() {
    InputStream configStream = getResource("config.yml");
    if (configStream == null) {
      System.out.println("找不到配置文件 config.yml");
      return;
    }
    YamlConfiguration config = YamlConfiguration.loadConfiguration(
        new InputStreamReader(configStream, StandardCharsets.UTF_8));
    title = config.getString("title.title");
    subtitle = config.getString("title.subtitle");
    fadeIn = config.getInt("animation.fadeIn");
    stay = config.getInt("animation.stay");
    fadeOut = config.getInt("animation.fadeOut");
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    String playerName = player.getName();
    PlayerProfile playerProfile = player.getPlayerProfile();
    UUID playerUUID = playerProfile.getUniqueId();
    System.out.println("尝试验明正身：" + playerName + "携带 uuid: " + playerUUID + " 进入了游戏！");
    if (isPremium(playerName, playerUUID)) {
      System.out.println("认证通过，正版玩家 " + playerName + " 驾到！");
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        System.out.println("标题形式：" + ChatColor.YELLOW + title.replace("{{playerName}}",
            String.format(ChatColor.MAGIC + playerName + ChatColor.YELLOW)));
        onlinePlayer.sendTitle(ChatColor.YELLOW + title.replace("{{playerName}}",
                String.format(ChatColor.DARK_PURPLE + playerName + ChatColor.YELLOW)),
            ChatColor.AQUA + subtitle,
            fadeIn,
            stay,
            fadeOut);
      }
    }
  }

  private boolean isPremium(String playerName, UUID playerUUID) {
    try {
      System.out.println("向 Mojang 验证 uuid...");
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
      System.out.println("Mojang api：" + playerName + "的 uuid 是: " + uuid);
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
