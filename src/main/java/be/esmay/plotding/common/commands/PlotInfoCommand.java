package be.esmay.plotding.common.commands;

import be.esmay.plotding.Plotding;
import be.esmay.plotding.common.inventories.PlotInventory;
import be.esmay.plotding.compatibility.CompatibilityManager;
import be.esmay.plotding.compatibility.extensions.WorldGuardExtension;
import be.esmay.plotding.utils.ChatUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.RequiredArgsConstructor;
import nl.minetopiasdb.api.objects.Plot;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Optional;

@CommandAlias("pi")
@Description("Open het plot menu!")
@RequiredArgsConstructor
public class PlotInfoCommand extends BaseCommand {

    private final Plotding plugin;

    @Default
    public void onPlotCommand(Player player) {
        WorldGuardExtension extension = (WorldGuardExtension) plugin.getCompatibilityManager().getExtensions().get(CompatibilityManager.Extension.WORLDGUARD);
        ProtectedRegion region = extension.getProtectedRegion(player.getLocation());

        if (region == null) {
            player.performCommand("plotinfo");
            return;
        }

        if (extension.getRegionFlag(region, extension.getPriceFlag()) == null) {
            player.performCommand("plotinfo");
            return;
        }

        new PlotInventory(player, region).open(player);
    }

    @Subcommand("setprice")
    @Syntax("<prijs>")
    @CommandPermission("plotbuy.admin")
    public void onSetPrice(Player player, int price) {
        WorldGuardExtension extension = (WorldGuardExtension) plugin.getCompatibilityManager().getExtensions().get(CompatibilityManager.Extension.WORLDGUARD);
        ProtectedRegion region = extension.getProtectedRegion(player.getLocation());

        if (region == null) {
            player.sendMessage(ChatUtils.format("&cEr is geen region op jou locatie!"));
            return;
        }

        region.setFlag(extension.getPriceFlag(), String.valueOf(price));
        player.sendMessage(ChatUtils.format("&7Prijs van het plot is opgeslagen!"));
    }

    @Subcommand("setlevel")
    @Syntax("<level>")
    @CommandPermission("plotbuy.admin")
    public void onSetLevel(Player player, int level) {
        WorldGuardExtension extension = (WorldGuardExtension) plugin.getCompatibilityManager().getExtensions().get(CompatibilityManager.Extension.WORLDGUARD);
        ProtectedRegion region = extension.getProtectedRegion(player.getLocation());

        if (region == null) {
            player.sendMessage(ChatUtils.format("&cEr is geen region op jou locatie!"));
            return;
        }

        if (extension.getRegionFlag(region, extension.getPriceFlag()) == null) {
            player.sendMessage(ChatUtils.format("&cEr is geen plot op jou locatie!"));
            return;
        }

        region.setFlag(extension.getLevelFlag(), String.valueOf(level));
        player.sendMessage(ChatUtils.format("&7Level van het plot is opgeslagen!"));
    }

    @Subcommand("remove")
    @CommandPermission("plotbuy.admin")
    public void onRemove(Player player) {
        WorldGuardExtension extension = (WorldGuardExtension) plugin.getCompatibilityManager().getExtensions().get(CompatibilityManager.Extension.WORLDGUARD);
        ProtectedRegion region = extension.getProtectedRegion(player.getLocation());

        if (region == null) {
            player.sendMessage(ChatUtils.format("&cEr is geen region op jou locatie!"));
            return;
        }

        if (extension.getRegionFlag(region, extension.getPriceFlag()) == null) {
            player.sendMessage(ChatUtils.format("&cEr is geen plot op jou locatie!"));
            return;
        }

        region.setFlag(extension.getPriceFlag(), null);
        region.setFlag(extension.getLevelFlag(), null);
        player.sendMessage(ChatUtils.format("&7Plot is verwijderd!"));
    }
}
