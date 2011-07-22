package com.qmetric.utilities.percentage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by: dwood Date: Jul 21, 2011 Time: 5:19:59 PM
 */
@RunWith(Parameterized.class)
public class PercentageTest
{

    private final Percentage percentage;

    private final String plainString;

    private final String displayValue;

    private final BigDecimal value;

    private final BigDecimal valueMultiplied;

    public PercentageTest(final String percentage, final String displayValue, final String value, final String valueMultiplied)
    {
        this.percentage = new Percentage(percentage);
        this.plainString = percentage;
        this.displayValue = displayValue;
        this.value = new BigDecimal(value);
        this.valueMultiplied = new BigDecimal(valueMultiplied);
    }

    @Parameterized.Parameters
    public static Collection<String[]> data()
    {
        final List<String[]> tests = new ArrayList<String[]>();
        tests.add(new String[] {"20.00", "20.00%", "50.00", "10.00"});
        tests.add(new String[] {"11.25", "11.25%", "100.00", "11.25"});
        tests.add(new String[] {"13.45", "13.45%", "99.00", "13.32"});
        tests.add(new String[] {"22.275", "22.28%", "150.00", "33.41"});
        return tests;
    }

    @Test
    public void multiplyByPercentage()
    {
        assertThat(percentage.of(value).setScale(2, RoundingMode.HALF_UP), equalTo(valueMultiplied));
    }

    @Test
    public void asBigDecimal()
    {
        assertThat(percentage.asBigDecimal(), equalTo(new BigDecimal(plainString)));
    }

    @Test
    public void shouldReturnExpectedDisplayValue()
    {
        assertThat(percentage.getPercentageFormattedString(), equalTo(displayValue));
        assertThat(percentage.getPercentageFormattedString(2, RoundingMode.HALF_UP), equalTo(displayValue));
    }

    @Test
    public void shouldReturnExpectedPlainString()
    {
        assertThat(percentage.toPlainString(), equalTo(plainString));
    }

    @Test
    public void shouldReturnExpectedPercentageFromMultiplier()
    {
        final BigDecimal multiplier = percentage.of(BigDecimal.ONE);
        assertThat(Percentage.fromMultiplier(multiplier), equalTo(percentage));
    }
}