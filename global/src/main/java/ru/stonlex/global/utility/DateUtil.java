package ru.stonlex.global.utility;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateUtil {

    public static final Date DATE_FORMATTER = new Date();

    public static final String DEFAULT_DATETIME_PATTERN = ("dd.MM.yyyy h:mm:ss a");
    public static final String DEFAULT_DATE_PATTERN     = ("EEE, MMM d, yyyy");
    public static final String DEFAULT_TIME_PATTERN     = ("h:mm a");


    public String formatPattern(@NonNull String pattern) {
        return createDateFormat(pattern).format(DATE_FORMATTER);
    }

    public String formatTime(long millis, @NonNull String pattern) {
        return createDateFormat(pattern).format(new Time(millis));
    }


    private DateFormat createDateFormat(@NonNull String pattern) {
        return new SimpleDateFormat(pattern);
    }


    @SneakyThrows
    public Date parseDate(@NonNull String datePattern,
                          @NonNull String formattedDate) {

        return createDateFormat(datePattern).parse(formattedDate);
    }

}
