package com.clinic.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"consultas"})
public class Medico extends Base {

    private String nombre;
    private String apellido;
    private int edad;

    // -String especialidad, -String matricula
    private String especialidad;
    private String matricula;

    // Relación Inversa 1-M: Medico 1 <- m Consulta (mappedBy)
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Consulta> consultas;

    public Medico(String nombre, String apellido, int edad, String especialidad, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.especialidad = especialidad;
        this.matricula = matricula;
    }
}