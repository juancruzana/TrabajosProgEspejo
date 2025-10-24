import java.util.List;

public class Curso {
    String nombre;
    List<Estudiante> listEstudiantes;

    public Curso(String estructurasDeDatos, List<Estudiante> estudiantesEstructura) {
        this.nombre = estructurasDeDatos;
        this.listEstudiantes = estudiantesEstructura;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "nombre='" + nombre + '\'' +
                ", listEstudiantes=" + listEstudiantes +
                '}';
    }
}
