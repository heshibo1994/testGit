package com.example.order_consumer.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"email.fanout.emailQueue"})
public class FanoutEmailConsumer {
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("email接收到了订单消息是" + message);
    }

}