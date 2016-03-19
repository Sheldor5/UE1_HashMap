package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class StockHashMap extends HashMap {

    private final Stock[] stocksByName;
    private final Stock[] stocksByWkn;

    public StockHashMap(int paramSize) {
        super(paramSize);
        stocksByName = new Stock[paramSize];
        stocksByWkn = new Stock[paramSize];
    }

    public StockHashMap(final boolean paramVerboseOutput, int paramSize) {
        super(paramSize);
        stocksByName = new Stock[paramSize];
        stocksByWkn = new Stock[paramSize];
        verbose = paramVerboseOutput;
    }

    public StockHashMap(final boolean paramVerboseOutput) {
        stocksByName = new Stock[MAX_SIZE];
        stocksByWkn = new Stock[MAX_SIZE];
        verbose = paramVerboseOutput;
    }

    public StockHashMap() {
        this(false);
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
                stocksByName[iName] = deletedStock;
                stocksByWkn[iWKn] = deletedStock;
                used--;
            } else {
                System.out.println("PROBLEM?");
            }
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
}
