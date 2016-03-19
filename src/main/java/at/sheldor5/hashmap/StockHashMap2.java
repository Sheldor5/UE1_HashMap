package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class StockHashMap2 extends HashMap {

    private final Slot[] slot = new Slot[DEFAULT_SIZE];

    public StockHashMap2(final boolean paramVerboseOutput) {
        verbose = paramVerboseOutput;
    }

    public StockHashMap2() {
        this(false);
    }

    public void put(final Stock paramStock) {
        if (paramStock == null) {
            return;
        }

        int hash;
        int index;

        // store by name
        hash = hash(paramStock.name);
        index = getNextFreeIndex(hash, true);
        if (slot[index] == null) {
            slot[index] = new Slot();
        }
        slot[index].byName = paramStock;

        // store by wkn
        hash = hash(paramStock.wkn);
        index = getNextFreeIndex(hash, false);
        if (slot[index] == null) {
            slot[index] = new Slot();
        }
        slot[index].byWkn = paramStock;

        used++;
    }

    private int getNextFreeIndex(int paramHash, boolean byName) {
        if (paramHash < 0) {
            paramHash *= -1;
        }

        int i = paramHash;
        int c = 0;

        if (byName) {
            while (slot[i] != null && slot[i].byName != null && slot[i].byName.name != null) {
                i = getNextIndex(paramHash, ++c);
            }
        } else {
            while (slot[i] != null && slot[i].byWkn != null && slot[i].byWkn.wkn != null) {
                i = getNextIndex(paramHash, ++c);
            }
        }
        if (verbose) {
            System.out.println("Found free slot after " + c + " collisions");
        }

        return i;
    }

    public void remove(String paramKey) {
        if (paramKey == null) {
            return;
        }

        int c = 0;
        int x = 0;
        int hash = hash(paramKey);

        boolean byName = true;
        boolean byWkn = true;
        boolean b = true;
        int i = hash;
        Stock stock;

        while (slot[i] != null && (byName || byWkn)) {
            if ((stock = slot[i].getByKey(paramKey)) != null) {
                if (byName && stock.equals(slot[i].byName)) {
                    paramKey = slot[i].byName.wkn;
                    slot[i].byName = DELETED_OBJECT;
                    i = hash = hash(paramKey);
                    x = 0;
                    b = false;
                    byName = false;
                } else if (byWkn && stock.equals(slot[i].byWkn)) {
                    paramKey = slot[i].byWkn.name;
                    slot[i].byWkn = DELETED_OBJECT;
                    i = hash = hash(paramKey);
                    x = 0;
                    b = false;
                    byWkn = false;
                }
            }
            c++;
            if (b) {
                i = getNextIndex(hash, ++x);
            } else {
                b = true;
            }
        }

        if (byName && byWkn) {
            System.out.println("No stock with key \"" + paramKey + "\" found to delete");
        } else if (byName || byWkn) {
            System.out.println("FATAL ERROR");
        } else {
            used--;
            System.out.println("Removed stock with key \"" + paramKey + "\" after " + c + " collisions");
        }
    }

    public Stock get(final String paramKey) {
        Stock result = null;
        if (paramKey == null) {
            return null;
        }

        int c = 0;
        int hash = hash(paramKey);

        boolean byName = true;
        boolean byWkn = true;
        int i = hash;
        Slot sl;
        Stock st;

        while ((byName || byWkn) && (sl = slot[i]) != null) {
            if (byName && ((st = sl.byName) == null || sl.byName.name == null || sl.byName.equals(DELETED_OBJECT))) {
                byName = false;
            } else if (byWkn && ((st = sl.byWkn) == null || sl.byWkn.name == null || sl.byWkn.equals(DELETED_OBJECT))) {
                byWkn = false;
            }
            if (byName &&  sl.byName.equals(sl.byName)) {
                result = slot[i].byName;
                byName= false;
            } else if (byWkn && sl.byWkn.equals(sl.byWkn)) {
                result = slot[i].byWkn;
                byWkn = false;
            }
            i = getNextIndex(hash, ++c);
        }

        if (verbose) {
            if (result == null) {
                System.out.println("No stock with key \"" + paramKey + "\" found to delete");
            } else {

            }
        }

        return result;
    }

    public double getLoadFactor() {
        return used / DEFAULT_SIZE;
    }

    public int getStockCount() {
        return (int) used;
    }
}
