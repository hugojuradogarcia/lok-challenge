package com.lok.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection = "addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotBlank(message="Please provide a street.")
    @Size(max = 50, message = "The street must contain less than 50 characters.")
    private String street;

    @NotBlank(message="Please provide a neighborhood.")
    @Size(max = 50, message = "The neighborhood must contain less than 50 characters.")
    private String neighborhood;

    @NotBlank(message="Please provide a state.")
    @Size(max = 50, message = "The state must contain less than 50 characters.")
    private String state;

    @NotBlank(message="Please provide a country.")
    @Size(max = 50, message = "The country must contain less than 50 characters.")
    private String country;
}
