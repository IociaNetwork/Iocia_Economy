package iocia.network.economy.core;

import iocia.network.economy.commands.Balance;
import iocia.network.economy.commands.BalanceOther;
import iocia.network.economy.commands.Pay;
import iocia.network.economy.commands.economy.Economy;
import iocia.network.economy.currency.CurrencyContainer;
import iocia.network.economy.currency.PlayerCoinController;
import iocia.network.economy.listeners.PlayerUpdate;
import iocia.network.economy.tasks.PayDelay;
import iocia.network.economy.utls.config.Config;
import iocia.network.economy.utls.config.ConfigOptions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Base framework for all economy related functions deployed on the Boundless Minecraft Servers.
 */
public class Main extends JavaPlugin {

    //Static instance of Main to allows global access to Main.
    private static Main instance;

    public void onEnable() {

        instance = this;

        initializeConfigFiles();
        ConfigOptions.loadConfigs();

        CurrencyContainer.loadCurrencyTypes();

        initializeListeners();
        initializeCommands();
        initializeTimers();

    }

    public void onDisable() {

        saveProfiles();

    }

    private void initializeConfigFiles() {

        //Config
        Config.createLoadConfigFromBase("Config", getDataFolder());

        //Currency
        Config.createLoadConfigFromBase("Currency", getDataFolder());

        //UserData
        Config.createFolder("UserData", getDataFolder());

    }

    private void initializeListeners() {

        getServer().getPluginManager().registerEvents(new PlayerUpdate(), this);

    }

    private void initializeCommands() {

        getCommand("Balance").setExecutor(new Balance());
        getCommand("BalanceOther").setExecutor(new BalanceOther());
        getCommand("Pay").setExecutor(new Pay());
        getCommand("Economy").setExecutor(new Economy());

    }

    private void initializeTimers() {

        if (ConfigOptions.PAY_TIMER_ENABLE) {
            PayDelay.initInstance();
            PayDelay.getInstance().runTaskTimer(this, 0L, ConfigOptions.PAY_TIMER);
        }

    }

    private void saveProfiles() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerCoinController.savePlayerProfile(player);
            PlayerCoinController.removePlayer(player);
        }

    }



    public static Main getInstance() {
        return instance;
    }

}