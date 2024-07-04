package org.csu.mypetstore.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVO {
    private String itemId;
    private String productId;
    private String productName;
//    private String description;
    private String descriptionImage;
    private String descriptionText;
    private BigDecimal listPrice;
    private String attributes1;
    private String attributes2;
    private String attributes3;
    private String attributes4;
    private String attributes5;
    private Integer quantity;


}
