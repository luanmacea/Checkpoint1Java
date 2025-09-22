package com.luan.clinica.clinicapp.dto;

public record MedicoDTO(
        Long id,
        String nome,
        String crm,
        String especialidade,
        String email,
        String telefone) {
}
