package iocia.network.economy.commands;

import iocia.network.economy.currency.CurrencyContainer;
import iocia.network.economy.currency.PlayerCoinController;
import iocia.network.economy.utls.LogTypes;
import iocia.network.economy.utls.config.ConfigOptions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 * Allows players to check their balance.
 * Any currency types the players do not have access to will not show unless
 * specified in the config file.
 */
public class Balance implements TabExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command c, String ss, String[] args) {

        if (!(s instanceof Player)) {
            s.sendMessage(LogTypes.WARNING.get() + "Only players can use this command.");
            return false;
        }

        Player player = (Player) s;

        if (!PlayerCoinController.contains(player)) {
            s.sendMessage(LogTypes.SEVERE.get() + "You are not registered in the economy database.");
            return false;
        }

        if (!player.hasPermission("economy.balance")) {
            s.sendMessage(LogTypes.WARNING.get() + "Insufficient permissions. Please contact a server " +
                    "administrator if you believe this is a mistake.");
            return false;
        }

        DecimalFormat format = new DecimalFormat("#,###");
        if (ConfigOptions.BALANCE_MESSAGE_ENABLE) {
            s.sendMessage("");
            s.sendMessage(ConfigOptions.BALANCE_MESSAGE);
        }

        boolean hasPerms = false;
        if (ConfigOptions.SHOW_HIDDEN_CURRENCY) {

            for (String type : CurrencyContainer.getRegisteredTypes()) {

                if (!CurrencyContainer.getCType(type).isEnabled())
                    continue;

                if (CurrencyContainer.getCType(type).isPermsEmpty()) {

                    s.sendMessage(CurrencyContainer.getCType(type).getDisplayName() +
                            ": " +
                            ChatColor.GREEN + format.format(PlayerCoinController.getPlayerCoin(player).getBalance(type)));

                    continue;

                }

                for (Permission perm : CurrencyContainer.getCType(type).getPermissions()) {
                    if (player.hasPermission(perm))
                        hasPerms = true;
                }

                if (hasPerms) {
                    s.sendMessage(CurrencyContainer.getCType(type).getDisplayName() +
                            ": " +
                            ChatColor.GREEN + format.format(PlayerCoinController.getPlayerCoin(player).getBalance(type)));
                } else {
                    s.sendMessage(CurrencyContainer.getCType(type).getDisplayName() +
                            ": " +
                            ChatColor.RED + format.format(PlayerCoinController.getPlayerCoin(player).getBalance(type)));
                }

                hasPerms = false;
            }

            return true;

        }


        for (String type : CurrencyContainer.getRegisteredTypes()) {

            if (!CurrencyContainer.getCType(type).isEnabled())
                continue;

            if (!CurrencyContainer.getCType(type).isPermsEmpty()) {

                for (Permission perm : CurrencyContainer.getCType(type).getPermissions()) {
                    if (player.hasPermission(perm))
                        hasPerms = true;
                }

                if (hasPerms) {
                    s.sendMessage(CurrencyContainer.getCType(type).getDisplayName() +
                            ": " +
                            ChatColor.GREEN + format.format(PlayerCoinController.getPlayerCoin(player).getBalance(type)));
                }

                hasPerms = false;
            } else {
                s.sendMessage(CurrencyContainer.getCType(type).getDisplayName() +
                        ": " +
                        ChatColor.GREEN + format.format(PlayerCoinController.getPlayerCoin(player).getBalance(type)));
            }

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command c, String ss, String[] args) {
        return Collections.emptyList();
    }
}
