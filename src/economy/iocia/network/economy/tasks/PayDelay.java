package iocia.network.economy.tasks;

import iocia.network.economy.utls.config.ConfigOptions;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PayDelay extends BukkitRunnable {

    private static PayDelay instance = null;
    private Map<UUID, Long> timer;

    private PayDelay() {
        timer = new HashMap<>();
    }

    public static void initInstance() {
        if (instance == null)
            instance = new PayDelay();
    }

    public static PayDelay getInstance() {
        if (instance == null)
            initInstance();

        return instance;
    }

    public void addEntry(UUID pID) {
        this.timer.put(pID, ConfigOptions.PAY_TIMER);

    }

    public void addEntry(Player player) {
        addEntry(player.getUniqueId());
    }

    public boolean contains(UUID pID) {
        return this.timer.containsKey(pID);
    }

    public boolean contains(Player player) {
        return contains(player.getUniqueId());
    }

    public long getTime(UUID pID) {
        return this.timer.get(pID);
    }

    public long getTime(Player player) {
        return getTime(player.getUniqueId());
    }

    @Override
    public void run() {

        for (UUID ID : this.timer.keySet()) {
            this.timer.put(ID, this.timer.get(ID) - 1);
        }

        for (UUID ID : timer.keySet()) {
            if (timer.get(ID) < 0)
                timer.remove(ID);
        }

    }
}
