package com.internetshop.controller;

import com.internetshop.config.AppConfig;
import com.internetshop.model.Client;
import com.internetshop.model.Mail;
import com.internetshop.model.Order;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.MailService;
import com.internetshop.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
public class EmailController {

    private final MailService mailService;
    private final ClientService clientService;
    public final HttpSession session;
    GoodsService goodsService;
    OrderService orderService;

    @Autowired
    public EmailController(MailService mailService, ClientService clientService, HttpSession session, GoodsService goodsService, OrderService orderService) {
        this.mailService = mailService;
        this.clientService = clientService;
        this.session = session;
        this.goodsService = goodsService;
        this.orderService = orderService;
    }

    @RequestMapping(value = "/email/send", method = RequestMethod.GET)
    public String sendEmailConfirm(ModelMap modelMap){
        String email = (String)session.getAttribute("nonVerifiedClientEmail");
        if (email==null) {
            return "redirect:/";
        }
        Client client = clientService.getUserByEmail(email);
        Mail mail = new Mail();
        mail.setMailFrom(AppConfig.MAIL_FROM);
        mail.setMailTo(client.getEmail());
        mail.setMailSubject("Dice Games, Email Verification");

        Map< String, Object > model = new HashMap<>();
        model.put("firstName", client.getName());
        model.put("link", AppConfig.HOST_URL+"/confirm?id="+client.getConfirmationId());
        model.put("location", AppConfig.MAIL_LOCATION);
        model.put("signature", AppConfig.MAIL_SIGNATURE);
        mail.setModel(model);

        mailService.sendEmail(mail,"email-template.txt");


        modelMap.put("confirmation",true);
        return "registr_success";
    }

    @RequestMapping(value = "/confirm")
    public String confirmEmail( @RequestParam(value = "id", required = false) String id, ModelMap modelMap){

        String email;
        try {
             email = clientService.getEmailByConfirmationId(id);
        } catch (NoResultException e){
            email = null;
        }
        if (email!=null){
            clientService.confirmClientEmail(email);
            modelMap.put("regSuccess",true);
            return "registr_success";
        }

        modelMap.put("isSessionUnAvailable",true);
        return "registr_success";
    }

    @RequestMapping(value = "/send/recover/{email}", method = RequestMethod.GET)
    public String recoverEmail( @PathVariable(value = "email") String email, ModelMap modelMap){
        clientService.recoverConfirmationIdAndSendEmail(email);
        return "registr_success";
    }

    @RequestMapping(value = "/send/order/{id}", method = RequestMethod.GET)
    public String sendOrder(ModelMap modelMap,
                            @PathVariable(value = "id") int id){
        Order order = orderService.getOrderById(id);
        Mail mail = new Mail();
        mail.setMailFrom(AppConfig.MAIL_FROM);
        Client client = order.getClient();
        mail.setMailTo(client.getEmail());
        mail.setMailSubject("Dice Games, new order");

        Map< String, Object > model = new HashMap<>();
        model.put("firstName", client.getName());
        model.put("location", AppConfig.MAIL_LOCATION);
        model.put("signature", AppConfig.MAIL_SIGNATURE);
        model.put("msg", "your order with ID:"+id+" is accepted for processing");
        model.put("link", AppConfig.HOST_URL+"/order/details/"+id);
        mail.setModel(model);

        mailService.sendEmail(mail,"order.txt");
        if (client.getPhone()!=null) {
            mailService.sendSMS("Your order ID: " + id + " DiceGames.com", client.getPhone());
        }
        modelMap.put("randomGoods", goodsService.getRandomGoods(GoodsController.amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        String searchStr = "";
        modelMap.put("search", searchStr);
        modelMap.put("bestSellersList", goodsService.getBestSellers(GoodsController.amountOfBestSellers));
        modelMap.put("orderId",id);
        return "order_success";
    }
}
