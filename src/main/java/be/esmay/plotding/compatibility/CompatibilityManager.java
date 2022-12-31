package be.esmay.plotding.compatibility;

import be.esmay.plotding.Plotding;
import be.esmay.plotding.compatibility.extensions.VaultExtension;
import be.esmay.plotding.compatibility.extensions.WorldGuardExtension;
import be.esmay.plotding.compatibility.extensions.abstraction.ExtensionImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CompatibilityManager {

    private final Plotding plugin;

    @Getter private final Map<Extension, ExtensionImpl> extensions = new HashMap<>();


    public boolean isExtensionEnabled(Extension extension) {
        return extensions.containsKey(extension);
    }

    public void registerExtensions() {
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            extensions.put(Extension.WORLDGUARD, new WorldGuardExtension());
        }

        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            extensions.put(Extension.VAULT, new VaultExtension());
        }
    }

    public void enableExtensions() {
        for (ExtensionImpl extension : extensions.values()) {
            extension.onEnable();
        }
    }

    public void loadExtensions() {
        for (ExtensionImpl extension : extensions.values()) {
            extension.onLoad();
        }
    }

    public void disableExtensions() {
        for (ExtensionImpl extension : extensions.values()) {
            extension.onDisable();
        }
    }

    public enum Extension {
        WORLDGUARD, VAULT
    }

}
