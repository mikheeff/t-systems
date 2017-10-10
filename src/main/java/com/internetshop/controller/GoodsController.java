package com.internetshop.controller;

import com.internetshop.entities.GoodsEntity;
import com.internetshop.model.Goods;
import com.internetshop.service.api.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "products";
    }
//    @RequestMapping(value ="/page/{page}", method = RequestMethod.GET)
//    public String getAllUsers(@PathVariable(value = "page") int page, ModelMap modelMap) {
//        modelMap.put("listGoods", goodsService.getAllGoods());
//        return "products";
//    }


    @RequestMapping(value ="../add", method = RequestMethod.GET)
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

    @RequestMapping(value ="/delete/{id}", method = RequestMethod.GET)
    public String deleteGoods(@PathVariable(value = "id") int id, ModelMap modelMap) {
        this.goodsService.deleteGoodsById(id);
        modelMap.put("listGoods", goodsService.getAllGoods());
        return "catalog_page";
    }
    @RequestMapping(value ="/edit/{id}", method = RequestMethod.GET)
    public String editGoods(@PathVariable(value = "id") int id, ModelMap modelMap) {
        modelMap.put("goods", goodsService.getGoodsById(id));
        return "edit_page";
    }
    @RequestMapping(value ="/edit", method = RequestMethod.POST)
    public String editGoods(@ModelAttribute(value = "goods") Goods goods, ModelMap modelMap) {
        this.goodsService.updateGoods(goods);
        modelMap.put("listGoods", goodsService.getAllGoods());
        return "catalog_page";
    }
}
