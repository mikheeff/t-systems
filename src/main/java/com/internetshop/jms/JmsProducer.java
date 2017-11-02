package com.internetshop.jms;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProducer extends Thread implements AutoCloseable {
    private static String DEF_QUEUE = "test.in";

    private final ActiveMQConnectionFactory _connectionFactory; //
    private Connection _connection = null;
    private Session _session = null;
    private Queue<String> _messagesQueue;
    private boolean _active = true;

    /**
     * Конструктор используется в случае, когда брокер не требует авторизации.
     */
    public JmsProducer(String url)
    {
        this(url, null, null);
    }

    /**
     * Конструктор используется в случае, когда брокер требует авторизацию.
     */
    public JmsProducer(String url, String user, String password)
    {
        if (user != null && !user.isEmpty() && password != null)
            _connectionFactory = new ActiveMQConnectionFactory(url, user, password);
        else
            _connectionFactory = new ActiveMQConnectionFactory(url);

        _messagesQueue = new PriorityBlockingQueue<String>();
    }

    /**
     * Инициализация producer-а.
     */
    private MessageProducer init() throws JMSException
    {
        _connection = _connectionFactory.createConnection();
        _connection.start();
        _session = _connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = _session.createQueue(DEF_QUEUE);
        return _session.createProducer(dest);
    }

    /**
     * Метод отправки только лишь добавляет сообщение во внутреннюю очередь.
     * Самой отправки в брокер здесь не происходит, она делается в методе run.
     */
    public void send(String line)
    {
        _messagesQueue.add(line);
    }

    /**
     * Метод цикла отправки сообщений в брокер.
     */
    @Override
    public void run()
    {
        try
        {
            System.out.println("Init producer...");
            MessageProducer producer = init();
            System.out.println("Producer successfully initialized");
            while (_active)
            {
                try
                {
                    String text = null;
                    while (_active && (text = _messagesQueue.poll()) != null)
                    {
                        Message msg = _session.createTextMessage(text);
                        msg.setObjectProperty("Created", (new Date()).toString());
                        producer.send(msg);
                        System.out.println("Message " + msg.getJMSMessageID() + " was sent");
                    }

                }
                catch (JMSException e)
                {
                    e.printStackTrace();
                    _session.close();
                    _connection.close();
                    producer = init(); // trying to reconnect
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void close()
    {
        _active = false;
        if (_connection != null)
        {
            try
            {
                _connection.close();
            }
            catch (JMSException e)
            {
                e.printStackTrace();
            }
        }
    }

}
