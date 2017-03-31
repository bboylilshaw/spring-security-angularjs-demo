package org.jshaw.demo.repository;

import org.jshaw.demo.domain.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}
