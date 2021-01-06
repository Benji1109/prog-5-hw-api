package hu.mik.prog5.reactive.service;

import hu.mik.prog5.reactive.document.Book;
import hu.mik.prog5.reactive.document.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

    Mono<Movie> save(Movie movie);

    Mono<Movie> findById(String id);
    
    Flux<Movie> findByTitleContaining(String title);
    
    Mono<Void> delete(String id);
    
    Flux<Movie> listAll();
    
}
