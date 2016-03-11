package at.sheldor5;

import at.sheldor5.hashmap.StockHashMap;
import at.sheldor5.stock.Stock;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static final StockHashMap map = new StockHashMap(true);
    private static Stock x;

    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        boolean running = false;
        try {
            while (running && (str = br.readLine()) != null) {
                switch (str) {
                    case "ADD":
                        System.out.println("Adding new Stock ...");
                        break;
                    case "DEL":
                        System.out.println("Deleting Stock ...");
                        break;
                    case "IMPORT":
                        System.out.println("Importing Stocks ...");
                        break;
                    case "SEARCH":
                        System.out.println("Searching for Stock ...");
                        break;
                    case "PLOT":
                        System.out.println("Ploting Stock ...");
                        break;
                    case "SAVE":
                        System.out.println("Saving Stocks ...");
                        break;
                    case "QUIT":
                        System.out.println("Quitting ...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid input, try again");
                        break;
                }
            }

        } catch (final Exception e) {

        }

        if (running) {
            return;
        }

        Stock s = new Stock("Microsoft Corporation", "870747", "MSFT", "E:/table.csv");
        map.put(s);
        for (int i = 0; i < 1000; i++) {
            map.put(new Stock(String.format("Name%s", i), String.format("Code%s", i), String.format("WKN%s", i), "C:/"));
        }
        get("Name500");
        get("asdf");
        get("WKN300");
        get("1234");

        remove("Name500");
        remove("asdf");
        remove("WKN300");
        remove("1234");
    }

    private static void remove(final String paramKey) {
        System.out.println(map.getStockCount());
        map.remove(paramKey);
        System.out.println(map.getStockCount());
        x = map.get(paramKey);
        if (x != null) {
            System.out.println(x.wkn);
        }
    }

    private static void get(final String paramKey) {
        x = map.get(paramKey);
        if (x != null) {
            System.out.println(x.name);
        }
    }
}
