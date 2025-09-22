package com.luan.clinica.clinicapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luan.clinica.clinicapp.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Optional<Medico> findByCrm(String crm);

    boolean existsByCrm(String crm);

    boolean existsByCrmAndIdNot(String crm, Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
