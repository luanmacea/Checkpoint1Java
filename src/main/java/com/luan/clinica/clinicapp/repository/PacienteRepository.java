package com.luan.clinica.clinicapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luan.clinica.clinicapp.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
