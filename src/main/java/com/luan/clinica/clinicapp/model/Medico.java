package com.luan.clinica.clinicapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicos", uniqueConstraints = {
        @UniqueConstraint(name = "uk_medico_crm", columnNames = "crm"),
        @UniqueConstraint(name = "uk_medico_email", columnNames = "email")
})
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 30)
    private String crm;

    @Column(nullable = false, length = 80)
    private String especialidade;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(length = 20)
    private String telefone;
}
