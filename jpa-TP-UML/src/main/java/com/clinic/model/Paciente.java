package com.clinic.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"historiaClinica", "consultas", "medicamentos"})
public class Paciente extends Base {

    private String nombre;
    private String apellido;
    private int edad;
    // -int DNI
    private int dni;

    private String obraSocial;
    // - LocalDate fechaNacimiento
    private LocalDate fechaNacimiento;
    // - char sexo
    private char sexo;

    // Relación 1-1: Dueño de la relación. El Cascade permite persistir HC al persistir Paciente.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_clinica_id")
    private HistoriaClinica historiaClinica;

    // Relación 1-M: Mapeada por el campo 'paciente' en la entidad Consulta.
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Consulta> consultas = new HashSet<>();

    // Relación N-M: Define la tabla intermedia 'paciente_medicamento'.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "paciente_medicamento",
            joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "medicamento_id")
    )
    private Set<Medicamento> medicamentos = new HashSet<>();

    public Paciente(String nombre, String apellido, int edad, int dni, String obraSocial, LocalDate fechaNacimiento, char sexo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this.obraSocial = obraSocial;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
    }
}