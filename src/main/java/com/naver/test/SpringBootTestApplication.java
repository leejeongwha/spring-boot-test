package com.naver.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.naver.test.movie.domain.Movie;
import com.naver.test.movie.domain.User;
import com.naver.test.movie.repository.MovieRepository;
import com.naver.test.movie.repository.UserRepository;

@SpringBootApplication
public class SpringBootTestApplication implements ApplicationRunner {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		String[] fileNames = { "u.item", "u.user" };
		for (String fileName : fileNames) {
			ClassPathResource resource = new ClassPathResource(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
			String line;
			while ((line = br.readLine()) != null) {
				if (StringUtils.equals(fileName, "u.item")) {
					insertMovie(line);
				} else if (StringUtils.equals(fileName, "u.user")) {
					insertUser(line);
				}
			}
			br.close();
		}
	}

	private void insertMovie(String line) throws ParseException {
		String[] values = line.split("\\|", -1);
		Movie movie = new Movie();
		movie.setId(Integer.parseInt(values[0]));
		movie.setTitle(values[1]);
		if (StringUtils.isNotEmpty(values[2])) {
			movie.setReleaseDate(new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(values[2]));
		}

		movieRepository.save(movie);
	}

	private void insertUser(String line) throws ParseException {
		String[] values = line.split("\\|", -1);
		User user = new User();
		user.setId(Integer.parseInt(values[0]));
		user.setAge(Integer.parseInt(values[1]));
		user.setGender(values[2]);
		user.setOccupation(values[3]);

		userRepository.save(user);
	}
}
