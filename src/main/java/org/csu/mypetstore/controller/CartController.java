package org.csu.mypetstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.csu.mypetstore.entity.AccountCart;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.vo.CartItemsVO;
import org.csu.mypetstore.vo.CartVO;
import org.csu.mypetstore.vo.ItemVO;
import org.csu.mypetstore.vo.SignVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/cartFor")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CatalogService catalogService;


    @GetMapping("init")
    public String InitCart(HttpSession session) {
        String view = null;
        SignVO signVO = (SignVO) session.getAttribute("userInfo");
        if (signVO == null) {
            return view = "account/signon";
        }
        return "cart/cart";
    }


    @PostMapping("updateQuantity")
    public String updateCart(HttpSession session, HttpServletRequest request) {

        CartVO cartVO = (CartVO) session.getAttribute("cart");
        SignVO signVO = (SignVO) session.getAttribute("userInfo");
        Iterator<CartItemsVO> cartItemsVOIterators = cartService.getAllCartItems(cartVO.getCartItemsVOS());
        String username = signVO.getLoginAccount().getUsername();
        BigDecimal subtotal = BigDecimal.ZERO;
        while (cartItemsVOIterators.hasNext()) {
            CartItemsVO cartItemsVO = cartItemsVOIterators.next();
            String itemId = cartItemsVO.getItemVO().getItemId();
            System.out.println(itemId);
            String quantityString = request.getParameter(itemId);
            ItemVO itemVO = catalogService.getItem(itemId);
            Integer quantity = Integer.parseInt(quantityString);
            cartItemsVO.setQuantity(quantity);
            BigDecimal total;
            total = cartService.calculateTotal(cartItemsVO, itemVO, quantity);
            cartItemsVO.setTotal(total);
            cartService.setQuantityByItemId(username, itemId, quantity);//修改数据库数量
            if (quantity < 1) {
                cartItemsVOIterators.remove();
            }
            subtotal = subtotal.add(total);
        }
        cartVO.setSubtotal(subtotal);
        session.setAttribute("cart", cartVO);
        return "cart/cart";
    }

    @PostMapping("addItemToCart")
    public String addOneItemToCart(HttpSession session, HttpServletRequest request) {
        String workingItemId = request.getParameter("workingItemId");//传值
        CartVO cartVO = (CartVO) session.getAttribute("cart");
        SignVO signVO = (SignVO) session.getAttribute("userInfo");
        String username = signVO.getLoginAccount().getUsername();
        List<AccountCart> accountCarts = cartVO.getAccountCarts();//从userInfo得到购物车数据表
        List<CartItemsVO> cartItemsVOS = cartVO.getCartItemsVOS();//列表
        Integer sessionFlag = (Integer) session.getAttribute("flag");
        Integer requestFlag = Integer.parseInt(request.getParameter("flag"));
        System.out.println(sessionFlag + "上面");
        System.out.println(requestFlag);
        if (cartVO == null) {
            cartVO = new CartVO();
        }
        if (requestFlag > sessionFlag) {
            if (cartService.containsItemId(accountCarts, workingItemId)) {//没改
                AccountCart accountCart = cartService.getAccountCartByItemId(accountCarts, workingItemId);//得到了那个订单
                //修改前端页面数据
                Integer quantity = accountCart.getNumber();
                for (CartItemsVO cartItemsVO : cartItemsVOS) {
                    if (cartItemsVO.getItemVO().getItemId().equals(workingItemId)) {
                        cartItemsVO.setQuantity(quantity);
                        ItemVO itemVO = cartItemsVO.getItemVO();
                        BigDecimal total;
                        total = cartService.calculateTotal(cartItemsVO, itemVO, quantity);
                        cartItemsVO.setTotal(total);
                    }
                }
                cartService.incrementQuantityByItemId(username, workingItemId, quantity);//修改后端
                cartVO.setCartItemsVOS(cartItemsVOS);
                int index = accountCarts.indexOf(accountCart);
                if (index != -1) {
                    // 将修改后的 accountCart 替换原始的 accountCart
                    accountCarts.set(index, accountCart);
                }
                cartVO.setAccountCarts(accountCarts);
                BigDecimal subtotal = cartService.getSubTotal(cartItemsVOS);
                cartVO.setSubtotal(subtotal);
                session.setAttribute("cart", cartVO);
                System.out.println("1执行");
            } else {
                boolean isInStock = cartService.isItemInStock(workingItemId);
                ItemVO itemVO = catalogService.getItem(workingItemId);
                //设置前端页面数据
                CartItemsVO cartItemsVO = new CartItemsVO();
                Integer quantity = 1;
                cartItemsVO.setQuantity(quantity);
                cartItemsVO.setInStock(true);
                cartItemsVO.setItemVO(itemVO);
                BigDecimal total;
                total = cartService.calculateTotal(cartItemsVO, itemVO, quantity);
                cartItemsVO.setTotal(total);
                cartItemsVOS.add(cartItemsVO);
                //添加一项，加入会话

                AccountCart newAccountCart = cartService.addItem(itemVO, isInStock, username);
                accountCarts.add(newAccountCart);
                cartVO.setCartItemsVOS(cartItemsVOS);
                cartVO.setAccountCarts(accountCarts);
                BigDecimal subtotal = cartService.getSubTotal(cartItemsVOS);
                cartVO.setSubtotal(subtotal);
                session.setAttribute("cart", cartVO);

                System.out.println("2执行");

            }
            session.setAttribute("flag", requestFlag);
        }


        return "cart/cart";
    }

    @GetMapping("removeCartItem")
    public String RemoveOneCartItem(HttpSession session, HttpServletRequest request) {
        String view = null;
        CartVO cartVO = (CartVO) session.getAttribute("cart");
        SignVO signVO = (SignVO) session.getAttribute("userInfo");
        String username = signVO.getLoginAccount().getUsername();
        List<CartItemsVO> cartItemsVOS = cartVO.getCartItemsVOS();//得到购物车信息列表
        String workingItemId = request.getParameter("workingItemId");

        ItemVO itemVO = cartService.removeItemById(username, cartItemsVOS, workingItemId);
        BigDecimal subtotal = cartService.getSubTotal(cartItemsVOS);
        cartVO.setSubtotal(subtotal);
        session.setAttribute("cart", cartVO);
        if (itemVO == null) {
            session.setAttribute("errorMsg", "Attempted to remove null CartItem from Cart.");
            view = "common/error";
        } else {
            view = "cart/cart";
        }

        return view;
    }

}
