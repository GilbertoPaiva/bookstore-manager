package com.i9systemas.bookstore.dto;

import com.i9systemas.bookstore.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer anoPublicacao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BookResponseDTO fromEntity(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitulo(),
                book.getAutor(),
                book.getIsbn(),
                book.getAnoPublicacao(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}