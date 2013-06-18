package com.qmetric.utilities.security.encrypt;

import com.qmetric.utilities.security.decrypt.Decrypter;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class EncryptedProperties
{
    private final PropertyDecrypter decrypter;

    private final Properties properties;

    public EncryptedProperties(final String secretKey, final Properties properties)
    {
        this.decrypter = new PropertyDecrypter(new Decrypter(secretKey));
        this.properties = properties;
    }

    public String get(final String propertyName)
    {
        return decrypter.decrypt(properties.getProperty(propertyName));
    }

    private static class PropertyDecrypter
    {
        private static final Pattern PREFIXED_STRING = Pattern.compile("^\\s*ENC\\s*\\((.*)\\)\\s*$", CASE_INSENSITIVE);

        private final Decrypter decrypter;

        public PropertyDecrypter(final Decrypter decrypter)
        {
            this.decrypter = decrypter;
        }

        public String decrypt(final String value)
        {
            final Matcher matcher = PREFIXED_STRING.matcher(value);

            return matcher.matches() ? decrypter.decrypt(matcher.group(1)) : value;
        }
    }
}
