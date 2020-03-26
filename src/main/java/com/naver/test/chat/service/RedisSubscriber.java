package com.naver.test.chat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naver.test.chat.model.RoomMessage;

@Service
public class RedisSubscriber implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);

	public final ApplicationEventPublisher eventPublisher;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String body = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
			RoomMessage roomMessage = objectMapper.readValue(body, RoomMessage.class);
			logger.info("Chat - Message : {}", roomMessage.toString());

			//SSE 이벤트 발행
			this.eventPublisher.publishEvent(roomMessage);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public RedisSubscriber(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
}
