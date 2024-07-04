package org.csu.mypetstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {
    @GetMapping("/demo")
    @ResponseBody
    public String demo(){
        return "ssss";
    }
    @GetMapping("demo1")
    public String demo1(Model model){
        model.addAttribute("messages","Hello");
        return "demo1";
    }
}
