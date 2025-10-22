package com.i9systemas.bookstore.service;

import com.i9systemas.bookstore.dto.BookRequestDTO;
import com.i9systemas.bookstore.dto.BookResponseDTO;
import com.i9systemas.bookstore.exception.BookNotFoundException;
import com.i9systemas.bookstore.model.Book;
import com.i9systemas.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookResponseDTO create(BookRequestDTO requestDTO) {
        Book book = new Book();
        book.setTitulo(requestDTO.getTitulo());
        book.setAutor(requestDTO.getAutor());
        book.setIsbn(requestDTO.getIsbn());
        book.setAnoPublicacao(requestDTO.getAnoPublicacao());

        Book savedBook = bookRepository.save(book);
        return BookResponseDTO.fromEntity(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookResponseDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return BookResponseDTO.fromEntity(book);
    }

    @Transactional
    public BookResponseDTO update(Long id, BookRequestDTO requestDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setTitulo(requestDTO.getTitulo());
        book.setAutor(requestDTO.getAutor());
        book.setIsbn(requestDTO.getIsbn());
        book.setAnoPublicacao(requestDTO.getAnoPublicacao());

        Book updatedBook = bookRepository.save(book);
        return BookResponseDTO.fromEntity(updatedBook);
    }

    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
}