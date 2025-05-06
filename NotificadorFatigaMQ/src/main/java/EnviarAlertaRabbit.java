import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EnviarAlertaRabbit {

    private static final String QUEUE_NAME = "alertas.fatiga";

    public static void enviar(String mensaje) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost"); // O IP si está en red
            factory.setUsername("guest");
            factory.setPassword("guest");

            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {

                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                channel.basicPublish("", QUEUE_NAME, null, mensaje.getBytes("UTF-8"));
                System.out.println("Alerta enviada a RabbitMQ: " + mensaje);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
