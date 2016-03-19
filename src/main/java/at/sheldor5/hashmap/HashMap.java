package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

import java.io.Serializable;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 11.03.2016.
 */
public abstract class HashMap implements HashMapInt, Serializable {

    protected static final int MAX_SIZE = 1499;
    protected final static Stock deletedStock = new Stock(null, null, null, null);

    private final int maxSize;
    public boolean verbose;
    protected double used = 0;

    public HashMap() {
        this(MAX_SIZE);
    }

    public HashMap(int paramSize) {
        maxSize = paramSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Use square exploratory to get the next index.
     * @param hash The hash of the key.
     * @param cc The collision count of how many times this hash has collided while searching for the next free index.
     * @return The next free index for this
     */
    protected int getNextIndex(int hash, int cc) {
        hash = (hash + (cc*cc)) % maxSize;
        if (hash < 0) {
            hash *= -1;
        }
        return hash;
    }

    /**
     * https://de.wikipedia.org/wiki/Hashtabelle#Quadratisches_Sondieren
     * @param hash
     * @param cc
     * @return
     */
    protected int getIndex(int hash, int cc) {
        int dir = (int)Math.pow(-1, cc+1);
        int sqr = (int)Math.pow(cc/2, 2);
        hash = (hash + (dir * sqr)) % maxSize;
        if (hash < 0) {
            hash += maxSize;
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
