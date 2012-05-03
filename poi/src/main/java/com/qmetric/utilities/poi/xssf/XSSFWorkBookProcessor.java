package com.qmetric.utilities.poi.xssf;

import com.qmetric.utilities.file.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;

import java.io.IOException;
import java.io.InputStream;

public class XSSFWorkBookProcessor
{
    private final XSSFWorksheetParserFactory xssfWorksheetParserFactory;

    private final FileUtils fileUtils;

    private final XSSFReaderFactory xssfReaderFactory;

    public XSSFWorkBookProcessor(final XSSFWorksheetParserFactory xssfWorksheetParserFactory, final FileUtils fileUtils, final XSSFReaderFactory xssfReaderFactory)
    {
        this.xssfWorksheetParserFactory = xssfWorksheetParserFactory;
        this.fileUtils = fileUtils;
        this.xssfReaderFactory = xssfReaderFactory;
    }

    public void process(final String location, final SheetProcessor sheetProcessor)
    {
        process(fileUtils.inputStreamFrom(location), sheetProcessor);
    }

    public void process(final InputStream stream, final SheetProcessor sheetProcessor)
    {
        final XSSFReader xssfReader = xssfReaderFactory.create(stream);

        try
        {
            XSSFReader.SheetIterator sheetsData = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            processSheets(sheetsData, sheetProcessor, xssfReader.getSharedStringsTable());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InvalidFormatException e)
        {
            e.printStackTrace();
        }
    }

    private void processSheets(final XSSFReader.SheetIterator sheetsData, final SheetProcessor sheetProcessor, final SharedStringsTable sharedStringsTable)
    {
        while(sheetsData.hasNext())
        {
            processNext(sheetsData.next(), sheetsData.getSheetName(), sheetProcessor, sharedStringsTable);
        }
    }

    private void processNext(final InputStream next, final String sheetName, final SheetProcessor sheetProcessor, final SharedStringsTable sharedStringsTable)
    {
        final WorksheetParser worksheetParser =
                xssfWorksheetParserFactory.createParserFor(new RichTextSharedStringTableLookup(sharedStringsTable), sheetProcessor.getNewRowProcessor(sheetName));

        worksheetParser.parse(next);
    }
}
