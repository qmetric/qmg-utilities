package com.qmetric.infrastructure.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;

interface RoundingStrategy
{
    BigDecimal round(BigDecimal value);

    RoundingMode getRoundingMode();

    int getScale();
}
