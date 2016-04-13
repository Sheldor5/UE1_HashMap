package at.sheldor5.stock;

import org.junit.Test;

import java.util.List;

/**
 * Created by andre on 11/04/2016.
 */
public class StockTest {

    @Test
    public void testStock() {
        final Stock xbox = new Stock("Microsoft Corporation", "870747", "MSFT", "src/test/resources/msft.csv");
        Stock.plot(xbox, 30);
    }
}


