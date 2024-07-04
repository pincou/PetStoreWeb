package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account")
public class Account {
    @TableId(value = "userid")
    private String username;
    @TableField("email")
    private String email;
    @TableField("firstname")
    private String firstName;
    @TableField("lastname")
    private String lastName;
    @TableField("status")
    private String status;
    @TableField("addr1")
    private String address1;
    @TableField("addr2")
    private String address2;
    @TableField("city")
    private String city;
    @TableField("state")
    private String state;
    @TableField("zip")
    private String zip;
    @TableField("country")
    private String country;
    @TableField("phone")
    private String phone;

}
