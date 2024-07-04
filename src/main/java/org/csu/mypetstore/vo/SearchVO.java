package org.csu.mypetstore.vo;

import lombok.Data;
import org.csu.mypetstore.entity.Product;

import java.util.List;

@Data
public class SearchVO {
    private String productId;
    private String description;
    private String productName;

    private List<SearchImageVO> searchImageVOS;
}
