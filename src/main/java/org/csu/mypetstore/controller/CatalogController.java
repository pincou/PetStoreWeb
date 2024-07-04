package org.csu.mypetstore.controller;

import jakarta.servlet.http.HttpSession;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.vo.CategoryVO;
import org.csu.mypetstore.vo.ItemVO;
import org.csu.mypetstore.vo.ProductVO;
import org.csu.mypetstore.vo.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("main")
    public String index(HttpSession session) {
        int flag = 0;
        session.setAttribute("flag", flag);
        return "catalog/main";
    }

    @GetMapping("viewCategory")
    public String viewCategory(String categoryId, Model model) {
        CategoryVO categoryVO = catalogService.getCategory(categoryId);
        model.addAttribute("category", categoryVO);//前端使用第一个参数获取数据
        return "catalog/category";
    }

    @GetMapping("viewProduct")
    public String viewProduct(String productId, Model model) {
        ProductVO productVO = catalogService.getProduct(productId);
        model.addAttribute("product", productVO);
        return "catalog/product";
    }

    @GetMapping("viewItem")
    public String viewForItem(@RequestParam String itemId, Model model) {
        ItemVO itemVO = catalogService.getItem(itemId);
        model.addAttribute("item", itemVO);
        return "catalog/item";
    }

    @PostMapping("searchProduct")
    public String search(String keyword, Model model) {
        SearchVO searchVO = catalogService.searchProductList(keyword);
        model.addAttribute("searchList", searchVO);
        return "catalog/searchProduct";
    }

    @GetMapping("help")
    public String helpForm(){
        return "common/help";
    }
}
