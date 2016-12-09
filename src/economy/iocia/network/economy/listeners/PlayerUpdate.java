package iocia.network.economy.listeners;

import iocia.network.economy.currency.PlayerCoinController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerUpdate implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        PlayerCoinController.initializePlayerProfile(e.getPlayer());
        PlayerCoinController.loadPlayerProfile(e.getPlayer());

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        PlayerCoinController.savePlayerProfile(e.getPlayer());
        PlayerCoinController.removePlayer(e.getPlayer());

    }

}
