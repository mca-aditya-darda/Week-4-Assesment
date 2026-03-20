package com.capgemini.model.controller;

import com.capgemini.model.entity.Book;
import com.capgemini.model.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping
	public Map<String, Object> addBook(@RequestBody Book book) {
		return bookService.addBook(book);
	}

	@GetMapping("/{id}")
	public Book getBookById(@PathVariable int id) {
		return bookService.getBookById(id);
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@PutMapping("/{id}")
	public Map<String, String> updateBook(@PathVariable int id, @RequestBody Book book) {
		return bookService.updateBook(id, book);
	}

	@DeleteMapping("/{id}")
	public Map<String, String> deleteBook(@PathVariable int id) {
		return bookService.deleteBook(id);
	}

	@PostMapping("/borrow/{id}")
	public Map<String, String> borrowBook(@PathVariable int id) {
		return bookService.borrowBook(id);
	}

	@PostMapping("/return/{id}")
	public Map<String, String> returnBook(@PathVariable int id) {
		return bookService.returnBook(id);
	}
}