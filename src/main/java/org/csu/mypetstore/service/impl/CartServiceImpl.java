package org.csu.mypetstore.service.impl;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.mypetstore.entity.*;
import org.csu.mypetstore.persistence.AccountCartMapper;
import org.csu.mypetstore.persistence.ItemQuantityMapper;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.vo.CartItemsVO;
import org.csu.mypetstore.vo.CartVO;
import org.csu.mypetstore.vo.ItemVO;
import org.csu.mypetstore.vo.SignVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("cartservice")
public class CartServiceImpl implements CartService {
    @Autowired
    private AccountCartMapper accountCartMapper;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ItemQuantityMapper itemQuantityMapper;

    @Override
    public CartVO getAccountCart(String username) {
        CartVO cartVO = new CartVO();
        QueryWrapper<AccountCart> queryWrapper = new QueryWrapper<>();//查找list时用这种
        queryWrapper.eq("username", username);
        List<AccountCart> accountCarts = accountCartMapper.selectList(queryWrapper);
        cartVO.setAccountCarts(accountCarts);//取得数据表

        List<CartItemsVO> cartItemsVOS = new ArrayList<>();
        for (AccountCart accountCart : accountCarts) {//遍历每个数据表的项
            CartItemsVO cartItem = new CartItemsVO();
            BigDecimal total;
            ItemVO itemVO = catalogService.getItem(accountCart.getItemId());//取得item
            cartItem.setInStock(true);//设置库存
            cartItem.setItemVO(itemVO);//设置item具体信息
            Integer number = accountCart.getNumber();//设置数量
            cartItem.setQuantity(number);
            total = calculateTotal(cartItem, itemVO, number);
            cartItem.setTotal(total);
            cartItemsVOS.add(cartItem);

        }
        BigDecimal subtotal = getSubTotal(cartItemsVOS);
        cartVO.setSubtotal(subtotal);
        cartVO.setCartItemsVOS(cartItemsVOS);
        cartVO.setNumberOfItems(0);
        return cartVO;
    }

    //计算单品总价
    @Override
    public BigDecimal calculateTotal(CartItemsVO cartItem, ItemVO itemVO, Integer number) {
        BigDecimal total;
        if (itemVO != null && itemVO.getListPrice() != null) {
            total = itemVO.getListPrice().multiply(new BigDecimal(number));
        } else {
            total = null;
        }
        return total;
    }
    //计算所有商品总价

    @Override
    public BigDecimal getSubTotal(List<CartItemsVO> cartItemsVOS) {
        BigDecimal subTotal = new BigDecimal("0");
        Iterator<CartItemsVO> items = getAllCartItems(cartItemsVOS);
        while (items.hasNext()) {
            CartItemsVO cartItemsVO = items.next();
            ItemVO itemVO = cartItemsVO.getItemVO();
            BigDecimal listPrice = itemVO.getListPrice();
            BigDecimal quantity = new BigDecimal(String.valueOf(cartItemsVO.getQuantity()));
            subTotal = subTotal.add(listPrice.multiply(quantity));
        }
        return subTotal;

    }

    //得到列表中所有物品
    @Override
    public Iterator<CartItemsVO> getAllCartItems(List<CartItemsVO> cartItemsVOS) {

        return cartItemsVOS.iterator();
    }

    @Override
    public Iterator<AccountCart> getAllAccountCart(List<AccountCart> accountCarts) {

        return accountCarts.iterator();
    }

    //设置商品数量
    @Override
    public void setQuantityByItemId(String username, String itemId, Integer quantity) {
        AccountCart accountCart = new AccountCart();
        accountCart.setItemId(itemId);
        accountCart.setUsername(username);
        accountCart.setNumber(quantity);
        //将数据库中的相应的项进行数量的修改
        UpdateWrapper<AccountCart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username).eq("itemId", itemId)
                .set("number", quantity);
        accountCartMapper.update(null, updateWrapper);
    }

    //添加商品数量时的改动
    public void incrementQuantityByItemId(String username, String itemId, Integer quantity) {
//        AccountCart accountCart = new AccountCart();
//        accountCart.setItemId(itemId);
//        accountCart.setUsername(username);
//        accountCart.setNumber(quantity);
        //将数据库中的相应的项进行数量的增加
        UpdateWrapper<AccountCart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username).eq("itemId", itemId)
                .set("number", quantity + 1);
        accountCartMapper.update(null, updateWrapper);

    }

    @Override
    public boolean containsItemId(List<AccountCart> accountCarts, String itemId) {
        for (AccountCart accountCart : accountCarts) {
            if (accountCart.getItemId().equals(itemId)) {
                return true;
            }
        }
        return false;
    }


    //返回符合商品,数据库
    @Override
    public AccountCart getAccountCartByItemId(List<AccountCart> accountCarts, String itemId) {
        for (AccountCart accountCart : accountCarts) {
            if (accountCart.getItemId().equals(itemId)) {
              int num=  accountCart.getNumber();
               num++;
               accountCart.setNumber(num);
                return accountCart;
            }
        }
        return null; // 如果未找到匹配项，返回null
    }

    @Override
    public boolean isItemInStock(String itemId) {
        ItemQuantity itemQuantity = itemQuantityMapper.selectById(itemId);
        if (itemQuantity != null) {
            Integer quantity = itemQuantity.getQuantity();
            if (quantity > 0) {
                return true;
            }
        }
        return false;


    }

    @Override
    public AccountCart  addItem(ItemVO itemVO, boolean isInStock, String username) {
        String itemId = itemVO.getItemId();
        AccountCart accountCart = new AccountCart();
//        CartItemsVO cartItemsVO = new CartItemsVO();
//        cartItemsVO.setItemVO(itemVO);
//        cartItemsVO.setQuantity(1);
//        cartItemsVO.setInStock(isInStock);
        accountCart.setItemId(itemId);
        accountCart.setNumber(1);
        accountCart.setUsername(username);

        //将该项添加到数据库中
        accountCartMapper.insert(accountCart);
        return accountCart;
    }
    //移除商品
    @Override
    public ItemVO removeItemById(String username,List<CartItemsVO> cartItemsVOS,String itemId) {
            for (CartItemsVO cartItemsVO : cartItemsVOS) {
                if (cartItemsVO.getItemVO().getItemId().equals(itemId)) {
                  ItemVO itemVO=cartItemsVO.getItemVO();
                  cartItemsVOS.remove(cartItemsVO);
                    UpdateWrapper<AccountCart> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("username", username).eq("itemId", itemId);
                    accountCartMapper.delete(updateWrapper);

                    return itemVO;
                }
            }
            return null;
        }

}





