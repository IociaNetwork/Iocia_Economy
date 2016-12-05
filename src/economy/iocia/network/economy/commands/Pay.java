package iocia.network.economy.commands;

import iocia.network.economy.core.Main;
import iocia.network.economy.currency.CurrencyContainer;
import iocia.network.economy.currency.PlayerCoinController;
import iocia.network.economy.tasks.PayDelay;
import iocia.network.economy.utls.LogTypes;
import iocia.network.economy.utls.config.ConfigOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to transfer funds between two entities.
 */
public class Pay implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String ss, String[] args) {

        if (!(s instanceof Player)) {
            s.sendMessage(LogTypes.WARNING.get() + "Only players may use this command.");
            return false;
        }

        if (args.length != 3) {
            s.sendMessage(LogTypes.WARNING.get() + "Invalid command arguments.");
            s.sendMessage(LogTypes.WARNING.get() + "Usage: /Pay <Player> <Amount> <Currency Type>");
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            s.sendMessage(LogTypes.WARNING.get() + "Target player cannot be found.");
            return false;
        }

        if (!s.hasPermission("economy.pay")) {
            s.sendMessage(LogTypes.WARNING.get() + "Insufficient permissions. Please contact a server " +
                    "administrator if you believe this is a mistake.");
            return false;
        }

        String ID = args[2];
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
            amount = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            s.sendMessage(LogTypes.WARNING.get() + "Please enter a valid number.");
            return false;
        }
        if (amount < 1) {
            s.sendMessage(LogTypes.WARNING.get() + "Please enter an amount larger than 0.");
            return false;
        }

        Player sender = (Player) s;
        if (!PlayerCoinController.contains(sender)) {
            s.sendMessage(LogTypes.SEVERE.get() + "You are not registered in the economy database.");
            return false;
        }
        if (!PlayerCoinController.contains(target)) {
            s.sendMessage(LogTypes.SEVERE.get() + "Target is not registered in the economy database.");
            return false;
        }

        if (!PlayerCoinController.getPlayerCoin(sender).canPay(ID, amount)) {
            s.sendMessage(LogTypes.WARNING.get() + "Insufficient funds to complete transfer.");
            return false;
        }

        if (ConfigOptions.PAY_TIMER_ENABLE) {
            if (PayDelay.getInstance().contains(sender)) {
                String timerMessage = ChatColor.translateAlternateColorCodes('&', ConfigOptions.PAY_TIMER_MESSAGE);
                timerMessage = timerMessage.replaceAll("%TIME%", Long.toString(PayDelay.getInstance().getTime(sender)));
                sender.sendMessage(timerMessage);
                return false;
            }
            PayDelay.getInstance().addEntry(sender);
        }

        DecimalFormat format = new DecimalFormat("#,###");
        PlayerCoinController.getPlayerCoin(sender).subtractBalance(ID, amount);
        PlayerCoinController.getPlayerCoin(target).addBalance(ID, amount);

        if (ConfigOptions.PAY_TO_SENDER_ENABLE) {
            String senderMessage = ChatColor.translateAlternateColorCodes('&', ConfigOptions.PAY_TO_SENDER);
            senderMessage = senderMessage.replaceAll("%VALUE%", format.format(amount)).replaceAll("%CURRENCY%", CurrencyContainer.getCType(ID).getDisplayName()).replaceAll("%TARGET%", target.getDisplayName());
            sender.sendMessage(LogTypes.SUCCESS.get() + senderMessage);
        }
        if (ConfigOptions.PAY_TO_TARGET_ENABLE) {
            String targetMessage = ChatColor.translateAlternateColorCodes('&', ConfigOptions.PAY_TO_TARGET);
            targetMessage = targetMessage.replaceAll("%VALUE%", format.format(amount)).replaceAll("%CURRENCY%", CurrencyContainer.getCType(ID).getDisplayName()).replaceAll("%SENDER%", sender.getDisplayName());
            target.sendMessage(LogTypes.SUCCESS.get() + targetMessage);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String ss, String[] args) {

        if (args.length == 1) {

            if (args[0].isEmpty())
                return Bukkit.getOnlinePlayers().stream()
                        .map(player -> player.getName())
                        .collect(Collectors.toList());

            return Bukkit.getOnlinePlayers().stream()
                    .filter(players -> players.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                    .map(players -> players.getName())
                    .collect(Collectors.toList());

        } else if (args.length == 2) {

            return Collections.emptyList();

        } else if (args.length == 3) {

            if (args[2].isEmpty())
                return CurrencyContainer.getRegisteredTypes().stream()
                        .filter(type -> CurrencyContainer.getCType(type).isEnabled())
                        .collect(Collectors.toList());

            List<String> idList = new ArrayList<>();
            for (String ID : CurrencyContainer.getRegisteredTypes()) {
                if (CurrencyContainer.getCType(ID).isEnabled())
                    if (ID.toLowerCase().startsWith(args[2]))
                        idList.add(ID);
            }
            return idList;

        }

        return Collections.emptyList();

    }

}
