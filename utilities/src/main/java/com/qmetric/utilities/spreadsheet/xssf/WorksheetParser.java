package com.qmetric.utilities.spreadsheet.xssf;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.io.InputStream;

public class WorksheetParser
{
    private static final String ROW_ELEMENT = "row";

    private static final String CELL_ELEMENT = "c";

    private static final String CELL_LOCATION_ATTRIBUTE = "r";

    private static final String CELL_TYPE_ATTRIBUTE = "t";

    private static final String CELL_TYPE_STRING = "s";

    private final RowCollator collator;

    private final XMLInputFactory xmlInputFactory;

    public WorksheetParser(final RowCollator collator, final XMLInputFactory xmlInputFactory)
    {
        this.collator = collator;
        this.xmlInputFactory = xmlInputFactory;
    }

    public void parse(final InputStream worksheet)
    {
        try
        {
            final XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(worksheet);

            while (xmlStreamReader.hasNext())
            {
                processXmlEvent(xmlStreamReader);
            }
            xmlStreamReader.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void processXmlEvent(final XMLStreamReader reader) throws XMLStreamException
    {
        int eventCode = reader.next();

        switch (eventCode)
        {
            case XMLStreamReader.START_ELEMENT:
                processStartElement(reader);
                break;
            case XMLStreamReader.END_ELEMENT:
                processEndElement(reader);
                break;
            case XMLStreamReader.CHARACTERS:
                processCharacters(reader);
                break;
        }
    }

    private void processCharacters(final XMLStreamReader reader)
    {
        collator.setCellValue(reader.getText());
    }

    private void processEndElement(final XMLStreamReader reader)
    {
        final String endElement = reader.getLocalName();
        if (endElement.equals(ROW_ELEMENT))
        {
            collator.finishRow();
        }
    }

    private void processStartElement(final XMLStreamReader reader)
    {
        final String startElement = reader.getLocalName();

        if (startElement.equals(ROW_ELEMENT))
        {
            collator.startRow();
        }
        else if (startElement.equals(CELL_ELEMENT))
        {
            defineCell(reader);
        }
    }

    private void defineCell(final XMLStreamReader reader)
    {
        String location = "";
        String type = "";

        for (int i = 0; i < reader.getAttributeCount(); i++)
        {
            final String attrName = reader.getAttributeLocalName(i);
            final String attrValue = reader.getAttributeValue(i);

            if (attrName.equals(CELL_LOCATION_ATTRIBUTE))
            {
                location = attrValue;
            }
            else if (attrName.equals(CELL_TYPE_ATTRIBUTE))
            {
                type = attrValue;
            }
        }

        if (type.equals(CELL_TYPE_STRING))
        {
            collator.newSharedStringsCell(location);
        }
        else
        {
            collator.newCell(location);
        }
    }
}
