package hu.mik.prog5.reactive.repository;

import hu.mik.prog5.reactive.document.Movie;
import hu.mik.prog5.reactive.document.Series;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SeriesRepository extends ReactiveMongoRepository<Series, String> {
	
	Flux<Series> findByTitleContaining(String title);
	
}
