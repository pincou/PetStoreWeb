package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("profile")
public class Profile {
    @TableId(value = "userid")
    private String username;
    @TableField("favcategory")
    private String favouriteCategoryId;
    @TableField("langpref")
    private String languagePreference;
    @TableField("mylistopt")
    private int listOption;
    @TableField("banneropt")
    private int bannerOption;
}
