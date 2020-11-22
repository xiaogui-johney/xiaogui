package com.xiaogui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 加入IoC容器中
@RequestMapping("/product")
public class ProductController {
    @RequestMapping("/findAll")
    public String findAll(){
        return "product-list";
    }


}
