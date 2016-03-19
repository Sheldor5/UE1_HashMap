package at.sheldor5.stock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class StockHistory {
    private static final int MAX_ENTRIES = 30;

    private final File file;
    private final List<HistoryEntry> historyEntries = new ArrayList<>();

    public StockHistory(final String paramPathToHistoryFile) {
        file = new File(paramPathToHistoryFile);
    }

    public List<HistoryEntry> getHistoryEntries() throws IOException {
        if (file == null) {

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
        }
        return historyEntries;
    }
}
