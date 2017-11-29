package com.internetshop.jms;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import javax.jms.*;

import com.tsystems.Event;
import com.tsystems.SmallGoods;
import com.tsystems.TestModel;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsProducer extends Thread implements AutoCloseable {
    private static String DEF_QUEUE = "test.in";

    private final ActiveMQConnectionFactory _connectionFactory; //
    private Connection _connection = null;
    private Session _session = null;
    private Queue<Event> _messagesQueue;
    private boolean _active = true;
    private static Logger logger = LoggerFactory.getLogger("JmsProducer");

    /**
     * The constructor is used in the case when the broker does not require authorization.
     */
    public JmsProducer(String url)
    {
        this(url, null, null);
    }

    /**
     * The constructor is used in the case when the broker requires authorization.
     */
    public JmsProducer(String url, String user, String password)
    {
        if (user != null && !user.isEmpty() && password != null)
            _connectionFactory = new ActiveMQConnectionFactory(url, user, password);
        else
            _connectionFactory = new ActiveMQConnectionFactory(url);

        _messagesQueue = new PriorityBlockingQueue<>();
    }

    /**
     * producer init
     */
    private MessageProducer init() throws JMSException
    {
        logger.info("Producer initialisation");
        _connection = _connectionFactory.createConnection();
        _connection.start();
        _session = _connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = _session.createQueue(DEF_QUEUE);
        return _session.createProducer(dest);
    }

    /**
     * The send method just adds the message to the internal queue.
     * The send itself to the broker does not happen here, it is done in the run method.
     */
    public void send(Event line)
    {
        _messagesQueue.add(line);
    }

    /**
     * The method of the cycle of sending messages to the broker.
     */
    @Override
    public void run()
    {
        try
        {
            MessageProducer producer = init();
            while (_active)
            {
                try
                {
                    Event text = null;
                    while (_active && (text = _messagesQueue.poll()) != null)
                    {
                        ObjectMessage msg = _session.createObjectMessage();
                        msg.setObject(text);
                        msg.setObjectProperty("Created", (new Date()).toString());
                        producer.send(msg);
                        logger.info("message was sent");
                    }

                }
                catch (JMSException e)
                {
                    e.printStackTrace();
                    _session.close();
                    _connection.close();
                    producer = init();
                    logger.warn("Trying to reconnect");
                }
            }
        }
        catch (Exception ex)
        {
            logger.error("jms exception", ex);
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
                logger.error("jms exception", e);
            }
        }
    }

}
