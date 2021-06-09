package com.retailapp.proto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.retailapp.proto.common.ErrorCodeConst;
import com.retailapp.proto.common.MessageConst;
import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.common.RetailDataTypes;
import com.retailapp.proto.dto.FormErrorDTO;
import com.retailapp.proto.dto.PagedListDTO;
import com.retailapp.proto.dto.ValidationResultDTO;
import com.retailapp.proto.dto.WholesaleEstimateDTO;
import com.retailapp.proto.entity.projections.WholesaleEstimateMetadata;
import com.retailapp.proto.excel.MappingInfo;
import com.retailapp.proto.excel.WholesaleEstimateParser;
import com.retailapp.proto.exception.AppException;
import com.retailapp.proto.service.WholesaleEstimateService;

import org.apache.poi.ss.util.CellAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/wholesaleEstimate")
public class EstimateController {

    @Autowired
    private WholesaleEstimateService service;

    @Autowired
    private MessageResolver message;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        Optional<WholesaleEstimateDTO> op = service.getById(id);
        if (op.isPresent()) {
            return ResponseEntity.ok().body(ApiResponse.ok(op.get()));
        } else {
            ApiResponse<?> r = ApiResponse.error(ErrorCodeConst.NOT_FOUND, message.get(MessageConst.ERROR_NOT_FOUND));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r);
        }
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        PagedListDTO<WholesaleEstimateMetadata> list = service.getList(page);
        return ResponseEntity.ok().body(ApiResponse.ok(list));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody WholesaleEstimateDTO wholesaleEstimateDTO) {
        try {
            service.create(wholesaleEstimateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(null));
        } catch (AppException appE) {
            appE.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(appE.getErrorCode(), appE.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody WholesaleEstimateDTO wholesaleEstimateDTO) {
        ValidationResultDTO validationResult = service.check(wholesaleEstimateDTO);
        if (validationResult.hasError()) {
            ApiResponse<List<FormErrorDTO>> res = ApiResponse.error(ErrorCodeConst.FIELD_ERROR, message.get(MessageConst.ERROR_FIELD), validationResult.getErrors());
            return ResponseEntity.badRequest().body(res);
        } else {
            return ResponseEntity.ok().body(ApiResponse.ok(null));
        }
    }

    @PostMapping(path = "/readFromExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> readFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            WholesaleEstimateDTO dto = WholesaleEstimateParser.parse(file.getInputStream(), getMappingInfo());
            service.correctProductInfo(dto);
            return ResponseEntity.ok(ApiResponse.ok(dto));
        } catch (AppException appE) {
            return ResponseEntity.badRequest().body(ApiResponse.error(appE.getErrorCode(), appE.getMessage()));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCodeConst.FILE_IO, message.get(MessageConst.ERROR_FILE_IO)));
        }
    }

    private List<MappingInfo> getMappingInfo() {
        List<MappingInfo> mappingInfoList = new ArrayList<>();
        mappingInfoList.add(new MappingInfo(RetailDataTypes.SELLING_START_DATE, new CellAddress(11, 0)));
        mappingInfoList.add(new MappingInfo(RetailDataTypes.PRODUCT_CATEGORY, new CellAddress(11, 1)));
        mappingInfoList.add(new MappingInfo(RetailDataTypes.PRODUCT_JAN, new CellAddress(11, 2)));
        mappingInfoList.add(new MappingInfo(RetailDataTypes.PRODUCT_MANUFACTURER, new CellAddress(11, 3)));
        mappingInfoList.add(new MappingInfo(RetailDataTypes.PRODUCT_NAME, new CellAddress(11, 4)));
        mappingInfoList.add(new MappingInfo(RetailDataTypes.PRODUCT_SPECIFICATION, new CellAddress(11, 5)));
        mappingInfoList.add(new MappingInfo(RetailDataTypes.COUNT, new CellAddress(11, 6)));
        mappingInfoList.add(new MappingInfo(RetailDataTypes.WHOLESALE_PRICE, new CellAddress(11, 17)));
        mappingInfoList.add(new MappingInfo(RetailDataTypes.CONDITION, new CellAddress(11, 18)));
        mappingInfoList.add(new MappingInfo(RetailDataTypes.NET_WHOLESALE_PRICE, new CellAddress(11, 19)));
        return mappingInfoList;
    }

}