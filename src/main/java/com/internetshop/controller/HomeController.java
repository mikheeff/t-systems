package com.internetshop.controller;

import com.internetshop.service.api.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private GoodsService goodsService;
    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public String main() {
        return "welcome";
    }
}
