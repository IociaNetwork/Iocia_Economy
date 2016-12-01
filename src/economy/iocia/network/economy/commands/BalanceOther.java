package iocia.network.economy.commands;

import iocia.network.economy.currency.CurrencyContainer;
import iocia.network.economy.currency.PlayerCoinController;
import iocia.network.economy.utls.LogTypes;
import iocia.network.economy.utls.config.ConfigOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Get balance of another player.
 */
public class BalanceOther implements TabExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command c, String ss, String[] args) {

        if (args.length < 1) {
            s.sendMessage(LogTypes.WARNING.get() + "Please enter the name of the target player.");
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            s.sendMessage(LogTypes.WARNING.get() + "Target player cannot be found. Please make sure" +
                    " the entered name is correct and the target is online.");
            return false;
        }

        if (!PlayerCoinController.contains(target)) {
            s.sendMessage(LogTypes.SEVERE.get() + "Target is not registered in the economy database.");
            return false;
        }

        if (!s.hasPermission("economy.balanceother")) {
            s.sendMessage(LogTypes.WARNING.get() + "Insufficient permissions. Please contact a server " +
                    "administrator if you believe this is a mistake.");
            return false;
        }

        DecimalFormat format = new DecimalFormat("#,###");
        if (ConfigOptions.BALANCE_OTHER_MESSAGE_ENABLE) {
            String preMessage = ChatColor.translateAlternateColorCodes('&', ConfigOptions.BALANCE_OTHER_MESSAGE.replaceAll("%PLAYER%", target.getDisplayName()));
            s.sendMessage("");
            s.sendMessage(preMessage);
        }

        boolean hasPerms = false;
        for (String type : CurrencyContainer.getRegisteredTypes()) {

            if (!CurrencyContainer.getCType(type).isEnabled())
                continue;

            if (!CurrencyContainer.getCType(type).isPermsEmpty()) {

                for (Permission perm : CurrencyContainer.getCType(type).getPermissions()) {
                    if (target.hasPermission(perm))
                        hasPerms = true;
                }

                if (hasPerms) {
                    s.sendMessage(CurrencyContainer.getCType(type).getDisplayName() +
                            ": " +
                            ChatColor.GREEN + format.format(PlayerCoinController.getPlayerCoin(target).getBalance(type)));
                } else {
                    s.sendMessage(CurrencyContainer.getCType(type).getDisplayName() +
                            ": " +
                            ChatColor.RED + format.format(PlayerCoinController.getPlayerCoin(target).getBalance(type)));
                }

                hasPerms = false;

            } else {
                s.sendMessage(CurrencyContainer.getCType(type).getDisplayName() +
                        ": " +
                        ChatColor.GREEN + format.format(PlayerCoinController.getPlayerCoin(target).getBalance(type)));
            }

        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String ss, String[] args) {

        if (args.length != 1)
            return Collections.emptyList();

        if (args[0].isEmpty())
            return Bukkit.getOnlinePlayers().stream()
            .map(player -> player.getName()).collect(Collectors.toList());

        return Bukkit.getOnlinePlayers().stream()
                .filter(players -> players.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                .map(players -> players.getName())
                .collect(Collectors.toList());

    }
}
