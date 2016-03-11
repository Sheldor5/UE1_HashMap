package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 11.03.2016.
 */
public abstract class HashMap implements HashMapInt {

    public final static int MAX_SIZE = 10;
    protected final static Stock deletedStock = new Stock(null, null, null, null);

    public boolean verbose;
    protected double used = 0;

    /**
     * Use square exploratory to get the next index.
     * @param hash The hash of the key.
     * @param cc The collision count of how many times this hash has collided while searching for the next free index.
     * @return The next free index for this
     */
    protected int getNextIndex(int hash, int cc) {
        if (hash < 0) {
            hash *= -1;
        } else if (cc == 0) {
            cc++;
        }
        hash = (hash + cc*cc) % MAX_SIZE;
        if (hash < 0) {
            hash *= -1;
        }
        return hash;
    }

    protected int hash(final String paramString) {
        int hash = paramString.hashCode() % MAX_SIZE;
        if (hash < 0) {
            hash *= -1;
        }
        return hash;
    }

    public double getLoadFactor() {
        return used / MAX_SIZE;
    }

    public int getStockCount() {
        return (int) used;
    }
}
