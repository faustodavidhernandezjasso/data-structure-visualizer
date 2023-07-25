package mx.unam.ciencias.edd.proyecto2;

import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.lang.NumberFormatException;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto2.Estructuras;
import mx.unam.ciencias.edd.proyecto2.GraficaSVG;
import mx.unam.ciencias.edd.proyecto2.ListaSVG;
import mx.unam.ciencias.edd.Grafica;

/**
 * Argumenos. Clase que sirve para determinar qué debemos graficas-
 */
public class Argumentos {
    
     /*La estructura de datos */
    private static String edd;
    /** El contenido de la Estructura de Datos */
    private Lista<String> contenido = new Lista<String>();
    /** Los números de la Estructura de Datos obtenida desde un archivo o de la entrada estándar */
    private Lista<Integer> elementos = new Lista<Integer>();
    /** El nombre del archivo recibido desde la consola */
    private String nombreDeArchivo = null;
    /** Estructura de Datos */
    private Estructuras estructura;
    /** Las aristas de la Gráfica (Sí es que nos pasan una gráfica) */
    private Lista<String> aristasGrafica  = new Lista<String>();
    /** Los elementos de la Gráfica (Sí es que nos pasan una gráfica) */
    private Lista<Integer> elementosGrafica = new Lista<Integer>();

    /**
     * leeArgumentos. Lee los argumentos recibidos desde la consola
     * @param args El arreglo recibido desde la consola.
     */
    public void leeArgumentos(String[] args) {
        if (args.length == 1) {
            nombreDeArchivo = args[0];
            leerArchivo(nombreDeArchivo);
        } else if (args.length == 0) {
            entradaEstandar();
        } else {
            System.err.println("Por favor introduzca un archivo de texto ó mediante la terminal introduzca una Estructura de Datos con enteros");
            System.exit(1);
        }
    }

    /**
     * entradaEstandar. Lee la Estructura de Datos recibido desde la Entrada Estándar.
     */
    public void entradaEstandar() {
        String cadena;
        String[] cadenas;
        try {
            InputStreamReader entrada = new InputStreamReader(System.in);
            BufferedReader leer = new BufferedReader(entrada);
            while ((cadena = leer.readLine()) != null) {
                cadena = elementoArchivo(cadena);
                cadenas = cadena.split(",");
                for (String s : cadenas) {
                    if (s.length() > 0) {
                        contenido.agregaFinal(s);
                    }
                }
            }
            leer.close();
        } catch (IOException ioe) {
            System.err.println("Ha ocurrido un error");
            System.exit(1);
        }
        edd = contenido.eliminaPrimero().trim();
        estructura = new Estructuras(edd);
        if (estructura.estructuraValida()) {
            getElementos(contenido);
        } else {
            System.err.println("La estructura de datos es inválida");
            System.exit(1);
        }
        if (edd.equals("Grafica")) {
            for (int i = 0; i < elementos.getLongitud(); i++) {
                if (elementos.get(i) < 10) {
                    elementosGrafica.agregaFinal(elementos.get(i));    
                } else {
                    aristasGrafica.agregaFinal(Integer.toString(elementos.get(i)));
                }
            }           
        }
        String edd = dibujaEstructuraDeDatos(estructura.getEstructura());
        System.out.println(edd);
    }

    /**
     * leerArchivo. Lee el archivo recibido desde la consola.
     * @param nombreDeArchivo El nombre del archivo recibido.
     */
    public void leerArchivo(String nombreDeArchivo) {
        String[] cadenas = null;
        try {
            String cadena;
            FileReader archivo = new FileReader(nombreDeArchivo);
            BufferedReader leer = new BufferedReader(archivo);
            while ((cadena = leer.readLine()) != null) {
                cadena = elementoArchivo(cadena);
                cadenas = cadena.split(",");
                for (String s : cadenas) {
                    if (s.length() > 0) {
                        contenido.agregaFinal(s);
                    }
                }
            }
            leer.close();
        } catch (IOException ioe) {
            System.err.println("Ha ocurrido un error al leer el archivo");
            System.exit(1);
        }
        edd = contenido.eliminaPrimero().trim();
        estructura = new Estructuras(edd);
        if (estructura.estructuraValida()) {
            getElementos(contenido);
        } else {
            System.err.println("La estructura de datos es inválida");
            System.exit(1);
        }
        if (edd.equals("Grafica")) {
            for (int i = 0; i < elementos.getLongitud(); i++) {
                if (elementos.get(i) < 10) {
                    elementosGrafica.agregaFinal(elementos.get(i));    
                } else {
                    aristasGrafica.agregaFinal(Integer.toString(elementos.get(i)));
                }
            }           
        }
        String edd = dibujaEstructuraDeDatos(estructura.getEstructura());
        System.out.println(edd);
    } 

    /**
     * elementoArchivo. Detecta si hay la almohadilla "#" en el archivo o la entrada estándar y normaliza la cadena.
     * @param cadena la cadena a normalizar.
     * @return cadena normaliza.
     */
    private String elementoArchivo(String cadena) {
        if (cadena.contains("#")) {
            cadena = cadena.substring(0, cadena.indexOf("#"));
            cadena = cadena.trim().replace(" ", ",");
            return cadena;
        } else {
            cadena = cadena.trim().replace(" ", ",");
            return cadena;    
        }
    }
    
    /**
     * getElementos. Convierte todas las cadenas de la Estructura De Datos.
     * @param contenido La lista de cadenas de la Estructura De Datos que se va convertir a enteros.
     */
    private void getElementos(Lista<String> contenido) {
        for (String s : contenido) {
            s = s.trim();
            String[] arreglo = s.split(" ");
            for (int i = 0; i < arreglo.length; i++) {
                try {
                    elementos.agregaFinal(Integer.parseInt(arreglo[i]));
                } catch (NumberFormatException nfe) {
                    System.err.println("Sólo debe introducir números a la estructura de datos");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * dibujaEstructuraDeDatos. Dibuja la Estructura De Datos.
     * @param cadena La Estructura de Datos a dibujar.
     * @return  La Estructura de Datos dibujada.
     */
    public String dibujaEstructuraDeDatos(String cadena) {
        String edd = "";
        switch(cadena) {
            case "Lista":
                ListaSVG lista = new ListaSVG(elementos);
                edd += lista.listaEnSVG();
                break;
            case "Pila":
                PilaSVG pila = new PilaSVG(elementos);
                edd += pila.pilaEnSVG();
                break;
            case "Cola":
                ColaSVG cola = new ColaSVG(elementos);
                edd += cola.colaEnSVG();
                break;
            case "ArbolBinarioCompleto":
                ArbolBinarioCompletoSVG arbolBC = new ArbolBinarioCompletoSVG(elementos);
                edd += arbolBC.arbolBinarioCompletoenSVG();
                break;
            case "ArbolBinarioOrdenado":
                ArbolBinarioOrdenadoSVG arbolBO = new ArbolBinarioOrdenadoSVG(elementos);
                edd += arbolBO.arbolBinarioOrdenadoenSVG();
                break;
            case "ArbolRojinegro":
                ArbolRojinegroSVG arbolRojinegro = new ArbolRojinegroSVG(elementos);
                edd += arbolRojinegro.arbolRojinegroEnSVG();
                break;
            case "ArbolAVL":
                ArbolAVLSVG arbolAVL = new ArbolAVLSVG(elementos);
                edd += arbolAVL.arbolAVLenSVG();
                break;
            case "Grafica":
                GraficaSVG grafica = new GraficaSVG();
                Grafica<Integer> graficaSVG = grafica.llenaGrafica(elementosGrafica, aristasGrafica);
                edd += grafica.graficaEnSVG(graficaSVG);
                break;
            case "MonticuloMinimo":
                MonticuloMinimoSVG monticuloMinimo = new MonticuloMinimoSVG(elementos);
                edd += monticuloMinimo.monticuloMinimoEnSVG();
                break;
            default:
                System.err.println("Estructura de Datos inválida");
                break;
        }
        return edd;
    }

    

}