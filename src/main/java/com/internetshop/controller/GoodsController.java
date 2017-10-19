package com.internetshop.controller;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.model.Category;
import com.internetshop.model.Goods;
import com.internetshop.service.api.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @RequestMapping(value ="/employee/add", method = RequestMethod.GET)
    public String addGoods(ModelMap modelMap) {
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        modelMap.put("listCategory",goodsService.getAllCategories());
        return "add_page";
    }
    @RequestMapping(value ="/employee/add/goods", method = RequestMethod.POST)
    public String addGoods(@ModelAttribute (value = "goods") @Valid Goods goods,
                           BindingResult bindingResultGoods,
                           ModelMap modelMap) {

        if(bindingResultGoods.hasErrors()) {
            modelMap.put("error1", "Invalid params!");
            modelMap.put("goods",goods);
            modelMap.put("category",new Category());
            modelMap.put("listCategory",goodsService.getAllCategories());
            return "add_page";
        }

        this.goodsService.addGoods(goods);
        modelMap.put("msgG", "You've been added new goods successfully.");

        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        return "add_page";
    }
    @RequestMapping(value = "/employee/add/category",method = RequestMethod.POST)
    public String addCategory( @ModelAttribute (value = "category")  @Valid Category category,
                               BindingResult bindingResult,ModelMap modelMap){
        if(bindingResult.hasErrors()) {
            modelMap.put("error2", "Invalid params!");
            modelMap.put("goods",new Goods());
            modelMap.put("category",category);
            modelMap.put("listCategory",goodsService.getAllCategories());
            return "add_page";
        }

        this.goodsService.addCategory(category);
        modelMap.put("msgC", "You've been added new category successfully.");

        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        return "add_page";
    }

    @RequestMapping(value ="/employee/delete/{id}", method = RequestMethod.GET)
    public String deleteGoods(@PathVariable(value = "id") int id) {
        this.goodsService.deleteGoodsById(id);
        return "redirect:/catalog";
    }

    @RequestMapping(value ="/employee/edit", method = RequestMethod.POST)
    public String editGoods(@ModelAttribute(value = "goods")  @Valid Goods goods, BindingResult bindingResult,
                            ModelMap modelMap) {
        if(bindingResult.hasErrors()) {
            modelMap.put("error", "Invalid params!");
            modelMap.put("randomGoods",getRandomGoods());
            modelMap.put("goods",goods);
            modelMap.put("listCategory",goodsService.getAllCategories());
            return "goods_detail";
        }
        this.goodsService.updateGoods(goods);
        modelMap.put("goods", goodsService.getGoodsById(goods.getId()));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("msg", "You've been edited goods successfully.");
        return "goods_detail";
    }

    @RequestMapping(value ="/goods/{id}", method = RequestMethod.GET)
    public String getGoodsById(@PathVariable(value = "id") int id, ModelMap modelMap) {
        modelMap.put("goods", goodsService.getGoodsById(id));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());

        return "goods_detail";
    }
    @RequestMapping(value = "/employee/edit/category/{id}", method = RequestMethod.GET)
    public String editCategory(@PathVariable(value = "id") int id, ModelMap modelMap){
        boolean editCatFlag = true;
        modelMap.put("editCatFlag",editCatFlag);
        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages());
        modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("category",goodsService.getCategoryById(id));
        return "goods";
    }

    @RequestMapping(value ="/employee/edit/category", method = RequestMethod.POST)
    public String editCategory(@ModelAttribute(value = "category")  @Valid Category category, BindingResult bindingResult,
                            ModelMap modelMap) {
        if(bindingResult.hasErrors()) {
            boolean editCatFlag = true;
            modelMap.put("editCatFlag",editCatFlag);
            modelMap.put("category",category);
            modelMap.put("currentPage",1);
            modelMap.put("amountOfPages",getAmountOfPages());
            modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
            modelMap.put("randomGoods",getRandomGoods());
            modelMap.put("listCategory",goodsService.getAllCategories());
            modelMap.put("categoryFilter",false);
            return "goods";
        }


        goodsService.updateCategory(category);

        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages());
        modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("msg", "You've been edited category successfully.");
        return "goods";
    }

    @RequestMapping(value ="/employee/delete/category/{id}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable(value = "id") int id) {
        this.goodsService.deleteCategoryById(id);
        return "redirect:/catalog";
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

    public List<Goods> getRandomGoods(){
        Set<Integer> randomGoodsIdSet = new HashSet<>();
        while (randomGoodsIdSet.size()<amountOfRandomGoodsOnPage) { //todo а если количество товаров будет всегда меньше запрашиваемо числа случайных товаров?
            randomGoodsIdSet.add(goodsService.getRandomGoodsId());
        }
        List<Goods> goodsList = new ArrayList<>();
        for(int id : randomGoodsIdSet){
            goodsList.add(goodsService.getGoodsById(id));
        }
        return goodsList;
    }
}
