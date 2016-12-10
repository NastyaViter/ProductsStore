package com.netcracker.crm.controller;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.netcracker.crm.dao.IEntityDao;
import com.netcracker.crm.dao.exception.DaoException;
import com.netcracker.crm.dao.impl.EntityDaoImpl;
import com.netcracker.crm.dao.validation.EntityDaoValidation;
import com.netcracker.crm.entity.Atribute;
import com.netcracker.crm.entity.Entity;
import com.netcracker.crm.entity.User;
import com.netcracker.crm.entity.Value;
import com.netcracker.crm.entity.enums.EntityType;
import com.netcracker.crm.entity.enums.PhoneAtribute;
import com.netcracker.crm.entity.enums.TabletAtribute;
import com.netcracker.crm.entity.serviceEntity.Cart;
import com.netcracker.crm.entity.serviceEntity.CartItem;
import com.netcracker.crm.entity.serviceEntity.Order;
import com.netcracker.crm.entity.serviceEntity.Product;
import com.netcracker.crm.services.IOrderService;
import com.netcracker.crm.services.IProductService;
import com.netcracker.crm.services.IUserService;
import com.netcracker.crm.services.impl.OrderServiceImpl;
import com.netcracker.crm.services.parser.*;
import javafx.util.Pair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by egor on 03.11.2016.
 */
public class TestDao {
    public static void main(String[] args) throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"SpringModule.xml"});

//        CatalogParser catalogParser = (CatalogParser) context.getBean("catalogParser");
//
//        String schemaLocation = "src/main/resources/xml-parser/catalogSchema.xsd";
//
//        String urlFrom = "src/main/resources/xml-parser/catalogToAdd.xml";
//        String urlTo = "src/main/resources/xml-parser/generatedCatalog.xml";
//        int productCount = 0;
//
//        catalogParser.exportCatalog(urlTo);
//
//        try {
//
//            productCount = catalogParser.importCatalog(urlFrom, schemaLocation);
//
//        }
//        catch (WrongXMLSchemaException e)
//        {
//            System.out.println("Wrong XML Schema Exception");
//        }
//
//        System.out.println(productCount + " new products was added");

//        List<Value> values =new ArrayList<>();
//        values.add(new Value(0,"16Gb",0,14));
//        values.add(new Value(0,"5.1-inch (diagonal)",0,15));
//        Entity entity=new Entity("Samsung Galaxy S3",true,8,-2,values);


//      REGULAR EXPRESSION
//        IProductService productService = (IProductService) context.getBean("productServiceTest");
//        Product prod = productService.getById(53);
//        System.out.println(prod.getRegExp(PhoneAtribute.Price.getAtributeId()));


// -----USER SERVICES-----
//      IUserService userService = (UserServiceImpl) context.getBean("userService");
//       List<User> userList = userService.getAll();
//       UserListWorker.sortById(userList);
//      userList = UserListWorker.getFromTo(userList, 0, 52313);
//        for (User user : userList             ) {
//            System.out.println(user.toString());
//        }
//        userService.isLoginFree("admin");
//        userService.isLoginFree("adm21in");
//
//        userService.isEmailFree("gav@panin.ru");
//
        IProductService productService = (IProductService) context.getBean("productServiceTest");
//        Product product = productService.getById(55);
//        product.setValueInList(23, "600");
//        System.out.println(product);
//        System.out.println("PRODUCT\n" + product);
//        for (Product item : productService.getList(9,"","","",1,2)) {
//            System.out.println(item.toString());
//        }
//        productService.getByUserAndType(-2, null);
//       Product product = productService.getById(53);
//        int a = product.getOrderId();
//        productService.delete(54);
//        productService.update(40, "iphone6s", 1, -2, null);
//        List<Value> values = new ArrayList<>();
//        values.add(new Value(41, "16Gb", 40, 14));
//        productService.update(40, "iphone6s", 1, -2, values);


     //     ADD ORDER
//        IOrderService orderService = (IOrderService) context.getBean("orderServiceTest");
//        List<Order> list = orderService.getList();
//        System.out.println(list.size());
//        for(Order o :list){
//            System.out.println(o);
//        }
//        orderService.delete(257);
//        Cart cart = new Cart(-1);
//        cart.addCartItem(new CartItem(productService.getById(55), 1));
//        cart.addCartItem(new CartItem(productService.getById(58), 2));
//        Order order = orderService.makeOrderByCart(cart);
//        int id = orderService.add(order);
//        orderService.getById(257);
//        List<Order> list = orderService.getListForUser(-1);
//        if (list != null) {
//            for (Order o : list) {
//                System.out.println(o);
//            }
//        }


//        CatalogParser catalogParser = (CatalogParser) context.getBean("catalogParser");
//        catalogParser.importCatalog("default");
//        System.out.println("check db");
//        catalogParser.exportCatalog("default");


        //Cart example
//        ICartService cartService= (CartServiceImpl) context.getBean("cartService");
//        Cart cart=new Cart(-1);
//        cartService.add(cart);
//        cartService.addProduct(49);
//        cartService.addProduct(50);
//        cartService.addProduct(51);
//        cartService.changeQuantity(49,2);
//        cartService.delete(51);
//        cartService.countTotal();
//        System.out.println(cartService.getCart().toString());

//        List<Product> productList = productService.getList(
//                9,
//                "25,25",
//                "100,700",
//               ">=,<=",
//                "25,25",
//                1,
//                2, "U");
//        for(Product p:productList){
//            System.out.println(p);
//        }
    }
}