package com.example.demo.controller;

import com.example.demo.BooksApplication;
import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    public final BookServiceImpl bookService;

    @Autowired
    public BookController(BookServiceImpl bookService){
        this.bookService=bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookDTO>> getAll()
    {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable Long id){
        return bookService.getBookById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    //OTHER FUNCTION

    @PostMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestBody BookDTO searchCriteria) {
        List<BookDTO> searchResults = bookService.searchBooks(searchCriteria);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<BookDTO>> getAllBooksSorted(@RequestParam(defaultValue = "title") String sortBy,
                                                           @RequestParam(defaultValue = "true") boolean ascending) {
        List<BookDTO> sortedBooks = bookService.getAllBooksSorted(sortBy, ascending);
        return ResponseEntity.ok(sortedBooks);
    }
}
