package com.lok.api.service;

import com.lok.api.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService{
    ResponseEntity<List<User>> findAll();
    ResponseEntity<User> save(User user);
    ResponseEntity<User> findById(String id);
    ResponseEntity<Void> delete(String id);
    ResponseEntity<Void> update(String id, User user);
}
