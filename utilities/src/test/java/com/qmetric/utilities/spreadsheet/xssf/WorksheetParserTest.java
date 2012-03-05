package com.qmetric.utilities.spreadsheet.xssf;

import org.junit.Before;
import org.junit.Test;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WorksheetParserTest
{
    private RowCollator rowCollator;

    private XMLStreamReader streamReader;

    private WorksheetParser parser;

    private InputStream worksheet;

    private static final String CELL_LOCATION = "A1";

    @Before
    public void init() throws XMLStreamException
    {
        rowCollator = mock(RowCollator.class);

        streamReader = mock(XMLStreamReader.class);

        worksheet = mock(InputStream.class);

        XMLInputFactory inputFactory = mock(XMLInputFactory.class);
        when(inputFactory.createXMLStreamReader(worksheet)).thenReturn(streamReader);

        parser = new WorksheetParser(rowCollator, inputFactory);
    }

    @Test
    public void shouldSetCellValueOnCollatorOnCharactersEvent() throws XMLStreamException
    {
        final String cellValue = "cellValue";

        nextEventIs(XMLStreamReader.CHARACTERS);
        when(streamReader.getText()).thenReturn(cellValue);

        parseWorksheet();

        verify(rowCollator).setCellValue(cellValue);
    }

    @Test
    public void shouldStartARowOnCollatorOnReadingRowStartElement() throws XMLStreamException
    {
        nextEventIs(XMLStreamReader.START_ELEMENT);
        nextElementIs("row");

        parseWorksheet();

        verify(rowCollator).startRow();
    }

    @Test
    public void shouldStartACellOnCollatorOnReadingCellStartElement() throws XMLStreamException
    {
        nextEventIs(XMLStreamReader.START_ELEMENT);
        nextElementIs("c");
        defineLocationAttribute();

        parseWorksheet();

        verify(rowCollator).newCell(CELL_LOCATION);
    }

    @Test
    public void shouldStartAStringCellOnCollatorOnReadingCellStartElementWithStringType() throws XMLStreamException
    {
        nextEventIs(XMLStreamReader.START_ELEMENT);
        nextElementIs("c");
        defineLocationAttribute();
        defineTypeAttribute();

        parseWorksheet();

        verify(rowCollator).newSharedStringsCell(CELL_LOCATION);
    }

    @Test
    public void shouldFinishRowOnCollatorWhenReadingRowEndElement() throws XMLStreamException
    {
        nextEventIs(XMLStreamReader.END_ELEMENT);
        nextElementIs("row");

        parseWorksheet();

        verify(rowCollator).finishRow();
    }

    private void nextEventIs(final int eventType) throws XMLStreamException
    {
        when(streamReader.next()).thenReturn(eventType);
    }

    private void nextElementIs(final String elementName)
    {
        when(streamReader.getLocalName()).thenReturn(elementName);
    }

    private void defineLocationAttribute()
    {
        when(streamReader.getAttributeCount()).thenReturn(1);

        when(streamReader.getAttributeLocalName(0)).thenReturn("r");
        when(streamReader.getAttributeValue(0)).thenReturn(CELL_LOCATION);
    }

    private void defineTypeAttribute()
    {
        when(streamReader.getAttributeCount()).thenReturn(2);
        when(streamReader.getAttributeLocalName(1)).thenReturn("t");
        when(streamReader.getAttributeValue(1)).thenReturn("s");
    }

    private void parseWorksheet() throws XMLStreamException
    {
        when(streamReader.hasNext()).thenReturn(true).thenReturn(false);
        parser.parse(worksheet);
    }

}
