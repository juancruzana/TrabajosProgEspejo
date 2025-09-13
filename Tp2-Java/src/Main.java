import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Estudiante> estudiantesEstructura = new ArrayList<>();
        estudiantesEstructura.add(new Estudiante("Javier", 20,"Derecho"));
        estudiantesEstructura.add(new Estudiante("Tomas",25,"Software"));
        estudiantesEstructura.add(new Estudiante("Juan", 30,"Medicina)"));


        Curso curso = new Curso("Estructuras de Datos",estudiantesEstructura);

        System.out.println(curso.toString());
    }
}