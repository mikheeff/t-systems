package com.internetshop.controller;

import com.internetshop.Exceptions.EmailExistException;
import com.internetshop.Exceptions.PasswordWrongException;
import com.internetshop.model.Client;
import com.internetshop.model.PasswordField;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private static Logger logger = LoggerFactory.getLogger(ClientController.class.getName());


    @Autowired
    private ClientService clientService;

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpSession session;

    /**
     * Identifies user or create form for new client,
     * handles errors with registration
     *
     * @param error    message if wasn't found typed email
     * @param logout   message when user logged out
     * @param regError message when registration params are invalid
     * @return registration and login page
     */
    @RequestMapping(value = "/identification", method = RequestMethod.GET)
    public String identifyUser(ModelMap modelMap,
                               @RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               @RequestParam(value = "regError", required = false) String regError,
                               HttpServletRequest httpServletRequest) {
        modelMap.put("newClient", new Client());
        modelMap.put("listCategory", goodsService.getAllCategories());
        if (error != null) {
            logger.warn("trying to enter with invalid email or password or not confirmed account");
            modelMap.put("error", "Invalid email or password or not confirmed account!");
        }
        if (regError != null) {
            logger.warn("Invalid params");
            modelMap.put("regError", "Invalid params!");
        }

        if (logout != null) {
            logger.info("user logged out");
            modelMap.put("msg", "You've been logged out successfully.");
        }
        Client client = (Client) session.getAttribute("client");
        if (client == null && httpServletRequest.getUserPrincipal() != null) {
            session.setAttribute("client", clientService.getClientByEmail(httpServletRequest.getUserPrincipal().getName()));
        }
        modelMap.put("client", client);
        return "register";
    }

    /**
     * Gets information for user's profile:
     * information about user and orders history
     * if user has employee role he will see all orders
     * handles errors with editing profile
     *
     * @param httpServletRequest for getting user principal
     * @param errorMatch         message when typed passwords didn't match
     * @param msg                success when password has been changed
     * @param error              message when user added forbidden symbols
     * @param errorInvalidPass   message when user trying to change password
     *                           but entered invalid password
     * @return profile page
     */

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String editClient(ModelMap modelMap, HttpServletRequest httpServletRequest,
                             @RequestParam(value = "errorMatch", required = false) String errorMatch,
                             @RequestParam(value = "msg", required = false) String msg,
                             @RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "errorInvalidPass", required = false) String errorInvalidPass,
                             @RequestParam(value = "clientError", required = false) String clientError,
                             @RequestParam(value = "msgClient", required = false) String msgClient,
                             @RequestParam(value = "msg_img", required = false) String msg_img){
        session.setAttribute("client", clientService.getClientByEmail(httpServletRequest.getUserPrincipal().getName()));
        modelMap.put("client", session.getAttribute("client"));
        if (modelMap.get("client") == null) {
            logger.info("client is null");
            return "redirect:/clients/identification";
        }
        modelMap.put("listCategory", goodsService.getAllCategories());
        Client client = (Client) session.getAttribute("client");
        modelMap.put("clientOrdersList", orderService.getAllOrdersByClientId(client.getId()));
        if (errorMatch != null) {
            modelMap.put("errorMatch", "Entered New passwords doesn't match!");
        }
        if (error != null) {
            modelMap.put("error", "Entered characters are not allowed!");
        }
        if (errorInvalidPass != null) {
            modelMap.put("errorInvalidPass", "Password is not valid!");
        }
        if (clientError != null) {
            modelMap.put("clientError", "Fields contain invalid characters");
        }
        if (msg != null) {
            modelMap.put("msg", "You have been changed password successfully!");
        }
        if (msgClient != null) {
            modelMap.put("msgClient", "Information has been changed successfully!");
        }
        if (msg_img != null){
            modelMap.put("msg_img","Image is too large. It must be less then 2MB");
        }
        modelMap.put("passwordField", new PasswordField());
        return "profile";
    }

    /**
     * Handle errors with registration
     * Send user information for registration to service
     *
     * @return success page
     */

    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public String addClient(@ModelAttribute(value = "client") @Valid Client client, BindingResult bindingResult, ModelMap modelMap) {
        logger.info("addClient");
        if (bindingResult.hasErrors()) {
            logger.warn("Entered data not valid");
            return "redirect:/clients/identification?regError";
        }
        try {
            this.clientService.addClient(client);
        } catch (EmailExistException e) {
            logger.error("Error email: " + e.getEnteredEmail(), e);
            modelMap.put("newClient", client);
            modelMap.put("regError", "Email already exists!");
            return "register";
        }
        session.setAttribute("nonVerifiedClientEmail", client.getEmail());
        return "redirect:/email/send";
    }

    /**
     * Gets clients information for editing
     *
     * @return profile page
     */
    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    public String editClient(@ModelAttribute(value = "client") @Valid Client client, BindingResult bindingResult) {
        logger.info("editClient");

        if (bindingResult.hasErrors()) {
            logger.warn("Entered information not valid");
            return "redirect:/clients/profile?clientError";
        }
        this.clientService.updateClient(client);
        session.setAttribute("client", clientService.getClientByEmail(client.getEmail()));
        return "redirect:/clients/profile?msgClient";
    }

    /**
     * Gets user's passwords new and old
     * checks new password
     * handle errors with changing password
     * Send new password to service
     *
     * @return result message about changing password
     */
    @RequestMapping(value = "/profile/edit/password", method = RequestMethod.POST)
    public String changePassword(@ModelAttribute(value = "passwordField") @Valid PasswordField passwordField, BindingResult bindingResult) {
        logger.info("changePassword");
        if (bindingResult.hasErrors()) {
            logger.warn("Password validation failed");
            return "redirect:/clients/profile?error";
        }
        Client client = (Client) session.getAttribute("client");
        if (passwordField.getNewPasswordFirst().equals(passwordField.getNewPasswordSecond())) {
            try {
                clientService.changePassword(passwordField, client);
            } catch (PasswordWrongException e) {
                logger.error("Error password", e);
                return "redirect:/clients/profile?errorInvalidPass";
            }
        } else {
            logger.warn("First and second password fields does't match");
            return "redirect:/clients/profile?errorMatch";
        }
        return "redirect:/clients/profile?msg";
    }
    @RequestMapping(value = "/profile/edit/avatar", method = RequestMethod.POST)
    public String uploadAvatar(HttpServletRequest httpServletRequest, @RequestParam CommonsMultipartFile[] fileUpload){
        Client client = (Client) session.getAttribute("client");
        if (fileUpload != null && fileUpload.length > 0) {
            CommonsMultipartFile aFile = fileUpload[0];
                if(aFile.getSize()>2000000){
                    logger.warn("upload image error, image is too large {}",client);
                    return "redirect:/clients/profile?msg_img";
                }
                client.setImg(aFile.getBytes());
                clientService.uploadAvatar(client);
        }
        return "redirect:/clients/profile";
    }
    @ResponseBody
    @RequestMapping(value = "/profile/edit/avatar/delete", method = RequestMethod.GET)
    public void deleteAvatar(){
        Client client = (Client) session.getAttribute("client");
        clientService.deleteAvatar(client);
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
        if (bindingResult.hasFieldErrors("newPasswordFirst") && bindingResult.hasFieldErrors("newPasswordSecond")) {
            modelMap.put("passwordField", new PasswordField());
            modelMap.put("error", "Entered characters are not allowed!");
            return "recover_password";
        }
        String email = (String) session.getAttribute("emailForRecovery");
        Client client = clientService.getClientByEmail(email);
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
