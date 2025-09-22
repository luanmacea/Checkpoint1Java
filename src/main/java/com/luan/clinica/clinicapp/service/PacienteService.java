package com.luan.clinica.clinicapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luan.clinica.clinicapp.dto.PacienteDTO;
import com.luan.clinica.clinicapp.dto.PacienteRequestDTO;
import com.luan.clinica.clinicapp.exception.BusinessException;
import com.luan.clinica.clinicapp.exception.ResourceNotFoundException;
import com.luan.clinica.clinicapp.model.Paciente;
import com.luan.clinica.clinicapp.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PacienteDTO buscarPorId(Long id) {
        return toDto(buscarEntidadePorId(id));
    }

    @Transactional
    public PacienteDTO criar(PacienteRequestDTO dto) {
        validarDuplicidade(dto.cpf(), dto.email(), null);
        Paciente paciente = Paciente.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .email(dto.email())
                .telefone(dto.telefone())
                .dataNascimento(dto.dataNascimento())
                .build();
        return toDto(pacienteRepository.save(paciente));
    }

    @Transactional
    public PacienteDTO atualizar(Long id, PacienteRequestDTO dto) {
        Paciente paciente = buscarEntidadePorId(id);
        validarDuplicidade(dto.cpf(), dto.email(), id);
        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setEmail(dto.email());
        paciente.setTelefone(dto.telefone());
        paciente.setDataNascimento(dto.dataNascimento());
        return toDto(pacienteRepository.save(paciente));
    }

    @Transactional
    public void remover(Long id) {
        Paciente paciente = buscarEntidadePorId(id);
        pacienteRepository.delete(paciente);
    }

    private void validarDuplicidade(String cpf, String email, Long idAtual) {
        if (idAtual == null) {
            if (pacienteRepository.existsByCpf(cpf)) {
                throw new BusinessException("Ja existe um paciente cadastrado com este CPF");
            }
            if (pacienteRepository.existsByEmail(email)) {
                throw new BusinessException("Ja existe um paciente cadastrado com este e-mail");
            }
        } else {
            if (pacienteRepository.existsByCpfAndIdNot(cpf, idAtual)) {
                throw new BusinessException("Ja existe outro paciente cadastrado com este CPF");
            }
            if (pacienteRepository.existsByEmailAndIdNot(email, idAtual)) {
                throw new BusinessException("Ja existe outro paciente cadastrado com este e-mail");
            }
        }
    }

    private Paciente buscarEntidadePorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente nao encontrado"));
    }

    private PacienteDTO toDto(Paciente paciente) {
        return new PacienteDTO(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getDataNascimento());
    }
}
