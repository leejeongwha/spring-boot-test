package com.naver.test.study;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

/**
 * RactiveProgramming : 이벤트나 데이터가 발생하면 대응하는 방식으로 동작하는 코드를 작성
 * 
 * 1) Duality (쌍대성)
 * 
 * 2) Oberver Pattern
 * 
 * 3) Reactive Streams - 표준 - Java9 API
 * 
 * @author ljk2491
 *
 */
public class ObserverPatternTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIter() {
		Iterable<Integer> iter = () -> new Iterator<Integer>() {
			int i = 0;
			final static int MAX = 10;

			@Override
			public boolean hasNext() {
				return i < 10;
			}

			@Override
			public Integer next() {
				return ++i;
			}
		};

		for (Integer i : iter) {
			System.out.println(i);
		}
	}

	/**
	 * Observer패턴의 맹점(MS에서 이를 확장, ReactiveX group)
	 * 
	 * 1. Complete 개념이 없음
	 * 
	 * 2. Error 개념이 없음
	 */
	@Test
	public void testObserver() {
		// subscriber
		Observer ob = new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				System.out.println(arg);
			}
		};

		// Observable에 등록되어 있는 모든 Observer에게 알림(broadcasting 가능)
		IntObservable io = new IntObservable();
		io.addObserver(ob);

		io.run();
	}
}

class IntObservable extends Observable implements Runnable {
	// publisher
	@Override
	public void run() {
		for (int i = 1; i <= 10; i++) {
			setChanged();
			notifyObservers(i); // push
			// int i = it.next(); //pull
		}
	}
}
