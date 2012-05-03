package com.qmetric.utilities.poi.xssf;

import com.qmetric.utilities.file.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//TODO Processor?
public class XSSFWorkBookProcessorTest
{
    @Test
    public void shouldProcessWorkBookWithProvidedRowProcessor() throws IOException, InvalidFormatException
    {
        final String location = "location";
        final String sheetName = "sheetName";

        final InputStream inputStream = mock(InputStream.class);
        final RowProcessor rowProcessor = mock(RowProcessor.class);
        final XSSFReaderFactory xssfReaderFactory = readerFactory(inputStream, sheetName);
        final XSSFWorksheetParserFactory xssfWorksheetParserFactory = xssfWorksheetParserFactory(location, inputStream, rowProcessor);
        final SheetProcessor sheetProcessor = rowProcessorFactory(sheetName, rowProcessor);


        final XSSFWorkBookProcessor xssfWorkBookProcessor = new XSSFWorkBookProcessor(xssfWorksheetParserFactory, fileUtils(location, inputStream), xssfReaderFactory);
        xssfWorkBookProcessor.process(location, sheetProcessor);

        verify(sheetProcessor).getNewRowProcessor(sheetName);
    }

    private SheetProcessor rowProcessorFactory(final String sheetName, final RowProcessor rowProcessor)
    {
        final SheetProcessor sheetProcessor = mock(SheetProcessor.class);
        when(sheetProcessor.getNewRowProcessor(sheetName)).thenReturn(rowProcessor);
        when(sheetProcessor.getNewRowProcessor(sheetName)).thenReturn(rowProcessor);
        return sheetProcessor;
    }

    private XSSFReaderFactory readerFactory(final InputStream inputStream, final String sheetName) throws IOException, InvalidFormatException
    {
        final XSSFReader.SheetIterator sheetIterator = mock(XSSFReader.SheetIterator.class);
        stub(sheetIterator.hasNext()).toReturn(true).toReturn(false);
        when(sheetIterator.next()).thenReturn(inputStream);
        when(sheetIterator.getSheetName()).thenReturn(sheetName);

        final SharedStringsTable sharedStringsTable = mock(SharedStringsTable.class);

        final XSSFReader xssfReader = mock(XSSFReader.class);
        when(xssfReader.getSheetsData()).thenReturn(sheetIterator);
        when(xssfReader.getSharedStringsTable()).thenReturn(sharedStringsTable);

        final XSSFReaderFactory xssfReaderFactory = mock(XSSFReaderFactory.class);
        when(xssfReaderFactory.create(inputStream)).thenReturn(xssfReader);

        return xssfReaderFactory;
    }

    private XSSFWorksheetParserFactory xssfWorksheetParserFactory(final String location, final InputStream inputStream, final RowProcessor rowProcessor)
    {
        fileUtils(location, inputStream);
        final XSSFWorksheetParserFactory xssfWorksheetParserFactory = mock(XSSFWorksheetParserFactory.class);
        final WorksheetParser worksheetParser = mock(WorksheetParser.class);
        when(xssfWorksheetParserFactory.createParserFor(any(SharedStringsTableLookup.class), eq(rowProcessor))).thenReturn(worksheetParser);

        return xssfWorksheetParserFactory;
    }

    private FileUtils fileUtils(final String location, final InputStream inputStream)
    {
        final FileUtils fileUtils = mock(FileUtils.class);
        when(fileUtils.inputStreamFrom(location)).thenReturn(inputStream);
        return fileUtils;
    }
}
