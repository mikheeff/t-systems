package com.internetshop.controller;

import com.internetshop.model.Client;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "clients/list", method = RequestMethod.GET)
    public String getAllUsers(ModelMap modelMap) {
        modelMap.put("listClient", clientService.getAllClients());
        return "client_page";
    }
    @RequestMapping(value ="identification",method = RequestMethod.GET)
    public String identifyUser(ModelMap modelMap) {
        modelMap.put("newClient", new Client());
        return "register";
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public String addGoods(@ModelAttribute (value = "newClient") Client client, ModelMap modelMap) {
        this.clientService.addClient(client);
        return "profile";
    }

}
