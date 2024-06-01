package com.example.h2.crudh2lombock.Repository;

import com.example.h2.crudh2lombock.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
