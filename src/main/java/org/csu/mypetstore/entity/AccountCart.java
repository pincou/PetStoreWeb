package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cart")
public class AccountCart {
    @TableId(value = "username")
    private String username;
    @TableField("itemId")
    private String itemId;
    @TableField("number")
    private Integer number;
}
