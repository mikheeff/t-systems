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

//    @RequestMapping(value= "/person/add", method = RequestMethod.POST)
//    public String addPerson(@ModelAttribute("goods") GoodsEntity goodsEntity) {

//        if (goodsEntity.getId() == 0) {
            //new person, add it
//            this.goodsService.addGoods(goodsEntity);
//        } else {
            //existing person, call update
//            this.goodsService.updatePerson(p);
//        }

//        return "add_page";
//    }
    @RequestMapping(value ="/add", method = RequestMethod.GET)
    public String addGoods(ModelMap modelMap) {
        modelMap.put("goods", new Goods());
        return "add_page";
    }
    @RequestMapping(value ="/add", method = RequestMethod.POST)
    public String addGoods(@ModelAttribute (value = "goods") Goods goods, ModelMap modelMap) {
        this.goodsService.addGoods(goods);
        modelMap.put("listGoods", goodsService.getAllGoods());
        return "catalog_page";
    }
}
