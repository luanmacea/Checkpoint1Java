package com.luan.clinica.clinicapp.dto;

import java.time.LocalDate;

public record PacienteDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        LocalDate dataNascimento) {
}
