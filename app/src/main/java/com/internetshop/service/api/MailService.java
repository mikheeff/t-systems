package com.internetshop.service.api;


import com.internetshop.model.Mail;

public interface MailService {
    void sendEmail(Mail mail, String template);
    void sendSMS(String msg,String number);
}
