package com.qmetric.utilities.money;

import org.apache.commons.lang3.StringUtils;

public class PenceToPoundsAndPenceConversion
{
    /**
     * Converts pennies amount into pounds and pence monetary value.
     * <p/>
     * eg.   15 ->  £0.15
     * <p/>
     * eg. 1239 -> £12.39
     *
     * @param pennies to convert
     * @return MonetaryValue representing pennies as pounds and pence
     */
    public static MonetaryValue convertToMonetaryValue(final int pennies)
    {
        int pounds = pennies / 100;
        int pence = pennies % 100;

        return new MonetaryValue(String.format("%d.%02d", pounds, pence));
    }

    /**
     * Converts a pound and pence monetary value to pence
     * <p/>
     * eg.  £0.15 ->   15
     * <p/>
     * eg. £12.39 -> 1239
     *
     * @param value MonetaryValue to convert
     * @return value as pence
     * @throws NullPointerException if value is null.
     */
    public static int convertToPence(final MonetaryValue value)
    {
        return Integer.parseInt(StringUtils.substringBefore(value.multiply(100).toString(), "."));
    }
}
