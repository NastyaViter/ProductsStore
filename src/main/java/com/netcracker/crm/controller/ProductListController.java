package com.netcracker.crm.controller;


import com.netcracker.crm.entity.serviceEntity.Product;
import com.netcracker.crm.services.ICartService;
import com.netcracker.crm.services.IProductService;
import com.netcracker.crm.services.ISearchService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.netcracker.crm.entity.serviceEntity.SearchAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 22.11.2016.
 */

@Controller
public class ProductListController {

    ApplicationContext context =
            new ClassPathXmlApplicationContext(new String[]{"SpringModule.xml"});
    private static final String PRODUCTS = "products";
    private static final String ITEM = "item";

    private IProductService productService;
    private ISearchService searchService;

    @Required
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }

    @Required
    public void setSearchService(ISearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/products/{type}", method = RequestMethod.GET)
    public String products(@PathVariable String type, ModelMap model) {
        int typeid=9;
        switch (type){
            case "telephone": typeid=9; break;
            case "tablet": typeid=10; break;
        }
        List<Product> productList = productService.getList(typeid,"","","",1,5);
        if(productList == null)
            productList = new ArrayList<>();
        model.addAttribute("productList", productList);
        model.addAttribute("currType", type);
        model.addAttribute("searchAttr", new SearchAttributes());
        return PRODUCTS;
    }

    @RequestMapping(value = "/products/search", method = RequestMethod.GET)
    public String searchProducts(SearchAttributes searchAttr, ModelMap model) {

        searchService.validate(searchAttr);
        searchService.parseSearchAttributes(searchAttr);
        List<Product> productList =  productService.getList(
                searchAttr.getTypeId(),
                searchAttr.getAttribute(),
                searchAttr.getValues(),
                searchAttr.getOperators(),
                1,
                5);
        if(productList == null)
            productList = new ArrayList<>();
        if(productList.isEmpty())
            model.addAttribute("emptyList","sorry, nothing to show");
        model.addAttribute("productList", productList);
        model.addAttribute("currType",searchAttr.getType());
        model.addAttribute("searchAttr", searchAttr);
        return PRODUCTS;
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public String item(@PathVariable String id, ModelMap model) {
        model.addAttribute("item",productService.getById(Integer.parseInt(id)));
        return ITEM;
    }

}
