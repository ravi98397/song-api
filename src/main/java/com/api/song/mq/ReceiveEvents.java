package com.api.song.mq;

import java.io.IOException;
import java.util.Map;

import com.rabbitmq.client.Channel;

public class ReceiveEvents {
	
	private Channel channel;
	
	public ReceiveEvents() {
		//try to get connection and create a new channel
		GetConnection con = new GetConnection();
		channel = con.getNewChannel();
	}
	
	public void consumeArtistUpdate(String exchangename, String severity, Map<String, String> message) {
		try {
			channel.basicPublish(exchangename, severity, null, message.toString().getBytes("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("publishArtistUpdate failed");
			e.printStackTrace();
		}
	}
}
