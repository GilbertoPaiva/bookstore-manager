package com.i9systemas.bookstore.service;

import com.i9systemas.bookstore.dto.BookRequestDTO;
import com.i9systemas.bookstore.dto.BookResponseDTO;
import com.i9systemas.bookstore.exception.BookNotFoundException;
import com.i9systemas.bookstore.model.Book;
import com.i9systemas.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService - Testes Unitários")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookRequestDTO bookRequestDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitulo("Clean Code");
        book.setAutor("Robert C. Martin");
        book.setIsbn("0132350884");
        book.setAnoPublicacao(2008);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());

        bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setTitulo("Clean Code");
        bookRequestDTO.setAutor("Robert C. Martin");
        bookRequestDTO.setIsbn("0132350884");
        bookRequestDTO.setAnoPublicacao(2008);
    }

    @Test
    @DisplayName("Deve criar um livro com sucesso")
    void deveCriarLivroComSucesso() {
        // Given
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        BookResponseDTO response = bookService.create(bookRequestDTO);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitulo()).isEqualTo("Clean Code");
        assertThat(response.getAutor()).isEqualTo("Robert C. Martin");
        assertThat(response.getIsbn()).isEqualTo("0132350884");
        assertThat(response.getAnoPublicacao()).isEqualTo(2008);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve buscar todos os livros")
    void deveBuscarTodosOsLivros() {
        // Given
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitulo("Design Patterns");
        book2.setAutor("Erich Gamma");
        book2.setIsbn("0201633612");
        book2.setAnoPublicacao(1994);
        book2.setCreatedAt(LocalDateTime.now());
        book2.setUpdatedAt(LocalDateTime.now());

        List<Book> books = Arrays.asList(book, book2);
        when(bookRepository.findAll()).thenReturn(books);

        // When
        List<BookResponseDTO> response = bookService.findAll();

        // Then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getTitulo()).isEqualTo("Clean Code");
        assertThat(response.get(1).getTitulo()).isEqualTo("Design Patterns");

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há livros")
    void deveRetornarListaVaziaQuandoNaoHaLivros() {
        // Given
        when(bookRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<BookResponseDTO> response = bookService.findAll();

        // Then
        assertThat(response).isNotNull();
        assertThat(response).isEmpty();

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar livro por ID com sucesso")
    void deveBuscarLivroPorIdComSucesso() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        BookResponseDTO response = bookService.findById(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitulo()).isEqualTo("Clean Code");

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando livro não for encontrado por ID")
    void deveLancarExcecaoQuandoLivroNaoForEncontradoPorId() {
        // Given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> bookService.findById(999L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Livro não encontrado com o ID: 999");

        verify(bookRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve atualizar livro com sucesso")
    void deveAtualizarLivroComSucesso() {
        // Given
        BookRequestDTO updateDTO = new BookRequestDTO();
        updateDTO.setTitulo("Clean Code - Updated");
        updateDTO.setAutor("Robert C. Martin");
        updateDTO.setIsbn("0132350884");
        updateDTO.setAnoPublicacao(2008);

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitulo("Clean Code - Updated");
        updatedBook.setAutor("Robert C. Martin");
        updatedBook.setIsbn("0132350884");
        updatedBook.setAnoPublicacao(2008);
        updatedBook.setCreatedAt(book.getCreatedAt());
        updatedBook.setUpdatedAt(LocalDateTime.now());

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // When
        BookResponseDTO response = bookService.update(1L, updateDTO);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitulo()).isEqualTo("Clean Code - Updated");

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar livro inexistente")
    void deveLancarExcecaoAoTentarAtualizarLivroInexistente() {
        // Given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> bookService.update(999L, bookRequestDTO))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Livro não encontrado com o ID: 999");

        verify(bookRepository, times(1)).findById(999L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve deletar livro com sucesso")
    void deveDeletarLivroComSucesso() {
        // Given
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        // When
        bookService.delete(1L);

        // Then
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar livro inexistente")
    void deveLancarExcecaoAoTentarDeletarLivroInexistente() {
        // Given
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        // When/Then
        assertThatThrownBy(() -> bookService.delete(999L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Livro não encontrado com o ID: 999");

        verify(bookRepository, times(1)).existsById(999L);
        verify(bookRepository, never()).deleteById(anyLong());
    }
}

