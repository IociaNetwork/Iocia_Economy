package iocia.network.economy.utls;

import iocia.network.economy.core.Main;
import org.bukkit.ChatColor;

/**
 * Allows global ability to log system activities in a thread safe manner.
 */
public enum Logger {

    INSTANCE;

    /**
     * Logs a message without any additional formatting and no severity level.
     *
     * @param message Message to log
     */
    public static void logRaw(String message) {
        Main.getInstance().getServer().getConsoleSender().sendMessage(message);
    }

    /**
     * Logs a already formatted message with a specific severity level.
     *
     * @param level Severity
     * @param message Message to log
     */
    public static void logFormatted(LogTypes level, String message) {
        Main.getInstance().getServer().getConsoleSender().sendMessage(level.get() + message);
    }

    /**
     * Logs an unformatted message with a specific severity.
     * This method will format the given message before logging it.
     *
     * @param level Severity
     * @param message Message to log
     */
    public static void logUnFormatted(LogTypes level, String message) {
        Main.getInstance().getServer().getConsoleSender().sendMessage(level.get() +
                ChatColor.translateAlternateColorCodes('&', message));
    }

}