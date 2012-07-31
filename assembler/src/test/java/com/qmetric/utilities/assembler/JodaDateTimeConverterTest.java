package com.qmetric.utilities.assembler;

import com.qmetric.utilities.assembler.JodaDateTimeConverter;
import org.dozer.MappingException;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JodaDateTimeConverterTest
{
    final JodaDateTimeConverter converter = new JodaDateTimeConverter();

    private static final DateTime TEST_DATE_TIME = new DateTime().withDayOfMonth(1).withMonthOfYear(10).withYear(2011);

    @Test
    public void shouldReturnNullIfSourceIsNull()
    {
        assertThat(converter.convert(null, null, String.class, DateTime.class), equalTo(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenStringFormatIncorrect()
    {
        converter.convert(null, "string", DateTime.class, String.class);
    }

    @Test(expected = MappingException.class)
    public void shouldThrowExceptionWhenConvertingWithOtherObjectTypes()
    {
        converter.convert(null, true, String.class, Boolean.class);
    }

    @Test
    public void shouldConvertDateTimeToString()
    {
        final DateTime dateTime = TEST_DATE_TIME;
        final String dateTimeString = "01/10/2011";
        final String result = (String) converter.convert(null, dateTime, String.class, DateTime.class);
        assertThat(result, equalTo(dateTimeString));
    }

    @Test
    public void shouldConvertDateStringToDateTime()
    {
        final DateTime expectedDateTime = TEST_DATE_TIME;
        final String exampleDateAsString = "01/10/2011";
        final DateTime result = (DateTime) converter.convert(null, exampleDateAsString, DateTime.class, String.class);
        assertThat(result, isSameDay(expectedDateTime));
    }

    @Test
    public void shouldConvertShortFormDateStringToDateTime()
    {
        final DateTime expectedDateTime = new DateTime().withDayOfMonth(1).withMonthOfYear(9).withYear(2011);
        final String exampleDateAsString = "1/9/2011";
        final DateTime result = (DateTime) converter.convert(null, exampleDateAsString, DateTime.class, String.class);
        assertThat(result, isSameDay(expectedDateTime));
    }

    @Test
    public void shouldKeepDefaultDateTimeFormatterWhenBlankParamGiven()
    {
        final DateTimeFormatter expectedDateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");

        converter.setParameter(null);

        assertThat((DateTimeFormatter) ReflectionTestUtils.getField(converter, "date_format"), equalTo(expectedDateTimeFormatter));
    }

    @Test
    public void shouldUseGivenParamAsDateTimeFormatPattern()
    {
        converter.setParameter("dd-MM-yyyy");
        final String result = (String) converter.convert(null, TEST_DATE_TIME, String.class, DateTime.class);
        assertThat(result, equalTo("01-10-2011"));
    }

    private Matcher<DateTime> isSameDay(final DateTime expectedDateTime)
    {
        return new BaseMatcher()
        {

            @Override public boolean matches(final Object o)
            {
                final DateTime actualDateTime = (DateTime) o;

                final int result = DateTimeComparator.getDateOnlyInstance().compare(actualDateTime, expectedDateTime);

                return result == 0 ? true : false;
            }

            @Override public void describeTo(final Description description)
            {
                description.appendText("Does not match, " + expectedDateTime);
            }
        };
    }
}
