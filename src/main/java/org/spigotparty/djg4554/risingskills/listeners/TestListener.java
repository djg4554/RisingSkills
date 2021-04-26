package org.spigotparty.djg4554.risingskills.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.spigotparty.djg4554.risingskills.PlayerAccount;
import org.spigotparty.djg4554.risingskills.RisingSkills;
import org.bukkit.Material;

public class TestListener implements Listener {
    RisingSkills plugin;


    public TestListener(RisingSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        PlayerAccount playerAccount = plugin.getPlayerAccountFromOnline(e.getPlayer());
        Block block = e.getBlock();
        if (block.getType().equals(Material.DIAMOND_ORE)) {
            plugin.getAccountManager().printMiningSKill(playerAccount);
        } else if (block.getType().equals(Material.GRASS_BLOCK)) {
            playerAccount.setMining(playerAccount.getMining() + 30);
        }

    }


}

