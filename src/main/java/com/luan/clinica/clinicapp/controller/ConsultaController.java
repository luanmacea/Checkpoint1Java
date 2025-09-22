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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.luan.clinica.clinicapp.dto.ConsultaDTO;
import com.luan.clinica.clinicapp.dto.ConsultaRequestDTO;
import com.luan.clinica.clinicapp.service.ConsultaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @GetMapping
    public List<ConsultaDTO> listar(
            @RequestParam(required = false) Long medicoId,
            @RequestParam(required = false) Long pacienteId) {
        return consultaService.listar(medicoId, pacienteId);
    }

    @GetMapping("/{id}")
    public ConsultaDTO buscar(@PathVariable Long id) {
        return consultaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaDTO agendar(@Valid @RequestBody ConsultaRequestDTO dto) {
        return consultaService.agendar(dto);
    }

    @PutMapping("/{id}")
    public ConsultaDTO atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaRequestDTO dto) {
        return consultaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        consultaService.remover(id);
    }
}
