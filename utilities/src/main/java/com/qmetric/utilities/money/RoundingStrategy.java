package com.qmetric.utilities.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

interface RoundingStrategy
{
    BigDecimal round(BigDecimal value);

    RoundingMode getRoundingMode();

    int getScale();
}
