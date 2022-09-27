package com.api.song;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.api.song.mq.Receiver;


@SpringBootApplication
@EnableEurekaClient
public class SongApiApplication {
	static final String topicExchangeName = "REL_EXCHANGE";

	static final String queueName = "SONG_QUEUE";
	
	@Bean
	Queue queue() {
	  return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
	  return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
	   return BindingBuilder.bind(queue).to(exchange).with("*.song.*");
	}
	

	  @Bean
	  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(queueName);
	    container.setMessageListener(listenerAdapter);
	    return container;
	  }

	  @Bean
	  MessageListenerAdapter listenerAdapter(Receiver receiver) {
		 return new MessageListenerAdapter(receiver, "receiveMessage");
	  }
	  

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SongApiApplication.class, args);
	}

}
