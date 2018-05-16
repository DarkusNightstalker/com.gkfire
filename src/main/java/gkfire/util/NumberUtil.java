package gkfire.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class NumberUtil {

    public static final MathContext CURRENCY_CONTEXT = new MathContext(2, RoundingMode.HALF_UP);

    public double round(double value, int decimal) {
        return new BigDecimal(value, new MathContext(decimal, RoundingMode.HALF_UP)).doubleValue();
    }
}
