package hu.mik.prog5.reactive.service;

import hu.mik.prog5.reactive.document.Book;
import hu.mik.prog5.reactive.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	
	private final BookRepository bookRepository;
	
	@Override
	public Mono<Book> save(Book book) {
		return this.bookRepository.save(book);
	}
	
	@Override
	public Mono<Book> findById(String id) {
		return this.bookRepository.findById(id);
	}
	
	@Override
	public Flux<Book> listAll() {
		return this.bookRepository.findAll();
	}
	
	@Override
	public Flux<Book> findByTitleContaining(String title) {
		return this.bookRepository.findByTitleContaining(title);
	}
	
	@Override
	public Mono<Void> delete(String id) {
		return this.bookRepository.deleteById(id);
	}
	
	
}
