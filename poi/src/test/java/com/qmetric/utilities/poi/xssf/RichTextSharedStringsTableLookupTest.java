package com.qmetric.utilities.poi.xssf;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RichTextSharedStringsTableLookupTest
{
    private SharedStringsTable sharedStringsTable;

    private SharedStringsTableLookup tableLookup;

    private XSSFRichTextString xssfRichTextString;

    @Before
    public void init() throws Exception
    {
        sharedStringsTable = mock(SharedStringsTable.class);

        tableLookup = new RichTextSharedStringTableLookup(sharedStringsTable);

        xssfRichTextString = mock(XSSFRichTextString.class);
    }

    @Ignore
    @Test
    public void shouldGetValueFromSharedStringsTable()
    {
        final String expectedString = "a value";

        when(xssfRichTextString.toString()).thenReturn(expectedString);

        final String result = tableLookup.lookupValueFor("1");

        verify(sharedStringsTable).getEntryAt(1);
        assertThat(result, equalTo(expectedString));
    }
}
