package com.internetshop.service.impl;

import com.internetshop.config.AppConfig;
import com.internetshop.model.Client;
import com.internetshop.model.Mail;
import com.internetshop.service.api.MailService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mailService")
public class MailServiceImpl implements MailService {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    Configuration fmConfiguration;

    @Override
    public void sendEmail(Client client, String mailMsg, String link, String subject, String template) {
        Mail mail = new Mail();
        mail.setMailFrom(AppConfig.MAIL_FROM);
        mail.setMailTo(client.getEmail());
        mail.setMailSubject(subject);

        Map<String, Object> model = new HashMap<>();
        model.put("firstName", client.getName());
        model.put("location", AppConfig.MAIL_LOCATION);
        model.put("signature", AppConfig.MAIL_SIGNATURE);
        model.put("mailMsg", mailMsg);
        model.put("link", link);
        mail.setModel(model);

        sendEmail(mail, template);
    }

    public void sendEmail(Mail mail, String template) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(mail.getMailFrom());
            mimeMessageHelper.setTo(mail.getMailTo());
            mail.setMailContent(getContentFromTemplate(mail.getModel(),template));
            mimeMessageHelper.setText(mail.getMailContent(), true);

            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplate(Map<String, Object> model,String template) {
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(fmConfiguration.getTemplate(template), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void sendSMS(String msg,String number) {
        try {
            TwilioRestClient client = new TwilioRestClient(AppConfig.ACCOUNT_SID, AppConfig.AUTH_TOKEN);

            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", msg));
            params.add(new BasicNameValuePair("To", number)); //Add real number here
            params.add(new BasicNameValuePair("From", AppConfig.TWILIO_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
        }
        catch (TwilioRestException e) {
            System.out.println(e.getErrorMessage());
        }
    }
}
