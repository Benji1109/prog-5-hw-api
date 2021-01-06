package hu.mik.prog5.reactive.repository;

import hu.mik.prog5.reactive.document.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
	
	Flux<Book> findByTitleContaining(String title);
	
}
