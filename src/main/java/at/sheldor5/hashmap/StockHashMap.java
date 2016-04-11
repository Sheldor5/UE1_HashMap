package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

import java.io.Serializable;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class StockHashMap implements Serializable {

    private final Stock[] stocksByName;
    private final Stock[] stocksByWkn;

    protected static final int DEFAULT_SIZE = 1499;
    protected final static Stock DELETED_OBJECT = new Stock("", "", "", null);

    private final int m;
    public boolean verbose = false;
    protected double used = 0;

    public StockHashMap(int paramSize) {
        m = paramSize;
        stocksByName = new Stock[paramSize];
        stocksByWkn = new Stock[paramSize];
    }

    public StockHashMap(final boolean paramVerboseOutput, int paramSize) {
        this(paramSize);
        verbose = paramVerboseOutput;
    }

    public StockHashMap(final boolean paramVerboseOutput) {
        this(DEFAULT_SIZE);
        verbose = paramVerboseOutput;
    }

    public StockHashMap() {
        this(DEFAULT_SIZE);
    }

    public void put(final Stock paramStock) {
        int hash;
        int index;

        // store by name
        hash = hash(paramStock.name);
        index = getNextFreeIndex(stocksByName, hash);
        stocksByName[index] = paramStock;

        // store by wkn
        hash = hash(paramStock.wkn);
        index = getNextFreeIndex(stocksByWkn, hash);
        stocksByWkn[index] = paramStock;

        used++;
    }

    private int getNextFreeIndex(final Stock[] paramArray, int paramHash) {
        if (paramHash < 0) {
            paramHash *= -1;
        }
        int i = paramHash;
        int c = 0;
        while (paramArray[i] != null && paramArray[i].name != null) {
            i = getNextIndex(paramHash, ++c);
        }
        if (verbose) {
            System.out.println("Found free index for hash " + paramHash + " after " + c + " collisions");
        }
        return i;
    }

    public void remove(final String paramKey) {
        int iName = getIndexOfKey(stocksByName, paramKey);
        int iWKn;
        if (iName < 0) {
            iWKn = getIndexOfKey(stocksByWkn, paramKey);
            if (iWKn >= 0) {
                iName = getIndexOfKey(stocksByName, stocksByWkn[iWKn].name);
            }
        } else {
            iWKn = getIndexOfKey(stocksByWkn, stocksByName[iName].wkn);
        }

        if (iName < 0 && iWKn < 0) {
            System.out.println("Key \"" + paramKey + "\" not found");
        } else if (iName < 0 || iWKn < 0) {
            System.out.println("FATAL ERROR");
        } else {
            // delete
            if (verbose) {
                System.out.println("Stock " + stocksByName[iName].toString() + " will be deleted");
                System.out.println("Stock " + stocksByWkn[iWKn].toString() + " will be deleted");
            }
            if (stocksByName[iName] != null && stocksByName[iName].equals(stocksByWkn[iWKn])) {
                stocksByName[iName] = DELETED_OBJECT;
                stocksByWkn[iWKn] = DELETED_OBJECT;
                used--;
            } else {
                System.out.println("PROBLEM?");
            }
        }
    }

    public boolean contains(String paramKey) {
        return false;
    }

    public Stock get(final String paramKey) {
        Stock result = null;
        int i = getIndexOfKey(stocksByName, paramKey);
        if (i < 0) {
            i = getIndexOfKey(stocksByWkn, paramKey);
            if (i >= 0) {
                result = stocksByWkn[i];
            }
        } else {
            result = stocksByName[i];
        }
        return result;
    }

    private int getIndexOfKey(final Stock[] paramArray, final String paramKey) {
        int result = -1;
        // handle invalid search key
        if (paramKey == null) {
            return -1;
        }

        // get initial hash
        int hash = hash(paramKey);

        // setting up helper variables for new search
        boolean n = stocksByName.equals(paramArray) ? true : false;
        boolean searching = true;
        int i = hash;
        int c = 0;

        while (searching && paramArray[i] != null) {
            if (n) {
                // search by name
                if (paramKey.equals(paramArray[i].name)) {
                    result = i;
                    searching = false;
                    if (verbose) {
                        System.out.println("Found \"" + paramKey + "\" by Name after " + c + " collisions");
                    }
                }
            } else {
                // search by wkn
                if (paramKey.equals(paramArray[i].wkn)) {
                    result = i;
                    searching = false;
                    if (verbose) {
                        System.out.println("Found \"" + paramKey + "\" by WKN after " + c + " collisions");
                    }
                }
            }

            // get next index
            i = getNextIndex(hash, ++c);
        }

        if (verbose && result < 0) {
            System.out.println("No stock for \"" + paramKey + "\" found, took " + c + " collisions to realize");
        }

        return result;
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
