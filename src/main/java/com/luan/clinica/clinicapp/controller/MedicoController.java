package com.luan.clinica.clinicapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.luan.clinica.clinicapp.dto.MedicoDTO;
import com.luan.clinica.clinicapp.dto.MedicoRequestDTO;
import com.luan.clinica.clinicapp.service.MedicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping
    public List<MedicoDTO> listar() {
        return medicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public MedicoDTO buscar(@PathVariable Long id) {
        return medicoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicoDTO criar(@Valid @RequestBody MedicoRequestDTO dto) {
        return medicoService.criar(dto);
    }

    @PutMapping("/{id}")
    public MedicoDTO atualizar(@PathVariable Long id, @Valid @RequestBody MedicoRequestDTO dto) {
        return medicoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        medicoService.remover(id);
    }
}
