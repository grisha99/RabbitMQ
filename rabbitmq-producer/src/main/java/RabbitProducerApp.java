import ru.grishchenko.producer.ExchangeProducer;

public class RabbitProducerApp {

    public static void main(String[] args) throws Exception {

        ExchangeProducer producer = new ExchangeProducer();
        producer.start();

    }
}
