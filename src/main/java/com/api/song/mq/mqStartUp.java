package com.api.song.mq;

import java.util.ArrayList;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class mqStartUp {
	
	//all the configuration are picked up from the configuration file////?yet to be programmed.
	private static GetConnection factory = new GetConnection();
	
	private static Channel channel;
	
	private static ArrayList<Channel> pvtChannel = new ArrayList<Channel>();
	
	public static void startmq() {
		try (Connection connection = factory.getNewConnection()) {
            
			//public channel
			channel = connection.createChannel();
            
            //Declaring queue for 
            factory.generateQueue(channel, "SONG_MQ", false, false, true);
            channel.confirmSelect();
            
            
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static Channel getChannel() {
		return channel;
	}
	
}
