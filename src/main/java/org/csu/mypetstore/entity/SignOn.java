package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "signon")
public class SignOn {
    @TableId(value = "username")
    private String username;
    @TableField("password")
    private String password;
}
