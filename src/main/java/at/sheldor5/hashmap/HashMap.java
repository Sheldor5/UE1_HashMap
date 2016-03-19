package at.sheldor5.hashmap;

import java.io.Serializable;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 11.03.2016.
 */
public abstract class HashMap implements HashMapInt, Serializable {

    protected static final int DEFAULT_SIZE = 1499;
    protected final static Object DELETED_OBJECT = new Object();

    private final int m;
    public boolean verbose;
    protected double used = 0;

    /**
     * Default Constructor to set up an HashMap with {@value DEFAULT_SIZE} buckets.
     */
    public HashMap() {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructor to set up an HashMap with a specific number of buckets.
     * @param paramSize The number of buckets this HashMap uses, should be prime number.
     */
    public HashMap(int paramSize) {
        m = paramSize;
    }

    /**
     * Getter for unmodifiable size of this HashMap.
     * @return The size of this HashMap.
     */
    public int getMaxSize() {
        return m;
    }

    /**
     * Use simple square exploration to get the next index.
     * @param h The hash of the key.
     * @param i The collision count of how many times this hash has already collided.
     * @return The next calculated index.
     */
    @Deprecated
    protected int getNextIndex__(int h, int i) {
        h = (h + (i*i)) % m;
        if (h < 0) {
            h += m;
        }
        return h;
    }

    /**
     * Use advanced square exploration to get the next index by
     * <a href="https://de.wikipedia.org/wiki/Hashtabelle#Quadratisches_Sondieren">Quadratische Sondierung</a>.
     * @param h The hash of the key.
     * @param i The collision count of how many times this hash has already collided.
     * @return The next calculated index.
     */
    protected int getNextIndex(int h, int i) {
        int a = (int)Math.pow(-1, i+1);
        int b = (int)Math.pow(i/2, 2);
        h = (h + (a * b)) % m;
        if (h < 0) {
            h += m;
        }
        return h;
    }

    /**
     * Hash key.
     * @param paramKey The key to hash.
     * @return The hash of the key as positive integer.
     */
    protected int hash(final String paramKey) {
        if (paramKey == null) {
            return 0;
        }
        int hash = paramKey.hashCode() % m;
        if (hash < 0) {
            hash *= -1;
        }
        return hash;
    }

    /**
     * TODO
     * @return
     */
    public double getLoadFactor() {
        return used / DEFAULT_SIZE;
    }

    /**
     * TODO
     * @return
     */
    public int getStockCount() {
        return (int) used;
    }
}
