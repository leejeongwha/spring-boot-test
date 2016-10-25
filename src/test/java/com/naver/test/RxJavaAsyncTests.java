package com.naver.test;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaAsyncTests {
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void defer_비동기_테스트() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + ", create observable");
		Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
			@Override
			public Observable<String> call() {
				System.out.println(Thread.currentThread().getName() + ", defer function call");
				return Observable.just("HelloWorld");
			}
		});
		System.out.println(Thread.currentThread().getName() + ", do subscribe");
		// computation 스레드에서 defer function이 실행
		// 새로운 스레드에서 Subscriber로 이벤트가 전달.
		observable.subscribeOn(Schedulers.computation()).observeOn(Schedulers.newThread())
				.subscribe(new Subscriber<String>() {
					@Override
					public void onNext(String text) {
						System.out.println(Thread.currentThread().getName() + ", onNext : " + text);
					}

					@Override
					public void onCompleted() {
						System.out.println(Thread.currentThread().getName() + ", onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println(Thread.currentThread().getName() + ", onError : " + e.getMessage());
					}
				});

		System.out.println(Thread.currentThread().getName() + ", after subscribe");

		Thread.sleep(Long.MAX_VALUE);
	}

	/**
	 * subscribeOn은 누군가 Observable에 구독이 이루어지는 thread를 지정하며, observeOn은
	 * Observable이 이벤트를 전파할때, 즉 관찰자에게 전달될때 사용되는 thread
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void defer_비동기_테스트_2() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + ", create observable");
		Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
			@Override
			public Observable<String> call() {
				System.out.println(Thread.currentThread().getName() + ", defer function call");
				return Observable.just("HelloWorld");
			}
		});
		System.out.println(Thread.currentThread().getName() + ", do subscribe");

		Observable<String> observable2 = observable.subscribeOn(Schedulers.computation())
				.observeOn(Schedulers.newThread()).map(new Func1<String, String>() {
					@Override
					public String call(String text) {
						System.out.println(Thread.currentThread().getName() + ", map");
						return "(" + text + ")";
					}
				});

		observable2.observeOn(Schedulers.newThread()).subscribe(new Subscriber<String>() {
			@Override
			public void onNext(String text) {
				System.out.println(Thread.currentThread().getName() + ", onNext : " + text);
			}

			@Override
			public void onCompleted() {
				System.out.println(Thread.currentThread().getName() + ", onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(Thread.currentThread().getName() + ", onError : " + e.getMessage());
			}
		});

		System.out.println(Thread.currentThread().getName() + ", after subscribe");

		Thread.sleep(Long.MAX_VALUE);
	}
}
