package xyz.namekun.creativepointop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreativePointOpListener implements Listener, CommandExecutor, TabCompleter {

    @EventHandler
    public static void onClickEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
            if (p.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK)) {
                if (!p.hasPermission("minecraft.debugstick")) {
                    e.setCancelled(true);
                    p.sendMessage("§c権限がありません。");
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sendtext")) {
            if (!sender.hasPermission("sendtext")) {
                sender.sendMessage("§c権限がありません。");
                return true;
            }
            if (args.length <= 0) {
                sender.sendMessage("§a/sendtext <player> <message>");
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (args[0].equals("all")) {
                    if (args.length < 2) {
                        sender.sendMessage("§c送りたい内容を指定してください。");
                    } else {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            builder.append(args[i]).append(" ");
                        }
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', builder.toString().replaceAll("``", "\n")));
                    }
                } else if (target == null) {
                    sender.sendMessage("§cそのプレイヤーは存在しません。");
                } else {
                    if (args.length < 2) {
                        sender.sendMessage("§c送りたい内容を指定してください。");
                    } else {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            builder.append(args[i]).append(" ");
                        }
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', builder.toString()).replaceAll("``", "\n"));
                    }
                }
            }
        } if (command.getName().equalsIgnoreCase("cplist")) {
            String url = "https://masa3mc.xyz/creativepoint.html";
            try {
                getFigure(sender, url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void getFigure(CommandSender sender, String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("tbody tr");
        for (Element element : elements) {
            sender.sendMessage(element.text());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> tab = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("sendtext")) {
            if (args.length == 1) {
                tab.add("all");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    tab.add(player.getDisplayName());
                }
            }
        }
        return tab;
    }
}
