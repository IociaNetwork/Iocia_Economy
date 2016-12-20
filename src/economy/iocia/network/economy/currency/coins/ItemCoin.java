package iocia.network.economy.currency.coins;

import iocia.network.economy.currency.CurrencyContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages item values for different currency types.
 */
public class ItemCoin {

    private Map<String, Long> prices;

    public ItemCoin() {

        prices = new HashMap<>();

    }

    public void setPrice(String type, long newValue) {

        if (!CurrencyContainer.contains(type))
            return;

        prices.put(type, newValue);

    }

    public long getPrice(String type) {

        if (!CurrencyContainer.contains(type) || !prices.containsKey(type))
            return 0;

        return prices.get(type);

    }



}
