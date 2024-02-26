package com.dev.shau.delaycanhao.scheduler;

import org.bukkit.block.Block;

import java.util.HashMap;

/**
 * @author Shau
 */

public class SchedulerManager {
    private static final HashMap<Block, Integer> schedulers = new HashMap<>();

    public static void addScheduler(Block block, int taskId) {
        schedulers.put(block, taskId);
    }

    public static Integer getSchedulerId(Block block) {
        return schedulers.get(block);
    }
}
