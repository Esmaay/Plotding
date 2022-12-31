package be.esmay.plotding.utils;

import be.esmay.plotding.common.configuration.lang.MessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public class ChatResponse extends BukkitRunnable implements Listener {

    private Player player;
    private final Consumer<String> consumer; // returns player's message

    /**
     * Makes a player to make a response in the chat + disables from the player to receive any messages
     * @param player - the player whom the plugin will wait
     * @param consumer - your function, what to do with the message player sent to the chat
     * @param wait - in seconds, how much time the plugin will wait for the player
     */
    public ChatResponse(Player player, Plugin plugin, Consumer<String> consumer, int wait) {
        this.player = player;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.consumer = consumer;
        runTaskLater(plugin, wait*20L);
    }

    /**
     * Makes the default wait time 10 seconds
     */
    public ChatResponse(Player player, Plugin plugin, Consumer<String> consumer) {
        this(player, plugin, consumer, 10);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getPlayer().equals(player)) {
            e.setCancelled(true);
            consumer.accept(e.getMessage());
            player = null;
            HandlerList.unregisterAll(this);
            cancel();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (event.getPlayer().equals(player)) {
            HandlerList.unregisterAll(this);
            cancel();
        }
    }

    @EventHandler
    public void onBlah(PlayerInteractEvent event){
        if (event.getPlayer().equals(player)) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                MessagesConfig.SUCCESS_TRANSFER_CANCELLED.get(player);
                HandlerList.unregisterAll(this);
                cancel();
            }
        }
    }

    @Override
    public void run() {
        if (this.player != null) {
            MessagesConfig.ERROR_TRANSFER_TOO_LONG.get(player);
            HandlerList.unregisterAll(this);
        }
        cancel();
    }
}