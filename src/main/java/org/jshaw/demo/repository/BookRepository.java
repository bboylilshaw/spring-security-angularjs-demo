package org.jshaw.demo.repository;

import org.jshaw.demo.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    Iterable<Book> findAll(Sort sort);

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    Page<Book> findAll(Pageable pageable);

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    Book findOne(Long aLong);

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    Iterable<Book> findAll();

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    Iterable<Book> findAll(Iterable<Long> longs);

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    long count();
}
