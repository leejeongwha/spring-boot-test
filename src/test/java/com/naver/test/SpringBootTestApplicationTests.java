package com.naver.test;

import static org.springframework.web.client.reactive.ClientWebRequestBuilders.get;
import static org.springframework.web.client.reactive.ResponseExtractors.body;
import static org.springframework.web.client.reactive.ResponseExtractors.bodyStream;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.reactive.WebClient;

import com.naver.test.entity.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest()
public class SpringBootTestApplicationTests {

	private WebClient webClient;

	@Before
	public void setup() {
		// 스프링에서 제공하는 web reactive를 위한 client
		this.webClient = new WebClient(new ReactorClientHttpConnector());
	}

	@Test
	public void personTest() throws Exception {
		Mono<Person> result = this.webClient
				.perform(get("http://localhost:8080/persons/1").accept(MediaType.APPLICATION_JSON))
				.extract(body(Person.class));

		CompletableFuture<Person> future = result.toFuture();
		System.out.println(future.get());

		// 아래의 경우는 Reactive의 장점을 활용할 수 없음
		// Person block = result.block();
		// System.out.println(block);
	}

	@Test
	public void personsTest() throws Exception {
		Flux<Person> result = this.webClient
				.perform(get("http://localhost:8080/persons").accept(MediaType.APPLICATION_JSON))
				.extract(bodyStream(Person.class));

		Stream<Person> stream = result.toStream();
		stream.forEach(System.out::println);
	}

	@Test
	public void fluxTest() throws Exception {
		// subscribe는 publisher에게 모든 데이터를 보내달라고 요청
		Flux.just("red", "white", "blue").log().map(String::toUpperCase).subscribe();

	}

	@Test
	public void fluxParallelTest() throws Exception {
		// subscribe는 publisher에게 모든 데이터를 보내달라고 요청(using single backgroud
		// thread)
		Flux.just("red", "white", "blue").log().map(String::toUpperCase).subscribeOn(Schedulers.parallel()).subscribe();

	}

	@Test
	public void fluxMultipleParallelTest() throws Exception {
		// subscribe는 publisher에게 모든 데이터를 보내달라고 요청(using multiple backgroud
		// thread)
		Flux.just("red", "white", "blue").log()
				.flatMap(value -> Mono.just(value.toUpperCase()).subscribeOn(Schedulers.parallel()), 2)
				.subscribe(value -> System.out.println("Consumed: " + value));

	}
}
