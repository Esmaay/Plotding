package be.esmay.plotding.compatibility.extensions;

import be.esmay.plotding.Plotding;
import be.esmay.plotding.compatibility.extensions.abstraction.ExtensionImpl;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardExtension implements ExtensionImpl {

    @Getter private StringFlag priceFlag;
    @Getter private StringFlag levelFlag;

    @Override
    public void onEnable() {
        Plotding.getInstance().getLogger().info("WorldGuard compatibility layer enabled!");
    }

    @Override
    public void onDisable() {
        Plotding.getInstance().getLogger().info("WorldGuard compatibility layer disabled!");
    }

    @Override
    public void onLoad() {
        this.priceFlag = registerFlag("plot-price");
        this.levelFlag = registerFlag("plot-level");
    }

    public String getRegionFlag(ProtectedRegion protectedRegion, StringFlag stringFlag) {
        if (protectedRegion != null && protectedRegion.getFlag(stringFlag) != null) {
            return protectedRegion.getFlag(stringFlag);
        }

        return null;
    }

    public String getRegionFlagOrDefault(ProtectedRegion protectedRegion, StringFlag stringFlag, String defaultValue) {
        if (protectedRegion != null && protectedRegion.getFlag(stringFlag) != null) {
            return protectedRegion.getFlag(stringFlag);
        }

        return defaultValue;
    }

    private StringFlag registerFlag(String flagName) {
        FlagRegistry registry = WorldGuardPlugin.inst().getFlagRegistry();
        try {
            StringFlag flag = new StringFlag(flagName);
            registry.register(flag);
            return flag;
        } catch (Exception e) {
            Flag<?> existingFlag = registry.get(flagName);
            if (existingFlag instanceof StringFlag) {
                return (StringFlag) existingFlag;
            }
        }

        return null;
    }

    public int getPlotCount(Player player) {
        RegionContainer container = WorldGuardPlugin.inst().getRegionContainer();
        RegionManager manager = container.get(player.getWorld());

        if (manager == null) return 0;
        return (int) manager.getRegions().values().stream().filter(region -> region.getOwners().getUniqueIds().contains(player.getUniqueId())).count();

    }

    public ProtectedRegion getProtectedRegion(Location location) {
        RegionContainer container = WorldGuardPlugin.inst().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(location);

        ProtectedRegion region = null;

        for (ProtectedRegion regions : set) {
            if (region == null) {
                region = regions;
                continue;
            }

            if (region.getPriority() < regions.getPriority()) region = regions;
        }

        return region;
    }
}
