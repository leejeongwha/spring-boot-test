package com.naver.test.redis.chat.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class ChatMessageListener implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(ChatMessageListener.class);

	@Override
	public void onMessage(Message arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		logger.info("Message Received at Listener : " + arg0.toString() + ", from Channel [" + new String(arg1) + "]");
	}

}
