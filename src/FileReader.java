import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private File archivoEntrada;
    private Scanner scArchivo;

    public String recuerarNombreArchivo(String indice){
        archivoEntrada = new File("archivos/indices.txt");
        try {
            scArchivo = new Scanner(archivoEntrada);
            Scanner scDatos = null;
            while (scArchivo.hasNextLine()){
                String linea = scArchivo.nextLine();
                scDatos = new Scanner(linea);
                scDatos.useDelimiter("\\s*,\\s");
                String idArchivo = scDatos.next();
                if(idArchivo.equals(indice))
                    return scDatos.next();
            }
            scDatos.close();
            scArchivo.close();
        } catch (FileNotFoundException e) {
            System.out.println(" [!] El archivo de indices no se ha encontrado.");
        }
        return "";
    }

    public String recuperarContenidoArchivo(String nombre){
        String contenidoArchivo = "";
        archivoEntrada = new File("archivos/" + nombre);
        try {
            scArchivo = new Scanner(archivoEntrada);
            while (scArchivo.hasNextLine())
                contenidoArchivo = contenidoArchivo.concat(scArchivo.nextLine());
            scArchivo.close();
        } catch (FileNotFoundException e) {
            System.out.println(" [!] El archivo " + nombre + "no se ha encontrado.");
        }
        return contenidoArchivo;
    }
}
