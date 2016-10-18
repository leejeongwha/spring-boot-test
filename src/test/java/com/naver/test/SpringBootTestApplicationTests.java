package com.naver.test;

import static org.springframework.web.client.reactive.ClientWebRequestBuilders.get;
import static org.springframework.web.client.reactive.ResponseExtractors.body;

import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.reactive.WebClient;

import com.naver.test.entity.Person;

import reactor.core.publisher.Mono;

@RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest()
public class SpringBootTestApplicationTests {

	private WebClient webClient;

	@Before
	public void setup() {
		this.webClient = new WebClient(new ReactorClientHttpConnector());
	}

	@Test
	public void personTest() throws Exception {
		Mono<Person> result = this.webClient
				.perform(get("http://localhost:8080/persons/1").accept(MediaType.APPLICATION_JSON))
				.extract(body(Person.class));

		CompletableFuture<Person> future = result.toFuture();

		System.out.println(future.get());
	}

}
