package at.sheldor5.stock;

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


    @Override
    public boolean equals(final Object paramObject) {
        if (paramObject == null || !(paramObject instanceof Stock)) {
            return false;
        }
        final Stock stock = (Stock) paramObject;
        if (name.equals(stock.name) && code.equals(stock.code) && wkn.equals(stock.wkn)) {
            return true;
        }
        return false;
    }

    public StockHistory getHistory() {
        return history;
    }

    public void load(final String paramFilePath) {
        history = new StockHistory(paramFilePath);
    }
}
