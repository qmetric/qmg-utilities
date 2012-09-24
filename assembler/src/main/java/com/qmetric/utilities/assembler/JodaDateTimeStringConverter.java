package com.qmetric.utilities.assembler;

import org.apache.commons.lang.StringUtils;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.MappingException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaDateTimeStringConverter implements ConfigurableCustomConverter
{
    private DateTimeFormatter date_format = DateTimeFormat.forPattern("dd/MM/yyyy");

    @Override public void setParameter(final String formatString)
    {
        if (StringUtils.isNotBlank(formatString))
        {
            date_format = DateTimeFormat.forPattern(formatString);
        }
    }

    @Override public Object convert(final Object destination, final Object source, final Class destinationClass, final Class sourceClass)
    {
        if (source == null)
        {
            return null;
        }

        if (source instanceof DateTime)
        {
            return date_format.print((DateTime) source);
        }
        else if (source instanceof String)
        {
            return date_format.parseDateTime((String) source);
        }

        throw new MappingException("Converter JodaDateTimeStringConverter used incorrectly. Arguments passed in were:" + destination + " and " + source);
    }
}
