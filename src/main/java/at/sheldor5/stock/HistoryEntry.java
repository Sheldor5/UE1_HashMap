package at.sheldor5.stock;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Michael Palata [github.com/Sheldor5] on 09.03.2016
 */
public class HistoryEntry implements Serializable, Comparable<HistoryEntry> {

    public static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Date date;
    public double open;
    public double high;
    public double low;
    public double close;
    public int volume;
    public double adj_close;

    public HistoryEntry(final String paramData) throws Exception {

        // paramData = "2016-03-03,52.970001,52.970001,51.779999,52.349998,24418100,52.349998"

        String[] str = paramData.split(",");

        // str[] = { "2016-03-03" , "52.970001" , "52.970001" , "51.779999" , "52.349998" , "24418100" , "52.349998" }
        if (str.length == 7) {
            date = format.parse(str[0]);
            open = Double.parseDouble(str[1]);
            high = Double.parseDouble(str[2]);
            low = Double.parseDouble(str[3]);
            close = Double.parseDouble(str[4]);
            volume = Integer.parseInt(str[5]);
            adj_close = Double.parseDouble(str[6]);
        } else {
            throw new Exception("Unknown data format");
        }
    }

    @Override
    public int compareTo(final HistoryEntry paramHistoryEntry) {
        return date.compareTo(paramHistoryEntry.date);
    }
}
