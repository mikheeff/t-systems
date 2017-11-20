package com.internetshop.service.api;


import com.internetshop.model.Client;
import com.internetshop.model.Mail;

public interface MailService {
    void sendEmail(Client client,String mailMsg,String link, String subject, String template);
    void sendEmail(Mail mail, String template);
    void sendSMS(String msg,String number);
}
