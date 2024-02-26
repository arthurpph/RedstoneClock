package com.dev.shau.delaycanhao.inventory.inventories;

import com.dev.shau.delaycanhao.DelayCanhao;
import com.dev.shau.delaycanhao.hologram.HologramManager;
import com.dev.shau.delaycanhao.scheduler.SchedulerManager;
import com.dev.shau.delaycanhao.utils.Utils;
import com.hakan.core.ui.inventory.InventoryGui;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashSet;

/**
 * @author Shau
 */

public class RelogioInventory extends InventoryGui {
    private void renderRelogio() {
        super.setItem(19, Utils.buildItemStack(Material.REDSTONE_BLOCK, "&4Relógio de Redstone", "&7Utilize os botões para alterar o delay", "&7que o relógio de redstone irá atualizar", "", "&7Delay: &f" + delay));
    }

    private void setRedGlass() {
        this.block.setType(Material.STAINED_GLASS);
        this.block.setData((byte) 14);
    }

    private void setHologramText(String text) {
        Hologram hologram = HologramManager.getHologram(this.block);

        if(hologram == null) {
            hologram = delayCanhao.getHolographicAPI().createHologram(this.block.getLocation().clone().add(0.5, 0.5, 0.5));
            hologram.getLines().appendText(text);
            HologramManager.addHologram(this.block, hologram);
        } else {
            hologram.getLines().clear();
            hologram.getLines().appendText(text);
        }
    }

    private void setBlockMetadata(String value) {
        this.block.setMetadata("relogio", new FixedMetadataValue(delayCanhao, value));
    }

    private BigDecimal delay;
    private double currentDelay;
    private final DelayCanhao delayCanhao;
    private final Block block;

    public RelogioInventory(DelayCanhao delayCanhao, Block block) {
        super("relogio_inventory", "Relógio - Configuração", 5, InventoryType.CHEST, new HashSet<>());

        super.addOption(Option.CLOSABLE);
        super.addOption(Option.CANCEL_TOP_CLICK);
        super.addOption(Option.CANCEL_DOWN_CLICK);

        this.delayCanhao = delayCanhao;
        this.delay = new BigDecimal("0.7");
        this.currentDelay = 0;
        this.block = block;
    }

    @Override
    public void onOpen(@Nonnull Player player) {
        String base64Texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzOGQwOGJkMDQwNWMwNWY0N2VhODZkNjY2NDM0MzRmZGQyZThjNDZmZjFlNmY4ODJiYjliZjg5MWM3ZDNhNSJ9fX0=";
        super.setItem(10, Utils.buildSkullItemWithTexture(base64Texture, "&c&l+", "&7Clique para aumentar o delay em 0.1s."), e -> {
            if(this.delay.doubleValue() >= 1.2) {
                player.sendMessage(Utils.alternativeColors("&cVocê não pode colocar um delay acima de 1.2s"));
                return;
            }

            this.delay = this.delay.add(new BigDecimal("0.1"));
            this.renderRelogio();
        });

        String base64Texture2 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTRmOTBjN2JkNjBiZmQwZGZjMzE4MDhkMDQ4NGQ4YzJkYjk5NTlmNjhkZjkxZmJmMjk0MjNhM2RhNjI0MjlhNiJ9fX0=";
        super.setItem(24, Utils.buildSkullItemWithTexture(base64Texture2, "&cConfiguração de Invasão", "&7Esta opção define o delay mínimo para", "&7canhões de invasão (0.7s)."), e -> {
            this.delay = new BigDecimal("0.7");
            this.renderRelogio();
            player.sendMessage(Utils.alternativeColors("&aYAY! Você configurou o delay com sucesso!"));
        });

        String base64Texture3 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTc5ZmM5MmI0Zjk1NjA5MGQ0YTY3MGM1YmUwNmE1MjZiM2JkYmYxMGU0Y2U2NjkwOTY4ODU3NDA3ZTMxNGVkZCJ9fX0=";
        super.setItem(25, Utils.buildSkullItemWithTexture(base64Texture3, "&cConfiguração de Counter", "&7Esta opção define o delay mínimo para", "&7canhões de counter (1.2s)."), e -> {
            this.delay = new BigDecimal("1.2");
            this.renderRelogio();
            player.sendMessage(Utils.alternativeColors("&aYAY! Você configurou o delay com sucesso!"));
        });

        String base64Texture4 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDczZmQ4YTA2ZTZlYTgyMDc5NGNkYTIxNGZjNDZiNGMzMjlmYTlhZjMyNGU0NGVhYjQ0OTZjMmQ5ZjViYTZmZCJ9fX0=";
        super.setItem(28, Utils.buildSkullItemWithTexture(base64Texture4, "&c&l-", "&7Clique para diminuir o delay em 0.1s."), e -> {
            if(this.delay.doubleValue() <= 0.7) {
                player.sendMessage(Utils.alternativeColors("&cVocê não pode colocar um delay abaixo de 0.7s"));
                return;
            }

            this.delay = this.delay.subtract(new BigDecimal("0.1"));
            this.renderRelogio();
        });

        this.renderRelogio();

        super.setItem(21, Utils.buildItemStack(Material.STAINED_CLAY, "&aCONFIRMAR", 5, "&7Clique para confirmar o delay e", "&7ativar o relógio de redstone"), e -> {
            double delayDouble = this.delay.doubleValue();

            if(delayDouble == this.currentDelay) {
                player.sendMessage(Utils.alternativeColors("&cO relógio já está configurado neste delay"));
                return;
            }

            this.currentDelay = delayDouble;

            this.setHologramText(Utils.alternativeColors("&c" + delayDouble + "s"));

            Integer taskId = SchedulerManager.getSchedulerId(this.block);
            if(taskId != null) {
                Bukkit.getScheduler().cancelTask(taskId);
            }

            this.setRedGlass();

            SchedulerManager.addScheduler(this.block, Bukkit.getScheduler().runTaskTimer(delayCanhao, () -> {
                this.block.setType(Material.REDSTONE_BLOCK);

                Bukkit.getScheduler().runTaskLater(delayCanhao, this::setRedGlass, 4);
            }, (long) (delayDouble * 20), (long) (delayDouble * 20)).getTaskId());

            this.setBlockMetadata("false");

            super.close(player);
        });

        super.setItem(22, Utils.buildItemStack(Material.STAINED_CLAY, "&cDESLIGAR", 14, "&7Clique para desligar o", "&7relógio de redstone"), e -> {
            Integer taskId = SchedulerManager.getSchedulerId(this.block);

            if(taskId != null) {
                Bukkit.getScheduler().cancelTask(taskId);
            }

            this.block.setType(Material.STAINED_GLASS);
            this.block.setData((byte) 15);

            this.setHologramText(Utils.alternativeColors("&7Insira o delay.."));
            this.setBlockMetadata("true");

            super.close(player);
        });
    }
}
