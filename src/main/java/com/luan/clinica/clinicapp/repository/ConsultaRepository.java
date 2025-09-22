package com.luan.clinica.clinicapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luan.clinica.clinicapp.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByMedicoIdAndDataConsulta(Long medicoId, LocalDateTime dataConsulta);

    boolean existsByMedicoIdAndDataConsultaAndIdNot(Long medicoId, LocalDateTime dataConsulta, Long id);

    boolean existsByPacienteIdAndDataConsulta(Long pacienteId, LocalDateTime dataConsulta);

    boolean existsByPacienteIdAndDataConsultaAndIdNot(Long pacienteId, LocalDateTime dataConsulta, Long id);

    List<Consulta> findByMedicoId(Long medicoId);

    List<Consulta> findByPacienteId(Long pacienteId);

    List<Consulta> findByMedicoIdAndPacienteId(Long medicoId, Long pacienteId);
}
