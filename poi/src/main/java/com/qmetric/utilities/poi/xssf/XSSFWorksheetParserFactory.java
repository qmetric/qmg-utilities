package com.qmetric.utilities.poi.xssf;

import javax.xml.stream.XMLInputFactory;

public class XSSFWorksheetParserFactory
{
    private DefaultCollatorFactory collatorFactory;

    public XSSFWorksheetParserFactory(final DefaultCollatorFactory collatorFactory)
    {
        this.collatorFactory = collatorFactory;
    }

    public WorksheetParser createParserFor(final SharedStringsTableLookup sharedStringsTableLookup, final RowProcessor rowProcessor)
    {
        return new WorksheetParser(collatorFactory.create(sharedStringsTableLookup, rowProcessor), XMLInputFactory.newInstance());
    }
}
