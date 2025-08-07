package com.example.thub_mq.config;

import com.ibm.mq.jakarta.jms.MQConnection;
import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import com.ibm.msg.client.jakarta.wmq.common.CommonConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;

@Configuration
public class MQConfig {

//    @Bean
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
//        factory.setAutoStartup(true);
//        return factory;
//    }

    @Bean
    public MQConnectionFactory mqConnectionFactory() throws Exception {
        MQConnectionFactory factory = new MQConnectionFactory();
        factory.setHostName("localhost");
        factory.setPort(1424);
        factory.setQueueManager("THUB");
        factory.setStringProperty(WMQConstants.USERID, "admin");
        factory.setStringProperty(WMQConstants.PASSWORD, "passw0rd");
        factory.setChannel("DEV.ADMIN.SVRCONN");
        factory.setTransportType(CommonConstants.WMQ_CM_CLIENT);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(MQConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}