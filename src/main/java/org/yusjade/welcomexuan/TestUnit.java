package org.yusjade.welcomexuan;

import java.util.UUID;
import org.bukkit.ChatColor;
import org.junit.Test;

public class TestUnit {

  @Test
  public void string() {
    String playerName = "player";
    String title = "-----{{playerName}}-----";
    System.out.println("标题形式：" + ChatColor.YELLOW + title.replace("{{playerName}}",
        String.format(ChatColor.MAGIC + playerName + ChatColor.YELLOW)));
//    StringBuilder uuid = new StringBuilder("12c2a9a1b649476f8f973110a515080a");
//    uuid.insert(8,"-");
//    uuid.insert(12,"-");
//    uuid.insert(16,"-");
//    uuid.insert(20,"-");
////    uuid.insert(20,"-");
////    uuid.insert(16,"-");
////    uuid.insert(12,"-");
////    uuid.insert(8,"-");
//    System.out.println(uuid.toString());
//    System.out.println(UUID.fromString(uuid.toString()));
  }
}
