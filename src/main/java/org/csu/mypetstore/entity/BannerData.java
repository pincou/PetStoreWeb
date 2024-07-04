package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bannerdata")
public class BannerData {
    @TableId(value = "favcategory")
    private String  favouriteCategoryId;
    @TableField("bannername")
    private String bannerName;
}
