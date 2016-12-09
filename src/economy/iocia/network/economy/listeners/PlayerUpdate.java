package iocia.network.economy.listeners;

import iocia.network.economy.currency.PlayerCoinController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerUpdate implements Listener {

    /**
     * Runs when a player player joins the server. When a player join, onJoin calls
     * initialize and load methods from PlayerCoinController.
     *
     * @param e PlayerJoinEvent
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        PlayerCoinController.initializePlayerProfile(e.getPlayer());
        PlayerCoinController.loadPlayerProfile(e.getPlayer());

    }

    /**
     * Runs when a player disconnects from the server. Calls nessesary save data methods
     * from PlayerCoinController to save the player's data.
     *
     * @param e PlayerQuitEvent
     */
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        PlayerCoinController.savePlayerProfile(e.getPlayer());
        PlayerCoinController.removePlayer(e.getPlayer());

    }

}
