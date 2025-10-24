import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Estudiante> Estudiantes = new ArrayList<>();
        Estudiantes.add(new Estudiante("Javier", 20,"Derecho"));
        Estudiantes.add(new Estudiante("Tomas",25,"Software"));
        Estudiantes.add(new Estudiante("Juan", 30,"Medicina)"));

        for(Estudiante Estudiante : Estudiantes){
            System.out.println(Estudiante.toString());
        }
    }
}