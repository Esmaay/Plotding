package be.esmay.plotding.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatUtils {

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> format(List<String> lore) {
        List<String> components = new ArrayList<>();
        for (String line : lore) {
            components.add(format(line));
        }
        return components;
    }

    /**
     * Send a message to a CommandSender
     * @param commandSender The CommandSender to send the message to
     * @param message The message to send
     */
    public static void sendMessage(CommandSender commandSender, String message) {
        if (message.length() >= 1) {
            if (message.startsWith("a:") && commandSender instanceof Player) {
                sendActionbar((Player) commandSender, message.substring(2));
            } else {
                commandSender.sendMessage(format(message));
            }
        }
    }

    public static void sendActionbar(Player player, String input) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(format(input)));
    }

    public static void sendBroadcast(String message) {
        Bukkit.broadcastMessage(format(message));
    }

}
