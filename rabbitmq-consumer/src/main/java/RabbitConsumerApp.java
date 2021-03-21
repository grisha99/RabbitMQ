import ru.grishchenko.consumer.ExchangeConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitConsumerApp {

    public static void main(String[] args) throws IOException, TimeoutException {

        ExchangeConsumer consumer = new ExchangeConsumer();
        consumer.start();
    }
}
