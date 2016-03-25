package at.sheldor5;

import at.sheldor5.hashmap.StockHashMap;
import at.sheldor5.stock.Stock;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static final StockHashMap map = new StockHashMap(true);
    private static Stock x;

    public static void init() {
        //Stock s = new Stock("Microsoft Corporation", "870747", "MSFT", "E:/table.csv");
        //map.put(s);
        for (int i = 0; i < (map.getMaxSize()/2); i++) {
            map.put(new Stock(String.format("Name%s", i), String.format("Code%s", i), String.format("WKN%s", i), "C:/"));
        }
    }

    public static void main(String[] args) {
        init();
    }

    private static void remove(final StockHashMap paramHashMap, final String paramKey) {
        System.out.println(paramHashMap.getStockCount());
        paramHashMap.remove(paramKey);
        System.out.println(paramHashMap.getStockCount());
        //x = paramHashMap.get(paramKey);
        if (x != null) {
            //System.out.println("Failed to remove " + x.toString() + ": " + x.name + " - " + x.wkn);
        }
    }

    private static void get(final StockHashMap paramHashMap, final String paramKey) {
        x = paramHashMap.get(paramKey);
        if (x != null) {
            System.out.println(x.toString() + ": " + x.name + " - " + x.wkn);
        }
    }

    public static void in() {

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
    }
}