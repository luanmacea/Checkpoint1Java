package com.luan.clinica.clinicapp.dto;

import java.time.LocalDateTime;

import com.luan.clinica.clinicapp.model.enums.StatusConsulta;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConsultaRequestDTO(
        @NotNull(message = "O paciente e obrigatorio")
        Long pacienteId,

        @NotNull(message = "O medico e obrigatorio")
        Long medicoId,

        @NotNull(message = "A data da consulta e obrigatoria")
        @FutureOrPresent(message = "A data da consulta deve ser no presente ou futuro")
        LocalDateTime dataConsulta,

        @Size(max = 255, message = "O motivo deve ter no maximo 255 caracteres")
        String motivo,

        StatusConsulta status) {
}
