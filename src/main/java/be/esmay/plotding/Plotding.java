package be.esmay.plotding;

import be.esmay.plotding.common.commands.PlotInfoCommand;
import be.esmay.plotding.common.configuration.DefaultConfig;
import be.esmay.plotding.common.configuration.lang.MessagesConfig;
import be.esmay.plotding.common.listeners.InventoryListener;
import be.esmay.plotding.compatibility.CompatibilityManager;
import be.esmay.plotding.utils.config.ConfigurationFile;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plotding extends JavaPlugin {

    @Getter private static Plotding instance;

    @Getter private CompatibilityManager compatibilityManager;
    @Getter private static ConfigurationFile messages;

    @Override
    public void onLoad() {
        this.compatibilityManager = new CompatibilityManager(this);
        this.getCompatibilityManager().registerExtensions();
        this.getCompatibilityManager().loadExtensions();
    }

    @Override
    public void onEnable() {
        instance = this;

        this.getCompatibilityManager().enableExtensions();

        DefaultConfig.init();
        messages = new ConfigurationFile(this, "messages.yml", false);
        MessagesConfig.init();
        messages.saveConfig();

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new PlotInfoCommand(this));

        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        this.getLogger().info(this.getDescription().getName() + " v" + this.getDescription().getVersion() + " has been enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info(this.getDescription().getName() + " v" + this.getDescription().getVersion() + " has been disabled!");
    }
}
