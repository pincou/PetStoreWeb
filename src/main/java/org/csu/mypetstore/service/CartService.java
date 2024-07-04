package org.csu.mypetstore.service;

import org.csu.mypetstore.entity.AccountCart;
import org.csu.mypetstore.vo.CartItemsVO;
import org.csu.mypetstore.vo.CartVO;
import org.csu.mypetstore.vo.ItemVO;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public interface CartService {
    public CartVO getAccountCart(String username);
    public BigDecimal calculateTotal(CartItemsVO cartItem, ItemVO itemVO, Integer number);
    public BigDecimal getSubTotal(List<CartItemsVO> cartItemsVOS);
    public Iterator<CartItemsVO> getAllCartItems(List<CartItemsVO> cartItemsVOS);
//    public void addItem(Item item, boolean isInStock);
//    public Item removeItemById(String itemId);
  public Iterator<AccountCart> getAllAccountCart(List<AccountCart> accountCarts);
    public void setQuantityByItemId(String username,String itemId, Integer quantity);
    public boolean containsItemId(List<AccountCart> accountCarts,String itemId);

    public void incrementQuantityByItemId(String username,String itemId,Integer quantity);
    public AccountCart getAccountCartByItemId(List<AccountCart> accountCarts, String itemId);
    public boolean isItemInStock(String itemId);
    public AccountCart  addItem(ItemVO itemVO, boolean isInStock,String username);

    public ItemVO removeItemById(String username,List<CartItemsVO> cartItemsVOS,String itemId);
//    public CartItemsVO getCartItemByItemId(List<CartItemsVO> cartItemsVOS, String itemId);
}
