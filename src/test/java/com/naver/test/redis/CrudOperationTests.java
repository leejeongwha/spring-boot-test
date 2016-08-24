package com.naver.test.redis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.naver.test.redis.person.domain.Address;
import com.naver.test.redis.person.domain.Person;
import com.naver.test.redis.person.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CrudOperationTests {
	@Autowired
	private PersonRepository personRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Person person = new Person();
		person.setFirstname("Lee");
		person.setLastname("JeongWha");

		Address address = new Address();
		address.setCountry("Korea");
		address.setCity("SungNam");
		person.setAddress(address);

		personRepository.save(person);

		Person findOne = personRepository.findOne(person.getId());

		System.out.println(findOne.toString());

		long count = personRepository.count();

		System.out.println("Stored Domain Object Count : " + count);

		// personRepository.delete(person);
	}

}
