package com.qmetric.utilities.spreadsheet.xssf;

import com.qmetric.utilities.spreadsheet.xssf.ColumnIndexParser;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class ColumnIndexParserTest
{
    private ColumnIndexParser parser;

    @Before
    public void setup()
    {
        parser = new ColumnIndexParser();
    }

    @Test
    public void aIs0()
    {
        assertThat(parser.columnIndexFrom("A3"), equalTo(0));        
    }

    @Test
    public void bIs1()
    {
        assertThat(parser.columnIndexFrom("B4"), equalTo(1));        
    }

    @Test
    public void zIs25()
    {
        assertThat(parser.columnIndexFrom("Z10"), equalTo(25));
    }

    @Test
    public void aaIs26()
    {
        assertThat(parser.columnIndexFrom("AA10"), equalTo(26));        
    }

    @Test
    public void baIs52()
    {
        assertThat(parser.columnIndexFrom("BA10"), equalTo(52));        
    }

    @Test
    public void aaaIs702()
    {
        assertThat(parser.columnIndexFrom("AAA98"), equalTo(702));
    }
    
    @Test
    public void abcIs730()
    {
        assertThat(parser.columnIndexFrom("ABC10"), equalTo(730));        
    }
}
