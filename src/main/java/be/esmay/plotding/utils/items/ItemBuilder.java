package be.esmay.plotding.utils.items;

import be.esmay.plotding.utils.ChatUtils;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {

    private ItemStack item;
    private final List<String> lore = new ArrayList<>();
    private final Map<Object, Object> nbtToSet = new HashMap<>();
    private final ItemMeta meta;

    public ItemBuilder(Material mat, short data, int amount) {
        item = new ItemStack(mat, amount, data);
        meta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material mat, short data) {
        item = new ItemStack(mat, 1, data);
        meta = item.getItemMeta();
    }

    public ItemBuilder(Material mat, int amount) {
        item = new ItemStack(mat, amount, (short) 0);
        meta = item.getItemMeta();
    }

    public ItemBuilder(Material mat) {
        item = new ItemStack(mat, 1, (short) 0);
        meta = item.getItemMeta();
    }

    public ItemBuilder setAmount(int value) {
        item.setAmount(value);
        return this;
    }

    public ItemBuilder setNoName() {
        meta.setDisplayName(" ");
        return this;
    }

    public ItemBuilder hideNBT() {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }

    public ItemBuilder setGlow() {
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Sets the NBT on a given item
     *
     * @param key   The key of the NBT
     * @param value The value of the NBT
     */
    public ItemBuilder setNBT(String key, Object value) {
        this.nbtToSet.put(key, value);
        return this;
    }

    public ItemBuilder setData(short data) {
        item.setDurability(data);
        return this;
    }

    public ItemBuilder setProfile(String string) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", string));
        Field field;
        SkullMeta meta2 = (SkullMeta) meta;
        try {
            field = meta2.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta2, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        lore.add(ChatUtils.format(line));
        return this;
    }

    public ItemBuilder addLoreArray(String[] lines) {
        return addLoreAll(Arrays.asList(lines));
    }

    public ItemBuilder addLoreAll(List<String> lines) {
        for (String line : lines) {
            lore.add(ChatUtils.format(line));
        }
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        meta.setDisplayName(ChatUtils.format(name));
        return this;
    }

    public ItemBuilder setSkullOwner(OfflinePlayer owner) {
        ((SkullMeta) meta).setOwningPlayer(owner);
        return this;
    }

    public ItemBuilder setColor(Color c) {
        ((LeatherArmorMeta) meta).setColor(c);
        return this;
    }

    public ItemBuilder setBannerColor(DyeColor c) {
        ((BannerMeta) meta).setBaseColor(c);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean value) {
        meta.spigot().setUnbreakable(value);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment ench, int lvl) {
        meta.addEnchant(ench, lvl, true);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder addLeatherColor(Color color) {
        ((LeatherArmorMeta) meta).setColor(color);
        return this;
    }

    public ItemStack build() {
        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        nbtToSet.forEach((key, value) -> item = NBTEditor.set(item, value, key));
        return item;
    }

}
