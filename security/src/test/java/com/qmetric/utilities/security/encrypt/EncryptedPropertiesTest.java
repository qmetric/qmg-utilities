package com.qmetric.utilities.security.encrypt;

import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EncryptedPropertiesTest
{

    @Test
    public void shouldEncryptProperties() throws Exception
    {

        // given
        final Properties properties = new Properties();
        properties.setProperty("encrypted-prop", "ENC(+22M6G6Ni6Zs977nw44/zQ==)");
        properties.setProperty("plain-prop", "plain-test");

        // when
        final EncryptedProperties encryptedProperties = new EncryptedProperties("secret_key", properties);

        // then
        assertThat(encryptedProperties.get("encrypted-prop"), is("test"));
        assertThat(encryptedProperties.get("plain-prop"), is("plain-test"));
    }
}
