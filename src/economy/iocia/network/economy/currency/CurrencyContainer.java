package iocia.network.economy.currency;

import iocia.network.economy.utls.config.Config;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A Singleton ENUM which contains a mapping of all registered currency types.
 * This ENUM maps the currency ID to the CurrencyType object associated with it.
 */
public enum CurrencyContainer {

    INSTANCE;

    private Map<String, CurrencyType> currencyTypeMap;

    CurrencyContainer() {
        currencyTypeMap = new LinkedHashMap<>();
    }

    /**
     * Loads and registers all valid currency types found in the Currency.yml config file.
     * If any duplicate currency IDs are found, only the first encounter will be registered;
     * all subsequent encounters will be ignored.
     */
    public static void loadCurrencyTypes() {

        YamlConfiguration config = Config.getConfig("Currency");
        for (String s : Config.getConfig("Currency").getKeys(false)) {

            if (INSTANCE.currencyTypeMap.containsKey(s))
                continue;

            INSTANCE.currencyTypeMap.put(s, new CurrencyType(
                    config.getString(s + ".display-name", s),
                    config.getBoolean(s + ".enabled", true),
                    config.getLong(s + ".initial-amount", 0)
            ));

            for (String perm : Config.getConfig("Currency").getStringList(s + ".permissions"))
                INSTANCE.currencyTypeMap.get(s).addPermission(new Permission(perm));

        }

    }

    /**
     * Determines if a currency type has been registered.
     *
     * @param ID ID of the currency to check
     * @return true if registered; false if not
     */
    public static boolean contains(String ID) {
        return INSTANCE.currencyTypeMap.containsKey(ID);
    }

    /**
     * Allows access to the CurrencyType object associated with the given ID.
     *
     * @param ID ID of the currency
     * @return Returns the CurrencyType object associated with the ID; null if it is not registered
     */
    public static CurrencyType getCType(String ID) {
        return INSTANCE.currencyTypeMap.get(ID);
    }

    /**
     * A String set of all registered currency IDs.
     *
     * @return String set of all registered currency IDs
     */
    public static Set<String> getRegisteredTypes() {
        return INSTANCE.currencyTypeMap.keySet();
    }

}
