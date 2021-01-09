package ru.stonlex.global.utility;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.function.BiConsumer;

@UtilityClass
public class PercentUtil {

    /**
     * Получить процент текущего числа от максимального
     *
     * @param currentNumber - текущее число
     * @param maxNumber     - максимальное число
     */
    public double getPercent(double currentNumber, double maxNumber) {
        return (currentNumber * 100D) / maxNumber;
    }

    /**
     * Получить число от процента максимального числа
     *
     * @param currentPercent - текущий процент, который надо найти
     * @param maxNumber      - максимальное число, от которого ищем процент
     */
    public double getNumberByPercent(double currentPercent, double maxNumber) {
        return (maxNumber / 100D) * currentPercent;
    }

    /**
     * Соотнести текущий процент к рандомному и
     * узнать, входит ли он в рацион рандома
     *
     * @param currentPercent - текущий процент
     */
    public boolean acceptRandomPercent(double currentPercent) {
        Preconditions.checkArgument(currentPercent <= 100, "Percentage " + currentPercent + " has not been > 100");

        double randomPercent = NumberUtil.randomDouble(0, 100);
        return randomPercent <= currentPercent;
    }

    /**
     * Соотнести текущий процент к рандомному и
     * применить результат - входит ли он в рацион рандома
     *
     * @param currentPercent  - текущий процент
     * @param percentConsumer - обработчик результата
     *                        (Boolean - результат соотношения,
     *                        Double - рандомный процент, который был создан в ходе соотношения)
     */
    public void acceptRandomPercent(double currentPercent, @NonNull BiConsumer<Boolean, Double> percentConsumer) {
        Preconditions.checkArgument(currentPercent <= 100, "Percentage " + currentPercent + " has not been > 100");

        double randomPercent = NumberUtil.randomDouble(0, 100);
        percentConsumer.accept(randomPercent <= currentPercent, randomPercent);
    }

}
