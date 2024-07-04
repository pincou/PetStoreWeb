package org.csu.mypetstore.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class SearchImageVO {
    private String productId;
    private String categoryId;
    private String name;
    private String description;

    private String descriptionImage;
    private String descriptionText;
}
