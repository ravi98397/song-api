package com.api.song.mapper;

import java.util.HashMap;
import java.util.Map;

public class MapCollectiontoObj {
	public static Map<String, String> receievedmessage(String message){
		Map<String, String> msg = new HashMap<String, String>();
		for(String i: message.substring(1, message.length()-1).split(",")) {
			String []pair = i.split("=");
			msg.put(pair[0], pair[1]);
		}
		return msg;
	}
}
