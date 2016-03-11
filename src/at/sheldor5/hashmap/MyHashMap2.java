package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class MyHashMap2 {

    private final static int MAX_SIZE = 1009;
    //private final static int MAX_SIZE = 2003;
    private final static MyHashMapEntry deletedEntry = new MyHashMapEntry(new Stock("", "", "", ""), -1);

    private final boolean debug;
    private int used = 0;

    private final MyHashMapEntry[] stocksByName = new MyHashMapEntry[MAX_SIZE];
    private final MyHashMapEntry[] stocksByWkn = new MyHashMapEntry[MAX_SIZE];

    public MyHashMap2(final boolean paramDebug) {
        debug = paramDebug;
    }

    public MyHashMap2() {
        this(false);
    }

    public void put(final Stock paramStock) {
        // store by name
        int nameHash = paramStock.name.hashCode() % MAX_SIZE;
        int x = getNextFreeIndex(stocksByName, nameHash);
        stocksByName[x] = new MyHashMapEntry(paramStock, x);

        // store by wkn
        int wknHash = paramStock.wkn.hashCode() % MAX_SIZE;
        int y = getNextFreeIndex(stocksByWkn, wknHash);
        stocksByWkn[y] = new MyHashMapEntry(paramStock, y);

        used++;
    }

    private int getNextFreeIndex(final MyHashMapEntry[] paramArray, int paramHash) {
        if (paramHash < 0) {
            paramHash *= -1;
        }
        int i = paramHash;
        int c = 0;
        while (paramArray[i] != null && paramArray[i].index >= 0) {
            i = getNextIndex(paramHash, ++c);
        }
        if (debug) {
            System.out.println("Found free index for hash " + paramHash + " after " + c + " collisions");
        }
        return i;
    }

    private int getNextIndex(int hash, int c) {
        return (hash + c*c) % MAX_SIZE;
    }

    public void remove(final String paramString) {

    }

    private int getIndex(final String paramString) {
        int result;
        // handle invalid search key
        if (paramString == null) {
            return -1;
        }

        // get initial hash
        int hash = paramString.hashCode() % MAX_SIZE;

        // handle invalid hashes
        if (hash < 0) {
            hash *= -1;
        }

        // setting up helper variables for new search
        int i = hash;
        boolean searchByName = true;
        boolean searchByWkn = true;
        int c = 0;

        // search while at least one array hasn't hit an empty index
        while (searchByName || searchByWkn) {

            // only search in this array if the last hit was not null
            if (searchByName) {
                if (stocksByName[i] != null && paramString.equals(stocksByName[i].stock.name)) {
                    result = stocksByName[i].index;
                    searchByName = searchByWkn = false;
                    if (debug) {
                        System.out.println("Found \"" + paramString + "\" by Name after " + c + " collisions");
                    }
                } else if (stocksByName[i] == null) {
                    // if hit on this array was null, stop searching in this array
                    searchByName = false;
                }
            }

            // same as above
            if (searchByWkn) {
                if (stocksByWkn[i] != null && paramString.equals(stocksByWkn[i].stock.wkn)) {
                    result = stocksByWkn[i].index;
                    searchByName = searchByWkn = false;
                    if (debug) {
                        System.out.println("Found \"" + paramString + "\" by WKN after " + c + " collisions");
                    }
                } else if (stocksByWkn[i] == null) {
                    searchByWkn = false;
                }
            }

            // get next index
            i = getNextIndex(hash, ++c);
        }

        return 0;
    }

    public Stock get(final String paramString) {
        Stock result = null;

        // handle invalid search key
        if (paramString == null) {
            return null;
        }

        // get initial hash
        int hash = paramString.hashCode() % MAX_SIZE;

        // handle invalid hashes
        if (hash < 0) {
            hash *= -1;
        }

        // setting up helper variables for new search
        int i = hash;
        boolean searchByName = true;
        boolean searchByWkn = true;
        int c = 0;

        // search while at least one array hasn't hit an empty index
        while (searchByName || searchByWkn) {

            // only search in this array if the last hit was not null
            if (searchByName) {
                if (stocksByName[i] != null && paramString.equals(stocksByName[i].stock.name)) {
                    result = stocksByName[i].stock;
                    searchByName = searchByWkn = false;
                    if (debug) {
                        System.out.println("Found \"" + result.name + "\" after " + c + " collisions");
                    }
                } else if (stocksByName[i] == null) {
                    // if hit on this array was null, stop searching in this array
                    searchByName = false;
                }
            }

            // same as above
            if (searchByWkn) {
                if (stocksByWkn[i] != null && paramString.equals(stocksByWkn[i].stock.wkn)) {
                    result = stocksByWkn[i].stock;
                    searchByName = searchByWkn = false;
                    if (debug) {
                        System.out.println("Found \"" + result.wkn + "\" after " + c + " collisions");
                    }
                } else if (stocksByWkn[i] == null) {
                    searchByWkn = false;
                }
            }

            // get next index
            i = getNextIndex(hash, ++c);
        }

        if (debug && result == null) {
            System.out.println("No stock for \"" + paramString + "\" found, took " + c + " collisions to realize");
        }

        return result;
    }
}
