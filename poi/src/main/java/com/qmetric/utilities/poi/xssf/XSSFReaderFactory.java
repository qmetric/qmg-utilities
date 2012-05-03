package com.qmetric.utilities.poi.xssf;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;

import java.io.IOException;
import java.io.InputStream;

public class XSSFReaderFactory
{
    public XSSFReader create(final InputStream inputStream)
    {
        try
        {
            return new XSSFReader(OPCPackage.open(inputStream));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (OpenXML4JException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
