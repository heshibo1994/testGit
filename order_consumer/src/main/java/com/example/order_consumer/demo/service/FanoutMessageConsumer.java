package com.example.order_consumer.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
@RabbitListener(queues = {"message.fanout.messageQueue"})
public class FanoutMessageConsumer {
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("message接收到了订单消息是" + message);
    }

}