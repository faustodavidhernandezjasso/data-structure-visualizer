package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
            this.altura = 0;
            // Aquí va su código.
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return this.altura;
            // Aquí va su código.
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            String cadena = this.elemento.toString() + " " + this.altura + "/" + balance(this);
            return cadena;  
            // Aquí va su código.
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            return (altura == vertice.altura && super.equals(objeto));
            // Aquí va su código.
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
        // Aquí va su código.
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeAVL vertice = (VerticeAVL) getUltimoVerticeAgregado();
        balancea(vertice);
        // Aquí va su código.
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeAVL eliminar = (VerticeAVL) super.busca(elemento);
        if (eliminar == null) {
            return;
        } else {
            elementos -= 1;
            if (!(eliminar.hayDerecho()) && !(eliminar.hayIzquierdo())) {
                eliminaVertice(eliminar);
                balancea((VerticeAVL) eliminar.padre);
            }
            else if (eliminar.hayDerecho() && !(eliminar.hayIzquierdo())) {
                eliminaVertice(eliminar);
                balancea((VerticeAVL) eliminar.padre);
            } else if (!(eliminar.hayDerecho()) && eliminar.hayIzquierdo()) {
                eliminaVertice(eliminar);
                balancea((VerticeAVL) eliminar.padre);
            } else {
                Vertice intercambia = intercambiaEliminable(eliminar);
                eliminaVertice(intercambia);
                balancea((VerticeAVL) intercambia.padre); 
            }
        }
        // Aquí va su código.
    }



    private int alturaAVL(VerticeAVL vertice) {
        if (vertice == null) {
            return -1;
        }
        vertice.altura = 1 + Math.max(alturaAVL((VerticeAVL) vertice.izquierdo), alturaAVL((VerticeAVL) vertice.derecho));
        return vertice.altura;
    }



    private int balance(VerticeAVL vertice) {
        return alturaAVL((VerticeAVL) vertice.izquierdo) - alturaAVL((VerticeAVL)vertice.derecho);
    }



    private void balancea(VerticeAVL vertice) {
        if (vertice == null) {
            return;
        }
        alturaAVL(vertice);
        if (balance(vertice) == -2) {
            if ((balance((VerticeAVL) vertice.derecho)) == 1) {
                VerticeAVL q = (VerticeAVL) vertice.derecho;
                super.giraDerecha(q);
                alturaAVL(q);
                alturaAVL((VerticeAVL) q.padre);
            }
            super.giraIzquierda(vertice);
            alturaAVL(vertice);
        }
        else if (balance(vertice) == 2) {
            if (balance((VerticeAVL) vertice.izquierdo) == -1) {
                VerticeAVL p = (VerticeAVL) vertice.izquierdo;
                super.giraIzquierda(p);
                alturaAVL(p);
                alturaAVL((VerticeAVL) p.padre);
            }
            super.giraDerecha(vertice);
            alturaAVL(vertice);
        }
        balancea((VerticeAVL) vertice.padre);
    }
    

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
