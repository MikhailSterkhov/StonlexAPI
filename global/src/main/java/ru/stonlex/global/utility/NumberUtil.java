package ru.stonlex.global.utility;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class NumberUtil {

    @Getter
    private final ThreadLocalRandom random = ThreadLocalRandom.current();


    /**
     * Добавить знак после каждой третьей цифры
     *
     * @param number - число
     * @param symbol - знак
     */
    public String spaced(int number, String symbol) {
        String integer = String.valueOf(number);
        StringBuilder builder = new StringBuilder();

        for (int a = 0; a < integer.length(); a++) {
            builder.append( integer.split("")[a] );

            if ( (integer.length() - a + 2) % 3 != 0 ) {
                continue;
            }
            builder.append(symbol);
        }

        return builder.toString().substring(0, builder.toString().length() - 1);
    }

    /**
     * Добавить запятую после каждой третьей цифры
     *
     * @param number - число
     */
    public String spaced(int number) {
        return spaced(number, ",");
    }

    /**
     * Возвести число в степень
     *
     * @param number - число
     * @param radian - степень
     */
    public int toRadians(int number, int radian) {
        for (int count = 0; number < radian; number++) {
            number *= number;
        }
        return number;
    }

    /**
     * Создать массив, который будет иметь в себе
     * множество значений между минимальным и
     * максимальным указанным индексом
     *
     * @param minIndex - минимальный индекс
     * @param maxIndex - максимальный индекс
     */
    public int[] toManyArray(int minIndex, int maxIndex) {
        int[] resultArray = new int[maxIndex - minIndex];

        int counter = 0;
        for (int i = minIndex ; i < maxIndex ; i++) {
            resultArray[counter] = i;

            counter++;
        }

        return resultArray;
    }

    /**
     * Получить рандомное число
     *
     * @param min - минимальное значение
     * @param max - максимальное значение
     */
    public int random(int min, int max) {
        return min + random.nextInt(max - min);
    }

    /**
     * Преобразовать число в грамотно составленное
     * словосочетание
     *
     * @param number - число
     * @param one - словосочетание, если число закаончивается на 1
     * @param two - словосочетание, если число закаончивается на 2
     * @param three - словосочетание, если число закаончивается на 5
     */
    public String formatting(int number, String one, String two, String three) {
        if (number % 100 > 10 && number % 100 < 15) {
            return number + " " + three;
        }
        switch (number % 10) {
            case 1: {
                return number + " " + one;
            }
            case 2:
            case 3:
            case 4: {
                return number + " " + two;
            }
            default: {
                return number + " " + three;
            }
        }
    }

    /**
     * Преобразовать число в грамотно составленное
     * словосочетание
     *
     * @param number - число
     * @param unit - словосочетание
     */
    public String formatting(int number, TimeUnit unit) {
        return formatting(number, unit.getOne(), unit.getTwo(), unit.getThree());
    }

    /**
     * Получить грамотно составленное время из
     * количества секунд
     *
     * @param seconds - кол-во секунд
     */
    public String getTime(int seconds) {
        int minutes = 0, hours = 0, days = 0, weeks = 0, months = 0, years = 0;

        if (seconds >= 60) {
            int i = seconds / 60;
            seconds -= 60 * i;
            minutes += i;
        }

        if (minutes >= 60) {
            int i = minutes / 60;
            minutes -= 60 * i;
            hours += i;
        }

        if (hours >= 24) {
            int i = hours / 24;
            hours -= 24 * i;
            days += i;
        }

        if (days >= 7) {
            int i = days / 7;
            days -= 7 * i;
            weeks += i;
        }

        if (weeks >= 4) {
            int i = weeks / 4;
            weeks -= 4 * i;
            months += i;
        }

        if (months >= 12) {
            int i = months / 12;
            months -= 12 * i;
            years += i;
        }

        StringBuilder builder = new StringBuilder();

        if ( years != 0 ) {
            builder.append(formatting(years, TimeUnit.YEARS)).append(" ");
        }

        if ( months != 0 ) {
            builder.append(formatting(months, TimeUnit.MONTHS)).append(" ");
        }

        if ( weeks != 0 ) {
            builder.append(formatting(weeks, TimeUnit.WEEKS)).append(" ");
        }

        if ( days != 0 ) {
            builder.append(formatting(days, TimeUnit.DAYS)).append(" ");
        }

        if ( hours != 0 ) {
            builder.append(formatting(hours, TimeUnit.HOURS)).append(" ");
        }

        if ( minutes != 0 ) {
            builder.append(formatting(minutes, TimeUnit.MINUTES)).append(" ");
        }

        if ( seconds != 0 ) {
            builder.append(formatting(seconds, TimeUnit.SECONDS));
        }

        return builder.toString();
    }

    /**
     * Парсит такие значения, как 5d, 3m, 50s и т.д. в миллисекунды
     *
     * @author DonDays
     * @param time - значения, которое нужно парсить
     */
    public long parseTimeToMills(String time) {
        Pattern timePattern = Pattern.compile(
                "(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?" +
                "(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)" +
                "\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE
        );

        Matcher m = timePattern.matcher(time);

        int years = 0, months = 0, weeks = 0, days = 0, hours = 0, minutes = 0, seconds = 0;

        boolean found = false;

        while(m.find()) {
            if(m.group() != null && !m.group().isEmpty()) {
                for(int c = 0; c < m.groupCount(); ++c) {
                    if(m.group(c) != null && !m.group(c).isEmpty()) {
                        found = true;
                        break;
                    }
                }

                if(found) {
                    if(m.group(1) != null && !m.group(1).isEmpty()) {
                        years = Integer.parseInt(m.group(1));
                    }

                    if(m.group(2) != null && !m.group(2).isEmpty()) {
                        months = Integer.parseInt(m.group(2));
                    }

                    if(m.group(3) != null && !m.group(3).isEmpty()) {
                        weeks = Integer.parseInt(m.group(3));
                    }

                    if(m.group(4) != null && !m.group(4).isEmpty()) {
                        days = Integer.parseInt(m.group(4));
                    }

                    if(m.group(5) != null && !m.group(5).isEmpty()) {
                        hours = Integer.parseInt(m.group(5));
                    }

                    if(m.group(6) != null && !m.group(6).isEmpty()) {
                        minutes = Integer.parseInt(m.group(6));
                    }

                    if(m.group(7) != null && !m.group(7).isEmpty()) {
                        seconds = Integer.parseInt(m.group(7));
                    }
                    break;
                }
            }
        }

        if(!found) {
            throw new RuntimeException("Illegal Date");
        } else if(years > 20) {
            throw new RuntimeException("Illegal Date");
        } else {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            if(years > 0) {
                gregorianCalendar.add(Calendar.YEAR, years);
            }

            if(months > 0) {
                gregorianCalendar.add(Calendar.MONTH, months);
            }

            if(weeks > 0) {
                gregorianCalendar.add(Calendar.WEEK_OF_YEAR, weeks);
            }

            if(days > 0) {
                gregorianCalendar.add(Calendar.DATE, days);
            }

            if(hours > 0) {
                gregorianCalendar.add(Calendar.HOUR_OF_DAY, hours);
            }

            if(minutes > 0) {
                gregorianCalendar.add(Calendar.MINUTE, minutes);
            }

            if(seconds > 0) {
                gregorianCalendar.add(Calendar.SECOND, seconds);
            }

            return gregorianCalendar.getTimeInMillis();
        }
    }


    @RequiredArgsConstructor
    @Getter
    public enum TimeUnit {
        SECONDS("секунда", "секунды", "секунд"),
        MINUTES("минута", "минуты", "минут"),
        HOURS("час", "часа", "часов"),
        DAYS("день", "дня", "дней"),
        WEEKS("неделя", "недели", "недель"),
        MONTHS("месяц", "месяца", "месяцев"),
        YEARS("год", "года", "лет");

        private final String one;
        private final String two;
        private final String three;
    }

}
