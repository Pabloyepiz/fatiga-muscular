import com.rabbitmq.client.*;

public class ConsumidorFatiga {

    private static final String QUEUE_NAME = "alertas.fatiga";

    public static void main(String[] argv) throws Exception {
        // Configurar conexión con RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Cambia si RabbitMQ está en otra IP
        factory.setUsername("guest");
        factory.setPassword("guest");

        // Crear conexión y canal
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Asegurarse que la cola existe
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("🔔 Esperando notificaciones de fatiga muscular...");

        // Definir comportamiento al recibir un mensaje
        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensaje = new String(delivery.getBody(), "UTF-8");
            System.out.println("🚨 ALERTA DE FATIGA DETECTADA: " + mensaje);
        };

        // Iniciar escucha
        channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
    }
}

