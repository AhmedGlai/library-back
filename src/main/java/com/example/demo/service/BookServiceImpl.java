package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.implementation.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> modelMapper.map(book,BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDTO> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(book1 -> modelMapper.map(book1,BookDTO.class));
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPrice(bookDTO.getPrice());
            Book updatedBook = bookRepository.save(book);
            return modelMapper.map(updatedBook, BookDTO.class);
        } else {
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }
    }


    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO,Book.class);
        Book bookSaved = bookRepository.save(book);
        return modelMapper.map(bookSaved,BookDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


    //OTHER FUNCTIONS


    @Override
    public List<BookDTO> searchBooks(BookDTO searchCriteria) {
        Book bookExample = modelMapper.map(searchCriteria, Book.class);
        ExampleMatcher matcher = ExampleMatcher.matchingAny().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Book> example = Example.of(bookExample, matcher);
        List<Book> matchedBooks = bookRepository.findAll(example);
        return matchedBooks.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getAllBooksSorted(String sortBy, boolean ascending) {
        Sort.Direction sortDirection = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(sortDirection, sortBy);
        List<Book> books = bookRepository.findAll(sort);
        return books.stream().map(book -> modelMapper.map(book,BookDTO.class)).collect(Collectors.toList());

    }


}