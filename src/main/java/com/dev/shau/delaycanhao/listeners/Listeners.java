package com.dev.shau.delaycanhao.listeners;

import com.dev.shau.delaycanhao.DelayCanhao;
import com.dev.shau.delaycanhao.hologram.HologramManager;
import com.dev.shau.delaycanhao.inventory.InventoryManager;
import com.dev.shau.delaycanhao.inventory.inventories.RelogioInventory;
import com.dev.shau.delaycanhao.utils.Utils;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * @author Shau
 */

public class Listeners implements Listener {
    private final DelayCanhao delayCanhao;

    public Listeners(DelayCanhao delayCanhao) {
        this.delayCanhao = delayCanhao;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack itemStack = e.getPlayer().getItemInHand();

        if(!itemStack.hasItemMeta())
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(!itemMeta.hasDisplayName())
            return;

        String displayName = itemMeta.getDisplayName();
        try {
            if(displayName.substring(2).replace("ó", "o").equals("Relogio de Redstone")) {
                Block block = e.getBlock();
                block.setType(Material.STAINED_GLASS);
                block.setData((byte) 15);
                block.setMetadata("relogio", new FixedMetadataValue(delayCanhao, "true"));

                Hologram hologram = delayCanhao.getHolographicAPI().createHologram(block.getLocation().clone().add(0.5, 0.5, 0.5));
                hologram.getLines().appendText(Utils.alternativeColors("&7Insira o delay.."));
                HologramManager.addHologram(block, hologram);
            }
        } catch (IndexOutOfBoundsException ignored) {}
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Block clickedBlock = e.getClickedBlock();
        Player player = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock.hasMetadata("relogio")) {
            RelogioInventory blockInventory = InventoryManager.getInventory(clickedBlock);
            if(blockInventory != null) {
                blockInventory.open(player);
                return;
            }

            RelogioInventory relogioInventory = new RelogioInventory(delayCanhao, clickedBlock).open(player);
            InventoryManager.addInventory(clickedBlock, relogioInventory);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if(block.hasMetadata("relogio") && block.getMetadata("relogio").get(0).value().equals("true")) {
            Hologram hologram = HologramManager.getHologram(block);

            if(hologram != null)
                hologram.delete();
        }
    }
}
