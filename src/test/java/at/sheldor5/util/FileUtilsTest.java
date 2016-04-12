package at.sheldor5.util;
import at.sheldor5.stock.HistoryEntry;
import at.sheldor5.stock.Stock;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Michael Palata on 11.04.2016.
 */
public class FileUtilsTest {

    private static final String testSer = "testStock.ser";

    private static final String l = "#######################################################";

    @BeforeClass
    public static void testHashMap() {
        System.out.println();
        System.out.println(l);
        System.out.println("#                                                     #");
        System.out.println("#             TESTING OBJECT SERIALIZATION            #");
        System.out.println("#                                                     #");
        System.out.println(l);
        System.out.println();
    }

    @Test
    public void testSerializing() {
        Stock stock = new Stock("Microsoft Corporation", "870747", "MSFT", "src/test/resources/msft.csv");
        Assert.assertEquals("Microsoft Corporation", stock.name);

        FileUtils.serialize(stock, testSer);

        stock = new Stock("HAHAHA", "1234", "ASDF", "");
        Assert.assertEquals("HAHAHA", stock.name);

        stock = (Stock) FileUtils.deserialize(testSer);
        Assert.assertEquals("Microsoft Corporation", stock.name);
    }

    @AfterClass
    public static void cleanup() {
        try {
            Files.delete(Paths.get(testSer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void closeTest() {
        System.out.println("\n");
    }
}
