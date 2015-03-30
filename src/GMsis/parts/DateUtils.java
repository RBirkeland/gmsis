package GMsis.parts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static final String FMT = "dd/MM/yyyy";

    public static long sec2milli(long sec) {
        return TimeUnit.MILLISECONDS.convert(sec, TimeUnit.SECONDS);
    }

    public static long milli2sec(long milli) {
        return TimeUnit.SECONDS.convert(milli, TimeUnit.MILLISECONDS);
    }

    public static String date2str(Date date) {
        return new SimpleDateFormat(FMT).format(date);
    }

    public static Date str2date(String str) throws ParseException{
        return new SimpleDateFormat(FMT).parse(str);
    }

}
