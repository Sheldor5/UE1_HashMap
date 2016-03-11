package at.sheldor5.stock;

import java.io.IOException;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class Stock {

    private final StockHistory history;
    public final String name;
    public final String code;
    public final String wkn;

    public Stock(final String paramName,  final String paramCode,  final String paramWkn, final String paramPathToHistoryFile) {
        if (paramPathToHistoryFile == null) {
            history = new StockHistory("");
        } else {
            history = new StockHistory(paramPathToHistoryFile);
        }
        name = paramName;
        code = paramCode;
        wkn = paramWkn;
    }

    public StockHistory getHistory() {
        return history;
    }
}
