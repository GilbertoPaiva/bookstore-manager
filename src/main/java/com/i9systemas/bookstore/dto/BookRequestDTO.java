package com.i9systemas.bookstore.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 1, max = 255, message = "O título deve ter entre 1 e 255 caracteres")
    private String titulo;

    @NotBlank(message = "O autor é obrigatório")
    @Size(min = 1, max = 255, message = "O autor deve ter entre 1 e 255 caracteres")
    private String autor;

    @NotBlank(message = "O ISBN é obrigatório")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "ISBN inválido")
    private String isbn;

    @NotNull(message = "O ano de publicação é obrigatório")
    @Min(value = 1000, message = "O ano de publicação deve ser maior que 1000")
    @Max(value = 2100, message = "O ano de publicação não pode ser maior que 2100")
    private Integer anoPublicacao;

    @AssertTrue(message = "O ano de publicação não pode ser maior que o ano atual")
    private boolean isAnoPublicacaoValid() {
        if (anoPublicacao == null) {
            return true;
        }
        return anoPublicacao <= Year.now().getValue();
    }
}