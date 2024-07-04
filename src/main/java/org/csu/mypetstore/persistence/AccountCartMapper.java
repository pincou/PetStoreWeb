package org.csu.mypetstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.csu.mypetstore.entity.AccountCart;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCartMapper extends BaseMapper<AccountCart>{

}
