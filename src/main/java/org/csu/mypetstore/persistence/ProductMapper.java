package org.csu.mypetstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.mypetstore.entity.Product;
import org.springframework.stereotype.Repository;

@Repository//定义这个接口的变量时，用括号里的名字能提高搜索效率
public interface ProductMapper extends BaseMapper<Product> {

}
