public class Estudiante extends Persona{
    String carrera;

    public Estudiante(String nombre, int edad, String carrera) {
        super(nombre, edad);
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "edad=" + edad +
                ", nombre='" + nombre + '\n' +
                ", carrera='" + carrera + '\'' +
                '}';
    }
}
