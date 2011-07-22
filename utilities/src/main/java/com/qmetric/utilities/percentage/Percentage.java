package com.qmetric.utilities.percentage;

import org.codehaus.jackson.annotate.JsonValue;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by: dwood Date: Jul 21, 2011 Time: 5:17:38 PM
 */
public class Percentage implements Serializable
{
    public static final Percentage ZERO = new Percentage(BigDecimal.ZERO, BigDecimal.ZERO);

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100.00");

    private static final int DEFAULT_SCALE = 2;

    private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    private final BigDecimal value;

    private final BigDecimal multiplier;

    public Percentage(final String stringValue)
    {
        this(new BigDecimal(stringValue));
    }

    public Percentage(final BigDecimal value)
    {
        this(value, value.divide(ONE_HUNDRED));
    }

    private Percentage(final BigDecimal value, final BigDecimal multiplier)
    {
        this.value = value;
        this.multiplier = multiplier;
    }

    public static Percentage fromMultiplier(final BigDecimal multiplier)
    {
        return new Percentage(multiplier.multiply(ONE_HUNDRED));
    }

    public BigDecimal of(final BigDecimal bigDecimal)
    {
        return bigDecimal.multiply(multiplier);
    }

    public BigDecimal asBigDecimal()
    {
        return value;
    }

    public String toPlainString()
    {
        return value.toPlainString();
    }

    @JsonValue
    public String getPercentageFormattedString()
    {
        return getPercentageFormattedString(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    public String getPercentageFormattedString(final int scale, final RoundingMode roundingMode)
    {
        return String.format("%s%%", value.setScale(scale, roundingMode).toPlainString());
    }

    private BigDecimal scale(final BigDecimal toScale)
    {
        return toScale.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Percentage))
        {
            return false;
        }

        final Percentage that = (Percentage) o;

        return scale(value).equals(scale(value));
    }

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }
}
