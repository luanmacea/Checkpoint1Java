package com.luan.clinica.clinicapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MedicoRequestDTO(
        @NotBlank(message = "O nome do medico e obrigatorio")
        @Size(max = 120, message = "O nome deve ter no maximo 120 caracteres")
        String nome,

        @NotBlank(message = "O CRM e obrigatorio")
        @Size(max = 30, message = "O CRM deve ter no maximo 30 caracteres")
        String crm,

        @NotBlank(message = "A especialidade e obrigatoria")
        @Size(max = 80, message = "A especialidade deve ter no maximo 80 caracteres")
        String especialidade,

        @NotBlank(message = "O e-mail e obrigatorio")
        @Email(message = "Informe um e-mail valido")
        @Size(max = 120, message = "O e-mail deve ter no maximo 120 caracteres")
        String email,

        @NotBlank(message = "O telefone e obrigatorio")
        @Size(max = 20, message = "O telefone deve ter no maximo 20 caracteres")
        String telefone) {
}
