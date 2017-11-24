package com.internetshop.controller;

import com.internetshop.Exceptions.PasswordWrongException;
import com.internetshop.config.AppConfig;
import com.internetshop.model.Client;
import com.internetshop.model.Mail;
import com.internetshop.model.Order;
import com.internetshop.model.PasswordField;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.MailService;
import com.internetshop.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    public String sendEmailConfirm(ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        String email = (String) session.getAttribute("nonVerifiedClientEmail");
        if (email == null) {
            return "redirect:/";
        }
        Client client = clientService.getUserByEmail(email);
        mailService.sendEmail(client,
                "To confirm your Email, please follow the link ",
                AppConfig.HOST_URL + "/confirm?id=" + client.getConfirmationId(),
                "Dice Games, Email Verification",
                "email-template.txt");


        modelMap.put("msg", "Please, confirm an email." +
                " Check your mail, we've sent you a verification letter");
        return "message";
    }

    @RequestMapping(value = "/email/send", method = RequestMethod.POST)
    public String resendEmailConfirm(String email,ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        Client client = clientService.getUserByEmail(email);
        if (client.getIsConfirm()==1){
            modelMap.put("error","Account is confirmed");
            return "recover_page";
        }
        session.setAttribute("nonVerifiedClientEmail",email);
        return "redirect:/email/send";
    }


    @RequestMapping(value = "/confirm")
    public String confirmEmail(@RequestParam(value = "id", required = false) String id, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        String email;
        try {
            email = clientService.getEmailByConfirmationId(id);
            clientService.resetConfirmationId(email);
        } catch (NoResultException e) {
            email = null;
        }
        if (email != null) {
            clientService.confirmClientEmail(email);
            modelMap.put("msg", "Congratulations, you have successfully confirmed your email! " +
                    "Now, if you want to see your account, please log in");
            return "message";
        }

        modelMap.put("msg", "Session is unavailable, please go to recover page to resend confirmation letter...");
        return "message";
    }

    @RequestMapping(value = "/send/recover", method = RequestMethod.GET)
    public String recoverMail(ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        return "recover_page";
    }

    @RequestMapping(value = "/send/recover", method = RequestMethod.POST)
    public String recoverMail(String email, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        try {
            clientService.recoverConfirmationIdAndSendEmail(email);
        } catch (NoResultException e) {
            modelMap.put("error", "User with such email not found");
            return "recover_page";
        }
        modelMap.put("error", "To recover the password, follow the instructions in the email that was sent to you...");
        return "message";
    }

    @RequestMapping(value = "/recover/password", method = RequestMethod.GET)
    public String recoverPassword(@RequestParam(value = "id", required = false) String id, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        String email;
        try {
            email = clientService.getEmailByConfirmationId(id);
            clientService.resetConfirmationId(email);
        } catch (NoResultException e) {
            email = null;
        }
        if (email != null) {
            session.setAttribute("emailForRecovery", email);
            modelMap.put("passwordField", new PasswordField());
            return "recover_password";
        }

        modelMap.put("error", "Session is unavailable, please go to recover page to resend mail...");
        return "message";
    }

    @RequestMapping(value = "/recover/password", method = RequestMethod.POST)
    public String recoverPassword(@ModelAttribute(value = "passwordField") @Valid PasswordField passwordField, BindingResult bindingResult, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        if (bindingResult.hasErrors()) {
            modelMap.put("passwordField", new PasswordField());
            modelMap.put("error", "Entered characters are not allowed!");
            return "recover_password";
        }
        String email = (String) session.getAttribute("emailForRecovery");
        Client client = clientService.getUserByEmail(email);
        if (passwordField.getNewPasswordFirst().equals(passwordField.getNewPasswordSecond())) {
            try {
                clientService.changePassword(passwordField, client);
            } catch (PasswordWrongException e) {
                modelMap.put("passwordField", new PasswordField());
                modelMap.put("error", "Password is not valid!");
                return "recover_password";
            }
        } else {
            modelMap.put("passwordField", new PasswordField());
            modelMap.put("error", "Entered New passwords doesn't match!");
            return "recover_password";
        }
        modelMap.put("msg", "Password has been successfully changed");
        return "message";
    }
}
