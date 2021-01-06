package hu.mik.prog5.reactive.repository;

import hu.mik.prog5.reactive.document.Book;
import hu.mik.prog5.reactive.document.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
	
	Flux<Movie> findByTitleContaining(String title);
	
}
