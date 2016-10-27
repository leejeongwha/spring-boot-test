package com.naver.test.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.davidmoten.rx.FileObservable;

import rx.Observable;

@RestController
@RequestMapping("tail")
public class TailFileController {
	private static final Logger log = LoggerFactory.getLogger(TailFileController.class);

	@RequestMapping("test")
	public String test() throws Exception {
		log.info("현재 시각은 : " + System.currentTimeMillis());
		return "success";
	}

	@PostConstruct
	public void postConstruct() {
		Runnable task2 = () -> {
			Observable<String> items = FileObservable.tailer().file("/Users/NAVER/Downloads/spring-boot-test.log")
					.tailText();
			items.subscribe(text -> System.out.println("tail -f log file content : " + text));
		};
		new Thread(task2).start();
	}
}
