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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class.getName());


    /**
     * builds a mail
     * @param client who will gets email
     * @param mailMsg message in main
     * @param link special link for client
     * @param subject main subject of mail
     * @param template template of mail, which contains html code
     */
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

    /**
     * finishes building message
     * and call mail sender to send mail
     */
    public void sendEmail(Mail mail, String template) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            logger.info("send email {}",mail);
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(mail.getMailFrom());
            mimeMessageHelper.setTo(mail.getMailTo());
            mail.setMailContent(getContentFromTemplate(mail.getModel(), template));
            mimeMessageHelper.setText(mail.getMailContent(), true);

            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            logger.error("send email error" + mail.getMailTo(), e);
        }
    }

    /**
     * gets content from template
     */
    public String getContentFromTemplate(Map<String, Object> model, String template) {
        logger.info("getting content from template");
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(fmConfiguration.getTemplate(template), model));
        } catch (Exception e) {
            logger.error("getContentFromTemplate error",e);
        }
        return content.toString();
    }

    /**
     * send sms to client mobile phone
     * @param msg message
     * @param number of client
     */
    public void sendSMS(String msg, String number) {
        try {
            logger.info("sending sms to "+number);
            TwilioRestClient client = new TwilioRestClient(AppConfig.ACCOUNT_SID, AppConfig.AUTH_TOKEN);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", msg));
            params.add(new BasicNameValuePair("To", number)); //Adding real number here
            params.add(new BasicNameValuePair("From", AppConfig.TWILIO_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
        } catch (TwilioRestException e) {
            logger.error("send SMS error" + e.getErrorMessage(), e);
        }
    }
}
