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

import com.luan.clinica.clinicapp.dto.PacienteDTO;
import com.luan.clinica.clinicapp.dto.PacienteRequestDTO;
import com.luan.clinica.clinicapp.service.PacienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public List<PacienteDTO> listar() {
        return pacienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public PacienteDTO buscar(@PathVariable Long id) {
        return pacienteService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteDTO criar(@Valid @RequestBody PacienteRequestDTO dto) {
        return pacienteService.criar(dto);
    }

    @PutMapping("/{id}")
    public PacienteDTO atualizar(@PathVariable Long id, @Valid @RequestBody PacienteRequestDTO dto) {
        return pacienteService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        pacienteService.remover(id);
    }
}
