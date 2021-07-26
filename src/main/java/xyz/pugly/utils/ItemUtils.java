package xyz.pugly.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public static ItemStack createItem(Material m, int amount, String name, String[] lore, boolean unbreakable, boolean glowing, int customModel) {
        ItemStack out = new ItemStack(m, amount);
        ItemMeta im = out.getItemMeta();
        im.setDisplayName(TextUtils.colorize(name));
        List<String> fixedLore = new ArrayList<>();
        for (String s : lore) {
            fixedLore.add(TextUtils.colorize(s));
        }
        im.setLore(fixedLore);
        im.setUnbreakable(unbreakable);
        if (glowing) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        im.setCustomModelData(customModel);
        out.setItemMeta(im);
        return out;
    }

    public static ItemStack createItem(Material m, int amount, String name, String[] lore, boolean unbreakable, boolean glowing) {
        ItemStack out = new ItemStack(m, amount);
        ItemMeta im = out.getItemMeta();
        im.setDisplayName(TextUtils.colorize(name));
        List<String> fixedLore = new ArrayList<>();
        for (String s : lore) {
            fixedLore.add(TextUtils.colorize(s));
        }
        im.setLore(fixedLore);
        im.setUnbreakable(unbreakable);
        if (glowing) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        out.setItemMeta(im);
        return out;
    }

    public static ItemStack createItem(Material m, int amount, String name, String[] lore, boolean unbreakable) {
        ItemStack out = new ItemStack(m, amount);
        ItemMeta im = out.getItemMeta();
        im.setDisplayName(TextUtils.colorize(name));
        List<String> fixedLore = new ArrayList<>();
        for (String s : lore) {
            fixedLore.add(TextUtils.colorize(s));
        }
        im.setLore(fixedLore);
        im.setUnbreakable(unbreakable);
        out.setItemMeta(im);
        return out;
    }

    public static ItemStack createItem(Material m, int amount, String name, String[] lore) {
        ItemStack out = new ItemStack(m, amount);
        ItemMeta im = out.getItemMeta();
        im.setDisplayName(TextUtils.colorize(name));
        List<String> fixedLore = new ArrayList<>();
        for (String s : lore) {
            fixedLore.add(TextUtils.colorize(s));
        }
        im.setLore(fixedLore);
        out.setItemMeta(im);
        return out;
    }

    public static ItemStack createItem(Material m, int amount, String name) {
        ItemStack out = new ItemStack(m, amount);
        ItemMeta im = out.getItemMeta();
        im.setDisplayName(TextUtils.colorize(name));
        out.setItemMeta(im);
        return out;
    }
}
