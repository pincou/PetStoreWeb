package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product")
public class Product {
    @TableId(value = "productid")
    private String productId;
    @TableField("category")
    private String categoryId;
    @TableField("name")
    private String name;
    @TableField("descn")
    private String description;
}
