package at.sheldor5;

import at.sheldor5.hashmap.StockHashMap;
import at.sheldor5.stock.Stock;
import at.sheldor5.util.FileUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Main {

    private static StockHashMap map = new StockHashMap();

    private static Stock stock;

    public static void main(final String[] args) {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        boolean running = true;
        try {
            System.out.print("#>");
            while (running && (str = br.readLine()) != null) {
                String[] s = str.split(" ");
                for (String a : s) {
                    System.out.println("\"" + a + "\"");
                }
                if (s.length < 1) {
                    invalid();
                } else {
                    switch (s[0]) {
                        case "ADD":
                            if (s.length == 4) {
                                stock = new Stock(s[1], s[2], s[3], null);
                                map.put(stock);
                            } else if (s.length == 5) {
                                stock = new Stock(s[1], s[2], s[3], s[4]);
                                map.put(stock);
                            } else {
                                invalid();
                            }
                            break;
                        case "DEL":
                            if (s.length == 2) {
                                map.remove(s[1]);
                            } else {
                                invalid();
                            }
                            break;
                        case "IMPORT":

                            break;
                        case "SEARCH":
                            if (s.length >= 2) {
                                stock = map.get(s[1]);
                            } else {
                                invalid();
                            }
                            break;
                        case "PLOT":
                            System.out.println("Ploting Stock ...");
                            break;
                        case "SAVE":
                            if (s.length == 2) {
                                FileUtils.serialize(map, s[1]);
                            } else {
                                invalid();
                            }
                            break;
                        case "LOAD":
                            if (s.length == 2) {
                                map = (StockHashMap) FileUtils.deserialize(s[1]);
                            } else {
                                invalid();
                            }
                            break;
                        case "QUIT":
                            running = false;
                            break;
                        case "VERBOSE":
                            map.verbose = !map.verbose;
                            System.out.println("Toggled verbose output to: " + map.verbose);
                            break;
                        case "HELP":
                            System.out.println(FileUtils.getHelp());
                            break;
                        default:
                            invalid();
                            break;
                    }
                }
                System.out.print("#>");
            }

            System.out.println(" Bye!");

            System.out.println(running);
            System.out.println(str);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static void invalid() {
        System.out.println("Ungueltige Eingabe, versuchen Sie HELP");
    }
}
