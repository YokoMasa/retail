package com.retailapp.proto.excel;

import java.io.File;
import java.io.IOException;

import com.retailapp.proto.exception.AppException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelTest {
    
    public static void readTest(String filePath) throws AppException {
        try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);
            System.out.println("Cell val at (0, 0) is: \"" + getCellValAsString(cell) + "\"");
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
            throw new AppException(filePath + "は暗号化されています。");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(filePath + "が開けませんでした。");
        }
    }

    public static String getCellValAsString(Cell cell) throws AppException {
        CellType cellType = cell.getCellType();
        if (cellType.equals(CellType.FORMULA)) {
            cellType = cell.getCachedFormulaResultType();
        }

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
    }
}
