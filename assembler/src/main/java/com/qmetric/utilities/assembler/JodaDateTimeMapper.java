package com.qmetric.utilities.assembler;

import org.dozer.DozerConverter;
import org.joda.time.DateTime;

// named 'Mapper' as opposed to 'Converter' as there is no conversion per se...
// also avoids conflict with previously named JodaDateTimeConverter (JodaDateTimeStringConverter)
public final class JodaDateTimeMapper extends DozerConverter<DateTime, DateTime>
{
    public JodaDateTimeMapper()
    {
        super(DateTime.class, DateTime.class);
    }

    @Override public DateTime convertTo(final DateTime source, final DateTime destination)
    {
        if (source == null)
        {
            return null;
        }

        return new DateTime(source);
    }

    @Override public DateTime convertFrom(final DateTime source, final DateTime destination)
    {
        if (source == null)
        {
            return null;
        }

        return new DateTime(source);
    }
}
