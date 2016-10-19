package com.naver.test;

import static org.springframework.web.reactive.function.RequestPredicates.GET;
import static org.springframework.web.reactive.function.RouterFunctions.route;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.Request;
import org.springframework.web.reactive.function.Response;
import org.springframework.web.reactive.function.RouterFunction;
import org.springframework.web.reactive.function.RouterFunctions;

import com.naver.test.entity.Person;
import com.naver.test.repository.PersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.HttpServer;

/**
 * @author NAVER
 *         https://spring.io/blog/2016/10/05/spring-tips-functional-reactive-
 *         endpoints-with-spring-framework-5-0
 *
 */
@Configuration
public class ReactorNettyServer {
	@Bean
	RouterFunction<?> router(PersonHandler handler) {
		return route(GET("/"), request -> Response.ok().body(BodyInserters.fromObject("Welcome to Reactor Web Server")))
				.and(route(GET("/persons"), handler::all)).and(route(GET("/persons/{id}"), handler::byId))
				.and(route(GET("/randomNumbers"), handler::randomNumbers)).filter((request, next) -> {
					System.out.println("Before handler invocation: " + request.path());
					Response<?> response = next.handle(request);
					Object body = response.body();
					System.out.println("After handler invocation: " + body);
					return response;
				});
	}

	/**
	 * Netty 혹은 Undertow사용가능(리턴타입 변경 필요)
	 * 
	 * @param router
	 * @return
	 */
	@Bean
	HttpServer server(RouterFunction<?> router) {
		HttpHandler handler = RouterFunctions.toHttpHandler(router);
		HttpServer httpServer = HttpServer.create(8080);
		httpServer.start(new ReactorHttpHandlerAdapter(handler));
		return httpServer;

		// HttpHandler handler = RouterFunctions.toHttpHandler(router);
		// UndertowHttpHandlerAdapter adapter = new
		// UndertowHttpHandlerAdapter(handler);
		// Undertow server = Undertow.builder().addHttpListener(8080,
		// "localhost").setHandler(adapter).build();
		// server.start();
		// return server;
	}

	@Component
	public class PersonHandler {
		@Autowired
		private PersonRepository personRepository;

		Response<Flux<Person>> all(Request request) {
			Flux<Person> flux = Flux.fromStream(personRepository.all()).log();
			return Response.ok().body(BodyInserters.fromPublisher(flux, Person.class));

		}

		Response<Mono<Person>> byId(Request request) {
			Optional<String> optional = request.pathVariable("id");
			return optional.map(id -> personRepository.findById(Long.parseLong(id)))
					.map(person -> Mono.fromFuture(person))
					.map(mono -> Response.ok().body(BodyInserters.fromPublisher(mono, Person.class)))
					.orElseThrow(() -> new IllegalStateException("Oops!!"));
		}

		Response<Flux<Double>> randomNumbers(Request request) {
			Flux<Double> flux = Flux.range(1, 10).delayMillis(500).map(i -> Math.random());
			return Response.ok().body(BodyInserters.fromServerSentEvents(flux, Double.class));
		}
	}
}
