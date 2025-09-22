package com.luan.clinica.clinicapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luan.clinica.clinicapp.dto.MedicoDTO;
import com.luan.clinica.clinicapp.dto.MedicoRequestDTO;
import com.luan.clinica.clinicapp.exception.BusinessException;
import com.luan.clinica.clinicapp.exception.ResourceNotFoundException;
import com.luan.clinica.clinicapp.model.Medico;
import com.luan.clinica.clinicapp.repository.MedicoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public List<MedicoDTO> listarTodos() {
        return medicoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public MedicoDTO buscarPorId(Long id) {
        return toDto(buscarEntidadePorId(id));
    }

    @Transactional
    public MedicoDTO criar(MedicoRequestDTO dto) {
        validarDuplicidade(dto.crm(), dto.email(), null);
        Medico medico = Medico.builder()
                .nome(dto.nome())
                .crm(dto.crm())
                .especialidade(dto.especialidade())
                .email(dto.email())
                .telefone(dto.telefone())
                .build();
        return toDto(medicoRepository.save(medico));
    }

    @Transactional
    public MedicoDTO atualizar(Long id, MedicoRequestDTO dto) {
        Medico medico = buscarEntidadePorId(id);
        validarDuplicidade(dto.crm(), dto.email(), id);
        medico.setNome(dto.nome());
        medico.setCrm(dto.crm());
        medico.setEspecialidade(dto.especialidade());
        medico.setEmail(dto.email());
        medico.setTelefone(dto.telefone());
        return toDto(medicoRepository.save(medico));
    }

    @Transactional
    public void remover(Long id) {
        Medico medico = buscarEntidadePorId(id);
        medicoRepository.delete(medico);
    }

    private void validarDuplicidade(String crm, String email, Long idAtual) {
        if (idAtual == null) {
            if (medicoRepository.existsByCrm(crm)) {
                throw new BusinessException("Ja existe um medico cadastrado com este CRM");
            }
            if (medicoRepository.existsByEmail(email)) {
                throw new BusinessException("Ja existe um medico cadastrado com este e-mail");
            }
        } else {
            if (medicoRepository.existsByCrmAndIdNot(crm, idAtual)) {
                throw new BusinessException("Ja existe outro medico cadastrado com este CRM");
            }
            if (medicoRepository.existsByEmailAndIdNot(email, idAtual)) {
                throw new BusinessException("Ja existe outro medico cadastrado com este e-mail");
            }
        }
    }

    private Medico buscarEntidadePorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico nao encontrado"));
    }

    private MedicoDTO toDto(Medico medico) {
        return new MedicoDTO(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getEmail(),
                medico.getTelefone());
    }
}
