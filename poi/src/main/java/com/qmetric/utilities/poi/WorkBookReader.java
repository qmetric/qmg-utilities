package com.qmetric.utilities.poi;

import com.qmetric.utilities.file.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class WorkBookReader
{
    private final FileUtils fileUtils;

    public WorkBookReader(final FileUtils fileUtils)
    {
        this.fileUtils = fileUtils;
    }

    public WorkBookReader()
    {
        this(null);
    }

    public Workbook readWorksheet(final String worksheetLocation)
    {
        final Workbook workbook;

        try
        {
            workbook = WorkbookFactory.create(fileUtils.inputStreamFrom(worksheetLocation));
        }
        catch (Exception e)
        {
            throw new RuntimeException(String.format("Could not read spreadsheet from location '%s'", worksheetLocation), e);
        }

        return workbook;
    }

    public String valueOf(final Cell cell)
    {
        if (cell != null)
        {
            cell.setCellType(Cell.CELL_TYPE_STRING);

            return cell.getStringCellValue();
        }

        return StringUtils.EMPTY;
    }

    public boolean cellHasContent(final Cell cell)
    {
        return cell != null && isNotBlankCell(cell);
    }

    private boolean isNotBlankCell(final Cell cell)
    {
        return (cell.getCellType() != Cell.CELL_TYPE_BLANK) && (isNotBlankStringCell(cell) || isNotBlankNumeric(cell));
    }

    private boolean isNotBlankStringCell(final Cell cell)
    {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
        {
            return StringUtils.isNotBlank(cell.getStringCellValue());
        }

        return false;
    }

    private boolean isNotBlankNumeric(final Cell cell)
    {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
        {
            return true;
        }

        return false;
    }
}
