package com.retailapp.proto.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

@SpringBootTest
public class ExcelUtilTest {
    
    @Test
    public void test001() throws Exception {
        ClassPathResource r = new ClassPathResource("test1.xlsx");
        try(Workbook workbook = WorkbookFactory.create(r.getFile())) {
            workbook.getSheetAt(0);
        }
        Assertions.assertEquals(1, 2);
    }

}
