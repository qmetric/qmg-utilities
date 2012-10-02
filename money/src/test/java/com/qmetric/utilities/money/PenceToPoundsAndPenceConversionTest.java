package com.qmetric.utilities.money;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class PenceToPoundsAndPenceConversionTest
{
    @Test
    public void shouldConvertPenceStringToPoundAndPenceMonetaryValue()
    {
        assertThat(PenceToPoundsAndPenceConversion.convertToMonetaryValue(13853), IsEqual.equalTo(new MonetaryValue("138.53")));
    }

    @Test
    public void shouldConvertPenceStringToZeroPoundsAndPenceMonetaryValue()
    {
        Assert.assertThat(PenceToPoundsAndPenceConversion.convertToMonetaryValue(29), IsEqual.equalTo(new MonetaryValue("0.29")));
    }

    @Test
    public void shouldConvertMonetaryValueToPence()
    {
        Assert.assertThat(PenceToPoundsAndPenceConversion.convertToPence(new MonetaryValue("1")), IsEqual.equalTo(100));
    }
}
