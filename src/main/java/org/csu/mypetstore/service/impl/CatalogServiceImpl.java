package org.csu.mypetstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.entity.Category;
import org.csu.mypetstore.entity.Item;
import org.csu.mypetstore.entity.ItemQuantity;
import org.csu.mypetstore.entity.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ItemQuantityMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("catalogService")//相当于起别名
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemQuantityMapper itemQuantityMapper;

    @Override
    public CategoryVO getCategory(String categoryId) {
        CategoryVO categoryVO = new CategoryVO();
        Category category = categoryMapper.selectById(categoryId);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();//查找list时用这种
        queryWrapper.eq("category", categoryId);
        List<Product> productList = productMapper.selectList(queryWrapper);
        categoryVO.setCategoryId(categoryId);
        categoryVO.setCategoryName(category.getName());
        categoryVO.setProductList(productList);
        return categoryVO;
    }

    @Override
    public ProductVO getProduct(String productId) {
        ProductVO productVO = new ProductVO();
        Product product = productMapper.selectById(productId);
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productid", productId);
        List<Item> itemList = itemMapper.selectList(queryWrapper);
        productVO.setProductId(productId);
        productVO.setCategoryId(product.getCategoryId());
        productVO.setProductName(product.getName());
        productVO.setItemList(itemList);
        return productVO;
    }

    @Override
    public ItemVO getItem(String itemId) {
        ItemVO itemVO = new ItemVO();
        Item item = itemMapper.selectById(itemId);

        Product product = productMapper.selectById(item.getProductId());
        ItemQuantity itemQuantity = itemQuantityMapper.selectById(itemId);
        itemVO.setAttributes1(item.getAttribute1());
        itemVO.setAttributes2(item.getAttribute2());
        itemVO.setAttributes3(item.getAttribute3());
        itemVO.setAttributes4(item.getAttribute4());
        itemVO.setAttributes5(item.getAttribute5());
        itemVO.setItemId(itemId);
        itemVO.setProductName(product.getName());

        itemVO.setProductId(product.getProductId());

//        itemVO.setDescription(product.getDescription());
        String[] temp = product.getDescription().split("\"");
        itemVO.setDescriptionImage(temp[1]);
        itemVO.setDescriptionText(temp[2].substring(1));
        itemVO.setListPrice(item.getListPrice());

        itemVO.setQuantity(itemQuantity.getQuantity());
        return itemVO;
    }

    @Override
    public SearchVO searchProductList(String keyword) {
        List<SearchImageVO> searchImageVOS = new ArrayList<>();
        SearchVO searchVO = new SearchVO();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword)
                .or()
                .like("descn", keyword);
        List<Product> productList = productMapper.selectList(queryWrapper);
        for (Product product : productList) {
            SearchImageVO searchImageVO = new SearchImageVO();
            searchImageVO.setProductId(product.getProductId());
            searchImageVO.setCategoryId(product.getCategoryId());
            searchImageVO.setName(product.getName());
            searchImageVO.setDescription(product.getDescription());
            // 这里假设产品描述图像的URL保存在descriptionImage字段中
            String[] temp = product.getDescription().split("\"");
            searchImageVO.setDescriptionImage(temp[1]);
            searchImageVO.setDescriptionText(temp[2].substring(1));
            searchImageVOS.add(searchImageVO);
        }
        searchVO.setSearchImageVOS(searchImageVOS);
        return searchVO;
    }


}
