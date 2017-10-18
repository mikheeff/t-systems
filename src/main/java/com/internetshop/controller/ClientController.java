package com.internetshop.controller;

import com.internetshop.model.Client;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.impl.ClientServiceImpl;
import com.internetshop.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getAllUsers(ModelMap modelMap) {
        modelMap.put("listClient", clientService.getAllClients());
        return "client_page";
    }
    @RequestMapping(value ="/identification",method = RequestMethod.GET)
    public String identifyUser(ModelMap modelMap,
                               @RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               @RequestParam(value = "regError", required = false) String regError) {
        modelMap.put("newClient", new Client());
        if (error != null) {
            modelMap.put("error", "Invalid email or password!");
        }
        if (regError != null) {
            modelMap.put("regError", "Invalid params!");
//            modelMap.put(session.getAttribute()) //todo хорошо бы сделать, чтобы при невалидных параметрах ввёденные данные не терялись
        }

        if (logout != null) {
            modelMap.put("msg", "You've been logged out successfully.");
        }

        return "register";
    }

//    @RequestMapping(value = "/profile", method = RequestMethod.POST)
//    public String addClient(@ModelAttribute (value = "client")@Valid Client client, BindingResult bindingResult,ModelMap modelMap) {
//        if(bindingResult.hasErrors()) {
//            modelMap.put("newClient",client);
//            return "register";
//        }
//        this.clientService.addClient(client);
//        session.setAttribute("user",clientService.getUserByEmail(client.getEmail()));
//        return "redirect:/clients/profile";
//    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String editClient(ModelMap modelMap, HttpServletRequest httpServletRequest) {
        session.setAttribute("client",clientService.getUserByEmail( httpServletRequest.getUserPrincipal().getName()));
        modelMap.put("client",session.getAttribute("client"));
        if(modelMap.get("client") == null) {
            return "redirect:/clients/identification";
        }
        return "profile";
    }
    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public String addClient(@ModelAttribute (value = "client")@Valid Client client, BindingResult bindingResult,ModelMap modelMap) {
        if(bindingResult.hasErrors()) {
            return "redirect:/clients/identification?error1";
        }
        this.clientService.addClient(client);
        return "registr_success";
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    public String editClient(@ModelAttribute (value = "client") @Valid Client client, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "redirect:/clients/profile";
        }
        this.clientService.updateUser(client);
        session.setAttribute("client",clientService.getUserByEmail(client.getEmail()));
        return "redirect:/clients/profile";
    }

}
