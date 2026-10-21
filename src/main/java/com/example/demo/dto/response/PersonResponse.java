package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {

	private Integer personId;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate dateOfBirth;
	private Boolean isActive;
	private LocalDateTime createdAt;

}
