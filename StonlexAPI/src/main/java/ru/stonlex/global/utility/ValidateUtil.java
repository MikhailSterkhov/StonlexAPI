package ru.stonlex.global.utility;

public final class ValidateUtil {

    /**
     * Проверить, является ли строка числом
     *
     * @param string - строка
     */
    public boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Проверить, является ли строка дробным числом
     *
     * @param string - строка
     */
    public boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Проверить, является ли строка Float
     *
     * @param string - строка
     */
    public boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Проверить, является ли строка Long
     *
     * @param string - строка
     */
    public boolean isLong(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Проверить, является ли строка Byte
     *
     * @param string - строка
     */
    public boolean isByte(String string) {
        try {
            Byte.parseByte(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Проверить, является ли строка Short
     *
     * @param string - строка
     */
    public boolean isShort(String string) {
        try {
            Short.parseShort(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
