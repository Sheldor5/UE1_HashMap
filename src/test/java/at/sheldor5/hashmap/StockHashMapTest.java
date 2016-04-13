package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 18.03.2016.
 */
public class StockHashMapTest {

    private static final int buckets = 1499;

    private static final StockHashMap map = new StockHashMap(buckets);
    private static final StockHashMap map2 = new StockHashMap(buckets);
    private static final Stock testStock = new Stock("Stock #1", "0001", "STK1", null);
    private static final Stock[] stocks = new Stock[1200];
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

    @Before
    public void setUp() {
        for (int i = 0; i < 1200; i++) {
            stocks[i] = new Stock("Stock" + i, "" + i, "S"+i, null);
        }
    }

    @Test
    public void testPerformance() {
        System.out.println("# TESTING PERFORMANCE BY PUTTING 1200 STOCKS INTO THE HASHMAP BY SEARCHING IN BOTH ARRAYS FOR NEXT FREE INDEX");
        long start = System.nanoTime();
        for (int i = 0; i < 1200; i++) {
            map.put(stocks[i]);
        }
        long end = System.nanoTime();
        double time = (end - start) / 1000000.0;
        System.out.format("> Insertion took %.2f milliseconds\n\n", time);

        Assert.assertEquals((double) 1200 / buckets, map.getLoadFactor(), 0.0001);

        /*System.out.println("# TESTING PERFORMANCE BY PUTTING 1200 STOCKS INTO THE HASHMAP BY ADDING OFFSET OF HASHES TO THE INDEX");
        start = System.nanoTime();
        for (int i = 0; i < 1200; i++) {
            map2.put__(stocks[i]);
        }
        end = System.nanoTime();
        time = (end - start) / 1000000.0;
        System.out.format("> Insertion took %.2f milliseconds\n\n", time);

        for (int i = 0; i < buckets; i++) {
            if (map.stocksByWkn[i] != null && map2.stocksByWkn[i] != null && !map.stocksByWkn[i].equals(map2.stocksByWkn[i])) {
                System.out.println(map.stocksByWkn[i].name);
                System.out.println(map2.stocksByWkn[i].name);
                break;
            } else if (map.stocksByName[i] != null && map2.stocksByName[i] != null && !map.stocksByName[i].equals(map2.stocksByName[i])) {
                System.out.println(map.stocksByName[i].name);
                System.out.println(map2.stocksByName[i].name);
                break;
            }
            //Assert.assertEquals(map.stocksByWkn[i], map2.stocksByWkn[i]);
            //Assert.assertEquals(map.stocksByName[i], map2.stocksByName[i]);
        }*/
    }

    @Test
    public void testPutGetRemove() {
        System.out.println("# TESTING STANDARD OPERATIONS WITH " + map.getStockCount() + " STOCKS LOADED\n");

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
        System.out.println("> Advanced square exploration:\t" + unused + " buckets were not hit, defined by (h+((-1)^(i+1))*((i/2)^2)%m)");
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

    @AfterClass
    public static void closeTest() {
        System.out.println("\n");
    }
}
