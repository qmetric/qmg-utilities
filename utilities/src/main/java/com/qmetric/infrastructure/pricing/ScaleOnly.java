package com.qmetric.infrastructure.pricing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ScaleOnly implements RoundingStrategy, Serializable
{
    private static final int DEFAULT_EXCEL_SCALE = 16;

    private final int scale;

    public ScaleOnly()
    {
        scale = DEFAULT_EXCEL_SCALE;
    }

    public ScaleOnly(final int scale)
    {
        this.scale = scale;
    }

    @Override public BigDecimal round(final BigDecimal value)
    {
        return value.setScale(scale, RoundingMode.DOWN);
    }

    @Override public RoundingMode getRoundingMode()
    {
        return RoundingMode.DOWN;
    }

    @Override public int getScale()
    {
        return scale;
    }
}
