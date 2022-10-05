package com.learnjava.apiclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.util.CommonUtil;

class MoviesClientTest {

	WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/movies").build();

	MoviesClient moviesClient = new MoviesClient(webClient);

	@RepeatedTest(10)
	void testRetrieveMovie() {
		CommonUtil.startTimer();
		var movieInfoId = 1L;
		Movie movie = moviesClient.retrieveMovie(movieInfoId);
		CommonUtil.timeTaken();

		assertNotNull(movie);
		assertEquals("Batman Begins", movie.getMovieInfo().getName());
		assertEquals(1, movie.getReviewList().size());
	}

	@RepeatedTest(10)
	void testRetrieveMovieCF() {
		CommonUtil.startTimer();
		var movieInfoId = 1L;
		Movie movie = moviesClient.retrieveMovieCF(movieInfoId).join();
		CommonUtil.timeTaken();

		assertNotNull(movie);
		assertEquals("Batman Begins", movie.getMovieInfo().getName());
		assertEquals(1, movie.getReviewList().size());
	}
	
	@RepeatedTest(10)
	void testRetrieveMovies() {
		CommonUtil.startTimer();
		var movieInfoIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);
		List<Movie> movies = moviesClient.retrieveMovies(movieInfoIds);
		CommonUtil.timeTaken();

		assertNotNull(movies);
		assertEquals(7, movies.size());
	}
	
	@RepeatedTest(10)
	void testRetrieveMoviesCF() {
		CommonUtil.startTimer();
		var movieInfoIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);
		List<Movie> movies = moviesClient.retrieveMoviesCF(movieInfoIds);
		CommonUtil.timeTaken();

		assertNotNull(movies);
		assertEquals(7, movies.size());
	}

}
