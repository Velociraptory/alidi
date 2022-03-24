package ru.alidi.market.util;

import java.math.MathContext;
import java.math.RoundingMode;

public final class NumberUtil {

    private NumberUtil() {
    }

    public static MathContext getDefaultCalculationContext() {
        return new MathContext(2, RoundingMode.HALF_UP);
    }
}
