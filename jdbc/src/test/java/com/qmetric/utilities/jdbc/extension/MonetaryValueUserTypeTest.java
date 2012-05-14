package com.qmetric.utilities.jdbc.extension;

import com.qmetric.utilities.money.MonetaryValue;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public final class MonetaryValueUserTypeTest
{
    private final static MonetaryValue ONE = new MonetaryValue("1.00");

    @Test
    public void shouldReturnExactCopyOnDeepCopy()
    {
        assertThat((MonetaryValue) new MonetaryValueUserType().deepCopy(new MonetaryValue("1.00000")), equalTo(ONE));
    }
}
