package org.jshaw.demo.repository;

import org.jshaw.demo.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
