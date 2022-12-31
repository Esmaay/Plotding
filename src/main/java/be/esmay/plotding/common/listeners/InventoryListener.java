package be.esmay.plotding.common.listeners;

import be.esmay.plotding.utils.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        InventoryHolder inventoryHolder = inventory.getHolder();
        if (!(inventoryHolder instanceof GUI)) return;
        if (!(event.getWhoClicked() instanceof Player)) return;

        GUI gui = (GUI) inventoryHolder;

        event.setCancelled(true);
        gui.handleClickAction(event);
    }

    @EventHandler
    public void onItemDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();

        InventoryHolder inventoryHolder = inventory.getHolder();
        if (!(inventoryHolder instanceof GUI)) return;
        if (!(event.getWhoClicked() instanceof Player)) return;

        event.setCancelled(true);
    }


    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        InventoryHolder inventoryHolder = inventory.getHolder();
        if (!(inventoryHolder instanceof GUI)) return;

        GUI gui = (GUI) inventoryHolder;
        gui.handleCloseAction(event);
    }
}
