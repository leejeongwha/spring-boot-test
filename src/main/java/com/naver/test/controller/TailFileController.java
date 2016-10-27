package com.naver.test.controller;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.davidmoten.rx.FileObservable;

import rx.Observable;

@RestController
@RequestMapping("tail")
public class TailFileController {
	private static final Logger log = LoggerFactory.getLogger(TailFileController.class);

	@Value("${logging.file}")
	private String fileName;

	@RequestMapping("test")
	public String test() throws Exception {
		log.info("현재 시각은 : " + System.currentTimeMillis());
		return "success";
	}

	@PostConstruct
	public void postConstruct() {
		Runnable task2 = () -> {
			Observable<String> items = FileObservable.tailer().file(fileName).tailText();
			items.subscribe(text -> System.out.println("tail -f log file content : " + text));
		};
		new Thread(task2).start();
	}

	@PreDestroy
	public void preDestroy() throws IOException {
		FileUtils.forceDelete(new File(fileName));
	}
}
