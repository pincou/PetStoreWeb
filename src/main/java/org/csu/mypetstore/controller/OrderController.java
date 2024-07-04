package org.csu.mypetstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Order")
public class OrderController {
    @GetMapping("OrderForm")
    public String OrderForm(){

        return "order/generateOrder";
    }
}
