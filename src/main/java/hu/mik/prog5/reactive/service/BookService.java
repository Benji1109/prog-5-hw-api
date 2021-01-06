package hu.mik.prog5.reactive.service;

import hu.mik.prog5.reactive.document.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
	
	Mono<Book> save(Book book);
	
	Mono<Book> findById(String id);
	
	Flux<Book> findByTitleContaining(String title);
	
	Mono<Void> delete(String id);
	
	Flux<Book> listAll();
	
}
