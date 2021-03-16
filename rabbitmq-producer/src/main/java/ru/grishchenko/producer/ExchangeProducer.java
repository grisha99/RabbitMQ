package ru.grishchenko.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ExchangeProducer {

    private final Logger logger = LoggerFactory.getLogger(ExchangeProducer.class);

    public static final String EXCHANGE_NAME = "itExchanger";

    private ConnectionFactory factory;

    private static class MsgObject {

        private final static String DELIMITER = "#";

        public static String getKey(String message) {
            if (message.contains(DELIMITER)) {
                return message.split(DELIMITER)[0];
            }
            return null;
        }

        public static String getValue(String message) {
            return message.split(DELIMITER)[1];
        }
    }

    public ExchangeProducer() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
    }

    public void start()  throws Exception{

        String msg, key, value;
        try (
                Scanner scanner = new Scanner(System.in);
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            do {
                msg = scanner.nextLine();
                key = MsgObject.getKey(msg);
                if (key != null) {
                    value = MsgObject.getValue(msg);

                    channel.basicPublish(EXCHANGE_NAME, key, null, value.getBytes("UTF-8"));
                    logger.info(" [x] Sent key: '" + key + "', with value: '" + value + "'");
                } else {
                    logger.error("Please enter message format: key#value");
                }

            } while (!msg.equals("exit"));

        }
    }
}
