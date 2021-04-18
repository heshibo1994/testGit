<<<<<<< HEAD
# 中间件

分布式消息中间件

​	ActiveMQ：Java语言开发，老牌

​	RabbitMQ：目前，比较流行，和Spring同一厂商

​	Kafka：高性能，大数据常用

​	RocketMQ：国产消息队列

负载均衡中间件

​	Nginx：负载均衡

​	CDN：高访问量

缓存中间件
	MemCache

​	Redis

 ## 消息中间件

是一种接受数据，接受请求，存储数据，发送数据等功能的技术服务。

单体架构：将所有的业务或者模块，源代码等都放在一个工程中，如果其中的一个模块升级或者迭代就需要重新编译和部署整个项目。1）耦合度过高2）运维成本高3）不易维护4）服务器的成本高

分布式架构：一个请求由多个服务器端的多个服务协同处理完成。

### 协议

协议：

1.计算机底层操作系统和应用程序通讯时共同遵守的一组约定，只有遵守共同的约定和规范，系统和底层操作系统之间才能够相互交流

2.负责数据的接收和传递，所以性能会比较高

3.协议对数据格式和计算机之间交换数据都必须严格遵守。

构成：

语法，语义，时序

默认的TCP/IP协议，对于RabbitMQ采用AMQP协议。

消息中间件不直接使用http的原因：

1.Http的请求报文头和相应报文头比较复杂，包括了cookie，数据的加密解密，状态码，相应码等，对于消息中间间而言，不需要复杂，只负责数据的传递存储，分发，追求的是高性能，尽量简洁和快速。

2.Http大部分是短链接，在实际交互中，一个请求到相应很可能会中断，中断后就不可能长久话，造成请求丢失，即使出现故障也要对数据或者消息进行持久化。尽可能要高可靠性和稳健运行

#### AMQP协议

高级消息队列协议，支持消息的分布式事务，消息的持久化，高性能和高可靠。

### 持久化

存在内存中的数据处理快，但重启数据就会丢失，因此数据一般存在磁盘中，可以长期持有。

### 分发

消费者会消费消息，可以通过主动拉去或者被动推送收到消息。

分发的策略：

发布订阅：生产者将消息发送到中间件中，只要订阅该中间间就能收到消息。

轮询分发：一种公平的分发方式，不论服务器的性能好坏，都会公平接受。

公平分发：服务器性能好的会接受更多的消息，能者多劳

重发：当消息堆积后，会重新发布

消息拉取 

### 高可靠性和高可用性

服务器阿紫任意时刻都处于可执行功能的能力。

主从共享数据的部署方式

主从同步部署方式

多主多从部署方式

## RabbitMQ实战

### 安装

https://www.jianshu.com/p/bc6da5db58ca

firewall-cmd --zone=public --add-port=15672/tcp --permanent

### simple模式 轮询

代码

生产者：

```java
package simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
            channel.queueDeclare("队列1", false, false, false, null);
            // 准备消息
            // 发送消息给队列

            channel.basicPublish("", "队列1", null, "hello world".getBytes());
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
```

 消费者：

```java
package simple;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
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
            // 准备消息
            // 发送消息给队列

            channel.basicConsume("队列1", true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("收到的消息是" + new String(delivery.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    System.out.println("接受失败");

                }
            });
            System.out.println("开始接受消息");
            System.in.read();
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

```

### AMQP

步骤：

生产过程

![image-20210418175055041](C:\Users\hsb\AppData\Roaming\Typora\typora-user-images\image-20210418175055041.png)

消费过程

![image-20210418175134885](C:\Users\hsb\AppData\Roaming\Typora\typora-user-images\image-20210418175134885.png)



![image-20210418183517969](C:\Users\hsb\AppData\Roaming\Typora\typora-user-images\image-20210418183517969.png)

Server（Broker）：接受了护短的连接，实现AMQP实体服务

Connection：连接，应用程序与broker的网络连接

channel：网络信道，几乎所有的操作倒在channel中进行，进行消息读写的通道，客户端可以创建多个channel，每一个channle代表一个绘画任务。

message：消息，服务与应用程序之间传送数据，由peoperties和body（消息的内容）组成

virtual host：虚拟地址，用于进行逻辑隔离

exchange：交换机，接受消息，根据路由键发出消息到绑定的队列

bindings：exchange和queue之间的虚拟连接，bindings可以保护多个routingkey

routingkey：路由规则

queue：队列，保存消息并转发给消费者



### 完成的申明方式

```java
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

```

### work轮询模式

当有多个消费者时，消息会被那个消息消费，如何均衡消费者消费消息的多少？

两种模式:

轮询模式：一个消费者消费一条，按均分配

公平分发：根据消费者的消费能力，处理快的处理的多

## 使用场景

解耦，削峰，异步
=======
# 中间件

分布式消息中间件

​	ActiveMQ：Java语言开发，老牌

​	RabbitMQ：目前，比较流行，和Spring同一厂商

​	Kafka：高性能，大数据常用

​	RocketMQ：国产消息队列

负载均衡中间件

​	Nginx：负载均衡

​	CDN：高访问量

缓存中间件
	MemCache

​	Redis

 ## 消息中间件

是一种接受数据，接受请求，存储数据，发送数据等功能的技术服务。

单体架构：将所有的业务或者模块，源代码等都放在一个工程中，如果其中的一个模块升级或者迭代就需要重新编译和部署整个项目。1）耦合度过高2）运维成本高3）不易维护4）服务器的成本高

分布式架构：一个请求由多个服务器端的多个服务协同处理完成。

### 协议

协议：

1.计算机底层操作系统和应用程序通讯时共同遵守的一组约定，只有遵守共同的约定和规范，系统和底层操作系统之间才能够相互交流

2.负责数据的接收和传递，所以性能会比较高

3.协议对数据格式和计算机之间交换数据都必须严格遵守。

构成：

语法，语义，时序

默认的TCP/IP协议，对于RabbitMQ采用AMQP协议。

消息中间件不直接使用http的原因：

1.Http的请求报文头和相应报文头比较复杂，包括了cookie，数据的加密解密，状态码，相应码等，对于消息中间间而言，不需要复杂，只负责数据的传递存储，分发，追求的是高性能，尽量简洁和快速。

2.Http大部分是短链接，在实际交互中，一个请求到相应很可能会中断，中断后就不可能长久话，造成请求丢失，即使出现故障也要对数据或者消息进行持久化。尽可能要高可靠性和稳健运行

#### AMQP协议

高级消息队列协议，支持消息的分布式事务，消息的持久化，高性能和高可靠。

### 持久化

存在内存中的数据处理快，但重启数据就会丢失，因此数据一般存在磁盘中，可以长期持有。

### 分发

消费者会消费消息，可以通过主动拉去或者被动推送收到消息。

分发的策略：

发布订阅：生产者将消息发送到中间件中，只要订阅该中间间就能收到消息。

轮询分发：一种公平的分发方式，不论服务器的性能好坏，都会公平接受。

公平分发：服务器性能好的会接受更多的消息，能者多劳

重发：当消息堆积后，会重新发布

消息拉取 

### 高可靠性和高可用性

服务器阿紫任意时刻都处于可执行功能的能力。

主从共享数据的部署方式

主从同步部署方式

多主多从部署方式

## RabbitMQ实战

### 安装

https://www.jianshu.com/p/bc6da5db58ca

firewall-cmd --zone=public --add-port=15672/tcp --permanent

代码

```java
package simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
            channel.queueDeclare("队列1", false, false, false, null);
            // 准备消息
            // 发送消息给队列

            channel.basicPublish("", "队列1", null, "hello world".getBytes());
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
```

 
>>>>>>> 1aef3e02f157a3c4ce9a24f259311f73898c1cb6
