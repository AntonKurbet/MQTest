package ru.geekbrains.mq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BlogPublisher {
    private static final String EXCHANGE_NAME = "it_blog";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        SendMessage(factory, EXCHANGE_NAME, "php.core.hello", "hello from php" );
        SendMessage(factory, EXCHANGE_NAME, "java.core", "java" );
        SendMessage(factory, EXCHANGE_NAME, "php.libs.main", "libs for php" );
    }

    private static void SendMessage(ConnectionFactory factory, String exchangeName, String topic, String message) throws IOException, TimeoutException {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.basicPublish(exchangeName, topic, null, message.getBytes("UTF-8"));
            System.out.printf("Publish message '%s':'%s'\n",topic,message);
        }
    }
}