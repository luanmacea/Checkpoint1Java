package com.luan.clinica.clinicapp.dto;

import java.time.LocalDateTime;

import com.luan.clinica.clinicapp.model.enums.StatusConsulta;

public record ConsultaDTO(
        Long id,
        Long pacienteId,
        String pacienteNome,
        Long medicoId,
        String medicoNome,
        LocalDateTime dataConsulta,
        StatusConsulta status,
        String motivo) {
}
