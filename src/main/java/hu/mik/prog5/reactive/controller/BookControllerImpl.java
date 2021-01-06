package hu.mik.prog5.reactive.controller;

import hu.mik.prog5.reactive.document.Book;
import hu.mik.prog5.reactive.service.BookService;
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
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookControllerImpl {
	
	private final BookService bookService;
	
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public Mono<Book> save(@Valid @RequestBody Book book) {
		return this.bookService.save(book);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('USER')")
	public Flux<Book> listAll(@RequestParam(required = false) String title) {
		if(!StringUtils.isNotEmpty(title)) {
			this.bookService.findByTitleContaining(title);
		}
		return this.bookService.listAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('USER')")
	public Mono<Book> findById(@PathVariable("id") String id) {
		return this.bookService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<Void> delete(@PathVariable("id") String id) {
		return this.bookService.delete(id);
	}
	
}
