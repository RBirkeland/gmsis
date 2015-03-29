package GMsis.booking;
import org.joda.time.LocalDate;
import java.sql.Date;
import org.joda.time.DateTimeZone;

public class ldtod {
    public static final DateTimeZone jodaTzUTC = DateTimeZone.forID("UTC");
    public static Date ldtodate(int y, int m, int d)
    {
        LocalDate ld = new LocalDate(y,m,d);
        Date date = new java.sql.Date(ld.toDateTimeAtStartOfDay(jodaTzUTC).getMillis());
        return date;
    }
}
