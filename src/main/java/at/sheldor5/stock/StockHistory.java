package at.sheldor5.stock;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class StockHistory implements Serializable {
    private static final int MAX_ENTRIES = 30;

    private final File file;
    private final List<HistoryEntry> historyEntries = new ArrayList<>();

    public StockHistory(final String paramPathToHistoryFile) {
        file = new File(paramPathToHistoryFile);
    }

    public final List<HistoryEntry> getHistoryEntries() {
        if (file == null) {
            return null;
        } else if (historyEntries.size() != 0) {
            return historyEntries;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine(); // skip first line
            int i = 0;
            String line;
            while (i++ < MAX_ENTRIES && (line = br.readLine()) != null) {
                historyEntries.add(new HistoryEntry(line));
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
        return historyEntries;
    }
}
