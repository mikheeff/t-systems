package com.internetshop.controller;

import com.internetshop.entities.ClientEntity;
import com.internetshop.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ResponseBody
    public String/*List<ClientEntity>*/ getAllUsers(ModelMap modelMap) {
//        return clientService.getAllClients();
        modelMap.put("listClient", clientService.getAllClients());
        return "client_page";
    }
}
