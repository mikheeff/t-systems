package com.internetshop.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
//import org.apache.commons.dbcp.BasicDataSource;
//import org.hibernate.SessionFactory;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
//import org.springframework.orm.hibernate4.HibernateTransactionManager;
//import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;


@EnableWebMvc
@Configuration
@ComponentScan({ "com.internetshop.*" })
@Import({ SecurityConfig.class })

public class AppConfig{

    public final static String HOST_URL = "http://93.100.84.138";
    public static final String ACTIVE_MQ_URL = "tcp://localhost:61616";
    public static final String MAIL_FROM = "dice.gamesstore@gmail.com";
    public static final String MAIL_LOCATION = "St.Petersburg, Russia";
    public static final String MAIL_SIGNATURE = "Dice Games shop";
    public static final String ACCOUNT_SID = "ACe9fc023d790d7d7ba114547654c43cfc";
    public static final String AUTH_TOKEN = "c267734dc9f8552f0ac2d5c326181c1d";
    public static final String TWILIO_NUMBER = "+18782050971";
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver
                = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("dice.gamesstore@gmail.com");
        mailSender.setPassword("dicegamesstore");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean fmConfigFactoryBean = new FreeMarkerConfigurationFactoryBean();
        fmConfigFactoryBean.setTemplateLoaderPath("/WEB-INF/email-templates/");
        return fmConfigFactoryBean;
    }


}