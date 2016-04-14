package at.sheldor5;

import at.sheldor5.hashmap.StockHashMap;
import at.sheldor5.stock.Stock;
import at.sheldor5.util.FileUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Main {

    private static StockHashMap map = new StockHashMap();
    private static final String[] empty = {};

    private static Stock stock;
    private static Stock tmp;

    public static void main(final String[] args) {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = "";
        boolean running = true;
        try {
            System.out.print("#>");
            String command;
            String[] params;
            while (running && (inputLine = br.readLine()) != null) {
                String[] split = inputLine.split(" ", 2);
                if (split.length < 1) {
                    invalid();
                } else {
                    command = split[0].toLowerCase();
                    if (split.length > 1) {
                        params = split[1].split(";");
                        for (int i = 0; i < params.length; i++) {
                            params[i] = params[i].trim();
                        }
                    } else {
                        params = empty;
                    }
                    switch (command) {
                        case "add":
                            if (params.length == 3) {
                                stock = new Stock(params[0], params[1], params[2], null);
                                map.put(stock);
                            } else if (params.length == 4) {
                                stock = new Stock(params[0], params[1], params[2], params[3]);
                                map.put(stock);
                            } else {
                                invalid();
                            }
                            break;
                        case "del":
                            if (stock != null && params.length == 0) {
                                tmp = map.remove(stock.wkn);
                                if (tmp != null) {
                                    stock = tmp;
                                }
                            } else if (stock != null && params.length == 1) {
                                tmp = map.remove(params[0]);
                                if (tmp != null) {
                                    stock = tmp;
                                } else {
                                    System.out.println("Keine Aktie gefunden!");
                                }
                            } else if (stock == null) {
                                System.out.println("Keine Aktie ausgewählt!");
                            } else {
                                invalid();
                            }
                            break;
                        case "import":
                            if (params.length == 1) {
                                if (stock != null) {
                                    stock.load(params[0]);
                                } else {
                                    System.out.println("Keine Aktie ausgewählt");
                                }
                            } else {
                                invalid();
                            }
                            break;
                        case "search":
                            if (params.length >= 1) {
                                tmp = map.get(params[0]);
                                if (tmp != null) {
                                    stock = tmp;
                                } else {
                                    System.out.println("Keine Aktie gefunden!");
                                }
                            } else {
                                invalid();
                            }
                            break;
                        case "plot":
                            if (stock == null) {
                                System.out.println("Keine Aktie ausgewählt!");
                            } else {
                                Stock.plot(stock, 30);
                            }
                            break;
                        case "save":
                            if (params.length == 1) {
                                FileUtils.serialize(map, params[0]);
                            } else {
                                invalid();
                            }
                            break;
                        case "load":
                            if (params.length == 1) {
                                map = (StockHashMap) FileUtils.deserialize(params[0]);
                            } else {
                                invalid();
                            }
                            break;
                        case "quit":
                            running = false;
                            break;
                        case "verbose":
                            map.verbose = !map.verbose;
                            System.out.println("Verbose output: " + map.verbose);
                            break;
                        case "help":
                            System.out.println(FileUtils.getHelp());
                            break;
                        default:
                            invalid();
                            break;
                    }
                }
                if (stock != null) {
                    System.out.println("Ausgewaehlte Aktie: " + stock.name + " | " + stock.code + " | " + stock.wkn);
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
