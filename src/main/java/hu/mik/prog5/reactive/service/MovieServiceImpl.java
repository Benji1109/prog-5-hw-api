package hu.mik.prog5.reactive.service;

import hu.mik.prog5.reactive.repository.MovieRepository;
import hu.mik.prog5.reactive.document.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
	
	private final MovieRepository movieRepository;
	
	@Override
	public Mono<Movie> save(Movie movie) {
		return this.movieRepository.save(movie);
	}
	
	@Override
	public Mono<Movie> findById(String id) {
		return this.movieRepository.findById(id);
	}
	
	@Override
	public Flux<Movie> findByTitleContaining(String title) {
		return this.movieRepository.findByTitleContaining(title);
	}
	
	@Override
	public Flux<Movie> listAll() {
		return this.movieRepository.findAll();
	}
	
	@Override
	public Mono<Void> delete(String id) {
		return this.movieRepository.deleteById(id);
	}
	
	
}
