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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/catalog")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    private static final int amountOfPicturesOnPage = 9;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllUsers(ModelMap modelMap) {
        List<Goods> goodsList = new ArrayList<>();
        List<Goods> allGoodsList = goodsService.getAllGoods();

        for (int i = 0; i < amountOfPicturesOnPage; i++) {
            goodsList.add(allGoodsList.get(i));
        }
        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages(allGoodsList));
        modelMap.put("listGoods",goodsList);
        return "products";
    }
    @RequestMapping(value ="/page/{number}", method = RequestMethod.GET)
    public String getAllUsers(@PathVariable(value = "number") int number, ModelMap modelMap) {
        List<Goods> goodsList = new ArrayList<>();
        List<Goods> allGoodsList = goodsService.getAllGoods();

        for (int i = (number-1)*amountOfPicturesOnPage , j = 0; (i < allGoodsList.size())&&(j<amountOfPicturesOnPage); i++) { //Цикл разбивает искходный список на подсписок из 9 и менее элементов
            goodsList.add(allGoodsList.get(i));
        }
        modelMap.put("currentPage",number);
        modelMap.put("amountOfPages",getAmountOfPages(allGoodsList));
        modelMap.put("listGoods", goodsList);
        return "products";
    }


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
    public int getAmountOfPages(List<Goods> allGoodsList){
        int amountOfPages;
        if (allGoodsList.size()%amountOfPicturesOnPage==0){
            return allGoodsList.size()/9;
        }
        else {
            return allGoodsList.size()/amountOfPicturesOnPage+1;
        }
    }
}
