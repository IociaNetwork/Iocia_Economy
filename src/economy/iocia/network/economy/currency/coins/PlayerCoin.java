package iocia.network.economy.currency.coins;

import iocia.network.economy.currency.CurrencyContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing individual player economy data.
 */
public class PlayerCoin {

    private Map<String, Long> wallet;

    public PlayerCoin() {

        wallet = new HashMap<>();

        for (String type : CurrencyContainer.getRegisteredTypes())
            wallet.put(type, (long) 0);

    }

    /**
     * Get the value stored to the currency ID.
     *
     * @param type ID of the currency type
     * @return value stored or null if an invalid currency type was given
     */
    public long getBalance(String type) {
        return wallet.get(type);
    }

    /**
     * Sets the value stored to the currency ID.
     * If an invalid currency type is given, nothing will happen.
     *
     * @param type ID of the currency type
     * @param balance New value to set
     */
    public void setBalance(String type, long balance) {

        if (!CurrencyContainer.contains(type))
            return;

        wallet.put(type, balance);

    }

    /**
     * Adds a value to the stored amount.
     * If an invalid currency type is given, nothing will happen.
     *
     * @param type ID of the currency type
     * @param amount Amount to add
     */
    public void addBalance(String type, long amount) {

        if (!CurrencyContainer.contains(type))
            return;

        if (amount < 0)
            return;

        wallet.put(type, wallet.get(type) + amount);

    }

    /**
     * Subtracts a value from the stored amount.
     * If the subtracted amount would cause the value to go negative,
     * the value will be set to zero.
     * If an invalid currency type is given, nothing will happen.
     *
     * @param type ID of the currency type
     * @param amount Amount to subtract
     */
    public void subtractBalance(String type, long amount) {

        if (!CurrencyContainer.contains(type))
            return;

        if (wallet.get(type) - amount < 0) {
            wallet.put(type, 0L);
            return;
        }

        wallet.put(type, wallet.get(type) - amount);

    }

    /**
     * Checks if a player has the necessary funds for a potential transaction.
     *
     * @param ID ID of the currency type
     * @param amount Amount to check (Value of transaction)
     * @return true if the player can pay. false if they cannot or the given currency type was invalid
     */
    public boolean canPay(String ID, long amount) {

        return CurrencyContainer.contains(ID) && !(amount > wallet.get(ID));

    }

}
