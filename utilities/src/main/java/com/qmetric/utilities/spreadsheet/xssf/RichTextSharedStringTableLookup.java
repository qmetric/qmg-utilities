package com.qmetric.utilities.spreadsheet.xssf;

import com.qmetric.utilities.spreadsheet.xssf.SharedStringsTableLookup;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.lang.Object;import java.lang.Override;import java.lang.String;import static java.lang.Integer.parseInt;

public class RichTextSharedStringTableLookup implements SharedStringsTableLookup
{
    private final SharedStringsTable sharedStringsTable;

    public RichTextSharedStringTableLookup(final SharedStringsTable sharedStringsTable)
    {
        this.sharedStringsTable = sharedStringsTable;
    }

    @Override public String lookupValueFor(final String entry)
    {
        return new XSSFRichTextString(sharedStringsTable.getEntryAt(parseInt(entry))).toString();
    }

    @Override public boolean equals(Object o)
    {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override public int hashCode()
    {
        return sharedStringsTable.hashCode();
    }
}
