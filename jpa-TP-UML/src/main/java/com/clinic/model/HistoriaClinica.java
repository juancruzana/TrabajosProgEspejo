package com.clinic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"paciente"})
public class HistoriaClinica extends Base {

    // -String Descripcion
    private String descripcion;

    // Relación Inversa 1-1 (No crea FK aquí, mapeada por 'historiaClinica' en Paciente)
    @OneToOne(mappedBy = "historiaClinica")
    private Paciente paciente;

    public HistoriaClinica(String descripcion) {
        this.descripcion = descripcion;
    }
}