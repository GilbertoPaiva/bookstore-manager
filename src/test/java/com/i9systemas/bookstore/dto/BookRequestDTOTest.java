package com.i9systemas.bookstore.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRequestDTO - Testes de Validação")
class BookRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Deve validar DTO válido sem erros")
    void deveValidarDTOValidoSemErros() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar erro quando título for nulo")
    void deveRetornarErroQuandoTituloNulo() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo(null);
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("titulo"));
    }

    @Test
    @DisplayName("Deve retornar erro quando título for vazio")
    void deveRetornarErroQuandoTituloVazio() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("titulo"));
    }

    @Test
    @DisplayName("Deve retornar erro quando título exceder 255 caracteres")
    void deveRetornarErroQuandoTituloExceder255Caracteres() {
        // Given
        String tituloLongo = "a".repeat(256);
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo(tituloLongo);
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("titulo"));
    }

    @Test
    @DisplayName("Deve retornar erro quando autor for nulo")
    void deveRetornarErroQuandoAutorNulo() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor(null);
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("autor"));
    }

    @Test
    @DisplayName("Deve retornar erro quando autor for vazio")
    void deveRetornarErroQuandoAutorVazio() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("autor"));
    }

    @Test
    @DisplayName("Deve retornar erro quando ISBN for nulo")
    void deveRetornarErroQuandoIsbnNulo() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn(null);
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("isbn"));
    }

    @Test
    @DisplayName("Deve retornar erro quando ISBN for vazio")
    void deveRetornarErroQuandoIsbnVazio() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("");
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("isbn"));
    }

    @Test
    @DisplayName("Deve validar ISBN-10 válido")
    void deveValidarISBN10Valido() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve validar ISBN-13 válido")
    void deveValidarISBN13Valido() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("9780132350884");
        dto.setAnoPublicacao(2008);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar erro quando ano de publicação for nulo")
    void deveRetornarErroQuandoAnoPublicacaoNulo() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(null);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("anoPublicacao"));
    }

    @Test
    @DisplayName("Deve retornar erro quando ano for menor que 1000")
    void deveRetornarErroQuandoAnoMenorQue1000() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(999);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("anoPublicacao"));
    }

    @Test
    @DisplayName("Deve retornar erro quando ano for maior que 2100")
    void deveRetornarErroQuandoAnoMaiorQue2100() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(2101);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("anoPublicacao"));
    }

    @Test
    @DisplayName("Deve retornar erro quando ano for maior que ano atual")
    void deveRetornarErroQuandoAnoMaiorQueAnoAtual() {
        // Given
        int anoFuturo = Year.now().getValue() + 1;
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(anoFuturo);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("anoPublicacaoValid"));
    }

    @Test
    @DisplayName("Deve validar ano atual")
    void deveValidarAnoAtual() {
        // Given
        int anoAtual = Year.now().getValue();
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("0132350884");
        dto.setAnoPublicacao(anoAtual);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve validar múltiplos erros simultaneamente")
    void deveValidarMultiplosErrosSimultaneamente() {
        // Given
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitulo("");
        dto.setAutor("");
        dto.setIsbn("");
        dto.setAnoPublicacao(null);

        // When
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);

        // Then
        assertThat(violations).hasSizeGreaterThan(3);
    }
}
