package iocia.network.economy.utls.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Used to centralize all configuration options
 */
public class ConfigOptions {

    private ConfigOptions() {}

    //GENERAL CONFIG
    public static boolean DEBUG;
    public static boolean STACK_TRACE;

    //COMMAND CONFIG
    // /Balance
    public static String BALANCE_MESSAGE;
    public static boolean BALANCE_MESSAGE_ENABLE;
    public static boolean SHOW_HIDDEN_CURRENCY;
    // /BalanceOther
    public static String BALANCE_OTHER_MESSAGE;
    public static boolean BALANCE_OTHER_MESSAGE_ENABLE;
    // /Pay
    public static String PAY_TO_SENDER;
    public static boolean PAY_TO_SENDER_ENABLE;
    public static String PAY_TO_TARGET;
    public static boolean PAY_TO_TARGET_ENABLE;
    public static long PAY_TIMER;
    public static String PAY_TIMER_MESSAGE;
    public static boolean PAY_TIMER_ENABLE;
    // /Economy Give
    public static String ECON_GIVE_SENDER;
    public static boolean ECON_GIVE_SENDER_ENABLE;
    public static String ECON_GIVE_TARGET;
    public static boolean ECON_GIVE_TARGET_ENABLE;
    // /Economy Subtract
    public static String ECON_SUBTRACT_SENDER;
    public static boolean ECON_SUBTRACT_SENDER_ENABLE;
    public static String ECON_SUBTRACT_TARGET;
    public static boolean ECON_SUBTRACT_TARGET_ENABLE;
    // /Economy Set
    public static String ECON_SET_SENDER;
    public static boolean ECON_SET_SENDER_ENABLE;
    public static String ECON_SET_TARGET;
    public static boolean ECON_SET_TARGET_ENABLE;

    public static void loadConfigs() {

        YamlConfiguration config = Config.getConfig("Config");

        DEBUG = config.getBoolean("show-debug", false);
        STACK_TRACE = config.getBoolean("show-stack-trace", false);

        BALANCE_MESSAGE = ChatColor.translateAlternateColorCodes('&', config.getString("Commands.Balance.message",ChatColor.WHITE + "---" + ChatColor.GOLD + "Your Wallet" + ChatColor.WHITE + "---"));
        BALANCE_MESSAGE_ENABLE = config.getBoolean("Commands.Balance.message-enable", true);
        SHOW_HIDDEN_CURRENCY = config.getBoolean("Commands.Balance.show-hidden-currency", false);

        BALANCE_OTHER_MESSAGE = config.getString("Commands.BalanceOther.message","&f---&6%PLAYER%'s Wallet&f---");
        BALANCE_OTHER_MESSAGE_ENABLE = config.getBoolean("Commands.BalanceOther.message-enable", true);

        PAY_TO_SENDER = config.getString("Commands.Pay.sender-message", "&f%VALUE% %CURRENCY%&r transferred to %TARGET%.");
        PAY_TO_SENDER_ENABLE = config.getBoolean("Commands.Pay.sender-message-enable", true);
        PAY_TO_TARGET = config.getString("Commands.Pay.target-message", "&f%VALUE% %CURRENCY%&r received from %SENDER%.");
        PAY_TO_TARGET_ENABLE = config.getBoolean("Commands.Pay.target-message-enable", true);
        PAY_TIMER = config.getLong("Commands.Pay.timer", 5);
        PAY_TIMER_MESSAGE = config.getString("Commands.Pay.timer-message", "Please wait &6%TIME%&r before using that command again.");
        PAY_TIMER_ENABLE = config.getBoolean("Commands.Pay.timer-enable", true);

        ECON_GIVE_SENDER = config.getString("Commands.Economy.Give.sender-message", ChatColor.WHITE + "%VALUE% %CURRENCY%&r has been given to %TARGET%.");
        ECON_GIVE_SENDER_ENABLE = config.getBoolean("Commands.Economy.Give.sender-message-enable", true);
        ECON_GIVE_TARGET = config.getString("Commands.Economy.Give.target-message", ChatColor.WHITE + "%SENDER% has given you %VALUE% %CURRENCY%&r.");
        ECON_GIVE_TARGET_ENABLE = config.getBoolean("Commands.Economy.Give.target-message-enable", true);

        ECON_SUBTRACT_SENDER = config.getString("Commands.Economy.Subtract.sender-message", ChatColor.WHITE + "%VALUE% %CURRENCY%&r has been taken from %TARGET%.");
        ECON_SUBTRACT_SENDER_ENABLE = config.getBoolean("Commands.Economy.Subtract.sender-message-enable", true);
        ECON_SUBTRACT_TARGET = config.getString("Commands.Economy.Subtract.target-message", ChatColor.WHITE + "%SENDER% has taken %VALUE% %CURRENCY%&r from you.");
        ECON_SUBTRACT_TARGET_ENABLE = config.getBoolean("Commands.Economy.Subtract.target-message-enable", true);

        ECON_SET_SENDER = config.getString("Commands.Economy.Set.sender-message", ChatColor.WHITE + "%TARGET%'s balance has been set to %VALUE% %CURRENCY%&r.");
        ECON_SET_SENDER_ENABLE = config.getBoolean("Commands.Economy.Set.sender-message-enable", true);
        ECON_SET_TARGET = config.getString("Commands.Economy.Set.target-message", ChatColor.WHITE + "%SENDER% has set your %CURRENCY%&r balance to %VALUE%.");
        ECON_SET_TARGET_ENABLE = config.getBoolean("Commands.Economy.Set.target-message-enable", true);

    }

}
