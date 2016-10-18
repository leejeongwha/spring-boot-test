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
		return route(GET("/persons"), handler::all).and(route(GET("/persons/{id}"), handler::byId));
	}

	@Bean
	HttpServer server(RouterFunction<?> router) {
		HttpHandler handler = RouterFunctions.toHttpHandler(router);
		HttpServer httpServer = HttpServer.create(8080);
		httpServer.start(new ReactorHttpHandlerAdapter(handler));
		return httpServer;
	}

	@Component
	public class PersonHandler {
		@Autowired
		private PersonRepository personRepository;

		Response<Flux<Person>> all(Request request) {
			Flux<Person> flux = Flux.fromStream(personRepository.all());
			return Response.ok().body(BodyInserters.fromPublisher(flux, Person.class));

		}

		Response<Mono<Person>> byId(Request request) {
			Optional<String> optional = request.pathVariable("id");
			return optional.map(id -> personRepository.findById(Long.parseLong(id)))
					.map(person -> Mono.fromFuture(person))
					.map(mono -> Response.ok().body(BodyInserters.fromPublisher(mono, Person.class)))
					.orElseThrow(() -> new IllegalStateException("Oops!!"));
		}
	}
}
