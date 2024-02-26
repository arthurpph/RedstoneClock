package com.dev.shau.delaycanhao.hologram;

import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.block.Block;

import java.util.HashMap;

/**
 * @author Shau
 */

public class HologramManager {
    private static final HashMap<Block, Hologram> holograms = new HashMap<>();

    public static void addHologram(Block block, Hologram hologram) {
        holograms.put(block, hologram);
    }

    public static Hologram getHologram(Block block) {
        return holograms.get(block);
    }
}
