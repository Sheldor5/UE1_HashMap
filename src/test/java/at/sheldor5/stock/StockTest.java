package at.sheldor5.stock;

import org.junit.Test;

import java.util.List;

/**
 * Created by andre on 11/04/2016.
 */
public class StockTest {
    @Test
    public void testStock() {
        Stock xbox = new Stock("Microsoft Corporation", "870747", "MSFT", "msft.csv");
        try {

            List<HistoryEntry> liste = xbox.getHistory().getHistoryEntries();
            double maxClose = 0.0;
            double minClose = Double.MAX_VALUE;
            for (int i = 0; i < liste.size(); i++) {
                HistoryEntry y = liste.get(i);
                if (maxClose < y.close) {
                    maxClose = y.close;
                }
                if (minClose > y.close) {
                    minClose = y.close;
                }
            }
            double scale = (30 / (maxClose - minClose));


            for (int y = 0; y < 30; y++) {
                HistoryEntry k = liste.get(y);
                double wert=(maxClose);
                wert *=100;
                wert = Math.round(wert);
                wert /=100;
                k.close -= minClose;
                k.close *= scale;

                if(y==0){
                    System.out.print(String.format("%.2f| ",wert));
                }
                if(k.close == (maxClose-minClose)*scale){
                    System.out.print("+");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();

            String s;
            int z=0;
            for (int j = 27; j >= 0; j--) {
                HistoryEntry x = liste.get(j);
                double wert= (maxClose - ++z*((maxClose-minClose)/30));
                wert*= 100;
                wert = Math.round(wert);
                wert /= 100;

                if(wert<10.0){
                    System.out.print(String.format(" %.2f| ",wert));
                }
                else{
                    System.out.print(String.format("%.2f| ",wert));
                }

                s = "";

                for (int i = 0; i < liste.size(); i++) {

                    HistoryEntry y = liste.get(i);
                    if (y.close > j) {
                        s += "+";
                    } else {
                        s += " ";
                    }
                }

                System.out.println(s);

            }

            double wert =(minClose);
            wert *=100;
            wert = Math.round(wert);
            wert /=100;

            if(wert<10.0){
                System.out.print(String.format(" %.2f| ",wert));
            }
            else{
                System.out.print(String.format("%.2f| ",wert));;
            }
            for(int i=0;i<30;i++){
                System.out.print('+');
            }
            System.out.println();
        }

            catch(Exception e){
            }
        }

}


