package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class StockHashMap implements Serializable {

    /**
     * Arrays to hold the Stocks.
     * One to access Stocks by name and one to access Stocks by Wkn
     */
    private final Stock[] stocksByName;
    private final Stock[] stocksByWkn;

    /**
     * Defaults
     */
    private static final int DEFAULT_SIZE = 1499;
    private static final Stock DELETED_OBJECT = new Stock("", "", "", null);
    private static final double MAX_LOAD = 0.9;

    /**
     *
     */
    private final int size;
    public boolean verbose = false;
    private double load = 0.0;
    private int used = 0;

    /**
     * Constructor to specify internal array size.
     * @param paramSize Amount of buckets to use.
     */
    public StockHashMap(int paramSize) {
        size = paramSize;
        stocksByName = new Stock[paramSize];
        stocksByWkn = new Stock[paramSize];
    }

    /**
     * Constructor to specify internal array size and verbose output.
     * @param paramVerboseOutput If true, outputs more information.
     * @param paramSize Amount of buckets to use.
     */
    public StockHashMap(final boolean paramVerboseOutput, int paramSize) {
        this(paramSize);
        verbose = paramVerboseOutput;
    }

    /**
     * Constructor to specify verbose output.
     * @param paramVerboseOutput If true, outputs more information.
     */
    public StockHashMap(final boolean paramVerboseOutput) {
        this(DEFAULT_SIZE);
        verbose = paramVerboseOutput;
    }

    /**
     * Default constructor.
     */
    public StockHashMap() {
        this(DEFAULT_SIZE);
    }

    /**
     * Insert a new Stock into the HashMap.
     * @param paramStock The Stock to insert.
     */
    public void put(final Stock paramStock) {
        if (load > MAX_LOAD) {
            System.out.println("Error, insertion failed: max load limit reached, remove elements before putting new ones in!");
            return;
        }
        int hash;
        int index;

        // store by name
        hash = hash(paramStock.name);
        index = getNextFreeIndex(stocksByName, hash, paramStock);
        stocksByName[index] = paramStock;
        System.out.print(hash + " - ");

        // store by wkn
        hash = hash(paramStock.wkn);
        index = getNextFreeIndex(stocksByWkn, hash, paramStock);
        stocksByWkn[index] = paramStock;
        System.out.println(hash);

        // internal data
        used++;
        load = used / size;
    }

    /**
     * Insert a new Stock into the HashMap.
     * @param paramStock The Stock to insert.
     */
    @SuppressWarnings("unused")
    public void put__(final Stock paramStock) {
        if (load > MAX_LOAD) {
            System.out.println("Error, insertion failed: max load limit reached, remove elements before putting new ones in!");
            return;
        }
        int h1, h2;
        int index;

        h1 = hash(paramStock.name);
        h2 = hash(paramStock.wkn);

        System.out.println(h1 + " - " + h2);

        int offset;

        if (h1 < h2) {
            offset = h2 - h1;
        } else {
            offset = h1 - h2;
        }

        System.out.println("Offset: " + offset);

        // store by wkn
        index = getNextFreeIndex(stocksByWkn, h2, paramStock);
        stocksByWkn[index] = paramStock;
        System.out.print(index + " : ");

        index = (index+offset) % size;
        stocksByName[index] = paramStock;
        System.out.println(index);

        // internal data
        used++;
        load = used / size;
    }

    /**
     * Get the next free index in the desired array.
     * @param paramArray The array to use.
     * @param paramHash The hash of key.
     * @return The next free index.
     */
    private int getNextFreeIndex(final Stock[] paramArray, int paramHash, final Stock paramStock) {
        if (paramHash < 0) {
            paramHash *= -1;
        }
        int i = paramHash;
        int c = 0;

        /*
         * Conditions for free index:
         *      - Bucket at index is empty (null)
         *      OR
         *      - Stock is the DELETED_OBJECT place holder
         *
         * Otherwise use square exploration to calculate the next index and check again.
         */
        while (paramArray[i] != null && paramArray[i] != DELETED_OBJECT) {
            i = getNextIndex(paramHash, ++c);
        }

        if (verbose) {
            System.out.println("Found free index for hash " + paramHash + " after " + c + " collisions");
        }

        return i;
    }

    /**
     * Remove and return Stock by key.
     * @param paramKey The key to identify the stock.
     * @return The removed stock, null otherwise.
     */
    public Stock remove(final String paramKey) {
        Stock result = null;
        int iName, iWkn;

        // search by Wkn
        iWkn = getIndexOfKey(stocksByWkn, paramKey);

        if (iWkn < 0) {
            // if not found by Wkn, search by Name
            iName = getIndexOfKey(stocksByName, paramKey);
            if (iName >= 0) {
                // if search by Name was successful, search again by the now known Wkn
                iWkn = getIndexOfKey(stocksByWkn, stocksByName[iName].wkn);
            } else {
                // handled by *
            }
        } else {
            // if search by Name was successful, search again by the now known Wkn
            iName = getIndexOfKey(stocksByName, stocksByWkn[iWkn].name);
        }

        if (iName < 0 && iWkn < 0) {
            // * Key does not lead to a known Stock
            if (verbose) {
                System.out.println("Key \"" + paramKey + "\" not found");
            }
        } else if (iName < 0 || iWkn < 0) {
            // Arrays are inconsistent
            System.out.println("/!\\ FATAL ERROR: INTERNAL ARRAYS ARE INCONSISTENT /!\\");
        } else {
            if (verbose) {
                System.out.println("Stock " + stocksByName[iName].toString() + " will be deleted");
            }
            if (stocksByName[iName] != null && stocksByName[iName].equals(stocksByWkn[iWkn])) {
                // if both Stocks (the one found by Name and the other one found by Wkn) are really the same
                result = stocksByName[iName];
                // delete by replacing Stocks with DELETED_OBJECT place holder to
                // not break the search chain wich uses square exploration
                stocksByName[iName] = DELETED_OBJECT;
                stocksByWkn[iWkn] = DELETED_OBJECT;
                used--;
                load = used / size;
            } else {
                // well, fuck?
                System.exit(-1);
            }
        }
        return result;
    }

    /**
     * Get a Stock by its Name or Wkn, searches in both arrays so
     * user does not need to specify if the key is the Name or Wkn.
     *
     * @param paramKey
     * @return
     */
    public Stock get(final String paramKey) {
        Stock result = null;
        int i;
        // search by Wkn
        i = getIndexOfKey(stocksByWkn, paramKey);
        if (i < 0) {
            // if not found by Wkn, search by Name
            i = getIndexOfKey(stocksByName, paramKey);
            if (i < 0) {
                // if also not found by Name, not found in general
                if (verbose) {
                    System.out.println("Key \"" + paramKey + "\" not found");
                }
            } else {
                result = stocksByName[i];
            }
        } else {
            result = stocksByWkn[i];
        }

        return result;
    }

    /**
     * Search key in array.
     *
     * @param paramArray The array to search in.
     * @param paramKey The key to use for calculating the index.
     * @return Index of array, -1 if not found.
     */
    private int getIndexOfKey(final Stock[] paramArray, final String paramKey) {
        int result = -1;

        // handle invalid key
        if (paramKey == null || paramKey.isEmpty()) {
            return result;
        }

        // get initial hash
        int hash = hash(paramKey);

        // setting up helper variables for new search
        boolean n = stocksByName.equals(paramArray) ? true : false;
        int i = hash;
        int c = 0;

        if (n) {
            // search by name
            while (paramArray[i] != null) {
                if (paramKey.equals(paramArray[i].name)) {
                    result = i;
                    if (verbose) {
                        System.out.println("Found \"" + paramKey + "\" by Name after " + c + " collisions");
                    }
                    break;
                }
                // get next index
                i = getNextIndex(hash, ++c);
            }
        } else {
            // search by wkn
            while (paramArray[i] != null) {
                if (paramKey.equals(paramArray[i].wkn)) {
                    result = i;
                    if (verbose) {
                        System.out.println("Found \"" + paramKey + "\" by WKN after " + c + " collisions");
                    }
                    break;
                }
                // get next index
                i = getNextIndex(hash, ++c);
            }
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
        return size;
    }

    /**
     * Use simple square exploration to get the next index.
     * @param h The hash of the key.
     * @param i The collision count of how many times this hash has already collided.
     * @return The next calculated index.
     */
    @Deprecated
    protected int getNextIndex__(int h, int i) {
        h = (h + (i*i)) % size;
        if (h < 0) {
            h += size;
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
        h = (h + (a * b)) % size;
        if (h < 0) {
            h += size;
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
        int hash = paramKey.hashCode() % size;
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
        return load;
    }

    /**
     * TODO
     * @return
     */
    public int getStockCount() {
        return used;
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unused")
    public List<Stock> getAll() {
        final List<Stock> list = new ArrayList<>();
        Stock s;
        for (int i = 0; i < size; i++) {
            s = stocksByName[i];
            if (s != null && s != DELETED_OBJECT) {
                list.add(s);
            }
        }
        return list;
    }
}
