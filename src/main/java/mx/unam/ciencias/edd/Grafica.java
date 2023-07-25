package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return this.iterador.hasNext();
            // Aquí va su código.
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return this.iterador.next().elemento;
            // Aquí va su código.
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            color = Color.NINGUNO;
            vecinos = new Lista<Vertice>();
            // Aquí va su código.
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return this.elemento;
            // Aquí va su código.
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return this.vecinos.getLongitud();
            // Aquí va su código.
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return this.color;
            // Aquí va su código.
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return this.vecinos;
            // Aquí va su código.
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        this.vertices = new Lista<Vertice>();
        // Aquí va su código.
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getLongitud();
        // Aquí va su código.
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
        // Aquí va su código.
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento es <code>null</code> o ya
     *         había sido agregado a la gráfica.
     */
    @Override public void agrega(T elemento) {
	if (contiene(elemento) || elemento == null) {
	    throw new IllegalArgumentException();
	}
	Vertice vertice = new Vertice(elemento);
	vertices.agregaFinal(vertice);
        // Aquí va su código.
    }


    private Vertice buscaVertice(T elemento) {
	for (Vertice vertice : vertices) {
	    if (vertice.elemento.equals(elemento)) {
		return vertice;
	    }
	}
	return null;
    }


    private boolean buscaVecino(Vertice vertice, Lista<Vertice> vecinos) {
	for (Vertice v : vecinos) {
	    if (v.elemento.equals(vertice.elemento)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
	Vertice v = buscaVertice(a);
	Vertice u = buscaVertice(b);
	if (v == null || u == null) {
	    throw new NoSuchElementException();
	} else if (v.elemento.equals(u.elemento)) {
	    throw new IllegalArgumentException();
	} else if (sonVecinos(a, b)) {
	    throw new IllegalArgumentException();
	} else {
	    u.vecinos.agregaFinal(v);
	    v.vecinos.agregaFinal(u);
	    aristas += 1;
	}
        // Aquí va su código.
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
	Vertice v = buscaVertice(a);
	Vertice u = buscaVertice(b);
	if (v == null || u == null) {
	    throw new NoSuchElementException();
	}
	if (!(sonVecinos(a,b))) {
	    throw new IllegalArgumentException();
	}
	v.vecinos.elimina(u);
	u.vecinos.elimina(v);
	aristas -= 1;
	// Aquí va su código.
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
	for (Vertice vertice : vertices) {
	    if (vertice.elemento.equals(elemento)) {
		return true;
	    }
	}
	return false;
        // Aquí va su código.
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
	Vertice vertice = buscaVertice(elemento);
	if (vertice == null) {
	    throw new NoSuchElementException();
	}
	vertices.elimina(vertice);
	for (Vertice vecino : vertice.vecinos) {
	    aristas -= 1;
	    vecino.vecinos.elimina(vertice);
	}
        // Aquí va su código.
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
	Vertice u = buscaVertice(a);
	Vertice v = buscaVertice(b);
	if (u == null || v == null) {
	    throw new NoSuchElementException();
	}
	boolean vecinoDeU = buscaVecino(v, u.vecinos);
	boolean vecinoDeV = buscaVecino(u, v.vecinos);
	return (vecinoDeU && vecinoDeV);
        // Aquí va su código.
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
	for (Vertice vertice : vertices) {
	    if (vertice.elemento.equals(elemento)) {
		return (VerticeGrafica<T>) vertice;
	    }
	}
	throw new NoSuchElementException();
        // Aquí va su código.
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
	if (vertice == null || vertice.getClass() != Vertice.class) {
	    throw new IllegalArgumentException();
	}
	Vertice v = (Vertice) vertice;
	v.color = color;
        // Aquí va su código.
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
	if (this.getElementos() == 0) {
	    return true;
	}
	Vertice vertice = vertices.getPrimero();
	for (Vertice v : vertices) {
	    v.color = Color.ROJO;
	}
	vertice.color = Color.NEGRO;
	Pila<Vertice> pila = new Pila<Vertice>();
	pila.mete(vertice);
	while (!(pila.esVacia())) {
	    Vertice auxiliar = pila.saca();
	    for (Vertice vecino : auxiliar.vecinos) {
		if (vecino.color == Color.ROJO) {
		    vecino.color = Color.NEGRO;
		    pila.mete(vecino);
		}
	    }
	}
	for (Vertice v : vertices) {
	    if (v.color == Color.ROJO) {
		return false;
	    }
	}
	for (Vertice v : vertices) {
	    v.color = Color.NINGUNO;
	}
	return true;
        // Aquí va su código.
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
	for (Vertice vertice : vertices) {
	    accion.actua(vertice);
	}
        // Aquí va su código.
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
	Vertice v = buscaVertice(elemento);
	if (v == null) {
	    throw new NoSuchElementException();
	}
	for (Vertice vertice : vertices) {
	    vertice.color = Color.ROJO;
	}
	v.color = Color.NEGRO;
	Cola<Vertice> cola = new Cola<Vertice>();
	cola.mete(v);
	while (!(cola.esVacia())) {
	    Vertice auxiliar = cola.saca();
	    accion.actua(auxiliar);
	    if (!(auxiliar.vecinos.esVacia())) {
		for (Vertice vertice : auxiliar.vecinos) {
		    if (vertice.color == Color.ROJO) {
			vertice.color = Color.NEGRO;
			cola.mete(vertice);
		    }
		}
	    }
	}
	for (Vertice vertice : vertices) {
	    vertice.color = Color.NINGUNO;
	}
        // Aquí va su código.
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
	Vertice v = buscaVertice(elemento);
	if (v == null) {
	    throw new NoSuchElementException();
	}
	for (Vertice vertice : vertices) {
	    vertice.color = Color.ROJO;
	}
	Pila<Vertice> pila = new Pila<Vertice>();
	v.color = Color.NEGRO;
	pila.mete(v);
	while (!(pila.esVacia())) {
	    Vertice auxiliar = pila.saca();
	    accion.actua(auxiliar);
	    if (!(auxiliar.vecinos.esVacia())) {
		for (Vertice vecino : auxiliar.vecinos) {
		    if (vecino.color == Color.ROJO) {
			vecino.color = Color.NEGRO;
			pila.mete(vecino);
		    }
		}
	    }
	}
	for (Vertice vertice : vertices) {
	    vertice.color = Color.NINGUNO;
	}
        // Aquí va su código.
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
	return this.vertices.esVacia();
        // Aquí va su código.
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
	this.vertices.limpia();
	this.aristas = 0;
        // Aquí va su código.
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
	String cadena = "{";
	for (Vertice vertice : vertices) {
	    cadena += vertice.elemento.toString() + ", ";
	}
	cadena += "}, {";
	paraCadaVertice((v) -> setColor(v, Color.NINGUNO));
	for (Vertice vertice : vertices) {
	    vertice.color = Color.NEGRO;
	    for (Vertice vecino : vertice.vecinos) {
		if (vecino.color != Color.NEGRO) {
		    cadena += "(" + vertice.elemento.toString() + ", " + vecino.elemento.toString() + "), ";
		}
	    }
	}
	cadena += "}";
	paraCadaVertice((vertice) -> setColor(vertice, Color.NINGUNO));
	return cadena;
        // Aquí va su código.
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
	if (this.getElementos() != grafica.getElementos()) {
	    return false;
	}
	if (this.getAristas() != grafica.getAristas()) {
	    return false;
	}
	for (Vertice vertice : this.vertices) {
	    Vertice v = vecinos(vertice, grafica.vertices);
	    if (v == null) {
		return false;
	    }
	    for (Vertice u : vertice.vecinos) {
		if (this.vecinos(u, v.vecinos) == null ) {
		    return false;
		}
	    }
	}
	return true;
        // Aquí va su código.
    }

    private Vertice vecinos(Vertice vertice, Lista<Vertice> lista) {
	for(Vertice v : lista) {
	    if (vertice.elemento.equals(v.elemento)) {
		return v;
	    }
	}
	return null;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
