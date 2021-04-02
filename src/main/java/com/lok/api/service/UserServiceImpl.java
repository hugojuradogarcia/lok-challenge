package com.lok.api.service;

import com.lok.api.dao.UserRepository;
import com.lok.api.model.User;
import com.lok.api.model.commons.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Utils utils;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(Utils utils, UserRepository userRepository) {
        this.utils = utils;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Override
    public ResponseEntity<User> save(User user) {
       User userSaved = userRepository.save(user);

       return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @Override
    public ResponseEntity<User> findById(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }
        return utils.RESPONSE_NOT_FOUND();
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.delete(user.get());
            return utils.RESPONSE_NO_CONTENT();
        }

        return utils.RESPONSE_NOT_FOUND();
    }

    @Override
    public ResponseEntity<Void> update(String id, User user) {
        Optional<User> userFound = userRepository.findById(id);

        if (userFound.isPresent()){
            if (id.equals(user.getId())) {
                userRepository.save(user);
                return utils.RESPONSE_NO_CONTENT();
            }
            return utils.RESPONSE_BAD_REQUEST();
        }

        return utils.RESPONSE_NOT_FOUND();
    }
}
