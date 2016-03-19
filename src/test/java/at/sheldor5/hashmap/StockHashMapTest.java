package at.sheldor5.hashmap;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 18.03.2016.
 */
public class StockHashMapTest {

    private static final int hashMapBuckets = 1499;

    private static HashMap map = new StockHashMap(hashMapBuckets);
    private static final int[] tmp = new int[hashMapBuckets];

    private static int unused = 0;

    private static final String l = "#######################################################";

    @Test
    public void testSquareExploratory() {
        System.out.println();
        System.out.println(l);
        System.out.println("#                                                     #");
        System.out.println("#             TESTING SQUARE EXPLORATION              #");
        System.out.println("#                                                     #");
        System.out.println("#             h = hash(key)                           #");
        System.out.println("#             i = collision count                     #");
        System.out.println("#             m = buckets                             #");
        System.out.println("#                                                     #");
        System.out.println(l);

        int x;

        System.out.println();
        System.out.println("# Testing with " + hashMapBuckets + " buckets");

        System.out.println();
        System.out.println("# Testing simple square exploration (h+(i*i)%m):");

        reset();
        for (int i = 0; i < hashMapBuckets; i++) {
            x = map.getNextIndex(1, i);
            tmp[x]++;
        }
        check();
        print();

        System.out.println("# Testing advanced square exploration (h+((-1)^(i+1))*((i/2)^2)%m):");

        reset();
        for (int i = 0; i < hashMapBuckets; i++) {
            x = map.getIndex(1, i);
            tmp[x]++;
        }
        check();
        print();
    }

    public void reset() {
        for (int i = 0; i < hashMapBuckets; i++) {
            tmp[i] = 0;
        }
        unused = 0;
    }

    private void check() {
        for (int i = 0; i < hashMapBuckets; i++) {
            if (tmp[i]==0) {
                unused++;
            }
        }
    }

    private void print() {
        System.out.println("> " + unused + " buckets were not hit");
        System.out.println();
    }
}
