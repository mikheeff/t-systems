package com.internetshop.controller;

import com.internetshop.model.Goods;
import com.internetshop.service.api.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/catalog")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    private static final int amountOfGoodsOnPage = 9; //при вводе больше 9 едет картинки на первой странице??
    private static final int amountOfRandomGoodsOnPage = 6;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllGoods(ModelMap modelMap) { // TODO: 11.10.2017 HttpSession session
        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages());
        modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);

        return "goods";
    }
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public String getAllGoods(@PathVariable(value = "page") int page, ModelMap modelMap) {
        modelMap.put("currentPage",page);
        modelMap.put("amountOfPages",getAmountOfPages());
        modelMap.put("listGoods", goodsService.getAllGoods(amountOfGoodsOnPage*(page-1),amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        return "goods";
    }
    @RequestMapping(value ="/{category}/page/{page}", method = RequestMethod.GET)
    public String getAllGoodsByCategory(@PathVariable("category") String categoryName,@PathVariable(value = "page") int page, ModelMap modelMap) {
        modelMap.put("currentPage",page);
        modelMap.put("amountOfPages",getAmountOfPages(categoryName));
        modelMap.put("listGoods", goodsService.getAllGoodsByCategoryName(0,amountOfGoodsOnPage,categoryName));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryName",categoryName);
        modelMap.put("categoryFilter",true);
        return "goods";
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

    @RequestMapping(value ="/goods/{id}", method = RequestMethod.GET)
    public String getGoodsById(@PathVariable(value = "id") int id, ModelMap modelMap) {
        modelMap.put("goods", goodsService.getGoodsById(id));
        modelMap.put("randomGoods",getRandomGoods());
        return "goods_detail";
    }

    public long getAmountOfPages(){
        if (goodsService.getAmountOfGoods()% amountOfGoodsOnPage ==0){
            return goodsService.getAmountOfGoods()/ amountOfGoodsOnPage;
        }
        else {
            return goodsService.getAmountOfGoods()/ amountOfGoodsOnPage +1;
        }
    }
    public long getAmountOfPages(String categoryName){
        if (goodsService.getAmountOfGoodsByCategoryName(categoryName)% amountOfGoodsOnPage ==0){
            return goodsService.getAmountOfGoodsByCategoryName(categoryName)/ amountOfGoodsOnPage;
        }
        else {
            return goodsService.getAmountOfGoodsByCategoryName(categoryName)/ amountOfGoodsOnPage +1;
        }
    }

    public Set<Goods> getRandomGoods(){
        Set<Goods> randomGoodsSet = new HashSet<>();
        while (randomGoodsSet.size()<amountOfRandomGoodsOnPage) {
            randomGoodsSet.add(goodsService.getRandomGoods());
        }
        return randomGoodsSet;
    }
}
