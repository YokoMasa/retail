package com.retailapp.proto.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;
import java.util.stream.Collectors;

import com.retailapp.proto.common.ErrorCodeConst;
import com.retailapp.proto.common.RetailDataTypes;
import com.retailapp.proto.dto.WholesaleEstimateDTO;
import com.retailapp.proto.dto.WholesaleEstimateDetailDTO;
import com.retailapp.proto.exception.AppException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.springframework.util.StringUtils;

public class WholesaleEstimateParser {
    
    /**
     * Excelファイルの一番左のシートから卸向け見積情報を読み取る。
     * 
     * @param is 見積ExcelファイルのInputStream
     * @param mappingInfoList 見積Excelファイルのマッピング情報
     * @return 見積DTO
     * @throws AppException
     */
    public static WholesaleEstimateDTO parse(InputStream is, List<MappingInfo> mappingInfoList) throws AppException {
        WholesaleEstimateDTO dto = new WholesaleEstimateDTO();
        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            parseDetails(dto, sheet, mappingInfoList);
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
            throw new AppException(ErrorCodeConst.INPUT_FILE_ENCRYPTED, "ファイルが暗号化されています。");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(ErrorCodeConst.FILE_IO, "ファイルが開けませんでした。");
        }
        return dto;
    }

    private static void parseDetails(WholesaleEstimateDTO dto, Sheet sheet, List<MappingInfo> mappingInfoList) throws AppException {
        // 見積詳細のみに絞る
        List<MappingInfo> detailMappingInfoList = filterDetailMappingInfo(mappingInfoList);

        // 見積詳細マッピング位置チェック。
        validateDetailMappingPositions(detailMappingInfoList);

        boolean reachedToTheEnd = false;
        int rowOffset = 0;
        while (!reachedToTheEnd) {
            // 1行見積詳細を読み込み
            WholesaleEstimateDetailDTO detail = new WholesaleEstimateDetailDTO();
            for (MappingInfo info: mappingInfoList) {
                readDetail(detail, sheet, info, rowOffset);
            }

            // 商品JANが空なら見積詳細読み込み終了
            if (!StringUtils.hasText(detail.getProductJan())) {
                reachedToTheEnd = true;
            } else {
                dto.addDetail(detail);
            }

            // 次の行へ
            rowOffset++;
        }

    }

    private static List<MappingInfo> filterDetailMappingInfo(List<MappingInfo> mappingInfoList) {
        return mappingInfoList.stream().filter(info -> {
            return info.getType() == RetailDataTypes.SELLING_START_DATE ||
                info.getType() == RetailDataTypes.PRODUCT_CATEGORY ||
                info.getType() == RetailDataTypes.PRODUCT_JAN ||
                info.getType() == RetailDataTypes.PRODUCT_MANUFACTURER ||
                info.getType() == RetailDataTypes.PRODUCT_NAME ||
                info.getType() == RetailDataTypes.PRODUCT_SPECIFICATION ||
                info.getType() == RetailDataTypes.COUNT ||
                info.getType() == RetailDataTypes.WHOLESALE_PRICE ||
                info.getType() == RetailDataTypes.CONDITION ||
                info.getType() == RetailDataTypes.NET_WHOLESALE_PRICE ||
                info.getType() == RetailDataTypes.NOTES;
        }).collect(Collectors.toList());
    }

    /**
     * 見積詳細マッピング位置チェック。見積詳細情報は同じ行に並んでいる必要がある。
     * 
     * @param detailMappingInfoList 見積詳細マッピング位置
     * @throws AppException
     */
    private static void validateDetailMappingPositions(List<MappingInfo> detailMappingInfoList) throws AppException {
        if (detailMappingInfoList.size() == 0) {
            return;
        }

        int row = detailMappingInfoList.get(0).getCellAddress().getRow();

        for (MappingInfo info: detailMappingInfoList) {
            if (info.getCellAddress().getRow() != row) {
                throw new AppException(ErrorCodeConst.FORMAT_ESTIMATE_INFO_NOT_ALIGNED, "見積詳細情報は同じ行に並んでいる必要があります。");
            }
        }
    }

    private static void readDetail(WholesaleEstimateDetailDTO dto, Sheet sheet, MappingInfo mappingInfo, int rowOffset) {
        CellAddress cellAddress = new CellAddress(mappingInfo.getCellAddress().getRow() + rowOffset, mappingInfo.getCellAddress().getColumn());
        switch (mappingInfo.getType()) {
            case SELLING_START_DATE:
            dto.setSellingStartDate(ExcelUtil.getCellValAsDate(sheet, cellAddress));
            break;
            case PRODUCT_CATEGORY:
            dto.setProductCategory(ExcelUtil.getCellValAsString(sheet, cellAddress));
            break;
            case PRODUCT_JAN:
            String tempJan = ExcelUtil.getCellValAsString(sheet, cellAddress);
            if (tempJan != null) {
                tempJan = Normalizer.normalize(tempJan, Form.NFKC);
            }
            dto.setProductJan(tempJan);
            break;
            case PRODUCT_MANUFACTURER:
            dto.setProductManufacturer(ExcelUtil.getCellValAsString(sheet, cellAddress));
            break;
            case PRODUCT_NAME:
            dto.setProductName(ExcelUtil.getCellValAsString(sheet, cellAddress));
            break;
            case PRODUCT_SPECIFICATION:
            dto.setProductSpecification(ExcelUtil.getCellValAsString(sheet, cellAddress));
            break;
            case COUNT:
            dto.setCount(ExcelUtil.getCellValAsString(sheet, cellAddress));
            break;
            case WHOLESALE_PRICE:
            dto.setWholesalePrice(ExcelUtil.getCellValAsDouble(sheet, cellAddress));
            break;
            case NET_WHOLESALE_PRICE:
            dto.setNetWholesalePrice(ExcelUtil.getCellValAsDouble(sheet, cellAddress));
            break;
            case CONDITION:
            dto.setCondition(ExcelUtil.getCellValAsString(sheet, cellAddress));
            break;
            case NOTES:
            dto.setNotes(ExcelUtil.getCellValAsString(sheet, cellAddress));
            break;
        }
    }

}
