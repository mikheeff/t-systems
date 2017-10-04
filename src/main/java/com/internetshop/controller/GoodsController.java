package com.internetshop.controller;

import com.internetshop.entities.GoodsEntity;
import com.internetshop.model.Goods;
import com.internetshop.service.api.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/catalog")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllUsers(ModelMap modelMap) {
        modelMap.put("listGoods", goodsService.getAllGoods());
        return "catalog_page";
    }
    @RequestMapping(value ="/add", method = RequestMethod.GET)
    public String addGoods(ModelMap modelMap) {
        modelMap.put("goods", new GoodsEntity());
        return "add_page";
    }
    @RequestMapping(value ="/add", method = RequestMethod.POST)
    public String addGoods(@ModelAttribute (value = "goods") GoodsEntity goods, ModelMap modelMap) {
//        this.goodsService.
//                modelMap.put("listGoods", goodsService.getAllGoods());
        return "catalog_page";
    }
}
