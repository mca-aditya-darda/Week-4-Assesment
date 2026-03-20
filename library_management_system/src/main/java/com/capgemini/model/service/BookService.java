package com.capgemini.model.service;

import com.capgemini.model.entity.Book;
import com.capgemini.model.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Map<String, Object> addBook(Book book) {

		if (book.getAvailableCopies() < 0) {
			throw new RuntimeException("Available copies cannot be negative");
		}

		book.setBorrowedCopies(0);

		Book saved = bookRepository.save(book);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Book added successfully");
		response.put("bookId", saved.getId());

		return response;
	}

	public Book getBookById(int id) {
		return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Map<String, String> updateBook(int id, Book updatedBook) {

		Book existing = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

		if (updatedBook.getAvailableCopies() < 0) {
			throw new RuntimeException("Available copies cannot be negative");
		}

		existing.setTitle(updatedBook.getTitle());
		existing.setAuthor(updatedBook.getAuthor());
		existing.setAvailableCopies(updatedBook.getAvailableCopies());

		bookRepository.save(existing);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Book updated successfully");

		return response;
	}

	public Map<String, String> deleteBook(int id) {

		Book existing = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

		bookRepository.delete(existing);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Book deleted successfully");

		return response;
	}

	public Map<String, String> borrowBook(int id) {

		Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

		if (book.getAvailableCopies() == 0) {
			throw new RuntimeException("No copies available");
		}

		book.setAvailableCopies(book.getAvailableCopies() - 1);
		book.setBorrowedCopies(book.getBorrowedCopies() + 1);

		bookRepository.save(book);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Book borrowed successfully");

		return response;
	}

	public Map<String, String> returnBook(int id) {

		Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

		if (book.getBorrowedCopies() == 0) {
			throw new RuntimeException("No borrowed books to return");
		}

		book.setBorrowedCopies(book.getBorrowedCopies() - 1);
		book.setAvailableCopies(book.getAvailableCopies() + 1);

		bookRepository.save(book);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Book returned successfully");

		return response;
	}
}