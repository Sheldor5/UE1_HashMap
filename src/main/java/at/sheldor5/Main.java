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
        String inputLine = "";
        boolean running = true;
        try {
            System.out.print("#>");
            String command;
            String[] params = {};
            while (running && (inputLine = br.readLine()) != null) {
                String[] split = inputLine.split(" ");
                if (split.length < 1) {
                    invalid();
                } else {
                    command = split[0];
                    if (split.length > 1) {
                        params = split[1].split(":");
                    }
                    for (final String s : params) {
                        s.trim();
                    }
                    switch (command) {
                        case "ADD":
                            if (params.length == 4) {
                                stock = new Stock(params[1], params[2], params[3], null);
                                map.put(stock);
                            } else if (params.length == 5) {
                                stock = new Stock(params[1], params[2], params[3], params[4]);
                                map.put(stock);
                            } else {
                                invalid();
                            }
                            break;
                        case "DEL":
                            if (params.length == 1) {
                                map.remove(stock.wkn);
                            } else if (params.length == 2) {
                                map.remove(params[1]);
                            } else {
                                invalid();
                            }
                            break;
                        case "IMPORT":
                            if (params.length == 2) {
                                if (stock != null) {
                                    stock.load(params[1]);
                                } else {
                                    System.out.println("Keine Aktie ausgewählt");
                                }
                            } else {
                                invalid();
                            }
                            break;
                        case "SEARCH":
                            if (params.length >= 2) {
                                stock = map.get(params[1]);
                            } else {
                                invalid();
                            }
                            break;
                        case "PLOT":
                            Stock.plot(stock, 30);
                            break;
                        case "SAVE":
                            if (params.length == 2) {
                                FileUtils.serialize(map, params[1]);
                            } else {
                                invalid();
                            }
                            break;
                        case "LOAD":
                            if (params.length == 2) {
                                map = (StockHashMap) FileUtils.deserialize(params[1]);
                            } else {
                                invalid();
                            }
                            break;
                        case "QUIT":
                            running = false;
                            break;
                        case "VERBOSE":
                            map.verbose = !map.verbose;
                            System.out.println("Verbose output: " + map.verbose);
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
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static void invalid() {
        System.out.println("Ungueltige Eingabe, versuchen Sie HELP");
    }
}
