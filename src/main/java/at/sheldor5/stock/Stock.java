package at.sheldor5.stock;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class Stock implements Serializable {

    private StockHistory history;
    public final String name;
    public final String code;
    public final String wkn;

    public Stock(final String paramName,  final String paramCode,  final String paramWkn, final String paramFilePath) {
        if (paramFilePath != null) {
            history = new StockHistory(paramFilePath);
        }
        name = paramName;
        code = paramCode;
        wkn = paramWkn;
    }

    public StockHistory getHistory() {
        return history;
    }

    public void load(final String paramFilePath) {
        history = new StockHistory(paramFilePath);
    }
}
