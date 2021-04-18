package com.example.order_consumer.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfiguration {
    // 申明fanout模式的交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout_order_exchange",true,true);
    }
    // 声明队列
    @Bean
    public Queue smsQueue(){
        return new Queue("sms.fanout.smsQueue",true);
    }

    @Bean
    public Queue messageQueue(){
        return new Queue("message.fanout.messageQueue",true);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue("email.fanout.emailQueue",true);
    }
    // 完成绑定
    @Bean
    public Binding smsBinding(){
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }


    @Bean
    public Binding messageBinding(){
        return BindingBuilder.bind(messageQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }
}
