package com.naver.test.chat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.naver.test.chat.model.RoomMessage;

@Controller
@RequestMapping("/chat")
public class ChatController {
	private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	@GetMapping("")
	public String chat(@RequestParam String roomId, Model model) throws Exception {
		model.addAttribute("roomId", roomId);
		model.addAttribute("name", "이정화");
		model.addAttribute("profileImage", "https://ssl.pstatic.net/static/m/mooc/common/prof_img_default.png");
		return "chat/chat";
	}

	/**
	 * SSE 이벤트 발행
	 * new SseEmitter의 파라미터는 요청 타임아웃을 의미 함
	 * @return
	 */
	@GetMapping(path = "/events")
	public SseEmitter handle() {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		this.emitters.add(emitter);

		emitter.onCompletion(() -> this.emitters.remove(emitter));
		emitter.onTimeout(() -> this.emitters.remove(emitter));

		return emitter;
	}

	/**
	 * 특정 이벤트 명에 해당하는 이벤트 발생
	 * @param roomMessage
	 */
	@EventListener
	public void onRoomMessage(RoomMessage roomMessage) {
		List<SseEmitter> deadEmitters = new ArrayList<>();
		this.emitters.forEach(emitter -> {
			try {
				SseEventBuilder builder = SseEmitter.event().name(roomMessage.getRoomId()).data(roomMessage);
				emitter.send(builder);

				// close connnection, browser automatically reconnects
				// emitter.complete();

				// SseEventBuilder builder = SseEmitter.event().name("second").data("1");
				// SseEventBuilder builder =
				// SseEmitter.event().reconnectTime(10_000L).data(memoryInfo).id("1");
				// emitter.send(builder);
			} catch (Exception e) {
				deadEmitters.add(emitter);
			}
		});

		this.emitters.removeAll(deadEmitters);
	}
}
