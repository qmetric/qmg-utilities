package com.qmetric.utilities.security.decrypt;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DecrypterTest
{

    @Test
    public void shouldDecryptValueCorrectly()   {
        final String secret = "5n4pp3r";
        Decrypter decrypter = new Decrypter(secret);

        assertThat(decrypter.decrypt("zpmcVC9q6Opm1Sn1qLWc4A=="), equalTo("qmg_app"));
    }
}
