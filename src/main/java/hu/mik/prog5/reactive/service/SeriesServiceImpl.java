package hu.mik.prog5.reactive.service;

import hu.mik.prog5.reactive.document.Series;
import hu.mik.prog5.reactive.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SeriesServiceImpl implements SeriesService {
	
	private final SeriesRepository seriesRepository;
	
	@Override
	public Mono<Series> save(Series series) {
		return this.seriesRepository.save(series);
	}
	
	@Override
	public Mono<Series> findById(String id) {
		return this.seriesRepository.findById(id);
	}
	
	@Override
	public Flux<Series> findByTitleContaining(String title) {
		return this.seriesRepository.findByTitleContaining(title);
	}
	
	@Override
	public Flux<Series> listAll() {
		return this.seriesRepository.findAll();
	}
	
	@Override
	public Mono<Void> delete(String id) {
		return this.seriesRepository.deleteById(id);
	}
	
	
}
