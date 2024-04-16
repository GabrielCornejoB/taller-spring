package com.gcornejo.springplayground.domain.repositories;

import com.gcornejo.springplayground.domain.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
