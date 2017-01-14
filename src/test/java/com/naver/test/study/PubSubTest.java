package com.naver.test.study;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * reactive stream의 표준 인터페이스를 따라서 만들어보기
 * 
 * http://projectreactor.io/reference
 * 
 * @author ljk2491
 *
 */
public class PubSubTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		// Publisher <- Observable
		// Subscriber <- Observer
		Iterable<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);

		Publisher<Integer> p = new Publisher<Integer>() {
			@Override
			public void subscribe(Subscriber subscriber) {
				Iterator<Integer> it = itr.iterator();

				subscriber.onSubscribe(new Subscription() {
					@Override
					public void request(long arg0) {
						try {
							while (arg0-- > 0) {
								if (it.hasNext()) {
									subscriber.onNext(it.next());
								} else {
									subscriber.onComplete();
									break;
								}
							}
						} catch (RuntimeException e) {
							subscriber.onError(e);
						}
					}

					@Override
					public void cancel() {
					}
				});
			}
		};

		Subscriber<Integer> s = new Subscriber<Integer>() {
			// http://reactivex.io/documentation/operators/backpressure.html
			// backpressure 설명
			Subscription subscription;

			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}

			@Override
			public void onError(Throwable arg0) {
				System.out.println("onError");
			}

			@Override
			public void onSubscribe(Subscription arg0) {
				System.out.println("onSubscribe");
				// 있는 것 받는 개수
				this.subscription = arg0;
				this.subscription.request(1);
			}

			@Override
			public void onNext(Integer t) {
				System.out.println("onNext " + t);
				this.subscription.request(1);
			}
		};

		p.subscribe(s);
	}

}
