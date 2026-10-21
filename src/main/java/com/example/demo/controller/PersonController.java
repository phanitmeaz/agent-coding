package com.example.demo.controller;

import com.example.demo.dto.request.PersonRequest;
import com.example.demo.dto.response.PersonResponse;
import com.example.demo.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

	private final PersonService personService;

	@PostMapping
	public ResponseEntity<PersonResponse> create(@Valid @RequestBody PersonRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(request));
	}

	@GetMapping("/{personId}")
	public ResponseEntity<PersonResponse> getById(@PathVariable Integer personId) {
		return ResponseEntity.ok(personService.getById(personId));
	}

	@GetMapping
	public ResponseEntity<List<PersonResponse>> getAll() {
		return ResponseEntity.ok(personService.getAll());
	}

	@PutMapping("/{personId}")
	public ResponseEntity<PersonResponse> update(@PathVariable Integer personId,
		@Valid @RequestBody PersonRequest request) {
		return ResponseEntity.ok(personService.update(personId, request));
	}

	@DeleteMapping("/{personId}")
	public ResponseEntity<Void> delete(@PathVariable Integer personId) {
		personService.delete(personId);
		return ResponseEntity.noContent().build();
	}

}
