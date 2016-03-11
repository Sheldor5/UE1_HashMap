package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class StockHashMap {

    private final static int MAX_SIZE = 1399;
    //private final static int MAX_SIZE = 2003;
    private final static Stock deletedStock = new Stock(null, null, null, null);

    public boolean verbose;
    private double used = 0;

    private final Stock[] stocksByName = new Stock[MAX_SIZE];
    private final Stock[] stocksByWkn = new Stock[MAX_SIZE];

    public StockHashMap(final boolean paramVerboseOutput) {
        verbose = paramVerboseOutput;
    }

    public StockHashMap() {
        this(false);
    }

    public void put(final Stock paramStock) {
        // store by name
        int nameHash = paramStock.name.hashCode() % MAX_SIZE;
        int x = getNextFreeIndex(stocksByName, nameHash);
        stocksByName[x] = paramStock;

        // store by wkn
        int wknHash = paramStock.wkn.hashCode() % MAX_SIZE;
        int y = getNextFreeIndex(stocksByWkn, wknHash);
        stocksByWkn[y] = paramStock;

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

    /**
     * Use square exploratory to get the next index.
     * @param hash The hash of the key.
     * @param cc The collision count of how many times this hash has collided while searching for the next free index.
     * @return The next free index for this
     */
    private int getNextIndex(int hash, int cc) {
        return (hash + cc*cc) % MAX_SIZE;
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
            stocksByName[iName] = deletedStock;
            stocksByWkn[iWKn] = deletedStock;
            used--;
        }
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
        int hash = paramKey.hashCode() % MAX_SIZE;

        // handle invalid hashes
        if (hash < 0) {
            hash *= -1;
        }

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

    public double getLoadFactor() {
        return used / MAX_SIZE;
    }

    public int getStockCount() {
        return (int) used;
    }
}
