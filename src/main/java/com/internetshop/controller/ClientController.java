package com.internetshop.controller;

import com.internetshop.model.Client;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String identifyUser(ModelMap modelMap) {
        modelMap.put("newClient", new Client());
        return "register";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String addClient(@ModelAttribute (value = "client")@Valid Client client, BindingResult bindingResult,ModelMap modelMap) {
        if(bindingResult.hasErrors()) {
            modelMap.put("newClient",client);
            return "register";
        }
        this.clientService.addClient(client);
        session.setAttribute("user",clientService.getUserByEmail(client.getEmail()));
        return "redirect:/clients/profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String editClient(ModelMap modelMap) {
        modelMap.put("client",session.getAttribute("user"));
        return "profile";
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    public String editClient(@ModelAttribute (value = "client") /*@Valid*/ Client client, ModelMap modelMap,BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            return "redirect:/clients/profile";
//        }
        this.clientService.updateUser(client);
        session.setAttribute("user",clientService.getUserByEmail(client.getEmail()));
        return "redirect:/clients/profile";
    }


}
