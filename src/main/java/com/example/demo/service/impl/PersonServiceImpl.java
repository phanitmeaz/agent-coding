package com.example.demo.service.impl;

import com.example.demo.dto.request.PersonRequest;
import com.example.demo.dto.response.PersonResponse;
import com.example.demo.entity.Person;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;
	private final PersonMapper personMapper;

	@Override
	@Transactional
	public PersonResponse create(PersonRequest request) {
		log.debug("Creating person with email {}", request.getEmail());
		if (personRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email already in use: " + request.getEmail());
		}
		Person person = personMapper.toEntity(request);
		if (person.getIsActive() == null) {
			person.setIsActive(true);
		}
		person.setCreatedAt(LocalDateTime.now());
		return personMapper.toResponse(personRepository.save(person));
	}

	@Override
	@Transactional(readOnly = true)
	public PersonResponse getById(Integer personId) {
		log.debug("Fetching person with id {}", personId);
		return personRepository.findById(personId)
			.map(personMapper::toResponse)
			.orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personId));
	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonResponse> getAll() {
		log.debug("Fetching all persons");
		return personRepository.findAll()
			.stream()
			.map(personMapper::toResponse)
			.toList();
	}

	@Override
	@Transactional
	public PersonResponse update(Integer personId, PersonRequest request) {
		log.debug("Updating person with id {}", personId);
		Person person = personRepository.findById(personId)
			.orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personId));
		if (personRepository.existsByEmailAndPersonIdNot(request.getEmail(), personId)) {
			throw new DuplicateResourceException("Email already in use: " + request.getEmail());
		}
		person.setFirstName(request.getFirstName());
		person.setLastName(request.getLastName());
		person.setEmail(request.getEmail());
		person.setDateOfBirth(request.getDateOfBirth());
		if (request.getIsActive() != null) {
			person.setIsActive(request.getIsActive());
		}
		return personMapper.toResponse(personRepository.save(person));
	}

	@Override
	@Transactional
	public void delete(Integer personId) {
		log.debug("Deleting person with id {}", personId);
		if (!personRepository.existsById(personId)) {
			throw new ResourceNotFoundException("Person not found with id: " + personId);
		}
		personRepository.deleteById(personId);
	}

}
