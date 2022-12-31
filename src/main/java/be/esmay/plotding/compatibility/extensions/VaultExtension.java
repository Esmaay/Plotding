package be.esmay.plotding.compatibility.extensions;

import be.esmay.plotding.Plotding;
import be.esmay.plotding.compatibility.extensions.abstraction.ExtensionImpl;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;
import java.util.Set;

public class VaultExtension implements ExtensionImpl {
    @Getter private Economy economy;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return;

        economy = rsp.getProvider();
        Plotding.getInstance().getLogger().info("Vault compatibility layer enabled!");
    }

    @Override
    public void onDisable() {
        Plotding.getInstance().getLogger().info("Vault compatibility layer disabled!");
    }

    @Override
    public void onLoad() {}
}
