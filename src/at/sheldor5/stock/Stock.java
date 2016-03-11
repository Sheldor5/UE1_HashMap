package at.sheldor5.stock;

import java.io.IOException;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class Stock {

    private StockHistory history;

    public final String name;
    public final String code;
    public final String wkn;

    public Stock(final String paramName,  final String paramCode,  final String paramWkn, final String paramPathToHistoryFile) {
        try {
            history = new StockHistory(paramPathToHistoryFile);
        } catch (final IOException e) {
            history = null;
        }
        name = paramName;
        code = paramCode;
        wkn = paramWkn;
    }

    public StockHistory getHistory() {
        return history;
    }
}
