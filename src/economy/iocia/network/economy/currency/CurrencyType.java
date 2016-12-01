package iocia.network.economy.currency;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * Individual currency type class for creating custom coins.
 */
public class CurrencyType {

    private String displayName;
    private List<Permission> permissions;
    private boolean enabled;
    private long initialAmount;

    CurrencyType(String displayName, boolean enabled, long initialAmount) {

        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.permissions = new ArrayList<>();
        this.enabled = enabled;
        this.initialAmount = initialAmount;

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isPermsEmpty() {
        return this.permissions.isEmpty();
    }

    void addPermission(Permission permission) {

        if (this.permissions.contains(permission))
            return;

        this.permissions.add(permission);

    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    long getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(long initialAmount) {
        this.initialAmount = initialAmount;
    }

}
