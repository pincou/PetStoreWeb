package org.csu.mypetstore.vo;

import lombok.Data;
import org.csu.mypetstore.entity.Item;

import java.math.BigDecimal;
@Data
public class CartItemsVO {
    private ItemVO itemVO;
    private Integer quantity;
    private boolean inStock;
    private BigDecimal total;
}
