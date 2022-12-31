package be.esmay.plotding.common.inventories;

import be.esmay.plotding.Plotding;
import be.esmay.plotding.common.configuration.DefaultConfig;
import be.esmay.plotding.common.configuration.PlaceHolder;
import be.esmay.plotding.common.configuration.lang.MessagesConfig;
import be.esmay.plotding.compatibility.CompatibilityManager;
import be.esmay.plotding.compatibility.extensions.VaultExtension;
import be.esmay.plotding.compatibility.extensions.WorldGuardExtension;
import be.esmay.plotding.utils.ChatResponse;
import be.esmay.plotding.utils.ChatUtils;
import be.esmay.plotding.utils.GUI;
import be.esmay.plotding.utils.items.ItemBuilder;
import be.esmay.plotding.utils.items.NBTEditor;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.milkbowl.vault.economy.Economy;
import nl.minetopiasdb.api.playerdata.PlayerManager;
import nl.minetopiasdb.api.playerdata.objects.SDBPlayer;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlotInventory extends GUI {

    private static final String STONE_HEAD = "ewogICJ0aW1lc3RhbXAiIDogMTYxNTc1NDc4NDExNCwKICAicHJvZmlsZUlkIiA6ICI3Njc0Y2I3NzNiYWM0OThmOGViN2FkOGE1MWEwNDQwZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGljaCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80MzUzMTdhOTU2NzAzZjkyOWVmZDIwMTM1NjU2NzVmZWNjYjYwYmNjZDFjNmU1YThhZDRiOTE1NTM5YjdhNTRmIgogICAgfQogIH0KfQ==";
    private static final String MONEY_BAG = "ewogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJpZCIgOiAiNTdjZWJiZTU4ZGNlNDhlYTllZjk4OGNhZGM4YmRkMmYiLAogICAgICAidHlwZSIgOiAiU0tJTiIsCiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFiOGU3YWY0ZTU0NmU3MWI0ZTg3YzFjZjk3M2NiYzYwNWQ3ZmRkMjllYThjYmE1MjVmYzVmZjgzNDUxMzA0IiwKICAgICAgInByb2ZpbGVJZCIgOiAiOTFmZTE5Njg3YzkwNDY1NmFhMWZjMDU5ODZkZDNmZTciLAogICAgICAidGV4dHVyZUlkIiA6ICI1MWI4ZTdhZjRlNTQ2ZTcxYjRlODdjMWNmOTczY2JjNjA1ZDdmZGQyOWVhOGNiYTUyNWZjNWZmODM0NTEzMDQiCiAgICB9CiAgfSwKICAic2tpbiIgOiB7CiAgICAiaWQiIDogIjU3Y2ViYmU1OGRjZTQ4ZWE5ZWY5ODhjYWRjOGJkZDJmIiwKICAgICJ0eXBlIiA6ICJTS0lOIiwKICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFiOGU3YWY0ZTU0NmU3MWI0ZTg3YzFjZjk3M2NiYzYwNWQ3ZmRkMjllYThjYmE1MjVmYzVmZjgzNDUxMzA0IiwKICAgICJwcm9maWxlSWQiIDogIjkxZmUxOTY4N2M5MDQ2NTZhYTFmYzA1OTg2ZGQzZmU3IiwKICAgICJ0ZXh0dXJlSWQiIDogIjUxYjhlN2FmNGU1NDZlNzFiNGU4N2MxY2Y5NzNjYmM2MDVkN2ZkZDI5ZWE4Y2JhNTI1ZmM1ZmY4MzQ1MTMwNCIKICB9LAogICJjYXBlIiA6IG51bGwKfQ==";
    private static final String ARROW = "ewogICJ0aW1lc3RhbXAiIDogMTYzODgyNTU3ODA1NSwKICAicHJvZmlsZUlkIiA6ICIyNzZlMDQ2YjI0MDM0M2VkOTk2NmU0OTRlN2U2Y2IzNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJBRFJBTlM3MTAiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE0NDM5OWQ1MDIyMDg2ZmFmZGQ4ZTBhODg1OWNjYTNiODg0NTU4ZjRlZmZiM2E4NzM5Yjg0ZTI3ODNkNGVmNiIKICAgIH0KICB9Cn0=";

    private final Player player;
    private final ProtectedRegion region;

    private int price = 0;
    private int level = 0;
    private int sellPrice = 0;

    private final UUID owner;

    public PlotInventory(Player player, ProtectedRegion region) {
        super(ChatUtils.format(MessagesConfig.INVENTORY_TITLE.get(new PlaceHolder("plot", region.getId()))), 3);

        this.player = player;
        this.region = region;

        WorldGuardExtension extension = (WorldGuardExtension) Plotding.getInstance().getCompatibilityManager().getExtensions().get(CompatibilityManager.Extension.WORLDGUARD);
        this.price = Integer.parseInt(extension.getRegionFlag(region, extension.getPriceFlag()));
        this.level = Integer.parseInt(extension.getRegionFlagOrDefault(region, extension.getLevelFlag(), "1"));
        this.owner = region.getOwners().getUniqueIds().stream().findFirst().orElse(null);
        this.sellPrice = (DefaultConfig.SELL_PERCENTAGE.asInteger() * this.price) / 100;
    }

    @Override
    public void setContents() {
        for (int i = 0; i < 3 * 9; ++i) {
            this.inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setData((short) 15).setNoName().build());
        }

        this.inventory.setItem(14, new ItemBuilder(Material.SKULL_ITEM).setData((short) 3).setProfile(STONE_HEAD).setNoName().build());
        if (owner == null) {
            this.inventory.setItem(10, new ItemBuilder(Material.SKULL_ITEM).setData((short) 3).setDisplayName(MessagesConfig.INVENTORY_ITEM_FOR_SALE_NAME.get()).addLoreLine(MessagesConfig.INVENTORY_ITEM_FOR_SALE_LORE.get()).setProfile(STONE_HEAD).build());
            this.inventory.setItem(15, new ItemBuilder(Material.SKULL_ITEM).setNBT("plot_action", "buy").setData((short) 3).setProfile(MONEY_BAG).setDisplayName(MessagesConfig.INVENTORY_ITEM_BUY_NAME.get()).addLoreLine(MessagesConfig.INVENTORY_ITEM_BUY_LORE.get(new PlaceHolder("price", String.valueOf(price)))).build());
        } else {
            this.inventory.setItem(10, new ItemBuilder(Material.SIGN).setDisplayName(MessagesConfig.INVENTORY_ITEM_OWNER_NAME.get(new PlaceHolder("owner", Bukkit.getOfflinePlayer(owner).getName()))).build());
            this.inventory.setItem(15, new ItemBuilder(Material.SKULL_ITEM).setData((short) 3).setProfile(MONEY_BAG).setDisplayName(MessagesConfig.INVENTORY_ITEM_NOT_FOR_SALE_NAME.get()).addLoreLine(MessagesConfig.INVENTORY_ITEM_NOT_FOR_SALE_LORE.get(new PlaceHolder("price", String.valueOf(price)))).build());
        }

        this.inventory.setItem(16, new ItemBuilder(Material.SKULL_ITEM).setData((short) 3).setProfile(STONE_HEAD).setNoName().build());
        if (owner != null && owner.equals(player.getUniqueId())) {
            this.inventory.setItem(15, new ItemBuilder(Material.SKULL_ITEM).setNBT("plot_action", "sell").setData((short) 3).setProfile(MONEY_BAG).setDisplayName(MessagesConfig.INVENTORY_ITEM_SELL_NAME.get()).addLoreLine(MessagesConfig.INVENTORY_ITEM_SELL_LORE.get(new PlaceHolder("price", String.valueOf(sellPrice)))).build());
            this.inventory.setItem(16, new ItemBuilder(Material.SKULL_ITEM).setNBT("plot_action", "transfer").setData((short) 3).setProfile(ARROW).setDisplayName(MessagesConfig.INVENTORY_ITEM_TRANSFER_NAME.get()).addLoreLine(MessagesConfig.INVENTORY_ITEM_TRANSFER_LORE.get()).build());
        }
    }

    @Override
    public void handleClickAction(InventoryClickEvent event) {
        ItemStack stack = event.getCurrentItem();

        if (stack == null) return;

        String action = NBTEditor.getString(stack, "plot_action");
        if (action == null) return;

        CompatibilityManager compatibilityManager = Plotding.getInstance().getCompatibilityManager();
        VaultExtension vaultExtension = (VaultExtension) compatibilityManager.getExtensions().get(CompatibilityManager.Extension.VAULT);
        WorldGuardExtension worldGuardExtension = (WorldGuardExtension) compatibilityManager.getExtensions().get(CompatibilityManager.Extension.WORLDGUARD);

        Economy economy = vaultExtension.getEconomy();

        switch (action) {
            case "buy":
                if (region.getOwners().size() > 1) {
                    MessagesConfig.ERROR_PLOT_ALREADY_BOUGHT.get(player);
                    return;
                }

                if (economy.getBalance(player) < price) {
                    MessagesConfig.ERROR_NOT_ENOUGH_MONEY.get(player);
                    return;
                }

                if (worldGuardExtension.getPlotCount(player) > 4) {
                    MessagesConfig.ERROR_MAX_PLOTS.get(player);
                    return;
                }

                SDBPlayer sdbPlayer = PlayerManager.getOnlinePlayer(player.getUniqueId());
                if (sdbPlayer.getLevel() < level) {
                    MessagesConfig.ERROR_LEVEL_NOT_HIGH_ENOUGH.get(player, new PlaceHolder("level", String.valueOf(level)));
                    return;
                }

                economy.withdrawPlayer(player, price);

                region.getOwners().clear();
                region.getMembers().clear();
                region.getOwners().addPlayer(player.getUniqueId());

                MessagesConfig.SUCCESS_PLOT_BOUGHT_SUCCESSFULLY.get(player, new PlaceHolder("price", String.valueOf(price)), new PlaceHolder("plot", String.valueOf(region.getId())));
                new PlotInventory(player, region).open(player);
                break;
            case "sell":
                if (!owner.equals(player.getUniqueId())) {
                    MessagesConfig.ERROR_NOT_YOUR_PLOT.get(player);
                    return;
                }

                economy.depositPlayer(player, sellPrice);
                region.getOwners().clear();
                region.getMembers().clear();

                MessagesConfig.SUCCESS_PLOT_SOLD_SUCCESSFULLY.get(player, new PlaceHolder("price", String.valueOf(sellPrice)), new PlaceHolder("plot", String.valueOf(region.getId())));
                new PlotInventory(player, region).open(player);
                break;
            case "transfer":
                if (!owner.equals(player.getUniqueId())) {
                    MessagesConfig.ERROR_NOT_YOUR_PLOT.get(player);
                    return;
                }

                MessagesConfig.SUCCESS_TRANSFER_PLOT_REQUEST_NAME.get(player);
                player.closeInventory();

                new ChatResponse(player, Plotding.getInstance(), (reply) -> {
                    if (Bukkit.getPlayer(reply) == null || !Bukkit.getPlayer(reply).isOnline() || !player.canSee(Bukkit.getPlayer(reply))) {
                        MessagesConfig.ERROR_PLAYER_NOT_FOUND.get(player);
                        return;
                    }
                    Player target = Bukkit.getPlayer(reply);

                    if (target.getUniqueId().equals(player.getUniqueId())) {
                        MessagesConfig.ERROR_CANNOT_TRANSFER_TO_YOURSELF.get(player);
                        return;
                    }

                    SDBPlayer targetSdbPlayer = PlayerManager.getOnlinePlayer(target.getUniqueId());
                    if (targetSdbPlayer.getLevel() < level) {
                        player.sendMessage(ChatUtils.format(MessagesConfig.ERROR_LEVEL_NOT_HIGH_ENOUGH_OTHER.get(new PlaceHolder("level", String.valueOf(level)))));
                        return;
                    }

                    if (worldGuardExtension.getPlotCount(target) > 4) {
                        MessagesConfig.ERROR_MAX_PLOTS_OTHER.get(player);
                        return;
                    }

                    region.getOwners().clear();
                    region.getMembers().clear();
                    region.getOwners().addPlayer(target.getUniqueId());

                    MessagesConfig.SUCCESS_PLOT_TRANSFERRED_SUCCESSFULLY.get(player, new PlaceHolder("player", target.getName()), new PlaceHolder("plot", String.valueOf(region.getId())));
                    MessagesConfig.SUCCESS_PLOT_TRANSFERRED_SUCCESSFULLY_OTHER.get(target, new PlaceHolder("player", player.getName()), new PlaceHolder("plot", String.valueOf(region.getId())));

                    new PlotInventory(player, region).open(player);
                });
                break;
        }
    }

    @Override
    public void handleCloseAction(InventoryCloseEvent event) {}
}
