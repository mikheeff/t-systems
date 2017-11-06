package com.internetshop.controller;

import com.internetshop.config.AppConfig;
import com.internetshop.model.Client;
import com.internetshop.model.Mail;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @Autowired
    MailService mailService;
    @Autowired
    ClientService clientService;
    @Autowired
    private HttpSession session;
    @Autowired
    GoodsController goodsController;
    @Autowired
    GoodsService goodsService;

    @RequestMapping(value = "/email/send", method = RequestMethod.GET)
    public String sendEmail(ModelMap modelMap){
        String email = (String)session.getAttribute("nonVerifiedClientEmail");
        if (email==null) {
            return "redirect:/";
        }
        Client client = clientService.getUserByEmail(email);
        Mail mail = new Mail();
        mail.setMailFrom("dice.gamesstore@gmail.com");
        mail.setMailTo(client.getEmail());
        mail.setMailSubject("Dice Games, Email Verification");

        Map< String, Object > model = new HashMap<>();
        model.put("firstName", client.getName());
        model.put("link", AppConfig.HOST_URL+"/confirm?id="+client.getConfirmationId());
        model.put("location", "St.Petersburg, Russia");
        model.put("signature", "Dice Games shop");
        mail.setModel(model);

        mailService.sendEmail(mail);


        modelMap.put("confirmation",true);
        return "registr_success";
    }

    @RequestMapping(value = "/confirm")
    public String confirmEmail( @RequestParam(value = "id", required = false) String id, ModelMap modelMap){

//        String email = (String)session.getAttribute("nonVerifiedClientEmail");
        String email;
        try {
             email = clientService.getEmailByConfirmationId(id);
        } catch (NoResultException e){
            email = null;
        }
//        if (email!=null && clientService.isIdContains(email,id)){
        if (email!=null){
            clientService.confirmClientEmail(email);
            modelMap.put("regSuccess",true);
            return "registr_success";
        }

        modelMap.put("isSessionUnAvailable",true);
        return "registr_success";
    }
}
