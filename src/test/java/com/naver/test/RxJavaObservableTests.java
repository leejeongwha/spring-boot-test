package com.naver.test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class RxJavaObservableTests {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Observable.just는 subscribe를 하게 되면 "개미"라는 이벤트를 1번 발생시키는 Observable이다.
	 */
	@Test
	public void just_테스트() {
		System.out.println("create observable");
		Observable<String> observable = Observable.just("개미");
		System.out.println("do subscribe");
		observable.subscribe(new Subscriber<String>() {
			@Override
			public void onNext(String text) {
				System.out.println("onNext : " + text);
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError : " + e.getMessage());
			}
		});
	}

	/**
	 * Observable.from은 배열 또는 Iterable의 요소를 순서대로 이벤트로 발생시키는 Observable
	 */
	@Test
	public void from_테스트() {
		System.out.println("create observable");
		Observable<String> observable = Observable.from(new String[] { "개미", "매", "마루" });
		System.out.println("do subscribe");
		observable.subscribe(new Subscriber<String>() {
			@Override
			public void onNext(String text) {
				System.out.println("onNext : " + text);
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError : " + e.getMessage());
			}
		});
	}

	/**
	 * Observable.defer() 는 구독(subscribe) 하는 순간 특정 function 을 실행하고 리턴받은
	 * Observable 의 이벤트를 전달
	 */
	@Test
	public void defer_테스트() {
		System.out.println("create observable");
		Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
			@Override
			public Observable<String> call() {
				System.out.println("defer function call");
				return Observable.just("HelloWorld");
			}
		});
		System.out.println("do subscribe");
		observable.subscribe(new Subscriber<String>() {
			@Override
			public void onNext(String text) {
				System.out.println("onNext : " + text);
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError : " + e.getMessage());
			}
		});
	}

	@Test
	public void just_테스트_lambda() {
		System.out.println("create observable");
		Observable<String> observable = Observable.just("개미");
		System.out.println("do subscribe");
		observable.subscribe(text -> System.out.println("onNext : " + text),
				e -> System.out.println("onError : " + e.getMessage()), () -> System.out.println("onCompleted"));
	}

	/**
	 * create에 넘겨지는 객체는 OnSubscribe라는 인터페이스를 구현
	 */
	@Test
	public void create() {
		Observable.create(subscriber -> {
			try {
				subscriber.onNext("안녕하세요");
				subscriber.onNext("반갑습니다");
				subscriber.onNext("종료하겠습니다");
				subscriber.onCompleted();
			} catch (Exception e) {
				subscriber.onError(e);
			}
		}).subscribe(text -> System.out.println("onNext : " + text),
				throwable -> System.out.println("onError : " + throwable.getMessage()),
				() -> System.out.println("onCompleted"));
	}

	/**
	 * 주기적으로 이벤트 전파
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void interval() throws InterruptedException {
		while (true) {
			Observable.interval(1, TimeUnit.SECONDS).subscribe(count -> {
				System.out.println("onNext : " + new Date());
			});

			Thread.sleep(Long.MAX_VALUE);
		}
	}

	/**
	 * 특정 시간이 지난 후 이벤트 발생
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void timer() throws InterruptedException {
		while (true) {
			System.out.println("start : " + new Date());
			Observable.timer(10, TimeUnit.SECONDS).observeOn(Schedulers.computation()).subscribe(
					count -> System.out.println("onNext : " + new Date()),
					e -> System.out.println("onError : " + new Date()),
					() -> System.out.println("onCompleted : " + new Date()));

			Thread.sleep(Long.MAX_VALUE);
		}
	}

	/**
	 * range는 시작점과 반복횟수를 지정하면 반복하여 이벤트를 발생
	 */
	@Test
	public void range() {
		Observable.range(10, 10).observeOn(Schedulers.computation()).subscribe(
				count -> System.out.println("onNext : " + count), e -> System.out.println("onError"),
				() -> System.out.println("onCompleted"));
	}

	/**
	 * map을 사용하여 이벤트를 다른 형태로 변형
	 */
	@Test
	public void map() {
		Observable.from(new String[] { "개미", "매", "마루" }).map(text -> "** " + text + " **").subscribe(
				text -> System.out.println("onNext : " + text), e -> System.out.println("onError"),
				() -> System.out.println("onCompleted"));
	}

	/**
	 * 전달받은 이벤트로부터 다른 Observable들을 생성하고 그 Observable들에서 발생한 이벤트들을 쭉 펼쳐서 전파
	 */
	@Test
	public void flatMap() {
		Observable.from(new String[] { "개미", "매", "마루" })
				.flatMap(text -> Observable.from(new String[] { text + " 사랑합니다.", text + " 고맙습니다." }))
				.subscribe(text -> System.out.println("onNext : " + text), e -> System.out.println("onError"),
						() -> System.out.println("onCompleted"));
	}

	/**
	 * Observable 합성하기
	 */
	@Test
	public void zip() {
		Observable
				.zip(Observable.just("개미"), Observable.just("gaemi.jpg"),
						(profile, image) -> "프로필 : " + profile + ", 이미지 : " + image)
				.subscribe(print -> System.out.println("onNext : " + print), e -> System.out.println("onError"),
						() -> System.out.println("onCompleted"));
	}

	/**
	 * Subject는 이벤트를 전달받아 구독자들에게 이벤트를 전파하는 중간다리. PublishSubject를 구독한 시점 이후에 발생하는
	 * 이벤트를 전달받음
	 */
	@Test
	public void publishSubject() {
		PublishSubject<String> subject = PublishSubject.create();

		subject.onNext("첫번째 호출");
		subject.onNext("두번째 호출");

		subject.subscribe(text -> {
			System.out.println("onNext : " + text);
		});

		subject.onNext("세번째 호출");
		subject.onNext("네번째 호출");
	}

	/**
	 * 구독전에 한건이라도 이벤트가 발생했다면 구독시점에 해당 이벤트도 같이 받음
	 */
	@Test
	public void behaviorSubject() {
		BehaviorSubject<String> subject = BehaviorSubject.create();

		subject.onNext("첫번째 호출");
		subject.onNext("두번째 호출");

		subject.subscribe(text -> {
			System.out.println("onNext : " + text);
		});

		subject.onNext("세번째 호출");
		subject.onNext("네번째 호출");
	}
}
