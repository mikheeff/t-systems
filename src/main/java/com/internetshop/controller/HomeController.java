package com.internetshop.controller;

import com.internetshop.service.api.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsController goodsController;

    private static Logger logger = LoggerFactory.getLogger(HomeController.class.getName());


    /**
     * Gets goods for homepage
     * @return homepage
     */
    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public String main(ModelMap modelMap) {
        logger.info("main");
        modelMap.put("randomGoods",goodsController.getRandomGoods(6));
        modelMap.put("listCategory",goodsService.getAllCategories());
        return "index";
    }


}
