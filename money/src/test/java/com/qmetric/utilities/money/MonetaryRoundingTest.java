package com.qmetric.utilities.money;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MonetaryRoundingTest
{
    private final MonetaryRounding monetaryRounding = new MonetaryRounding();

    @Test
    public void shouldCorrectlyRoundToTwoPlaces()
    {
        assertThat(monetaryRounding.round(new BigDecimal("10.499")), equalTo(new BigDecimal("10.50")));
        assertThat(monetaryRounding.round(new BigDecimal("10.495")), equalTo(new BigDecimal("10.50")));
        assertThat(monetaryRounding.round(new BigDecimal("10.491")), equalTo(new BigDecimal("10.49")));
        assertThat(monetaryRounding.round(new BigDecimal("0.00")), equalTo(new BigDecimal("0.00")));
        assertThat(monetaryRounding.round(new BigDecimal("10.00")), equalTo(new BigDecimal("10.00")));
    }
}