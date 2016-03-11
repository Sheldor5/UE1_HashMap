package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 11.03.2016.
 */
public class Slot {

    public Stock byName = null;
    public Stock byWkn = null;

    public Stock getByKey(final String paramKey) {
        if (paramKey == null) {
            return null;
        } else if (byName != null && byName.name != null && byName.name.equals(paramKey)) {
            return byName;
        } else if (byWkn != null && byWkn.wkn != null && byWkn.wkn.equals(paramKey)) {
            return byWkn;
        }
        return null;
    }

}
