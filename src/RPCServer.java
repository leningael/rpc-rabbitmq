import com.rabbitmq.client.*;

public class RPCServer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    private static String recuperarArchivo(String indice){
        FileReader fileReader = new FileReader();
        String nombreArchivo = fileReader.recuerarNombreArchivo(indice);
        if(!nombreArchivo.isBlank())
            return fileReader.recuperarContenidoArchivo(nombreArchivo);
        return "";
    }

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            channel.queuePurge(RPC_QUEUE_NAME);

            channel.basicQos(1);

            System.out.println(" [x] Esperando solicitudes");

            Object monitor = new Object();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";

                try {
                    String message = new String(delivery.getBody(), "UTF-8");
                    response = recuperarArchivo(message);
                    if(response.isBlank())
                        System.err.println(" [!] El indice proporcionado no pertenece a ningun archivo");
                    else
                        System.out.println(" [.] Lectura del archivo con indice" + message);
                } catch (RuntimeException e) {
                    System.out.println(" [!] " + e.toString());
                } finally {
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    // RabbitMq consumer worker thread notifies the RPC server owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));
            // Wait and be prepared to consume the message from RPC client.
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
