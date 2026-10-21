package com.example.demo.mapper;

import com.example.demo.dto.request.PersonRequest;
import com.example.demo.dto.response.PersonResponse;
import com.example.demo.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

	public Person toEntity(PersonRequest request) {
		return Person.builder()
			.firstName(request.getFirstName())
			.lastName(request.getLastName())
			.email(request.getEmail())
			.dateOfBirth(request.getDateOfBirth())
			.isActive(request.getIsActive())
			.build();
	}

	public PersonResponse toResponse(Person person) {
		return PersonResponse.builder()
			.personId(person.getPersonId())
			.firstName(person.getFirstName())
			.lastName(person.getLastName())
			.email(person.getEmail())
			.dateOfBirth(person.getDateOfBirth())
			.isActive(person.getIsActive())
			.createdAt(person.getCreatedAt())
			.build();
	}

}
