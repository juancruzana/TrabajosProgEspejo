import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Lista de productos ducplicados
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("1233", "Cafe",3000));
        productos.add(new Producto("1233", "Cafee",3000));
        productos.add(new Producto("4325", "Leche",1000));

        // Lista de productos sin duplicar
        List<Producto> productosUnicos = new ArrayList<>();

        for (Producto producto : productos) {
            if (!productosUnicos.contains(producto)) {
                productosUnicos.add(producto);
            }
        }

        for (Producto producto : productosUnicos) {
        System.out.println(producto.toString());
        }

    }
}