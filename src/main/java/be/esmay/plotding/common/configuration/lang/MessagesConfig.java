package be.esmay.plotding.common.configuration.lang;

import be.esmay.plotding.Plotding;
import be.esmay.plotding.common.configuration.PlaceHolder;
import be.esmay.plotding.utils.ChatUtils;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public enum MessagesConfig {
    PREFIX("prefix", "&7[&bPlotding&7]"),
    ERROR_NO_PERMISSION("error.no-permission", "%prefix% &cJe kunt dit niet doen."),
    ERROR_PLOT_ALREADY_BOUGHT("error.plot-already-bought", "%prefix% &cDeze plot is al gekocht."),
    ERROR_NOT_ENOUGH_MONEY("error.not-enough-money", "%prefix% &cJe hebt niet genoeg geld voor dit plot."),
    ERROR_MAX_PLOTS("error.max-plots", "%prefix% &cJe mag maar 4 plots hebben."),
    ERROR_MAX_PLOTS_OTHER("error.max-plots-other", "%prefix% &cDeze speler mag maar 4 plots hebben."),
    ERROR_LEVEL_NOT_HIGH_ENOUGH("error.level-not-high-enough", "%prefix% &cJe moet level &4%level% &czijn voor dit plot."),
    ERROR_LEVEL_NOT_HIGH_ENOUGH_OTHER("error.level-not-high-enough-other", "%prefix% &cDeze speler moet level &4%level% &czijn voor dit plot."),
    ERROR_NOT_YOUR_PLOT("error.not-your-plot", "%prefix% &cDit is niet jouw plot."),
    ERROR_PLAYER_NOT_FOUND("error.player-not-found", "%prefix% &cDeze speler is niet online."),
    ERROR_CANNOT_TRANSFER_TO_YOURSELF("error.cannot-transfer-to-yourself", "%prefix% &cJe kunt je plot niet aan jezelf overdragen."),
    ERROR_TRANSFER_TOO_LONG("error.transfer-too-long", "&c[ELF] Stop met het stelen van mijn tijd jij id#!@t!"),

    SUCCESS_PLOT_BOUGHT_SUCCESSFULLY("success.plot.bought-successfully", "%prefix% &fJe hebt plot &b%plot% &fgekocht voor &b€ %price%&f."),
    SUCCESS_PLOT_SOLD_SUCCESSFULLY("success.plot.sold-successfully", "%prefix% &fJe hebt plot &b%plot% &fverkocht voor &b€ %price%&f."),
    SUCCESS_TRANSFER_PLOT_REQUEST_NAME("success.transfer.plot-request-name", "%prefix% &fTyp in chat naar wie dit plot moet. Gebruik &blinker muisknop &fom te annuleren!"),
    SUCCESS_PLOT_TRANSFERRED_SUCCESSFULLY("success.plot.transferred-successfully", "%prefix% &fJe hebt plot &b%plot% &fovergedragen aan &b%player%&f."),
    SUCCESS_PLOT_TRANSFERRED_SUCCESSFULLY_OTHER("success.plot.transferred-successfully-other", "%prefix% &fJe hebt plot &b%plot% &fontvangen van &b%player%&f."),
    SUCCESS_TRANSFER_CANCELLED("success.transfer.cancelled", "%prefix% &fJe hebt de transfer geannuleerd."),

    INVENTORY_TITLE("inventory.title", "&8Plot: %plot%"),
    INVENTORY_ITEM_FOR_SALE_NAME("inventory.item.for-sale.name", "&bTe koop"),
    INVENTORY_ITEM_FOR_SALE_LORE("inventory.item.for-sale.lore", "&fDit plot is te koop."),
    INVENTORY_ITEM_BUY_NAME("inventory.item.buy.name", "&bKlik hier om het plot te kopen."),
    INVENTORY_ITEM_BUY_LORE("inventory.item.buy.lore", "&fPrijs: &b€ %price%"),
    INVENTORY_ITEM_OWNER_NAME("inventory.item.owner.name", "&fDit plot is van &b%owner%"),
    INVENTORY_ITEM_NOT_FOR_SALE_NAME("inventory.item.not-for-sale.name", "&bDit plot is niet te koop."),
    INVENTORY_ITEM_NOT_FOR_SALE_LORE("inventory.item.not-for-sale.lore", "&fKoopprijs: &b€ %price%"),
    INVENTORY_ITEM_SELL_NAME("inventory.item.sell.name", "&bVerkoop dit plot"),
    INVENTORY_ITEM_SELL_LORE("inventory.item.sell.lore", "&fVerkoopprijs: &b€ %price%"),
    INVENTORY_ITEM_TRANSFER_NAME("inventory.item.transfer.name", "&bPlot Overdracht"),
    INVENTORY_ITEM_TRANSFER_LORE("inventory.item.transfer.lore", "&fDraag een plot over naar een andere speler");

    @Getter private final String path;
    @Getter(AccessLevel.PRIVATE) private final String message;

    MessagesConfig(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPrefix() {
        String msg = Plotding.getMessages().getConfig().getString(this.getPath());

        return ChatUtils.format(msg);
    }

    public String get() {
        String msg = Plotding.getMessages().getConfig().getString(this.getPath());

        msg = msg.replaceAll("%prefix%", PREFIX.getPrefix());

        return ChatUtils.format(msg);
    }

    public void get(CommandSender commandSender) {
        String msg = Plotding.getMessages().getConfig().getString(this.getPath());

        msg = msg.replaceAll("%prefix%", PREFIX.getPrefix());

        ChatUtils.sendMessage(commandSender, msg);
    }

    public String get(PlaceHolder... placeholder) {
        String msg = Plotding.getMessages().getConfig().getString(this.getPath());
        for (PlaceHolder placeHolder : placeholder) {
            if (msg == null) continue;
            msg = msg.replaceAll(placeHolder.getPlaceholder(), placeHolder.getValue());
        }

        msg = msg.replaceAll("%prefix%", PREFIX.getPrefix());

        return msg;
    }

    public void get(CommandSender commandSender, PlaceHolder... placeholder) {
        String msg = Plotding.getMessages().getConfig().getString(this.getPath());
        for (PlaceHolder placeHolder : placeholder) {
            if (msg == null) continue;
            msg = msg.replaceAll(placeHolder.getPlaceholder(), placeHolder.getValue());
        }

        msg = msg.replaceAll("%prefix%", PREFIX.getPrefix());

        ChatUtils.sendMessage(commandSender, msg);
    }

    public static void init() {
        for (MessagesConfig msg : MessagesConfig.values()) {
            if (Plotding.getMessages().getConfig().getString(msg.getPath()) == null) {
                Plotding.getMessages().getConfig().set(msg.getPath(), msg.getMessage());
            }
        }

        Plotding.getMessages().saveConfig();
    }
}
