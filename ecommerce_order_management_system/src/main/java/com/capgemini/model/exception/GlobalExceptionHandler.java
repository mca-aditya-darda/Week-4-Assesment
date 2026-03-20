package com.capgemini.model.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> bad(BadRequestException ex) {
		return ResponseEntity.badRequest()
				.body(Map.of("status", 400, "error", "Bad Request", "message", ex.getMessage()));
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<?> duplicate(DuplicateResourceException ex) {
		return ResponseEntity.status(409).body(Map.of("status", 409, "error", "Conflict", "message", ex.getMessage()));
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> notFound(ResourceNotFoundException ex) {
		return ResponseEntity.status(404).body(Map.of("status", 404, "error", "Not Found", "message", ex.getMessage()));
	}

	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ResponseEntity<?> conflict() {
		return ResponseEntity.status(409).body(Map.of("status", 409, "error", "Conflict", "message",
				"Order was modified by another request. Please retry."));
	}
}