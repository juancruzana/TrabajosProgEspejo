package com.clinic.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera constructor sin argumentos
public abstract class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean eliminado = false;

    // Constructor custom para inicialización
    public Base(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    // Método + void eliminar()
    public void eliminar() {
        this.eliminado = true;
    }
}
