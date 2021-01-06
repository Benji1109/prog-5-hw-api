package hu.mik.prog5.reactive.controller;

import hu.mik.prog5.reactive.document.Book;
import hu.mik.prog5.reactive.document.Movie;
import hu.mik.prog5.reactive.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieControllerImpl {
	
	private final MovieService movieService;
	
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public Mono<Movie> save(@Valid @RequestBody Movie movie) {
		return this.movieService.save(movie);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('USER')")
	public Flux<Movie> listAll(@RequestParam(required = false) String title) {
		if(!StringUtils.isNotEmpty(title)) {
			this.movieService.findByTitleContaining(title);
		}
		return this.movieService.listAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('USER')")
	public Mono<Movie> findById(@PathVariable("id") String id) {
		return this.movieService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<Void> delete(@PathVariable("id") String id) {
		return this.movieService.delete(id);
	}
	
}
