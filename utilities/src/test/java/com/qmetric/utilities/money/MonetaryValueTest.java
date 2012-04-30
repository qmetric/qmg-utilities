package com.qmetric.utilities.money;

import com.qmetric.utilities.json.JsonUtils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MonetaryValueTest
{
    private static final MonetaryValue ONE = new MonetaryValue("1.00");

    private static final MonetaryValue TWO = new MonetaryValue("2.00");

    @Test
    public void shouldSaveSuppliedBigDecimal()
    {
        final BigDecimal bigDecimal = new BigDecimal("40.00");
        assertThat(new MonetaryValue(new BigDecimal("40.00")).asBigDecimal(), equalTo(bigDecimal));
    }

    @Test
    public void shouldRoundAddedValues()
    {
        MonetaryValue roundedBigDecimal = new MonetaryValue("3.33");
        MonetaryValue roundedBigDecimal2 = new MonetaryValue("6.235");

        final MonetaryValue result = roundedBigDecimal.add(roundedBigDecimal2);

        assertThat(result, equalTo(new MonetaryValue("9.57")));
    }

    @Test
    public void shouldRoundMultipliedValues()
    {
        MonetaryValue roundedBigDecimal = TWO;
        MonetaryValue roundedBigDecimal2 = new MonetaryValue("2.333");

        MonetaryValue result = roundedBigDecimal.multiply(roundedBigDecimal2);

        assertThat(result, equalTo(new MonetaryValue("4.67")));
    }

    @Test
    public void shouldRoundMultipliedValuesUsingBigDecimal()
    {
        MonetaryValue roundedBigDecimal = TWO;
        BigDecimal roundedBigDecimal2 = new BigDecimal("2.333");

        MonetaryValue result = roundedBigDecimal.multiply(roundedBigDecimal2);

        assertThat(result, equalTo(new MonetaryValue("4.67")));
    }

    @Test
    public void shouldMultiplyValuesUsingInteger()
    {
        MonetaryValue roundedBigDecimal = TWO;

        final MonetaryValue result = roundedBigDecimal.multiply(2);

        assertThat(result, equalTo(new MonetaryValue("4")));
    }

    @Test
    public void shouldRoundDividedNumbersUsingBigDecimal()
    {
        MonetaryValue monetaryValue = new MonetaryValue("50.00");
        BigDecimal roundedBigDecimal2 = new BigDecimal("0.30");

        final MonetaryValue result = monetaryValue.divide(roundedBigDecimal2);

        assertThat(result, equalTo(new MonetaryValue("166.67")));
    }

    @Test
    public void shouldDivideAndRoundToRequiredScale()
    {
        MonetaryValue monetaryValue = new MonetaryValue("50.00");
        BigDecimal roundedBigDecimal2 = new BigDecimal("0.30");

        final MonetaryValue result = monetaryValue.divide(roundedBigDecimal2, 5);

        assertThat(result, equalTo(new MonetaryValue("166.66667")));
    }

    @Test
    public void shouldSubtractAndRound()
    {
        MonetaryValue monetaryValue = new MonetaryValue("20.00");

        MonetaryValue subtrahend = new MonetaryValue("13.33");

        final MonetaryValue result = monetaryValue.subtract(subtrahend);

        assertThat(result, equalTo(new MonetaryValue("6.67")));
    }

    @Test
    public void shouldRound()
    {
        MonetaryValue roundedBigDecimal = new MonetaryValue("19.333");
        assertThat(roundedBigDecimal, equalTo(new MonetaryValue("19.33")));
    }

    @Test
    public void shouldBeEqual()
    {
        MonetaryValue roundedBigDecimal = new MonetaryValue("33.329");
        MonetaryValue roundedBigDecimal2 = new MonetaryValue("33.33");

        assertThat(roundedBigDecimal, equalTo(roundedBigDecimal2));
    }

    @Test
    public void shouldHaveSameHashCode()
    {
        MonetaryValue roundedBigDecimal = new MonetaryValue("43.329");
        MonetaryValue roundedBigDecimal2 = new MonetaryValue("43.33");

        assertThat(roundedBigDecimal.hashCode(), equalTo(roundedBigDecimal2.hashCode()));
    }

    @Test
    public void shouldBeGreaterThan()
    {
        assertThat(TWO.compareTo(ONE), greaterThan(0));
    }

    @Test
    public void shouldBeLessThanOrEqual()
    {
        assertThat(ONE.lessThanOrEqualTo(TWO), is(true));
        assertThat(ONE.lessThanOrEqualTo(ONE), is(true));
    }

    @Test
    public void shouldBeEqualWhenCompared()
    {
        assertThat(ONE.compareTo(ONE), equalTo(0));
    }

    @Test
    public void shouldDisplayValueWithPoundSymbol()
    {
        // for some reason the £ symbol matcher failed when moving this from engine to qmg-utilities even though UTF-8 char encoding set it pom
        assertThat(TWO.getCurrencyFormattedString(), equalTo("\u00A32.00"));
    }

    @Test
    public void roundingModeShouldBePreservedAcrossAddOperations()
    {
        final MonetaryValue base = new MonetaryValue("5.005", new ScaleOnly()).add(new MonetaryValue("1.001"));
        assertThat(base.add(new MonetaryValue("1.001")), equalTo(new MonetaryValue("7.007")));
    }

    @Test
    public void roundingModeShouldBePreservedAcrossMultiplyOperations()
    {
        final MonetaryValue base = new MonetaryValue("5.005", new ScaleOnly()).multiply(new MonetaryValue("1.001"));
        assertThat(base.multiply(new MonetaryValue("1.001")), equalTo(new MonetaryValue("5.015015005")));
    }

    @Test
    public void roundingModeShouldBePreservedAcrossDivideOperations()
    {
        final MonetaryValue base = new MonetaryValue("10.55555", new ScaleOnly()).divide(new BigDecimal("0.256"));
        assertThat(base.add(new MonetaryValue("1.00")), equalTo(new MonetaryValue("42.2326171875")));
    }

    @Test
    public void shouldReturnTrueForGreaterThanComparisonIfBaseValueIsGreater()
    {
        final MonetaryValue base = new MonetaryValue("10.50");

        assertThat(base.greaterThan(new MonetaryValue("10.00")), equalTo(true));
    }

    @Test
    public void shouldReturnFalseForGreaterThanComparisonIfBaseValueIsLess()
    {
        final MonetaryValue base = new MonetaryValue("9.50");
        assertThat(base.greaterThan(new MonetaryValue("10.00")), equalTo(false));
    }

    @Test
    public void shouldReturnTrueForLessThanComparisonWhenValueIsLess()
    {
        final MonetaryValue base = new MonetaryValue("9.50");
        assertThat(base.lessThanOrEqualTo(new MonetaryValue("10.00")), equalTo(true));
    }

    @Test
    public void shouldReturnTrueForLessThanComparisonWhenValueIsEqual()
    {
        final MonetaryValue base = new MonetaryValue("10.00");
        assertThat(base.lessThanOrEqualTo(new MonetaryValue("10.00")), equalTo(true));
    }

    @Test
    public void shouldReturnFalseForLessThanComparisonIfBaseValueIsGreater()
    {
        final MonetaryValue base = new MonetaryValue("10.50");

        assertThat(base.lessThanOrEqualTo(new MonetaryValue("10.00")), equalTo(false));
    }

    @Test
    public void shouldReturnTrueForGreaterThanOrEqualToComparisonWhenValueIsGreater()
    {
        final MonetaryValue base = new MonetaryValue("10.50");

        assertThat(base.greaterThanOrEqualTo(new MonetaryValue("10.00")), equalTo(true));
    }

    @Test
    public void shouldReturnTrueForGreaterThanOrEqualToComparisonWhenValueIsEqual()
    {
        final MonetaryValue base = new MonetaryValue("10.00");

        assertThat(base.greaterThanOrEqualTo(new MonetaryValue("10.00")), equalTo(true));
    }

    @Test
    public void shouldReturnFalseForGreaterThanOrEqualToComparisonWhenValueIsLess()
    {
        final MonetaryValue base = new MonetaryValue("9.00");

        assertThat(base.greaterThanOrEqualTo(new MonetaryValue("10.00")), equalTo(false));
    }

    @Test
    public void shouldSerialiseMonetaryValueToJson() throws Exception
    {
        final String json = new JsonUtils().serializeToJson(ONE);

        assertThat(json, equalTo("\"" + ONE.getCurrencyFormattedString() + "\""));
    }

    @Test
    public void shouldBeLessThan()
    {
        assertThat(ONE.lessThan(TWO), is(true));
        assertThat(ONE.lessThan(ONE), is(false));
    }
}