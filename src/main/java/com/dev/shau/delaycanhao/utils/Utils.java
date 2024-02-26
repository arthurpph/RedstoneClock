package com.dev.shau.delaycanhao.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Shau
 */

public class Utils {
    public static String alternativeColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> loreAlternativeColors(String... lore) {
        List<String> loreColored = new ArrayList<>();
        for(String l : lore) {
            loreColored.add(alternativeColors(l));
        }

        return loreColored;
    }

    public static ItemStack buildItemStack(Material material, String displayName, String... lore) {
        ItemStack itemStack = new ItemStack(material, 1);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(alternativeColors(displayName));

        itemMeta.setLore(loreAlternativeColors(lore));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack buildItemStack(Material material, String displayName, int color, String... lore) {
        ItemStack itemStack = new ItemStack(material, 1, (byte) color);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(alternativeColors(displayName));

        itemMeta.setLore(loreAlternativeColors(lore));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack buildSkullItemWithTexture(String base64Texture, String displayName, String... lore) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setDisplayName(alternativeColors(displayName));
        skullMeta.setLore(loreAlternativeColors(lore));

        putBase64Texture(skullMeta, base64Texture);

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public static void putBase64Texture(SkullMeta skullMeta, String base64Texture) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64Texture));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
