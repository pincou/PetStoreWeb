package org.csu.mypetstore.vo;

import lombok.Data;
import org.csu.mypetstore.entity.AccountCart;
import org.csu.mypetstore.entity.Item;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVO {

    private Integer numberOfItems;
    private BigDecimal subtotal;
   private List<AccountCart> accountCarts;//数据库表格
   private  List<CartItemsVO> cartItemsVOS;
}
