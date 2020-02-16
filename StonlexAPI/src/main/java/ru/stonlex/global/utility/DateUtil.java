package ru.stonlex.global.utility;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@UtilityClass
public class DateUtil {

    static {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");

        TimeZone.setDefault( timeZone );
    }

    /**
     * Получить дату по указанному формату
     *
     * @param pattern - формат
     */
    public String getDate(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * Получить дату по стандартному формату
     */
    public String getDate() {
        return getDate("dd/MM/yyyy");
    }

    /**
     * Получить дату и время по стандартному формату
     */
    public String getDateWithTime() {
        return getDate("dd/MM/yyyy HH:mm:ss");
    }

    /**
     * Получить Timestamp по количеству миллисекунд
     *
     * @param mills - кол-во миллисекунд
     */
    public Timestamp getTimestamp(long mills) {
        return new Timestamp(mills);
    }

    /**
     * Получить количество миллисекунд из Timestamp
     *
     * @param timestamp - timestamp
     */
    public long fromTimestamp(Timestamp timestamp) {
        return timestamp.getTime();
    }

}
