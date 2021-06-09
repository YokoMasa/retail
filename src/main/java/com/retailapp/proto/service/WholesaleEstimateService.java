package com.retailapp.proto.service;

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
import com.retailapp.proto.dto.WholesaleEstimateDetailDTO;
import com.retailapp.proto.entity.Product;
import com.retailapp.proto.entity.Retailer;
import com.retailapp.proto.entity.WholesaleEstimate;
import com.retailapp.proto.entity.WholesaleEstimateDetail;
import com.retailapp.proto.entity.WholesalePrice;
import com.retailapp.proto.entity.projections.WholesaleEstimateMetadata;
import com.retailapp.proto.exception.AppException;
import com.retailapp.proto.repository.ProductRepository;
import com.retailapp.proto.repository.WholesaleEstimateDetailRepository;
import com.retailapp.proto.repository.WholesaleEstimateRepository;
import com.retailapp.proto.repository.WholesalePriceRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class WholesaleEstimateService {

    public static final int PAGE_SIZE = 20;

    private Logger logger = LoggerFactory.getLogger(WholesaleEstimateService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WholesalePriceRepository wholesalePriceRepository;

    @Autowired
    private WholesaleEstimateRepository wholesaleEstimateRepository;

    @Autowired
    private WholesaleEstimateDetailRepository wholesaleEstimateDetailRepository;

    @Autowired
    private MessageResolver message;

    /**
     * 見積リスト取得
     * 
     * @param page 1起点のページ数
     * @return
     */
    public PagedListDTO<WholesaleEstimateMetadata> getList(int page) {
        page--;
        Page<WholesaleEstimateMetadata> p = wholesaleEstimateRepository.findMetadataBy(PageRequest.of(page, PAGE_SIZE));
        return PagedListDTO.create(p);
    }

    public Optional<WholesaleEstimateDTO> getById(int id) {
        Optional<WholesaleEstimate> op = wholesaleEstimateRepository.findById(id);
        if (!op.isPresent()) {
            return Optional.empty();
        }

        WholesaleEstimate e = op.get();
        WholesaleEstimateDTO dto = new WholesaleEstimateDTO();
        dto.setEstimateDate(e.getEstimateDate());
        dto.setTitle(e.getTitle());
        dto.setRetailer(e.getRetailer());
        dto.setManufacturer(e.getManufacturer());

        for (WholesaleEstimateDetail d: e.getDetails()) {
            WholesaleEstimateDetailDTO detailDto = new WholesaleEstimateDetailDTO();
            detailDto.setSellingStartDate(d.getSellingStartDate());
            detailDto.setProductManufacturer(d.getProductManufacturer());
            detailDto.setProductSpecification(d.getProductSpecification());
            detailDto.setProductJan(d.getProductJan());
            detailDto.setProductName(d.getProductName());
            detailDto.setProductCategory(d.getProductCategory());
            detailDto.setCount(d.getCount());
            detailDto.setWholesalePrice(d.getWholesalePrice());
            detailDto.setCondition(d.getConditions());
            detailDto.setNetWholesalePrice(d.getNetWholesalePrice());
            detailDto.setNotes(d.getNotes());
            dto.addDetail(detailDto);
        }

        return Optional.of(dto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(WholesaleEstimateDTO estimateDto) throws AppException {
        if (!checkIfSavable(estimateDto)) {
            throw new AppException(ErrorCodeConst.FIELD_ERROR, message.get(MessageConst.ERROR_FIELD));
        }
        
        WholesaleEstimate e = new WholesaleEstimate();
        e.setTitle(estimateDto.getTitle());
        e.setEstimateDate(estimateDto.getEstimateDate());
        if (estimateDto.getRetailer() != null) {
            e.setRetailer(estimateDto.getRetailer());
        }

        if (estimateDto.getManufacturer() != null) {
            e.setManufacturer(estimateDto.getManufacturer());
        }

        WholesaleEstimate estimate = wholesaleEstimateRepository.saveAndFlush(e);
        int newEstimateId = estimate.getId();
        logger.debug("new ID: " + newEstimateId);

        for (WholesaleEstimateDetailDTO detailDto: estimateDto.getDetailList()) {
            createDetail(estimate, estimateDto, detailDto);
        }
    }

    /**
     * 卸向け見積詳細を登録
     * 
     * @param estimate
     * @param detailDto
     */
    private void createDetail(WholesaleEstimate estimate, WholesaleEstimateDTO estimateDto, WholesaleEstimateDetailDTO detailDto) {
        Optional<Product> productOptional = productRepository.findByJan(detailDto.getProductJan());

        // 商品が存在しなければ商品をまず登録
        Product p = null;
        if (!productOptional.isPresent()) {
            p = saveProduct(detailDto);
        } else {
            p = productOptional.get();
        }

        // 仕入れ価格が存在しなければ仕入れ価格を登録
        WholesalePrice wp = null;
        List<WholesalePrice> priceList = wholesalePriceRepository.findExactSame(p.getJan(), detailDto.getWholesalePrice(), detailDto.getNetWholesalePrice());
        if (priceList.size() == 0) {
            wp = saveWholesalePrice(detailDto, p);
        } else {
            wp = priceList.get(0);
        }

        // 卸先の指定がされていて、かつ仕入れ価格がデフォルト価格でない場合、仕入れ価格と仕入先を結びつける
        if (estimate.getRetailer() != null && wp.getDefaultPriceFlag() == WholesalePrice.NON_DEFAULT_PRICE) {
            saveRetailerWholesalePrice(estimate.getRetailer(), wp);
        }

        // 卸向け見積詳細を登録
        WholesaleEstimateDetail detail = new WholesaleEstimateDetail();
        detail.setEstimate(estimate);
        detail.setSellingStartDate(detailDto.getSellingStartDate());
        detail.setProductManufacturer(detailDto.getProductManufacturer());
        detail.setProductSpecification(detailDto.getProductSpecification());
        detail.setProductName(detailDto.getProductName());
        detail.setProductCategory(detailDto.getProductCategory());
        detail.setProductJan(detailDto.getProductJan());
        detail.setCount(detailDto.getCount());
        detail.setWholesalePrice(detailDto.getWholesalePrice());
        detail.setConditions(detailDto.getCondition());
        detail.setNetWholesalePrice(detailDto.getNetWholesalePrice());
        detail.setNotes(detailDto.getNotes());
        wholesaleEstimateDetailRepository.save(detail);
    }

    private WholesalePrice saveWholesalePrice(WholesaleEstimateDetailDTO detailDto, Product p) {
        WholesalePrice wp = new WholesalePrice();
        wp.setJan(p.getJan());
        wp.setWholesalePrice(detailDto.getWholesalePrice());
        wp.setNetWholesalePrice(detailDto.getNetWholesalePrice());
        wp.setConditions(detailDto.getCondition());

        Optional<WholesalePrice> defaultOptional = wholesalePriceRepository.findByJanAndDefaultPriceFlag(p.getJan(), WholesalePrice.DEFAULT_PRICE);
        if (!defaultOptional.isPresent()) {
            wp.setDefaultPriceFlag(WholesalePrice.DEFAULT_PRICE);
        } else {
            wp.setDefaultPriceFlag(WholesalePrice.NON_DEFAULT_PRICE);
        }

        logger.debug("仕入れ価格を登録: " + wp.toString());

        return wholesalePriceRepository.save(wp);
    }

    private Product saveProduct(WholesaleEstimateDetailDTO detailDto) {
        Product p = new Product();
        p.setJan(detailDto.getProductJan());
        p.setName(detailDto.getProductName());

        logger.debug("商品を登録: " + p.toString());

        return productRepository.save(p);
    }

    private void saveRetailerWholesalePrice(Retailer retailer, WholesalePrice price) {
        if (wholesalePriceRepository.getCountOfRetailerWholesalePrice(price.getId(), retailer.getId()) == 0) {
            wholesalePriceRepository.insertRetailerWholesalePrice(price.getId(), retailer.getId());
        }
    }

    /**
     * save出来ないエラーが無いことを確認する。JAN_NOT_FOUND、PRICE_NOT_FOUNDは許容する。
     * 
     * @param estimate
     * @return
     */
    private boolean checkIfSavable(WholesaleEstimateDTO estimate) {
        ValidationResultDTO result = check(estimate);
        if (!result.hasError()) {
            System.out.println("[checkIfSavable()] returning true");
            return true;
        }

        for (FormErrorDTO e: result.getErrors()) {
            if ( !ErrorCodeConst.JAN_NOT_FOUND.equals(e.getErrorCode()) && !ErrorCodeConst.PRICE_NOT_FOUND.equals(e.getErrorCode()) ) {
                System.out.println("[checkIfSavable()] returning false. error code: " + e.getErrorCode() + ", " + e.getMessage());
                return false;
            }
        }
        System.out.println("[checkIfSavable()] returning true");
        return true;
    }

    /**
     * 卸向け見積のバリデーションを行う
     * 
     * @param estimate 
     * @return
     */
    public ValidationResultDTO check(WholesaleEstimateDTO estimate) {
        List<FormErrorDTO> errors = new ArrayList<>();

        if (estimate.getManufacturer() == null) {
            FormErrorDTO e = FormErrorDTO.fieldNonNullError(0, message, RetailDataTypes.MANUFACTURER);
            errors.add(e);
        }
        
        for (int i = 0; i < estimate.getDetailList().size(); i++) {
            WholesaleEstimateDetailDTO detail = estimate.getDetailList().get(i);
            checkDetail(detail, i, errors);
        }

        return new ValidationResultDTO(errors.size() != 0, errors);
    }

    /**
     * 見積詳細バリデーション
     * 
     * @param detail
     * @param itemNo
     * @param errors
     */
    private void checkDetail(WholesaleEstimateDetailDTO detail, int itemNo, List<FormErrorDTO> errors) {
        // janバリデーション
        String jan = detail.getProductJan();
        if (!StringUtils.hasText(jan)) {
            errors.add(FormErrorDTO.fieldNonNullError(itemNo, message, RetailDataTypes.PRODUCT_JAN));
        }

        // wholesalePriceバリデーション
        double wholesalePrice = detail.getWholesalePrice();
        if (wholesalePrice == 0) {
            errors.add(FormErrorDTO.fieldNonZeroError(itemNo, message, RetailDataTypes.WHOLESALE_PRICE));
        }

        // netWholesalePriceバリデーション
        double netWholesalePrice = detail.getNetWholesalePrice();
        if (netWholesalePrice == 0) {
            errors.add(FormErrorDTO.fieldNonZeroError(itemNo, message, RetailDataTypes.NET_WHOLESALE_PRICE));
        }

        if (errors.size() == 0) {
            List<WholesalePrice> l = wholesalePriceRepository.findExactSame(jan, wholesalePrice, netWholesalePrice);
            if (l.size() == 0) {
                errors.add(new FormErrorDTO(itemNo, ErrorCodeConst.PRICE_NOT_FOUND, RetailDataTypes.WHOLESALE_PRICE.getFieldName(), message.get(MessageConst.ERROR_PRICE_NOT_FOUND)));
            }
        }
    }
    
    public void correctProductInfo(WholesaleEstimateDTO dto) {
        for (WholesaleEstimateDetailDTO detail: dto.getDetailList()) {
            Optional<Product> o = productRepository.findByJan(detail.getProductJan());
            if (o.isPresent()) {
                detail.setProductName(o.get().getName());
            }
        }
    }

}
