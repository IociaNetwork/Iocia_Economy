package iocia.network.economy.utls;

import org.bukkit.ChatColor;

/**
 * Used to level log events at specified severities.
 */
public enum LogTypes {

    GENERAL(ChatColor.WHITE + "(" +
            ChatColor.AQUA + "i" +
            ChatColor.WHITE + ") "),
    SUCCESS(ChatColor.WHITE + "(" +
            ChatColor.GREEN + "✔" +
            ChatColor.WHITE + ") "),
    WARNING(ChatColor.WHITE + "(" +
            ChatColor.YELLOW + "!" +
            ChatColor.WHITE + ") "),
    SEVERE(ChatColor.WHITE + "(" +
            ChatColor.RED + "X" +
            ChatColor.WHITE + ") "),
    DEBUG(ChatColor.WHITE + "(" +
            ChatColor.LIGHT_PURPLE + "$" +
            ChatColor.WHITE + ") ");

    private String message;

    LogTypes(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }

}
