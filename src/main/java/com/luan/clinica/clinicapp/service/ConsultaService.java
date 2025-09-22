package com.luan.clinica.clinicapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luan.clinica.clinicapp.dto.ConsultaDTO;
import com.luan.clinica.clinicapp.dto.ConsultaRequestDTO;
import com.luan.clinica.clinicapp.exception.BusinessException;
import com.luan.clinica.clinicapp.exception.ResourceNotFoundException;
import com.luan.clinica.clinicapp.model.Consulta;
import com.luan.clinica.clinicapp.model.Medico;
import com.luan.clinica.clinicapp.model.Paciente;
import com.luan.clinica.clinicapp.model.enums.StatusConsulta;
import com.luan.clinica.clinicapp.repository.ConsultaRepository;
import com.luan.clinica.clinicapp.repository.MedicoRepository;
import com.luan.clinica.clinicapp.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public List<ConsultaDTO> listar(Long medicoId, Long pacienteId) {
        List<Consulta> consultas;
        if (medicoId != null && pacienteId != null) {
            consultas = consultaRepository.findByMedicoIdAndPacienteId(medicoId, pacienteId);
        } else if (medicoId != null) {
            consultas = consultaRepository.findByMedicoId(medicoId);
        } else if (pacienteId != null) {
            consultas = consultaRepository.findByPacienteId(pacienteId);
        } else {
            consultas = consultaRepository.findAll();
        }

        return consultas.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ConsultaDTO buscarPorId(Long id) {
        return toDto(buscarEntidadePorId(id));
    }

    @Transactional
    public ConsultaDTO agendar(ConsultaRequestDTO dto) {
        Paciente paciente = buscarPaciente(dto.pacienteId());
        Medico medico = buscarMedico(dto.medicoId());
        validarConflitos(dto, null);

        Consulta consulta = Consulta.builder()
                .paciente(paciente)
                .medico(medico)
                .dataConsulta(dto.dataConsulta())
                .motivo(dto.motivo())
                .status(dto.status() != null ? dto.status() : StatusConsulta.AGENDADA)
                .build();
        return toDto(consultaRepository.save(consulta));
    }

    @Transactional
    public ConsultaDTO atualizar(Long id, ConsultaRequestDTO dto) {
        Consulta consulta = buscarEntidadePorId(id);
        Paciente paciente = buscarPaciente(dto.pacienteId());
        Medico medico = buscarMedico(dto.medicoId());
        validarConflitos(dto, id);

        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataConsulta(dto.dataConsulta());
        consulta.setMotivo(dto.motivo());
        consulta.setStatus(dto.status() != null ? dto.status() : consulta.getStatus());

        return toDto(consultaRepository.save(consulta));
    }

    @Transactional
    public void remover(Long id) {
        Consulta consulta = buscarEntidadePorId(id);
        consultaRepository.delete(consulta);
    }

    private void validarConflitos(ConsultaRequestDTO dto, Long consultaId) {
        Long medicoId = dto.medicoId();
        Long pacienteId = dto.pacienteId();

        boolean existeConflitoMedico = consultaId == null
                ? consultaRepository.existsByMedicoIdAndDataConsulta(medicoId, dto.dataConsulta())
                : consultaRepository.existsByMedicoIdAndDataConsultaAndIdNot(medicoId, dto.dataConsulta(), consultaId);

        if (existeConflitoMedico) {
            throw new BusinessException("O medico ja possui uma consulta marcada neste horario");
        }

        boolean existeConflitoPaciente = consultaId == null
                ? consultaRepository.existsByPacienteIdAndDataConsulta(pacienteId, dto.dataConsulta())
                : consultaRepository.existsByPacienteIdAndDataConsultaAndIdNot(pacienteId, dto.dataConsulta(), consultaId);

        if (existeConflitoPaciente) {
            throw new BusinessException("O paciente ja possui uma consulta marcada neste horario");
        }
    }

    private Paciente buscarPaciente(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente nao encontrado"));
    }

    private Medico buscarMedico(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico nao encontrado"));
    }

    private Consulta buscarEntidadePorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta nao encontrada"));
    }

    private ConsultaDTO toDto(Consulta consulta) {
        return new ConsultaDTO(
                consulta.getId(),
                consulta.getPaciente().getId(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getId(),
                consulta.getMedico().getNome(),
                consulta.getDataConsulta(),
                consulta.getStatus(),
                consulta.getMotivo());
    }
}
