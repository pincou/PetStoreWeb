package org.csu.mypetstore;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.entity.AccountCart;
import org.csu.mypetstore.entity.Category;
import org.csu.mypetstore.entity.Item;
import org.csu.mypetstore.entity.Order;
import org.csu.mypetstore.persistence.AccountCartMapper;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyPetStoreSsmDevApplicationTests {
    @Autowired
    private CategoryMapper categoryMapper;
  @Autowired
  private AccountCartMapper accountCartMapper;
  @Autowired
   private ItemMapper itemMapper;
  @Autowired
  private OrderMapper orderMapper;
    @Test
    void contextLoads() {
        Category category=categoryMapper.selectById("CATS");
        System.out.println(category);

    }
    @Test
    void test1(){
        Item item=itemMapper.selectById("EST-1");
        System.out.println(item);
    }
    @Test
    void  test(){
        String username="wx";
        QueryWrapper<AccountCart> queryWrapper=new QueryWrapper<>();//查找list时用这种
        queryWrapper.eq("username",username);
        List<AccountCart> accountCarts=accountCartMapper.selectList(queryWrapper);
        System.out.println(accountCarts);
    }
    @Test
    void testForOrder(){
        String orderid="1000";
       Order order= orderMapper.selectById(orderid);
        System.out.println(order);
    }

}
