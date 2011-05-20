package com.qmetric.infrastructure.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_UP;

public class MonetaryRounding implements RoundingStrategy
{
    @Override public BigDecimal round(final BigDecimal value)
    {
        return value.setScale(2, HALF_UP);
    }

    @Override public RoundingMode getRoundingMode()
    {
        return HALF_UP;
    }

    @Override public int getScale()
    {
        return 2;
    }
}