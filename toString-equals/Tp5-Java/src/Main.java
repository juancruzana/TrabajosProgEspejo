import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Lista de productos duplicados
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("1233", "Cafe",3000));
        productos.add(new Producto("1233", "Cafe",3000));
        productos.add(new Producto("1233", "Cafee",3000));
        productos.add(new Producto("4325", "Leche",1000));
        System.out.println("Productos Duplicados");
        for(Producto producto : productos) {
            System.out.println("   " + producto);
        }

        // Lista de productos sin duplicidad
        HashSet<Producto> productosHash = new HashSet<>();
        productosHash.addAll(productos);

        System.out.println("Productos HashSet");
        for(Producto producto : productosHash) {
            System.out.println("   " + producto);
        }
    }
}