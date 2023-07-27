package com.example.demo.service.implementation;
import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;

import java.util.List;
import java.util.Optional;


public interface BookService {
    List<BookDTO> getAllBooks();

    Optional<BookDTO> getBookById(Long id);

    BookDTO updateBook(Long id, BookDTO bookDTO);

    BookDTO createBook(BookDTO bookDTO);

    void deleteBook(Long id);


    //OTHER FUNCTIONS
    List<BookDTO> searchBooks(BookDTO searchCriteria);

    List<BookDTO> getAllBooksSorted(String sortBy, boolean ascending);

}

