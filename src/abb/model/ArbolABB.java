package abb.model;

import java.util.*;

/**
 * Implementación completa del Árbol Binario de Búsqueda (ABB).
 *
 * Operaciones implementadas:
 *  - estaVacio, agregar, existe, eliminar, borrarArbol
 *  - obtenerPeso, obtenerAltura, obtenerNivel
 *  - contarHojas, obtenerNodoMayor, obtenerNodoMenor
 *  - recorrerInOrden, recorrerPreOrden, recorrerPostOrden, imprimirAmplitud
 *
 */
public class ArbolABB {

    private Nodo raiz;
    private int peso;

    private Nodo nodoResaltado;
    private List<Nodo> caminoResaltado;

    public ArbolABB() {
        this.raiz = null;
        this.peso = 0;
        this.caminoResaltado = new ArrayList<>();
    }

    /**
     * Determina si el árbol está vacío.
     * Árbol vacío: árbol que no tiene elementos ni subárboles asociados.
     */
    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Retorna el número de nodos del árbol (peso).
     * El peso es el número de elementos (nodos) que hay en el árbol.
     */
    public int obtenerPeso() {
        return peso;
    }

    /**
     * Borra completamente el árbol.
     */
    public void borrarArbol() {
        raiz = null;
        peso = 0;
        nodoResaltado = null;
        caminoResaltado.clear();
    }

    /**
     * Inserta un nuevo dato en el ABB respetando el orden.
     * @param dato
     */
    public void agregar(int dato) throws Exception {
        if (estaVacio()) {
            raiz = new Nodo(dato);
            peso++;
            return;
        }
        agregarRecursivo(raiz, dato);
    }

    /**
     * Agrega un nodo de manera recursiva
     * @param actual
     * @param dato
     * @throws Exception
     */
    private void agregarRecursivo(Nodo actual, int dato) throws Exception {
        if (dato == actual.getDato()) {
            throw new Exception("El nodo " + dato + " ya existe en el árbol. No se permiten duplicados.");
        }
        if (dato < actual.getDato()) {
            if (actual.getHijoIzquierdo() == null) {
                Nodo nuevo = new Nodo(dato);
                nuevo.setPadre(actual);
                actual.setHijoIzquierdo(nuevo);
                peso++;
            } else {
                agregarRecursivo(actual.getHijoIzquierdo(), dato);
            }
        } else {
            if (actual.getHijoDerecho() == null) {
                Nodo nuevo = new Nodo(dato);
                nuevo.setPadre(actual);
                actual.setHijoDerecho(nuevo);
                peso++;
            } else {
                agregarRecursivo(actual.getHijoDerecho(), dato);
            }
        }
    }

    /**
     * Busca un dato en el árbol. Retorna el nodo si lo encuentra, null si no.
     * El proceso de búsqueda aprovecha el orden del ABB comparando en cada nodo.
     * @param dato
     */
    public Nodo buscar(int dato) {
        caminoResaltado.clear();
        return buscarRecursivo(raiz, dato);
    }

    /**
     * Busca un nodo de manera recursiva
     * @param actual
     * @param dato
     * @return nodo
     */
    private Nodo buscarRecursivo(Nodo actual, int dato) {
        if (actual == null) return null;
        caminoResaltado.add(actual);
        if (dato == actual.getDato()) {
            nodoResaltado = actual;
            return actual;
        }
        if (dato < actual.getDato()) {
            return buscarRecursivo(actual.getHijoIzquierdo(), dato);
        } else {
            return buscarRecursivo(actual.getHijoDerecho(), dato);
        }
    }

    /**
     * Verifica si un dato existe en el árbol.
     */
    public boolean existe(int dato) {
        return buscar(dato) != null;
    }

    /**
     * Elimina un nodo del ABB. Tres casos posibles:
     * @param dato
     */
    public void eliminar(int dato) throws Exception {
        if (estaVacio()) {
            throw new Exception("El árbol está vacío. No se puede eliminar.");
        }
        Nodo nodo = buscarNodo(raiz, dato);
        if (nodo == null) {
            throw new Exception("El nodo " + dato + " no existe en el árbol.");
        }
        eliminarNodo(nodo);
        peso--;
    }

    /**
     * Busca un nodo
     * @param actual
     * @param dato
     * @return nodo
     */
    private Nodo buscarNodo(Nodo actual, int dato) {
        if (actual == null) return null;
        if (dato == actual.getDato()) return actual;
        if (dato < actual.getDato()) return buscarNodo(actual.getHijoIzquierdo(), dato);
        return buscarNodo(actual.getHijoDerecho(), dato);
    }

    /**
     * Elimina un nodo y organiza el arbol despues de su eliminacion
     * @param nodo
     */
    private void eliminarNodo(Nodo nodo) {
        if (nodo.esHoja()) {
            if (nodo == raiz) {
                raiz = null;
            } else {
                Nodo padre = nodo.getPadre();
                if (padre.getHijoIzquierdo() == nodo) {
                    padre.setHijoIzquierdo(null);
                } else {
                    padre.setHijoDerecho(null);
                }
            }
        }
        else if (nodo.getHijoIzquierdo() != null && nodo.getHijoDerecho() != null) {
            Nodo mayorIzquierdo = obtenerMayorDeSubarbol(nodo.getHijoIzquierdo());
            int datoSustituto = mayorIzquierdo.getDato();
            eliminarNodo(mayorIzquierdo);
            peso++;
            nodo.setDato(datoSustituto);
        }
        else {
            Nodo hijo = (nodo.getHijoIzquierdo() != null)
                    ? nodo.getHijoIzquierdo()
                    : nodo.getHijoDerecho();
            hijo.setPadre(nodo.getPadre());
            if (nodo == raiz) {
                raiz = hijo;
            } else {
                Nodo padre = nodo.getPadre();
                if (padre.getHijoIzquierdo() == nodo) {
                    padre.setHijoIzquierdo(hijo);
                } else {
                    padre.setHijoDerecho(hijo);
                }
            }
        }
    }

    /**
     * Obtiene el nodo mayor del subarbol
     * @param nodo
     * @return nodo mayor
     */
    private Nodo obtenerMayorDeSubarbol(Nodo nodo) {
        if (nodo.getHijoDerecho() == null) return nodo;
        return obtenerMayorDeSubarbol(nodo.getHijoDerecho());
    }

    /**
     * Calcula la altura del árbol.
     * La altura es la longitud del camino más largo desde la raíz más 1.
     */
    public int obtenerAltura() {
        return calcularAltura(raiz);
    }

    /**
     * Calcula la altura del arbol
     * @param nodo
     * @return altura
     */
    private int calcularAltura(Nodo nodo) {
        if (nodo == null) return 0;
        int alturaIzq = calcularAltura(nodo.getHijoIzquierdo());
        int alturaDer = calcularAltura(nodo.getHijoDerecho());
        return 1 + Math.max(alturaIzq, alturaDer);
    }

    /**
     * Obtiene el nivel de un nodo específico.
     * El nivel de la raíz es 0, el de sus hijos es 1, etc.
     * @param dato
     */
    public int obtenerNivel(int dato) {
        return calcularNivel(raiz, dato, 0);
    }

    private int calcularNivel(Nodo actual, int dato, int nivel) {
        if (actual == null) return -1;
        if (actual.getDato() == dato) return nivel;
        if (dato < actual.getDato()) return calcularNivel(actual.getHijoIzquierdo(), dato, nivel + 1);
        return calcularNivel(actual.getHijoDerecho(), dato, nivel + 1);
    }

    /**
     * Cuenta las hojas del árbol.
     * Una hoja es un nodo cuyos hijos izquierdo y derecho son null.
     */
    public int contarHojas() {
        return contarHojasRecursivo(raiz);
    }

    private int contarHojasRecursivo(Nodo nodo) {
        if (nodo == null) return 0;
        if (nodo.esHoja()) return 1;
        return contarHojasRecursivo(nodo.getHijoIzquierdo())
             + contarHojasRecursivo(nodo.getHijoDerecho());
    }

    /**
     * Obtiene el nodo con el valor mayor (el más a la derecha del árbol).
     */
    public Nodo obtenerNodoMayor() {
        if (estaVacio()) return null;
        Nodo actual = raiz;
        while (actual.getHijoDerecho() != null) {
            actual = actual.getHijoDerecho();
        }
        return actual;
    }

    /**
     * Obtiene el nodo con el valor menor (el más a la izquierda del árbol).
     */
    public Nodo obtenerNodoMenor() {
        if (estaVacio()) return null;
        Nodo actual = raiz;
        while (actual.getHijoIzquierdo() != null) {
            actual = actual.getHijoIzquierdo();
        }
        return actual;
    }

    /**
     * Retorna la lista de nodos hoja.
     */
    public List<Integer> obtenerHojas() {
        List<Integer> hojas = new ArrayList<>();
        recolectarHojas(raiz, hojas);
        return hojas;
    }

    /**
     * Recolecta las hojas en una lista
     * @param nodo
     * @param hojas
     */
    private void recolectarHojas(Nodo nodo, List<Integer> hojas) {
        if (nodo == null) return;
        if (nodo.esHoja()) {
            hojas.add(nodo.getDato());
            return;
        }
        recolectarHojas(nodo.getHijoIzquierdo(), hojas);
        recolectarHojas(nodo.getHijoDerecho(), hojas);
    }

    /**
     * Calcula la longitud del camino entre la raíz y un nodo.
     * Número de ramas recorridas para llegar al nodo desde la raíz.
     * @param dato
     */
    public int obtenerLongitudCamino(int dato) {
        return obtenerNivel(dato);
    }

    /**
     * Obtiene el camino desde la raíz hasta un nodo dado.
     * @param dato
     */
    public List<Integer> obtenerCamino(int dato) {
        List<Integer> camino = new ArrayList<>();
        construirCamino(raiz, dato, camino);
        return camino;
    }

    /**
     * Construye el camino del nodo
     * @param actual
     * @param dato
     * @param camino
     * @return
     */
    private boolean construirCamino(Nodo actual, int dato, List<Integer> camino) {
        if (actual == null) return false;
        camino.add(actual.getDato());
        if (actual.getDato() == dato) return true;
        if (dato < actual.getDato()) {
            if (construirCamino(actual.getHijoIzquierdo(), dato, camino)) return true;
        } else {
            if (construirCamino(actual.getHijoDerecho(), dato, camino)) return true;
        }
        camino.remove(camino.size() - 1);
        return false;
    }

    /**
     * Recorrido INORDEN: Izquierda → Raíz → Derecha.
     * Produce los elementos en orden ascendente.
     */
    public List<Integer> recorrerInOrden() {
        List<Integer> resultado = new ArrayList<>();
        recorrerInOrdenRecursivo(raiz, resultado);
        return resultado;
    }

    /**
     * Recorrido INORDEN: Izquierda → Raíz → Derecha.
     * @param nodo
     * @param resultado
     */
    private void recorrerInOrdenRecursivo(Nodo nodo, List<Integer> resultado) {
        if (nodo == null) return;
        recorrerInOrdenRecursivo(nodo.getHijoIzquierdo(), resultado);
        resultado.add(nodo.getDato());
        recorrerInOrdenRecursivo(nodo.getHijoDerecho(), resultado);
    }

    /**
     * Recorrido PREORDEN: Raíz → Izquierda → Derecha.
     */
    public List<Integer> recorrerPreOrden() {
        List<Integer> resultado = new ArrayList<>();
        recorrerPreOrdenRecursivo(raiz, resultado);
        return resultado;
    }

    /**
     * Recorrido PREORDEN: Raíz → Izquierda → Derecha
     * @param nodo
     * @param resultado
     */
    private void recorrerPreOrdenRecursivo(Nodo nodo, List<Integer> resultado) {
        if (nodo == null) return;
        resultado.add(nodo.getDato());
        recorrerPreOrdenRecursivo(nodo.getHijoIzquierdo(), resultado);
        recorrerPreOrdenRecursivo(nodo.getHijoDerecho(), resultado);
    }

    /**
     * Recorrido POSTORDEN: Izquierda → Derecha → Raíz.
     */
    public List<Integer> recorrerPostOrden() {
        List<Integer> resultado = new ArrayList<>();
        recorrerPostOrdenRecursivo(raiz, resultado);
        return resultado;
    }

    /**
     * Recorrido POSTORDEN: Izquierda → Derecha → Raíz.
     * @param nodo
     * @param resultado
     */
    private void recorrerPostOrdenRecursivo(Nodo nodo, List<Integer> resultado) {
        if (nodo == null) return;
        recorrerPostOrdenRecursivo(nodo.getHijoIzquierdo(), resultado);
        recorrerPostOrdenRecursivo(nodo.getHijoDerecho(), resultado);
        resultado.add(nodo.getDato());
    }

    /**
     * Recorrido POR NIVELES (amplitud / BFS).
     * Visita primero la raíz, luego los elementos del nivel 1 de
     * izquierda a derecha, y así sucesivamente.
     */
    public List<List<Integer>> recorrerPorNiveles() {
        List<List<Integer>> niveles = new ArrayList<>();
        if (estaVacio()) return niveles;
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            int tamañoNivel = cola.size();
            List<Integer> nivel = new ArrayList<>();
            for (int i = 0; i < tamañoNivel; i++) {
                Nodo actual = cola.poll();
                nivel.add(actual.getDato());
                if (actual.getHijoIzquierdo() != null) cola.add(actual.getHijoIzquierdo());
                if (actual.getHijoDerecho() != null) cola.add(actual.getHijoDerecho());
            }
            niveles.add(nivel);
        }
        return niveles;
    }

    public Nodo getRaiz() { return raiz; }
    public Nodo getNodoResaltado() { return nodoResaltado; }
    public void setNodoResaltado(Nodo n) { this.nodoResaltado = n; }
    public List<Nodo> getCaminoResaltado() { return caminoResaltado; }
    public void limpiarResaltado() {
        nodoResaltado = null;
        caminoResaltado.clear();
    }
}
