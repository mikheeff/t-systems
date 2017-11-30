package com.internetshop.controller;

import com.internetshop.Exceptions.PasswordWrongException;
import com.internetshop.config.AppConfig;
import com.internetshop.model.Client;
import com.internetshop.model.PasswordField;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.MailService;
import com.internetshop.service.api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("")
public class EmailController {

    private final MailService mailService;
    private final ClientService clientService;
    public final HttpSession session;
    GoodsService goodsService;
    OrderService orderService;

    private static Logger logger = LoggerFactory.getLogger(EmailController.class.getName());


    @Autowired
    public EmailController(MailService mailService, ClientService clientService, HttpSession session, GoodsService goodsService, OrderService orderService) {
        this.mailService = mailService;
        this.clientService = clientService;
        this.session = session;
        this.goodsService = goodsService;
        this.orderService = orderService;
    }

    /**
     * sends a confirmation mail
     */
    @RequestMapping(value = "/email/send", method = RequestMethod.GET)
    public String sendEmailConfirm(ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        String email = (String) session.getAttribute("nonVerifiedClientEmail");
        if (email == null) {
            return "redirect:/";
        }
        Client client = clientService.getClientByEmail(email);
        mailService.sendEmail(client,
                "To confirm your Email, please follow the link ",
                AppConfig.HOST_URL + "/confirm?id=" + client.getConfirmationId(),
                "Dice Games, Email Verification",
                "email-template.txt");


        modelMap.put("msg", "Please, confirm an email." +
                " Check your mail, we've sent you a verification letter");
        return "message";
    }

    /**
     * if clients account is not confirmed
     * send a new confirmation mail
     */
    @RequestMapping(value = "/email/send", method = RequestMethod.POST)
    public String resendEmailConfirm(String email, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        Client client;
        try {
            client = clientService.getClientByEmail(email);
        } catch (NoResultException e){
            logger.warn("resendEmailConfirm error : User with such email not found {}",email);
            modelMap.put("error", "User with such email not found");
            return "recover_page";
        }
        if (client.getIsConfirm() == 1) {
            logger.warn("resendEmailConfirm error: Account is {} confirmed",email);
            modelMap.put("error", "Account is confirmed");
            return "recover_page";
        }
        session.setAttribute("nonVerifiedClientEmail", email);
        return "redirect:/email/send";
    }


    /**
     * confirms client email
     */
    @RequestMapping(value = "/confirm")
    public String confirmEmail(@RequestParam(value = "id", required = false) String id, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        String email;
        try {
            email = clientService.getEmailByConfirmationId(id);
            clientService.resetConfirmationId(email);
        } catch (NoResultException e) {
            logger.error("no clients with such id" + id,e);
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

    /**
     * gets recover page
     */
    @RequestMapping(value = "/send/recover", method = RequestMethod.GET)
    public String recoverMail(ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        return "recover_page";
    }

    /**
     * gets email and call service to send recover mail
     */
    @RequestMapping(value = "/send/recover", method = RequestMethod.POST)
    public String recoverMail(String email, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        try {
            clientService.recoverConfirmationIdAndSendEmail(email);
        } catch (NoResultException e) {
            modelMap.put("error", "User with such email not found");
            return "recover_page";
        }
        modelMap.put("msg", "To recover the password, follow the instructions in the email that was sent to you...");
        return "message";
    }


}
