package com.clinic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"pacientes"})
public class Medicamento extends Base {

    private String nombre;
    private String droga;

    // -int pesoEnGramos
    private int pesoEnGramos;

    // Relación N-M Inversa
    @ManyToMany(mappedBy = "medicamentos")
    private Set<Paciente> pacientes;

    public Medicamento(String nombre, String droga, int pesoEnGramos) {
        this.nombre = nombre;
        this.droga = droga;
        this.pesoEnGramos = pesoEnGramos;
    }
}