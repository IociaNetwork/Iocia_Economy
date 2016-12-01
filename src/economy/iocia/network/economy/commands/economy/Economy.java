package iocia.network.economy.commands.economy;

import iocia.network.economy.utls.LogTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Master command for Economy functionality.
 * Controls which sub-commands get called.
 */
public class Economy implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String ss, String[] args) {

        if (args.length < 1) {
            s.sendMessage(LogTypes.WARNING.get() + "Unrecognized sub-command.");
            return false;
        }

        switch (args[0].toLowerCase()) {

            case "give":
                return Give.give(s, args);
            case "subtract":
            case "sub":
                return Subtract.subtract(s, args);
            case "set":
                return Set.set(s, args);
            default:
                s.sendMessage(LogTypes.WARNING.get() + "Unrecognized sub-command.");
                return false;

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String ss, String[] args) {

        if (args.length < 1)
            return Collections.emptyList();

        switch (args[0].toLowerCase()) {

            case "give":
                return Give.giveTab(args);
            case "subtract":
            case "sub":
                return Subtract.subtractTab(args);
            case "set":
                return Set.setTab(args);
            default:
                List<String> rList = new ArrayList<>();
                rList.add("give");
                rList.add("subtract");
                rList.add("set");
                return rList;

        }

    }

}
