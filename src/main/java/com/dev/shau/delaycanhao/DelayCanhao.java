package com.dev.shau.delaycanhao;

import com.dev.shau.delaycanhao.commands.GiveRelogioCmd;
import com.dev.shau.delaycanhao.listeners.Listeners;
import com.hakan.core.HCore;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DelayCanhao extends JavaPlugin {
    private HolographicDisplaysAPI holographicAPI;

    @Override
    public void onEnable() {
        if(!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("HolographicDisplays não está instalado ou ativado.");
            getLogger().severe("Plugin desativado.");
            this.setEnabled(false);
            return;
        }

        HCore.initialize(this);

        holographicAPI = HolographicDisplaysAPI.get(this);

        getCommand("giverelogio").setExecutor(new GiveRelogioCmd());

        Bukkit.getPluginManager().registerEvents(new Listeners(this), this);
    }

    @Override
    public void onDisable() {}

    public HolographicDisplaysAPI getHolographicAPI() { return holographicAPI; }
}
