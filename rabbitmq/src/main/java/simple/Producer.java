package simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Producer {
    public static void main(String[] args) {
        // rabbitMq遵循amqp协议
        // 创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        // 创建连接Connection
        Connection connection = null;
        Channel channel = null;
        // 通过通道创建交换机，队列。邦定关系，路由key,接受发送消息
        try {
            connection = connectionFactory.newConnection("生产者");
            channel = connection.createChannel();
            // 队列名称
            // 是否持久化，true：持久化；false：非持久化，也会存盘，但是会随着重启服务器而丢失
            channel.queueDeclare("队列1", false, false, false, null);
            // 准备消息
            // 发送消息给队列
            String message = "hello world";


            // 交换机
            String exchangeName = "direct_message_exchange";
            // 交换机类型：direct/topic/fanout/headers
            String exchangeType = "direct";
            channel.exchangeDeclare(exchangeName,exchangeType,true);
            // 队列
            channel.queueDeclare("队列2",true,false,false,null);
            channel.queueDeclare("队列3",true,false,false,null);
            channel.queueDeclare("队列4",true,false,false,null);
            // 绑定交换机和队列
            channel.queueBind("队列2",exchangeName,"order");
            channel.queueBind("队列3",exchangeName,"order");
            channel.queueBind("队列4",exchangeName,"course");


            // parameter1：交换机：负责消息的接受，将消息投递至队列虽然不指定交换机，但是有一个默认的交换机
            // parameter2:
            // parameter3:
            channel.basicPublish(exchangeName, "course", null, message.getBytes());
            System.out.println("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 关闭通道
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
