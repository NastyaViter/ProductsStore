package com.netcracker.crm.controller;

import com.netcracker.crm.entity.User;

import com.netcracker.crm.entity.controllerEntity.form.OrderForm;
import com.netcracker.crm.entity.controllerEntity.validator.OrderValidator;
import com.netcracker.crm.entity.serviceEntity.Order;
import com.netcracker.crm.services.ICartService;
import com.netcracker.crm.services.IOrderService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by Ксения on 05.12.2016.
 */
@Controller
public class OrderController {

    private static final String ORDER = "order";
    private static final String CHECK = "checkOrder";
    private ICartService cartService;
    private IOrderService orderService;
    private OrderValidator orderValidator;
    private User user;

    @Required
    public void setUser(User user) {
        this.user = user;
    }

    @Required
    public void setCartService(ICartService cartService) {
        this.cartService = cartService;
    }

    @Required
    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Required
    public void setOrderValidator(OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public String createOrder(ModelMap model) {

        model.addAttribute("cartInfo",cartService.getCart().toString());
        OrderForm order = new OrderForm();
        if(user.getRoleId().equals(User.ROLE_ANON)) {
            model.addAttribute("order", order);
        }
        else{
            order.setFieldsFromUser(user);
            model.addAttribute("order",order);
        }
        return ORDER;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public String saveOrder(OrderForm orderForm, BindingResult result, ModelMap model) {
        orderValidator.validate(orderForm, result);
        if (result.hasErrors()) {
            return ORDER;
        }

        Order order = orderService.makeOrderByCart(cartService.getCart());
        order.setFieldsFromForm(orderForm);
        orderService.add(order);
        model.addAttribute("order", order);
        model.addAttribute("orderInfo",order.toString());
        return CHECK;
    }
}
