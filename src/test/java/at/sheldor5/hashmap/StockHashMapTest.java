package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 18.03.2016.
 */
public class StockHashMapTest {

    private static final StockHashMap map = new StockHashMap();
    private static final Stock testStock = new Stock("Stock #1", "0001", "STK1", null);
    private static Stock tmpStock;
    private static final int[] tmp = new int[map.getMaxSize()];

    private static int unused = 0;

    private static final String l = "#######################################################";

    @BeforeClass
    public static void testHashMap() {
        System.out.println();
        System.out.println(l);
        System.out.println("#                                                     #");
        System.out.println("#           TESTING HASH MAP WITH " + map.getMaxSize() + " buckets        #");
        System.out.println("#                                                     #");
        System.out.println(l);
        System.out.println();
    }

    @Test
    public void testPerformance() {
        System.out.println("# TESTING PERFORMANCE BY PUTTING 1000 STOCKS INTO THE HASHMAP");
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            map.put(new Stock("Stock" + i, "" + i, "S"+i, null));
        }
        long end = System.nanoTime();
        System.out.println("> Insertion took " + (end - start) / 1000000 + " milliseconds\n");
    }

    @Test
    public void testPutGetRemove() {
        System.out.println("# TESTING STANDARD OPERATIONS WITH " + map.getStockCount() + " STOCKS LOADED");

        // PUT
        map.put(testStock);

        // GET BY NAME
        tmpStock = map.get(testStock.name);
        Assert.assertEquals(testStock, tmpStock);

        // GET BY WKN
        tmpStock = map.get(testStock.wkn);
        Assert.assertEquals(testStock, tmpStock);

        // REMOVE
        tmpStock = map.remove(testStock.name);
        Assert.assertEquals(testStock, tmpStock);

        // CHECK IF REMOVED
        tmpStock = map.get(testStock.name);
        Assert.assertNull(tmpStock);
    }

    @Test
    public void testSquareExploratory() {
        System.out.println("# TESTING SQUARE EXPLORATION (h=hash(key), i=collision count, m=buckets)");

        reset();
        for (int i = 0; i < map.getMaxSize(); i++) {
            tmp[map.getNextIndex__(1, i)]++;
        }
        check();
        System.out.println("> Simple square exploration:\t" + unused + " buckets were not hit, defined by (h+(i*i)%m)");

        reset();
        for (int i = 0; i < map.getMaxSize(); i++) {
            tmp[map.getNextIndex(1, i)]++;
        }
        check();
        System.out.println("> Advanced square exploration:\t" + unused + " buckets were not hit, defined by (h+((-1)^(i+1))*((i/2)^2)%m)\n");
    }

    private void reset() {
        for (int i = 0; i < map.getMaxSize(); i++) {
            tmp[i] = 0;
        }
        unused = 0;
    }

    private void check() {
        for (int i = 0; i < map.getMaxSize(); i++) {
            if (tmp[i]==0) {
                unused++;
            }
        }
    }
}
