package org.jshaw.demo.repository;

import org.jshaw.demo.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Jason on 6/12/15.
 */

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {
}
