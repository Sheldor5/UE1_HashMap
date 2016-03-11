package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 11.03.2016.
 */
public interface HashMapInt {

    void put(final Stock paramStock);
    Stock get(final String paramKey);
    void remove(final String paramKey);
}
