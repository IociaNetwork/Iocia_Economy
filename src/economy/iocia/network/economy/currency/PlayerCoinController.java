package iocia.network.economy.currency;

import iocia.network.economy.core.Main;
import iocia.network.economy.currency.coins.PlayerCoin;
import iocia.network.economy.utls.config.Config;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A Singleton ENUM which contains a mapping of all player UUIDs to their respective PlayerCoin objects.
 */
public enum PlayerCoinController {

    INSTANCE;

    private Map<UUID, PlayerCoin> playerCoinMap;
    private File userData;

    PlayerCoinController() {
        playerCoinMap = new HashMap<>();
        userData = new File(Main.getInstance().getDataFolder(), "UserData");
    }

    /**
     * Used to access the given player's wallet.
     * The player's wallet is what stores all of the player's currency data.
     *
     * @param pID UUID of the player
     * @return PlayerCoin for the player or null if the player is not registered
     */
    public static PlayerCoin getPlayerCoin(UUID pID) {
        return INSTANCE.playerCoinMap.get(pID);
    }

    /**
     * Used to access the given player's wallet.
     * The player's wallet is what stores all of the player's currency data.
     *
     * @param player Name of the player
     * @return PlayerCoin for the player or null if the player is not registered
     */
    public static PlayerCoin getPlayerCoin(Player player) {
        return getPlayerCoin(player.getUniqueId());
    }

    /**
     * Checks if the given player is registered.
     *
     * @param pID UUID of the player
     * @return true if registered; false if not
     */
    public static boolean contains(UUID pID)  {
        return INSTANCE.playerCoinMap.containsKey(pID);
    }

    /**
     * Checks if the given player is registered.
     *
     * @param player UUID of the player
     * @return true if registered; false if not
     */
    public static boolean contains(Player player) {
        return contains(player.getUniqueId());
    }

    public static void initializePlayerProfile(UUID pID) {

        if (INSTANCE.playerCoinMap.containsKey(pID))
            return;

        INSTANCE.playerCoinMap.put(pID, new PlayerCoin());

    }

    public static void initializePlayerProfile(Player player) {
        initializePlayerProfile(player.getUniqueId());
    }

    public static void removePlayer(UUID pID) {
        INSTANCE.playerCoinMap.remove(pID);
    }

    public static void removePlayer(Player player) {
        removePlayer(player.getUniqueId());
    }

    public static void loadPlayerProfile(UUID pID) {

        if (!INSTANCE.playerCoinMap.containsKey(pID))
            return;

        Config.createLoadConfigFile(pID.toString(), INSTANCE.userData);
        YamlConfiguration config = Config.getConfig(pID.toString());

        for (String type : CurrencyContainer.getRegisteredTypes()) {
            INSTANCE.playerCoinMap.get(pID).setBalance(type, config.getLong("wallet." + type, CurrencyContainer.getCType(type).getInitialAmount()));
        }

    }

    public static void loadPlayerProfile(Player player) {
        loadPlayerProfile(player.getUniqueId());
    }

    public static void savePlayerProfile(UUID pID) {

        if (!INSTANCE.playerCoinMap.containsKey(pID))
            return;

        YamlConfiguration config = Config.getConfig(pID.toString());

        for (String type : CurrencyContainer.getRegisteredTypes()) {
            config.set("wallet." + type, INSTANCE.playerCoinMap.get(pID).getBalance(type));
        }

        Config.saveConfig(pID.toString());
        Config.removeConfig(pID.toString());

    }

    public static void savePlayerProfile(Player player) {
        savePlayerProfile(player.getUniqueId());
    }

}
