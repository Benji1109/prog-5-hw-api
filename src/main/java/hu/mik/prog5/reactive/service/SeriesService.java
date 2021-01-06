package hu.mik.prog5.reactive.service;

import hu.mik.prog5.reactive.document.Movie;
import hu.mik.prog5.reactive.document.Series;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SeriesService {

    Mono<Series> save(Series series);

    Mono<Series> findById(String id);
    
    Flux<Series> findByTitleContaining(String title);
    
    Mono<Void> delete(String id);
    
    Flux<Series> listAll();
    
}
