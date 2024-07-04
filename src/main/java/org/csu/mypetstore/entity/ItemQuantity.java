package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("inventory")
public class ItemQuantity {
    @TableId(value = "itemid")
    private String itemId;
    @TableField("qty")
    private Integer quantity;
}
