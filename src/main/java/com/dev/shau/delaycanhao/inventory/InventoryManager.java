package com.dev.shau.delaycanhao.inventory;

import com.dev.shau.delaycanhao.inventory.inventories.RelogioInventory;
import org.bukkit.block.Block;

import java.util.HashMap;

/**
 * @author Shau
 */

public class InventoryManager {
    private static final HashMap<Block, RelogioInventory> inventories = new HashMap<>();

    public static void addInventory(Block block, RelogioInventory relogioInventory) {
        inventories.put(block, relogioInventory);
    }

    public static RelogioInventory getInventory(Block block) {
        return inventories.get(block);
    }
}
