package be.esmay.plotding.common.configuration;

import be.esmay.plotding.Plotding;
import lombok.Getter;

import java.util.List;

public enum DefaultConfig {
    CONFIG_VERSION("config-version", "1.0"),
    SELL_PERCENTAGE("sell-percentage", 80);

    @Getter private final String path;
    @Getter private final Object value;

    DefaultConfig(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    public String asString() { return Plotding.getInstance().getConfig().getString(this.getPath()); }
    public Integer asInteger() {
        return Plotding.getInstance().getConfig().getInt(this.getPath());
    }
    public Boolean asBoolean() {
        return Plotding.getInstance().getConfig().getBoolean(this.getPath());
    }
    public Double asDouble() {
        return Plotding.getInstance().getConfig().getDouble(this.getPath());
    }
    public List<String> asList() {
        return Plotding.getInstance().getConfig().getStringList(this.getPath());
    }

    public static void init() {
        for (DefaultConfig configValue : DefaultConfig.values()) {
            if (Plotding.getInstance().getConfig().get(configValue.getPath()) == null) {
                Plotding.getInstance().getConfig().set(configValue.getPath(), configValue.getValue());
            }
        }

        Plotding.getInstance().saveConfig();
    }
}
