package com.example.order.producer.demo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 用户下单，用户Id，商品Id，商品数量
    public void makeOrder(String userId,String productId,int num){
        // 1 判断库存
        // 2 保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生产成功"+orderId);
        // 3 通过Mq完成消息分发
        String exchangeName = "fanout_order_exchange";
        String routingKey="";
        rabbitTemplate.convertAndSend(exchangeName,routingKey,orderId);
    }
}
