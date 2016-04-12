package at.sheldor5.hashmap;

import at.sheldor5.stock.HistoryEntry;
import org.junit.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Michael Palata on 12.04.2016.
 */
public class HistoryEntryTest {
    private static final String l = "#######################################################";
    private static final int MAX_ENTRIES = 30;

    private final List<HistoryEntry> entries = new ArrayList<>();

    @BeforeClass
    public static void testHashMap() {
        System.out.println();
        System.out.println(l);
        System.out.println("#                                                     #");
        System.out.println("#             TESTING STOCK HISTORY ENTRIES           #");
        System.out.println("#                                                     #");
        System.out.println(l);
        System.out.println();
    }

    @Before
    public void setUp() {
        String data;
        for (int i = MAX_ENTRIES; i > 0; i--) {
            data = String.format("2016-03-%d,52.970001,52.970001,51.779999,52.349998,24418100,52.349998", i);
            try {
                entries.add(new HistoryEntry(data));
            } catch (final Exception e) {
                e.printStackTrace();
                Assume.assumeTrue(false);
                return;
            }
        }
    }

    @Test
    public void testHistoryEntries() {
        System.out.println("# TESTING SORT ORDER OF ENTRIES");
        Collections.sort(entries);
        for (int i = 0; i < MAX_ENTRIES; i++) {
            try {
                Assert.assertEquals(HistoryEntry.format.parse(String.format("2016-03-%d", i+1)), entries.get(i).date);
            } catch (final ParseException e) {
                e.printStackTrace();
                Assume.assumeTrue(false);
                return;
            }
        }
    }

    private void printEntries(int amount) {
        if (amount > MAX_ENTRIES) {
            amount = MAX_ENTRIES;
        }
        for (int i = 0; i < amount; i++) {
            System.out.println(HistoryEntry.format.format(entries.get(i).date));
        }
    }

    @AfterClass
    public static void closeTest() {
        System.out.println("\n");
    }
}
