package iocia.network.economy.listeners;

import iocia.network.economy.currency.PlayerCoinController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Used to save a player's profile upon leaving the server
 */
public class PlayerLeave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        PlayerCoinController.savePlayerProfile(e.getPlayer());
        PlayerCoinController.removePlayer(e.getPlayer());

    }

}
