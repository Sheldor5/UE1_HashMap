package at.sheldor5.stock;

import java.io.Serializable;
import java.util.List;

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

    public static void plot(final Stock paramStock, final int paramRows) {
        char c = '|';
        try {
            if (paramStock == null || paramStock.getHistory() == null
                    || paramStock.getHistory().getHistoryEntries() == null
                    || paramStock.getHistory().getHistoryEntries().size() == 0) {
                System.out.println("Aktie besitzt keine historischen Daten");
                return;
            }

            final List<HistoryEntry> list = paramStock.getHistory().getHistoryEntries();

            double maxClose = 0.0;
            double minClose = Double.MAX_VALUE;
            for (final HistoryEntry entry : list) {
                if (maxClose < entry.close) {
                    maxClose = entry.close;
                }
                if (minClose > entry.close) {
                    minClose = entry.close;
                }
            }

            double scale = (paramRows / (maxClose - minClose));

            for (int y = 0; y < paramRows; y++) {
                final HistoryEntry entry = list.get(y);
                double wert = round(maxClose, 2);
                entry.close -= minClose;
                entry.close *= scale;

                if (y == 0) {
                    System.out.print(String.format("%.2f| ", maxClose));
                }

                if (entry.close == (maxClose-minClose)*scale) {
                    System.out.print(c);
                } else {
                    System.out.print(' ');
                }
            }

            System.out.println();

            String s;
            double step = (maxClose - minClose) / paramRows;
            double current = maxClose - step;
            for (int j = paramRows-3; j >= 0; j--) {

                if (current < 10.0) {
                    System.out.print(String.format(" %.2f| ", current));
                } else {
                    System.out.print(String.format("%.2f| ", current));
                }

                current -= step;
                current = round(current, 2);

                s = "";

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).close > j) {
                        s += c;
                    } else {
                        s += ' ';
                    }
                }
                System.out.println(s);
            }

            minClose = round(minClose, 2);

            if(minClose<10.0){
                System.out.print(String.format(" %.2f| ", minClose));
            } else {
                System.out.print(String.format("%.2f| ", minClose));;
            }
            for(int i = 0; i < list.size(); i++){
                System.out.print(c);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double round(double paramDouble, int paramPrecision) {
        int precision = (int) Math.pow(10.0, paramPrecision);

        paramDouble *= precision;
        paramDouble = Math.round(paramDouble);
        paramDouble /= precision;

        return paramDouble;
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
