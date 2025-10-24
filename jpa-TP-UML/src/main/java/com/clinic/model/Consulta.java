package com.clinic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"paciente", "medico"})
public class Consulta extends Base {

    private LocalDate fecha;
    // -String diagnostico
    private String diagnostico;

    // Relación M-1: Crea la FK 'paciente_id' en la tabla Consulta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // Relación M-1: Crea la FK 'medico_id' en la tabla Consulta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    public Consulta(LocalDate fecha, String diagnostico) {
        this.fecha = fecha;
        this.diagnostico = diagnostico;
    }
}