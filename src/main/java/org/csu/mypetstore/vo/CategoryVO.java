package org.csu.mypetstore.vo;

import lombok.Data;
import org.csu.mypetstore.entity.Product;

import java.util.List;
@Data//封装数据，一个对象包含三个属性
public class CategoryVO {
    private String categoryId;
    private String categoryName;
    private List<Product> productList;
    private String productId;
}
