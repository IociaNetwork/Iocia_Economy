package iocia.network.economy.listeners;

import iocia.network.economy.currency.PlayerCoinController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


/**
 * Used to load a player's profile upon joining the server.
 */
public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        PlayerCoinController.initializePlayerProfile(e.getPlayer());
        PlayerCoinController.loadPlayerProfile(e.getPlayer());

    }

}
