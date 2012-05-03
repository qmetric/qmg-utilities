package com.qmetric.utilities.money;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ScaleOnlyTest
{
    private final ScaleOnly scaleOnly = new ScaleOnly();

    @Test
    public void shouldCorrectlyScaleAndNotRound()
    {
        assertThat(scaleOnly.round(new BigDecimal("1.00000000000000009")), equalTo(new BigDecimal("1.0000000000000000")));
        assertThat(scaleOnly.round(new BigDecimal("1.00000000000000000")), equalTo(new BigDecimal("1.0000000000000000")));
    }
}
