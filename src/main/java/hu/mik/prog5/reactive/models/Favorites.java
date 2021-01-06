package hu.mik.prog5.reactive.models;

import hu.mik.prog5.reactive.document.Book;
import hu.mik.prog5.reactive.document.Movie;
import hu.mik.prog5.reactive.document.Series;
import lombok.Data;

import java.util.Set;

@Data
public class Favorites {
	
	private Set<Movie> movies;
	private Set<Series> series;
	private Set<Book> books;
	
}
