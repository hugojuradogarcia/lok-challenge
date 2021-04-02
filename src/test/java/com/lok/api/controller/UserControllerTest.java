package com.lok.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lok.api.model.Address;
import com.lok.api.model.User;
import com.lok.api.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static List<User> userList = new ArrayList<>();
    private static List<Address> addressList = new ArrayList<>();
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeAll
    public static void init() throws ParseException {
        addressList.add(new Address("Emiliano Zapata", "San Nicolas Tetelco", "Ciudad de México", "México"));
        addressList.add(new Address("Zaragoza", "Tecomitl", "Ciudad de México", "México"));
        userList.add(new User("6066273ce9f04b06832b427a", "Hugo", "Jurado", "JUGH880821RG8", format.parse("1988-08-21"), "hugojuradogarcia", "123456", addressList));
        userList.add(new User("60662e05cc6f083e8f360eff", "Karen", "Nunez Jalpa", "NUJK881229RG5", format.parse("1988-12-29"), "karenjalpa", "asdwsa", null));
    }

    @Test
    @DisplayName("GET /users Ok")
    void getUsersSuccess() throws Exception {
        // Setup our mocked service
        when(userService.findAll())
                .thenReturn(ResponseEntity.status(200).body(userList));

        // Execute the GET request
        mockMvc.perform(get("/users"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("GET /users Ok when the list is empty")
    void getUsersSuccessEmpty() throws Exception {
        // Setup our mocked service
        when(userService.findAll())
                .thenReturn(ResponseEntity.status(200).body(new ArrayList<User>(){}));

        // Execute the GET request
        mockMvc.perform(get("/users"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /user by id Ok")
    void getUserByIdSuccess() throws Exception {
        // Setup our mocked service
        when(userService.findById(userList.get(0).getId()))
                .thenReturn(ResponseEntity.status(200).body(userList.get(0)));

        // Execute the GET request
        mockMvc.perform(get("/users/" + userList.get(0).getId()))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.name", is(userList.get(0).getName())))
                .andExpect(jsonPath("$.surnames", is(userList.get(0).getSurnames())))
                .andExpect(jsonPath("$.rfc", is(userList.get(0).getRfc())))
                .andExpect(jsonPath("$.username", is(userList.get(0).getUsername())))
                .andExpect(jsonPath("$.password", is(userList.get(0).getPassword())))
                .andExpect(jsonPath("$.addresses", hasSize(2)));
    }

    @Test
    @DisplayName("GET /user by id NotFound")
    void getUserByIdNotFound() throws Exception {
        // Setup our mocked service
        when(userService.findById(userList.get(0).getId()))
                .thenReturn(ResponseEntity.status(404).build());

        // Execute the GET request
        mockMvc.perform(get("/users/" + userList.get(0).getId()))
                // Validate the response code and content type
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /user by id NoContent")
    void deleteUserByIdNoContent() throws Exception {
        // Setup our mocked service
        when(userService.delete(userList.get(0).getId()))
                .thenReturn(ResponseEntity.status(204).build());

        // Execute the DELETE request
        mockMvc.perform(delete("/users/" + userList.get(0).getId()))
                // Validate the response code and content type
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /user caused by id NotFound")
    void deleteUserByIdNotFound() throws Exception {
        // Setup our mocked service
        when(userService.delete(userList.get(0).getId()))
                .thenReturn(ResponseEntity.status(404).build());

        // Execute the DELETE request
        mockMvc.perform(delete("/users/" + userList.get(0).getId()))
                // Validate the response code and content type
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /user NotFound")
    void updateUserNotFound() throws Exception {
        // Setup our mocked service
        when(userService.update(Mockito.any(), Mockito.any()))
                .thenReturn(ResponseEntity.status(404).build());

        // Execute the PUT request
        mockMvc.perform(put("/users/" + userList.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userList.get(0))))
                // Validate the response code and content type
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /user BadRequest caused by ids equals")
    void updateUserBadRequest() throws Exception {
        // Setup our mocked service
        when(userService.update(Mockito.any(), Mockito.any()))
                .thenReturn(ResponseEntity.status(400).build());

        // Execute the PUT request
        mockMvc.perform(put("/users/" + userList.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userList.get(0))))
                // Validate the response code and content type
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /user NoContent")
    void putUserNoContent() throws Exception {
        User user = new User();
        user.setId("606756669dde1320c7eb0b69");
        user.setName("Hugo");
        user.setSurnames("Jurado");
        user.setRfc("JUGH880821RG8");
        user.setDate_birth(format.parse("1988-08-21"));
        user.setUsername("hugojuradogarcia");
        user.setPassword("123456");
        user.setAddresses(addressList);

        // Setup our mocked service
        when(userService.update(Mockito.any(), Mockito.any()))
                .thenReturn(ResponseEntity.status(204).build());

        // Execute the PUT request
        mockMvc.perform(put("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                // Validate the response code and content type
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PUT /user BadRequest caused by NotBlank")
    void putUserBadRequestNotBlank() throws Exception {
        User user = new User();
        Address address = new Address();
        address.setCountry("México");
        user.setId("606756669dde1320c7eb0b69");
        user.setSurnames("Garcia");
        user.setRfc("JUGH880821RG8");
        user.setAddresses(new ArrayList<Address>() {{ add(address); }});
        // Setup our mocked service
        when(userService.update(Mockito.any(), Mockito.any()))
                .thenReturn(ResponseEntity.status(400).build());

        // Execute the PUT request
        mockMvc.perform(put("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                // Validate the response code and content type
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Please provide a name.")))
                .andExpect(jsonPath("$.message", containsString("Please provide a username.")))
                .andExpect(jsonPath("$.message", containsString("Please provide a password.")))
                .andExpect(jsonPath("$.message", containsString("Please provide a date with the format yyyy-MM-dd.")));
    }

    @Test
    @DisplayName("POST /user BadRequest caused by NotBlank")
    void createUserBadRequestNotBlank() throws Exception {
        User user = new User();
        Address address = new Address();
        address.setCountry("México");
        user.setSurnames("Garcia");
        user.setRfc("JUGH880821RG8");
        user.setAddresses(new ArrayList<Address>() {{ add(address); }});
        // Setup our mocked service
        when(userService.save(user))
                .thenReturn(ResponseEntity.status(400).build());

        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                // Validate the response code and content type
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Please provide a name.")))
                .andExpect(jsonPath("$.message", containsString("Please provide a username.")))
                .andExpect(jsonPath("$.message", containsString("Please provide a password.")))
                .andExpect(jsonPath("$.message", containsString("Please provide a date with the format yyyy-MM-dd.")));
    }

    @Test
    @DisplayName("POST /user BadRequest caused by Size")
    void createUserBadRequestSize() throws Exception {
        User user = new User("6066273ce9f04b06832b427a",
                "Hugo 123456789012345678901234567890123456789012345678901234567890",
                "Jurado 123456789012345678901234567890123456789012345678901234567890",
                "JUGH880821RG8",
                format.parse("1988-08-21"),
                "hugojuradogarcia 123456789012345678901234567890123456789012345678901234567890",
                "123456123456789012345678901234567890123456789012345678901234567890",
                 addressList);
        // Setup our mocked service
        when(userService.save(user))
                .thenReturn(ResponseEntity.status(400).build());

        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                // Validate the response code and content type
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("The name must contain less than 50 characters.")))
                .andExpect(jsonPath("$.message", containsString("The surnames must contain less than 50 characters.")))
                .andExpect(jsonPath("$.message", containsString("The username must contain less than 50 characters.")))
                .andExpect(jsonPath("$.message", containsString("The password must contain less than 50 characters.")));
    }

    @Test
    @DisplayName("POST /user BadRequest caused by DateFormat")
    void createUserBadRequestDateFormat() throws Exception {
        User user = new User();
        user.setId("6066273ce9f04b06832b427a");
        user.setName("Hugo");
        user.setSurnames("Jurado");
        user.setRfc("JUGH880821RG8");
        user.setDate_birth(format.parse("1988-21-08"));
        user.setUsername("hugojuradogarcia");
        user.setPassword("123456");
        user.setAddresses(addressList);

        // Setup our mocked service
        when(userService.save(Mockito.any()))
                .thenReturn(ResponseEntity.status(400).build());

        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                // Validate the response code and content type
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /user BadRequest caused by RFC invalid")
    void createUserBadRequestRegex() throws Exception {
        User user = new User();
        user.setName("Hugo");
        user.setSurnames("Jurado");
        user.setRfc("JUGH880821RG81321");
        user.setDate_birth(format.parse("1988-08-21"));
        user.setUsername("hugojuradogarcia");
        user.setPassword("123456");
        user.setAddresses(addressList);

        // Setup our mocked service
        when(userService.save(user))
                .thenReturn(ResponseEntity.status(400).build());

        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                // Validate the response code and content type
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Please provide a valid RFC.")));
    }

    @Test
    @DisplayName("POST /user Created")
    void createUserOk() throws Exception {
        User user = new User();
        user.setName("Hugo");
        user.setSurnames("Jurado");
        user.setRfc("JUGH880821RG8");
        user.setDate_birth(format.parse("1988-08-21"));
        user.setUsername("hugojuradogarcia");
        user.setPassword("123456");
        user.setAddresses(addressList);

        // Setup our mocked service
        when(userService.save(Mockito.any()))
                .thenReturn(ResponseEntity.status(201).build());

        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                // Validate the response code and content type
                .andExpect(status().isCreated());
    }
}
