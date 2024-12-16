import java.io.*;
import java.util.Objects;

/**
 * La clase que se encarga de guardar recetas, buscar recetas por nombre, guardar toda la informaición de las recetas
 * en un archivo , cargar las recetas exscritas en otro archivo y eliminar una receta.
 * Además contiene funciones para devolver
 */
public class LibroDeRecetas {
    private Receta recetas[];
    private int numRecetas=0;
    public LibroDeRecetas(int maxRecetasEnLibro) {
        // Inicialización del libro de recetas
       recetas= new Receta[maxRecetasEnLibro];
    }

    /**
     * Función que añade una receta al libro de recetas.
     * @param receta- Objeto de la clase receta, es decir la receta, que se quiere añadir al libro de recetas en el
     *              array llamado recetas[]
     * @return - La función devuelve un valor boolean, que es verdadero si de ha podido añadir la receta con éxito o
     *          falso si se llegó al limite de recetas.
     */
    public boolean agregarReceta(Receta receta) {
        // Añade una receta al libro de recetas
        if(!recetasCompletas()){
            int i=0;
            while (recetas[i]!=null){
                i++;}
            recetas[i]=receta;
            numRecetas++;
            return true;
        }
        else{
            System.out.println("No se pueden añadir más instrucciones.");
            return false;}
    }

    /**
     * Función que a partir de un String busca recetas cuyos nombres lo contengan, interpreta las mayúsculas y
     * minúsculas como iguales. Devuelve un array con as recetas encontradas.
     * @param texto- Es el String del cual se busca si algún nombre de las rectas lo contienen como un substring.
     * @return - La función devuelve un array de la clase Receta[] con las recetas que contengan el param. texto
     *           como substring.
     */
    public Receta[] buscarRecetaPorNombre(String texto) {
       Receta[]Encontradas=new Receta[recetas.length];
       int recetasEncontradas=0;
        for(int i=0;i<recetas.length;i++){
            if(recetas[i]!=null) {
                Receta receta = recetas[i];
                if(((receta.getNombre()).toUpperCase()).contains(texto.toUpperCase())){
                    Encontradas[recetasEncontradas] = receta;
                    recetasEncontradas++;
                }
            }
        }
            return Encontradas;
    }

    /**
     * Función que crea y escribe un archivo que contenga toda la información de las recetas de manera que se puedan
     * después cargar con la función cargarRecetasDeArchivo.
     * @param nombreArchivo- String que contiene el nombre del archivo que va ha ser creado(incluye .txt)
     * @throws IOException La función puede lanzar una excepcción tipo IOException.
     */
    public void guardarRecetasEnArchivo(String nombreArchivo) throws IOException {
        // Guarda las recetas en un archivo de texto
       // File guardado = new File(nombreArchivo+".txt");
        try{
            PrintWriter escritor= new PrintWriter(new FileWriter(nombreArchivo));
            for(int i=0;i<recetas.length;i++) {
                if (recetas[i] != null) {
                    Receta receta = recetas[i];
                    escritor.print(receta.toRawString());}
            }
            escritor.close();
            System.out.println("Archivo guardado exitosamente");
        }catch(IOException e){
            System.out.println("Ocurrió un error al escribir en el archivo."+ e.getMessage());
        }
    }

    /**
     * Función que se encarga en leer un archivo de texto que contiene la información de las rectas e implementa esta
     * información en sus respectivas clases.
     * @param nombreArchivo- String que contiene el nombre del archivo que se busca para leer(incluyendo .txt)
     * @param maxIngredientes- Constante(int) que determina la contidad máxima de ingredientes permitidos que está
     *                       determinado al ejecutar el programa.
     * @param maxInstrucciones-Constante(int) que determina la contidad máxima de instrucciones permitidas que está
     *                       determinado al ejecutar el programa.
     * @throws IOException La función puede lanzar una excepcción tipo IOException.
     */
    public void cargarRecetasDeArchivo(String nombreArchivo, int maxIngredientes, int maxInstrucciones) throws IOException {
        // Solucionar errores al meter un archivo mal hecho
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
            String linea;
            do {
                linea = lector.readLine();
                if (linea!=null){
                    Receta receta = new Receta(linea, maxIngredientes, maxInstrucciones);
                    do{
                        linea = lector.readLine();
                        if(linea!=null) {
                            if (!linea.equals("INSTRUCCIONES"))
                                receta.agregarIngrediente(linea);
                            else {
                                linea = null;
                            }
                        }
                    }while(linea!=null);
                    do{
                        linea = lector.readLine();
                        if(linea!=null) {
                            if (!linea.equals("-----")) {
                                receta.agregarInstruccion(linea);
                            }
                        }
                    }while(!Objects.equals(linea, "-----"));
                    agregarReceta(receta);
                }
            }while(linea!=null);
        } catch (FileNotFoundException ex) {
            System.out.println("No se ha encontrado el archivo llamado: " + nombreArchivo);
        }catch (IOException ex) {
            System.out.println(ex.getMessage());}
    }


    /**
     * Función que devuelve un boolean dependiendo de si se ha llegado al máximo de recetas.
     * @return-Devuelve un valor boolean true, si ha alcanzado el limite de recetas y uno false si no lo ha hecho.
     */
    public boolean recetasCompletas() {
        if(numRecetas>=recetas.length) {
            System.out.println("No se pudo añadir la receta.");
            return true; // @todo MODIFICAR PARA DEVOLVER SI ESTÁ COMPLETO
        }
        else return false;
    }

    /**
     * Función que devuelve el numero de recetas actuales en el libro de recetas.
     * @return- Devuelve un valor int con el numero de recetas actuales.
     */
    public int numRecetas() {
        return numRecetas;
    }
    /**
     * Función que elimina una receta seleccionada, del array recetas[], sustituyendolo por un null.
     * @param seleccionada- Objeto de la clase Receta que se quiere va a eliminar.
     */
    public void eliminarReceta(Receta seleccionada) {
        // Elimina una receta del libro de recetas
        for(int i=0;i<recetas.length;i++){
            Receta receta=recetas[i];
            if(receta==seleccionada) {
                recetas[i] = null;
                numRecetas--;
            }
        }
    }
}

