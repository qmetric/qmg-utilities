package com.qmetric.utilities.poi.xssf;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class XSSFWorksheetParserFactoryTest
{
    private XSSFWorksheetParserFactory worksheetParserFactory;

    @Before
    public void setup()
    {
        final DefaultCollatorFactory collatorFactory = mock(DefaultCollatorFactory.class);
        worksheetParserFactory = new XSSFWorksheetParserFactory(collatorFactory);
    }

    @Test
    public void shouldCreateInstance()
    {
        final SharedStringsTableLookup sharedStrings = mock(SharedStringsTableLookup.class);
        final RowProcessor rowProcessor = mock(RowProcessor.class);

        assertNotNull(worksheetParserFactory.createParserFor(sharedStrings, rowProcessor));
    }
}
