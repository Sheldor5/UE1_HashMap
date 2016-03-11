package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 11.03.2016.
 */
public class MyHashMapEntry {

    public final Stock stock;
    public final int index;

    public MyHashMapEntry(final Stock paramStock, final int paramIndex) {
        stock = paramStock;
        index = paramIndex;
    }
}
