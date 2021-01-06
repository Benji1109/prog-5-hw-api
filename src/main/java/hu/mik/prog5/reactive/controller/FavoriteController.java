package hu.mik.prog5.reactive.controller;

import hu.mik.prog5.reactive.configuration.jwt.model.MessageResponse;
import hu.mik.prog5.reactive.document.*;
import hu.mik.prog5.reactive.models.Favorites;
import hu.mik.prog5.reactive.repository.UserRepository;
import hu.mik.prog5.reactive.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {
	
	private final UserDetailsServiceImpl userDetailsService;
	private final UserRepository userRepository;
	private final MovieService moviesService;
	private final SeriesService seriesService;
	private final BookService bookService;
	
	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Favorites> getFavorites() {
		
		UserDetails userDetails =
			(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUsername = this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
		Favorites favorites = new Favorites();
		favorites.setBooks(byUsername.getBooks());
		favorites.setMovies(byUsername.getMovies());
		favorites.setSeries(byUsername.getSeries());
		
		return ResponseEntity.ok(favorites);
	}
	
	@PostMapping("/movie/{id}")
	@PreAuthorize("hasRole('USER')")
	public Mono<ResponseEntity<MessageResponse>> favoriteMovie(@PathVariable("id") String id) {
		
		UserDetails userDetails =
			(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUsername = this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
		
		return this.moviesService.findById(id)
			.doOnSuccess((movie) -> {
				Set<Movie> movies = byUsername.getMovies();
				movies.add(movie);
				byUsername.setMovies(movies);
				this.userRepository.save(byUsername);
			}).thenReturn(ResponseEntity.ok(new MessageResponse("Movie added to favorite!")));
	}
	
	
	@PostMapping("/series/{id}")
	@PreAuthorize("hasRole('USER')")
	public Mono<ResponseEntity<MessageResponse>> favoriteSeries(@PathVariable("id") String id) {
		
		UserDetails userDetails =
			(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUsername = this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
		
		return this.seriesService.findById(id)
			.doOnSuccess((series) -> {
				Set<Series> seriesSet = byUsername.getSeries();
				seriesSet.add(series);
				byUsername.setSeries(seriesSet);
				this.userRepository.save(byUsername);
			}).thenReturn(ResponseEntity.ok(new MessageResponse("Series added to favorite!")));
	}
	
	
	@PostMapping("/book/{id}")
	@PreAuthorize("hasRole('USER')")
	public Mono<ResponseEntity<MessageResponse>> favoriteBook(@PathVariable("id") String id) {
		
		UserDetails userDetails =
			(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUsername = this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
		
		return this.bookService.findById(id)
			.doOnSuccess((book) -> {
				Set<Book> books = byUsername.getBooks();
				books.add(book);
				byUsername.setBooks(books);
				this.userRepository.save(byUsername);
			}).thenReturn(ResponseEntity.ok(new MessageResponse("Book added to favorite!")));
	}
	
	@DeleteMapping("/movie/{id}")
	@PreAuthorize("hasRole('USER')")
	public Mono<ResponseEntity<MessageResponse>> unFavoriteMovie(@PathVariable("id") String id) {
		UserDetails userDetails =
			(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUsername = this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
		
		Set<Movie> filteredMovies = byUsername.getMovies().stream().filter(movie -> !movie.getId().equalsIgnoreCase(id)).collect(Collectors.toSet());
		byUsername.setMovies(filteredMovies);
		this.userRepository.save(byUsername);
		return Mono.just(ResponseEntity.ok(new MessageResponse("Movie added to favorite!")));
	}
	
	
	@DeleteMapping("/series/{id}")
	@PreAuthorize("hasRole('USER')")
	public Mono<ResponseEntity<MessageResponse>> unFavoriteSeries(@PathVariable("id") String id) {
		UserDetails userDetails =
			(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUsername = this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
		
		Set<Series> filteredSeries = byUsername.getSeries().stream().filter(series -> !series.getId().equalsIgnoreCase(id)).collect(Collectors.toSet());
		byUsername.setSeries(filteredSeries);
		this.userRepository.save(byUsername);
		return Mono.just(ResponseEntity.ok(new MessageResponse("Movie added to favorite!")));
	}
	
	
	@DeleteMapping("/book/{id}")
	@PreAuthorize("hasRole('USER')")
	public Mono<ResponseEntity<MessageResponse>> unFavoriteBook(@PathVariable("id") String id) {
		UserDetails userDetails =
			(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUsername = this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
		
		Set<Book> filteredBooks = byUsername.getBooks().stream().filter(book -> !book.getId().equalsIgnoreCase(id)).collect(Collectors.toSet());
		byUsername.setBooks(filteredBooks);
		this.userRepository.save(byUsername);
		return Mono.just(ResponseEntity.ok(new MessageResponse("Movie added to favorite!")));
	}
	
}
