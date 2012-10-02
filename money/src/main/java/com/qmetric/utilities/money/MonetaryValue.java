package com.qmetric.utilities.money;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MonetaryValue implements Comparable<MonetaryValue>, Serializable
{
    public static final NumberFormat UK_CURRENCY_INSTANCE = NumberFormat.getCurrencyInstance(Locale.UK);

    public static final MonetaryValue ZERO = new MonetaryValue("0.00");

    private BigDecimal value;

    private RoundingStrategy roundingStrategy = new MonetaryRounding();

    public MonetaryValue(final String val, final RoundingStrategy roundingStrategy)
    {
        this(val);
        this.roundingStrategy = roundingStrategy;
    }

    public MonetaryValue(final MonetaryValue monetaryValue, final RoundingStrategy roundingStrategy)
    {
        this(monetaryValue.value, roundingStrategy);
    }

    MonetaryValue(final BigDecimal val, final RoundingStrategy roundingStrategy)
    {
        this.roundingStrategy = roundingStrategy;
        this.value = roundingStrategy.round(val);
    }

    public MonetaryValue(String val)
    {
        try
        {
            value = new BigDecimal(val);
        }
        catch(NumberFormatException ex) {
            try
            {
                value = new BigDecimal(UK_CURRENCY_INSTANCE.parse(val).doubleValue());
            }
            catch (ParseException e)
            {
                throw ex;
            }
        }
    }

    public MonetaryValue(final BigDecimal value)
    {
        this.value = value;
    }

    public MonetaryValue add(final MonetaryValue augend)
    {
        BigDecimal result = scale(this.value.add(augend.value));
        return new MonetaryValue(result, roundingStrategy);
    }

    public MonetaryValue multiply(final MonetaryValue multiplicand)
    {
        return multiply(multiplicand.value);
    }

    public MonetaryValue multiply(final BigDecimal multiplicand)
    {
        final BigDecimal result = scale(value.multiply(multiplicand));
        return new MonetaryValue(result, roundingStrategy);
    }

    public MonetaryValue multiply(final int i)
    {
        return multiply(new BigDecimal(i));
    }

    private BigDecimal scale(BigDecimal candidate)
    {
        return roundingStrategy.round(candidate);
    }

    public MonetaryValue divide(final BigDecimal divisor, int scale)
    {
        return new MonetaryValue(roundingStrategy.round(value.divide(divisor, scale)), new ScaleOnly(scale));
    }

    public MonetaryValue divide(final BigDecimal divisor)
    {
        final BigDecimal val = value.divide(divisor, roundingStrategy.getScale(), roundingStrategy.getRoundingMode());
        return new MonetaryValue(val, roundingStrategy);
    }

    public MonetaryValue subtract(final MonetaryValue subtrahend)
    {
        final BigDecimal result = scale(this.value.subtract(subtrahend.value));
        return new MonetaryValue(result, roundingStrategy);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MonetaryValue))
        {
            return false;
        }

        final MonetaryValue that = (MonetaryValue) o;

        return scale(value).equals(scale(that.value));
    }

    public BigDecimal asBigDecimal()
    {
        return value;
    }

    public MonetaryValue min(final MonetaryValue otherValue)
    {
        return new MonetaryValue(this.value.min(otherValue.value));
    }

    public boolean greaterThan(final MonetaryValue otherValue)
    {
        return this.value.compareTo(otherValue.value) > 0;
    }

    public boolean greaterThanOrEqualTo(final MonetaryValue otherValue)
    {
        return this.value.compareTo(otherValue.value) > -1;
    }

    public boolean lessThanOrEqualTo(final MonetaryValue otherValue)
    {
        return this.value.compareTo(otherValue.value) < 1;
    }

    public boolean lessThan(final MonetaryValue other)
    {
        return this.value.compareTo(other.value) < 0;
    }

    @Override
    public int hashCode()
    {
        return scale(value).hashCode();
    }

    public String getCurrencyFormattedString()
    {
        final BigDecimal decimal = scale(this.value);

        return UK_CURRENCY_INSTANCE.format(decimal);
    }

    public String getCurrencyFormattedStringWithoutDecimal()
    {
        return StringUtils.substringBefore(getCurrencyFormattedString(), ".");
    }

    public int getAsPence()
    {
        return PenceToPoundsAndPenceConversion.convertToPence(this);
    }

    @Override
    public String toString()
    {
        return value.toString();
    }

    @Override public int compareTo(final MonetaryValue other)
    {
        return this.value.compareTo(other.value);
    }
}
