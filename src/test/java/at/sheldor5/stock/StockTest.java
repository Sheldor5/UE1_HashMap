package at.sheldor5.stock;

import org.junit.Test;

import java.util.List;

/**
 * Created by andre on 11/04/2016.
 */
public class StockTest {

    private static void plot(final Stock paramStock, final int paramRows) {

        if (paramStock == null || paramStock.getHistory() == null
                || paramStock.getHistory().getHistoryEntries() == null
                || paramStock.getHistory().getHistoryEntries().size() == 0) {
            System.out.println("Aktie besitzt keine historischen Daten");
        }

        List<HistoryEntry> list = paramStock.getHistory().getHistoryEntries();
        double[] values = new double[list.size()];

        double maxClose = 0.0;
        double minClose = Double.MAX_VALUE;

        // get min and max Close Value
        HistoryEntry entry;
        for (int i = 0; i < list.size(); i++) {
            entry = list.get(i);
            if (entry != null) {
                if (maxClose < entry.close) {
                    maxClose = entry.close;
                } else if (minClose > entry.close) {
                    minClose = entry.close;
                }
            }
            values[i] = entry.close;
        }

        // scale factor
        double scale = paramRows / (maxClose - minClose);
        double step = maxClose / paramRows;

        for (int y = 0; y < paramRows; y++) {
            for (int x = 0; x < list.size(); x++) {

            }
            if (y == 0) {

            } else if (y == paramRows-1) {

            } else {

            }
        }


        for (HistoryEntry entry2 : list) {
            entry2.close
        }

        double current;
        for (int i = paramRows; i >= 0; i--) {
            current = list.get(i).close;
        }

        for (int y = 0; y < paramRows; y++) {
            HistoryEntry k = list.get(y);
            double wert = round(maxClose, 2);
            k.close -= minClose;
            k.close *= scale;

            if(y==0){
                //String.format("%1$"+length+ "s", string);
                //System.out.print(String.format("%.2f " ,wert));
                System.out.print(String.format("%.2f " ,wert));
            }
            if(k.close == (maxClose-minClose)*scale){
                System.out.print("+");
            }else{
                System.out.print(" ");
            }
        }
        System.out.println();

        String s;
        int z = 0;
        for (int j = paramRows-3; j >= 0; j--) {
            HistoryEntry x = list.get(j);
            double value = round(maxClose - ++z*((maxClose-minClose)/paramRows), 2);

            System.out.print(String.format("% 2.2f| ",wert));

            s = "";

            for (int i = 0; i < list.size(); i++) {
                HistoryEntry y = list.get(i);
                if (i == 0) {

                }
                if (y.close > j) {
                    s += "+";
                } else {
                    s += " ";
                }
            }

            System.out.println(s);

        }

        double wert =round(minClose, 2);
        System.out.print(String.format("% 2.2f| ",wert));

        for(int i=0;i<30;i++){
            System.out.print('+');
        }
        System.out.println();
    }


    private static double round(double paramDouble, int paramPrecision) {
        int precision = (int) Math.pow(10.0, paramPrecision);

        paramDouble *= precision;
        paramDouble = Math.round(paramDouble);
        paramDouble /= precision;

        return paramDouble;
    }

    @Test
    public void testStock() {
        final Stock xbox = new Stock("Microsoft Corporation", "870747", "MSFT", "msft.csv");
        plot(xbox, 30);
    }

}


