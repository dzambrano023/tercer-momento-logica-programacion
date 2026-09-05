import java.util.Scanner;

public class InventarioNoel {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] productos = {"Ducales", "Festival", "Tosh", null, null};
        boolean continuar = true;
        int opcion;
        System.out.println("********** Bienvenido Inventario **********");
        while (continuar) {
            System.out.println("Seleccione la opcion: ");
            System.out.println("Opcion 1: Listar Producto ");
            System.out.println("Opcion 2: Buscar Producto ");
            System.out.println("Opcion 3: Agregar Producto ");
            System.out.println("Opcion 4: Salir ");

            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                   listarProductos(productos);
                    break;
                case 2:
                    System.out.println("INGRESE EL NOMBRE DEL PRODUCTO: ");
                    String nombreProducto = sc.next();
                    buscarProducto(productos, nombreProducto);
                    if (buscarProducto(productos, nombreProducto)) {
                        System.out.println("Producto: " + nombreProducto + " Disponible");
                    }
                    break;

                case 3:
                    System.out.println("Ingrese el producto a Agregar");
                    agregarProducto(productos, sc.next());
                    break;

                case 4:
                    System.out.println("Saliendo...");
                    continuar = false;
                    break;



            }


        }

    }

    public static void listarProductos(String[] productos) {
        System.out.println("Lista de Productos: ");
        for (String producto : productos) {
            System.out.println(producto);
        }
    }


// Función que se debe modificar para que al buscar el producto también imprima el mensaje de que ha sido encontrado 

    public static boolean buscarProducto(String[] nombreArray, String nombreProducto) {

        for (int i = 0; i < nombreArray.length; i++) {

            if (nombreArray[i].equals(nombreProducto)) {
                return true;
            }
        }
        return false;
    }

    public static void agregarProducto(String[] nombreArray, String nombreProducto) {
        for (int i = 0; i < nombreArray.length; i++) {
            if (nombreArray[i] == null) {
                nombreArray[i] = nombreProducto;
                i = nombreArray.length;
                System.out.println("PRODUCTO: " + nombreProducto);
            }
        }

    }


}
