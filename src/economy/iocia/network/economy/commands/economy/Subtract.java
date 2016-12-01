package iocia.network.economy.commands.economy;

import iocia.network.economy.currency.CurrencyContainer;
import iocia.network.economy.currency.PlayerCoinController;
import iocia.network.economy.utls.LogTypes;
import iocia.network.economy.utls.config.ConfigOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Subtract {

    static boolean subtract(CommandSender s, String[] args) {

        if (args.length != 4) {
            s.sendMessage(LogTypes.WARNING.get() + "Usage: /Economy Subtract <Player> <Amount> <Currency Type>");
            return false;
        }

        if (!s.hasPermission("economy.subtract")) {
            s.sendMessage(LogTypes.WARNING.get() + "Insufficient permissions. Please contact a server " +
                    "administrator if you believe this is a mistake.");
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            s.sendMessage(LogTypes.WARNING.get() + "Target player cannot be found.");
            return false;
        }

        String ID = args[3];
        if (!CurrencyContainer.getRegisteredTypes().contains(ID)) {
            s.sendMessage(LogTypes.WARNING.get() + "Invalid currency type entered.");
            //TODO Add a new message prompting the player to type a command that can show them all the registered currency types.
            return false;
        }
        if (!CurrencyContainer.getCType(ID).isEnabled()) {
            //Removes the possibility of any players knowing the existence of disabled currency types.
            s.sendMessage(LogTypes.WARNING.get() + "Invalid currency type entered.");
            return false;
        }

        long amount;
        try {
            amount = Long.parseLong(args[2]);
        } catch (NumberFormatException e) {
            s.sendMessage(LogTypes.WARNING.get() + "Please enter a valid number.");
            return false;
        }
        if (amount < 1) {
            s.sendMessage(LogTypes.WARNING.get() + "Please enter an amount larger than 0.");
            return false;
        }

        if (!PlayerCoinController.contains(target)) {
            s.sendMessage(LogTypes.SEVERE.get() + "Target is not registered in the economy database.");
            return false;
        }

        DecimalFormat format = new DecimalFormat("#,###");
        PlayerCoinController.getPlayerCoin(target).subtractBalance(ID, amount);

        if (ConfigOptions.ECON_SUBTRACT_SENDER_ENABLE) {
            String senderMessage = ChatColor.translateAlternateColorCodes('&', ConfigOptions.ECON_SUBTRACT_SENDER);
            senderMessage = senderMessage.replaceAll("%VALUE%", format.format(amount)).replaceAll("%CURRENCY%", CurrencyContainer.getCType(ID).getDisplayName()).replaceAll("%TARGET%", target.getDisplayName());
            s.sendMessage(LogTypes.SUCCESS.get() + senderMessage);
        }
        if (ConfigOptions.ECON_SUBTRACT_TARGET_ENABLE) {
            String targetMessage = ChatColor.translateAlternateColorCodes('&', ConfigOptions.ECON_SUBTRACT_TARGET);
            targetMessage = targetMessage.replaceAll("%VALUE%", format.format(amount)).replaceAll("%CURRENCY%", CurrencyContainer.getCType(ID).getDisplayName()).replaceAll("%SENDER%", s.getName());
            target.sendMessage(LogTypes.SUCCESS.get() + targetMessage);
        }

        return true;

    }

    static List<String> subtractTab(String[] args) {

        if (args.length == 2) {

            if (args[1].isEmpty())
                return Bukkit.getOnlinePlayers().stream()
                        .map(player -> player.getName())
                        .collect(Collectors.toList());

            return Bukkit.getOnlinePlayers().stream()
                    .filter(players -> players.getName().toLowerCase().startsWith(args[1].toLowerCase()))
                    .map(player -> player.getName())
                    .collect(Collectors.toList());

        } else if (args.length == 3) {

            return Collections.emptyList();

        } else if (args.length == 4) {

            if (args[3].isEmpty())
                return CurrencyContainer.getRegisteredTypes().stream()
                        .filter(type -> CurrencyContainer.getCType(type).isEnabled())
                        .collect(Collectors.toList());

            List<String> idList = new ArrayList<>();
            for (String ID : CurrencyContainer.getRegisteredTypes()) {
                if (CurrencyContainer.getCType(ID).isEnabled())
                    if (ID.toLowerCase().startsWith(args[3]))
                        idList.add(ID);
            }
            return idList;

        }

        return Collections.emptyList();

    }

}
