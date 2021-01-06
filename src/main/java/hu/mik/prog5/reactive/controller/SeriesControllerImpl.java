package hu.mik.prog5.reactive.controller;

import hu.mik.prog5.reactive.document.Movie;
import hu.mik.prog5.reactive.document.Series;
import hu.mik.prog5.reactive.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
public class SeriesControllerImpl {
	
	private final SeriesService seriesService;
	
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public Mono<Series> save(@Valid @RequestBody Series series) {
		return this.seriesService.save(series);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('USER')")
	public Flux<Series> listAll(@RequestParam(required = false) String title) {
		if(!StringUtils.isNotEmpty(title)) {
			this.seriesService.findByTitleContaining(title);
		}
		return this.seriesService.listAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('USER')")
	public Mono<Series> findById(@PathVariable("id") String id) {
		return this.seriesService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<Void> delete(@PathVariable("id") String id) {
		return this.seriesService.delete(id);
	}
	
}
