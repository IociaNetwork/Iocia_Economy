package iocia.network.economy.utls.config;

import org.bukkit.ChatColor;

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

        DEBUG = Config.getConfig("Config").getBoolean("show-debug", false);
        STACK_TRACE = Config.getConfig("Config").getBoolean("show-stack-trace", false);

        BALANCE_MESSAGE = ChatColor.translateAlternateColorCodes('&', Config.getConfig("Config").getString("balance-message",
                ChatColor.WHITE + "---" + ChatColor.GOLD + "Your Wallet" + ChatColor.WHITE + "---"));
        BALANCE_MESSAGE_ENABLE = Config.getConfig("Config").getBoolean("balance-message-enable", true);
        SHOW_HIDDEN_CURRENCY = Config.getConfig("Config").getBoolean("show-hidden-currency", false);

        BALANCE_OTHER_MESSAGE = Config.getConfig("Config").getString("balance-other-message",
                "&f---&6%PLAYER%'s Wallet&f---");
        BALANCE_OTHER_MESSAGE_ENABLE = Config.getConfig("Config").getBoolean("balance-other-message-enable", true);

        PAY_TO_SENDER = Config.getConfig("Config").getString("pay-to-sender",
                "&f%VALUE% %CURRENCY%&r transferred to %TARGET%.");
        PAY_TO_SENDER_ENABLE = Config.getConfig("Config").getBoolean("pay-to-sender-enable", true);
        PAY_TO_TARGET = Config.getConfig("Config").getString("pay-to-target",
                "&f%VALUE% %CURRENCY%&r received from %SENDER%.");
        PAY_TO_TARGET_ENABLE = Config.getConfig("Config").getBoolean("pay-to-target-enable", true);

        ECON_GIVE_SENDER = Config.getConfig("Config").getString("give-to-sender",
                ChatColor.WHITE + "%VALUE% %CURRENCY%&r has been given to %TARGET%.");
        ECON_GIVE_SENDER_ENABLE = Config.getConfig("Config").getBoolean("give-to-sender-enable", true);
        ECON_GIVE_TARGET = Config.getConfig("Config").getString("give-to-target",
                ChatColor.WHITE + "%SENDER% has given you %VALUE% %CURRENCY%&r.");
        ECON_GIVE_TARGET_ENABLE = Config.getConfig("Config").getBoolean("give-to-target-enable", true);

        ECON_SUBTRACT_SENDER = Config.getConfig("Config").getString("subtract-to-sender",
                ChatColor.WHITE + "%VALUE% %CURRENCY%&r has been taken from %TARGET%.");
        ECON_SUBTRACT_SENDER_ENABLE = Config.getConfig("Config").getBoolean("subtract-to-sender-enable", true);
        ECON_SUBTRACT_TARGET = Config.getConfig("Config").getString("subtract-to-target",
                ChatColor.WHITE + "%SENDER% has taken %VALUE% %CURRENCY%&r from you.");
        ECON_SUBTRACT_TARGET_ENABLE = Config.getConfig("Config").getBoolean("subtract-to-target-enable", true);

        ECON_SET_SENDER = Config.getConfig("Config").getString("set-to-sender",
                ChatColor.WHITE + "%TARGET%'s balance has been set to %VALUE% %CURRENCY%&r.");
        ECON_SET_SENDER_ENABLE = Config.getConfig("Config").getBoolean("set-to-sender-enable", true);
        ECON_SET_TARGET = Config.getConfig("Config").getString("set-to-target",
                ChatColor.WHITE + "%SENDER% has set your %CURRENCY%&r balance to %VALUE%.");
        ECON_SET_TARGET_ENABLE = Config.getConfig("Config").getBoolean("set-to-target-enable", true);

    }

}
