package com.example.h2.crudh2lombock.Controller;

import com.example.h2.crudh2lombock.Repository.BookRepository;
import com.example.h2.crudh2lombock.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @GetMapping ("/getAllBooks")
    public ResponseEntity <List<Book>> getAllBooks(){
        try{
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().forEach(bookList::add);

        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> bookData = bookRepository.findById(id);

        if (bookData.isPresent()) {
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping ("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book bookObject = bookRepository.save(book);

        return new ResponseEntity<>(bookObject, HttpStatus.OK);
    }
    @PostMapping("/updateBookById/{id}")
    public ResponseEntity updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {
        Optional<Book> oldBookData = bookRepository.findById(id);

        if (oldBookData.isPresent()) {
            Book updatedBookData = oldBookData.get();
            updatedBookData.setTitle(newBookData.getTitle());
            updatedBookData.setAuthor(newBookData.getAuthor());

            Book bookObject = bookRepository.save(updatedBookData);
            return new ResponseEntity<>(bookObject, HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping ("/deleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBookById (@PathVariable Long id) {
        bookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
