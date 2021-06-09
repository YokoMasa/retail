package com.retailapp.proto.excel;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellAddress;

public class ExcelUtil {

    public static Cell getCell(Sheet sheet, CellAddress cellAddress) {
        Row row = sheet.getRow(cellAddress.getRow());
        if (row == null) {
            return null;
        }

        return row.getCell(cellAddress.getColumn());
    }

    public static Date getCellValAsDate(Sheet sheet, CellAddress cellAddress) {
        return getCellValAsDate(getCell(sheet, cellAddress));
    }

    public static Date getCellValAsDate(Cell cell) {
        if (cell == null || cell.getCellType().equals(CellType.STRING)) {
            return null;
        }

        LocalDateTime localDateTime = cell.getLocalDateTimeCellValue();
        if (localDateTime == null) {
            return null;
        }

        return java.sql.Date.valueOf(localDateTime.toLocalDate());
    }

    public static String getCellValAsString(Sheet sheet, CellAddress cellAddress) {
        return getCellValAsString(getCell(sheet, cellAddress));
    }

    public static String getCellValAsString(Cell cell) {
        if (cell == null) {
            return null;
        }

        CellType cellType = cell.getCellType();
        if (cellType.equals(CellType.FORMULA)) {
            cellType = cell.getCachedFormulaResultType();
            switch(cellType) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    double doubleVal = cell.getNumericCellValue();
                    return Double.toString(doubleVal);
                case BOOLEAN:
                    boolean booleanVal = cell.getBooleanCellValue();
                    return Boolean.toString(booleanVal);
                default:
                    return "";
            }
        } else {
            return new DataFormatter().formatCellValue(cell);
        }

    }

    public static double getCellValAsDouble(Sheet sheet, CellAddress cellAddress) {
        return getCellValAsDouble(getCell(sheet, cellAddress));
    }

    public static double getCellValAsDouble(Cell cell) {
        if (cell == null) {
            return 0;
        }

        CellType cellType = cell.getCellType();
        if (cellType.equals(CellType.FORMULA)) {
            cellType = cell.getCachedFormulaResultType();
        }

        switch(cellType) {
            case STRING:
                String strVal = cell.getStringCellValue();
                return Double.parseDouble(strVal);
            case NUMERIC:
                return cell.getNumericCellValue();
            default:
                return 0;
        }
    }

}
