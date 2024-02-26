package com.dev.shau.delaycanhao.commands;

import com.dev.shau.delaycanhao.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Shau
 */

public class GiveRelogioCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("relogio.admin")) {
            sender.sendMessage(Utils.alternativeColors("&cVocê não tem permissão para executar isso!"));
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        int amount = Integer.parseInt(args[1]);

        ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK, amount);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.alternativeColors("&4Relógio de Redstone"));
        itemStack.setItemMeta(itemMeta);

        player.getInventory().addItem(itemStack);

        return false;
    }
}
