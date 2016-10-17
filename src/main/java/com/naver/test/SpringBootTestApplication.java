package com.naver.test;

import static org.springframework.web.reactive.function.RequestPredicates.GET;
import static org.springframework.web.reactive.function.RouterFunctions.route;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.Request;
import org.springframework.web.reactive.function.Response;
import org.springframework.web.reactive.function.RouterFunction;
import org.springframework.web.reactive.function.RouterFunctions;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.HttpServer;

/**
 * @author NAVER
 *         https://spring.io/blog/2016/10/05/spring-tips-functional-reactive-
 *         endpoints-with-spring-framework-5-0
 */
@SpringBootApplication
public class SpringBootTestApplication {
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

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestApplication.class, args);
	}
}

@Component
class PersonHandler {
	@Autowired
	private PersonRepository personRepository;

	Response<Flux<Person>> all(Request request) {
		Flux<Person> flux = Flux.fromStream(personRepository.all());
		return Response.ok().body(BodyInserters.fromPublisher(flux, Person.class));

	}

	Response<Mono<Person>> byId(Request request) {
		Optional<String> optional = request.pathVariable("id");
		return optional.map(id -> personRepository.findById(Long.parseLong(id))).map(person -> Mono.fromFuture(person))
				.map(mono -> Response.ok().body(BodyInserters.fromPublisher(mono, Person.class)))
				.orElseThrow(() -> new IllegalStateException("Oops!!"));
	}
}

@Component
class SampleDataCLR implements CommandLineRunner {
	@Autowired
	private PersonRepository personRepository;

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		Stream.of("Stephane Maldini", "Arjen Poutsma", "Rossen Stoyanchev", "Sebastien Deleuze", "Josh Long")
				.forEach(name -> personRepository.save(new Person(name, new Random().nextInt(100))));

		personRepository.findAll().forEach(System.out::println);
	}
}

interface PersonRepository extends JpaRepository<Person, Long> {

	CompletableFuture<Person> findById(Long id);

	@Query("select p from Person p")
	Stream<Person> all();
}

@Entity
class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;
	@Column
	private int age;

	public Person() {

	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + "}";
	}

}