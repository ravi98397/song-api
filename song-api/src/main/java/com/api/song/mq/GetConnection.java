package com.api.song.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;

//closing connections and channels not needed, needs to be handled in future.

@Service
public class GetConnection {
	
	@Value("${MQ.HOSTNAME:localhost}")
	private String MQHOSTNAME = "localhost";
	
	private ConnectionFactory factory;
	
	private Connection connection;
	
	public GetConnection() {
			System.out.println(MQHOSTNAME);
			if(MQHOSTNAME != null) {
				factory = new ConnectionFactory();
				factory.setHost(MQHOSTNAME);
			}else {
				System.out.println("please setup hostname in configratution file");
				System.out.println("Connection error"); 
			}
	}
	
	public Connection getNewConnection() {
		try {
			if(connection != null) {
				return connection;
			}else {
				connection = factory.newConnection();
			}
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public Channel getNewChannel() {
		getNewConnection();
		Channel channel = null;
		try {
			channel = connection.createChannel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		return channel;
	}
	
	
	//it's customizable only till 4 params
	public void generateQueue(Channel ch, String queueName, boolean durable, boolean exclusive, boolean autodelete) {
		try {
			DeclareOk confirm = ch.queueDeclare(queueName,  //name of the queue
					false,       //durable
					false, 		 //exclusive
					true,        //auto delete
					null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
