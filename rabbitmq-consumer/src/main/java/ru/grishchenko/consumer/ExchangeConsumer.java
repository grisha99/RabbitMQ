package ru.grishchenko.consumer;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class ExchangeConsumer {

    private final String EXCHANGE_NAME = "itExchanger";

    private final Logger logger = LoggerFactory.getLogger(ExchangeConsumer.class);

    ConnectionFactory factory;

    public ExchangeConsumer() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
    }

    public void start() throws IOException, TimeoutException {

        MessageObject messageObject;

        try (
                Scanner scanner = new Scanner(System.in);
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String queueName = channel.queueDeclare().getQueue();
            logger.info("My queue name: " + queueName);

            logger.info(" [*] Waiting for messages");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                logger.info(" [x] Received '" + message + "'");
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });

            do {
                messageObject = new MessageObject(scanner.nextLine());
                if (!messageObject.getKey().equals("")) {
                    switch (messageObject.getType()) {
                        case SUBSCRIBE: {
                            channel.queueBind(queueName, EXCHANGE_NAME, messageObject.getKey());
                            logger.info("Subscribe by key: " + messageObject.getKey());
                            break;
                        }

                        case UNSUBSCRIBE: {
                            channel.queueUnbind(queueName, EXCHANGE_NAME, messageObject.getKey());
                            logger.info("UnSubscribe by key: " + messageObject.getKey());
                            break;
                        }

                        case UNDEFINED: {
                            logger.error("!!! Command Undefined !!!");
                            break;
                        }
                    }
                }

            } while (messageObject.getType() != MessageObject.MsgType.EXIT);
        }
        logger.info("Client exit");

    }
}
