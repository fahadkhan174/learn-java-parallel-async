package com.learnjava.apiclient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;

public class MoviesClient {

	private final WebClient webClient;

	public MoviesClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public Movie retrieveMovie(Long movieInfoId) {
		// movieInfo
		MovieInfo movieInfo = invokeMovieInfoService(movieInfoId);
		// reviewData
		List<Review> reviews = invokeReviewService(movieInfoId);

		return new Movie(movieInfo, reviews);
	}
	
	public List<Movie> retrieveMovies(List<Long> movieInfoIds){
		return movieInfoIds.stream()
				.map(this::retrieveMovie)
				.collect(Collectors.toList());
	}
	
	public List<Movie> retrieveMoviesCF(List<Long> movieInfoIds){
		var moviesCF = movieInfoIds.stream()
				.map(this::retrieveMovieCF)
				.collect(Collectors.toList());
		
		return moviesCF.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());
		
	}
	
	public CompletableFuture<Movie> retrieveMovieCF(Long movieInfoId) {
		var movieInfoCF = CompletableFuture.supplyAsync(()-> invokeMovieInfoService(movieInfoId));
		var reviewsCF = CompletableFuture.supplyAsync(()-> invokeReviewService(movieInfoId));
		
		return movieInfoCF.thenCombine(reviewsCF, Movie::new);
	}

	private MovieInfo invokeMovieInfoService(Long movieInfoId) {
		var movieInfoUri = "/v1/movie_infos/{movieInfoId}";

		return this.webClient
				.get()
				.uri(movieInfoUri, movieInfoId)
				.retrieve()
				.bodyToMono(MovieInfo.class)
				.block();
	}

	private List<Review> invokeReviewService(Long movieInfoId) {

		var reviewUri = UriComponentsBuilder.fromUriString("/v1/reviews").queryParam("movieInfoId", movieInfoId)
				.buildAndExpand().toString();

		return this.webClient
				.get()
				.uri(reviewUri)
				.retrieve()
				.bodyToFlux(Review.class)
				.collectList()
				.block();
	}

}
