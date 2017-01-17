package com.naver.test.study;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SchedulerTest {
	private static final Logger log = LoggerFactory.getLogger(SchedulerTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws InterruptedException {
		Subscriber<Integer> sub = new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				log.info("on complete");
			}

			@Override
			public void onError(Throwable arg0) {
			}

			@Override
			public void onNext(Integer arg0) {
				log.info(arg0.toString());
			}
		};

		Observable<Integer> ob = Observable.create(subscriber -> {
			Iterable<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);

			try {
				log.info("on subscriber");
				for (Integer i : itr) {
					subscriber.onNext(i);
				}
				subscriber.onCompleted();
			} catch (Exception e) {
				subscriber.onError(e);
			}
		});

		ob.observeOn(Schedulers.newThread()).subscribeOn(Schedulers.newThread()).subscribe(sub);

		log.info("exit");

		Thread.sleep(Long.MAX_VALUE);
	}

}
