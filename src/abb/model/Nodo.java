package abb.model;

/**
 * Clase Nodo para el Árbol Binario de Búsqueda (ABB).
 * Cada nodo almacena un dato entero, referencias a hijo izquierdo,
 * hijo derecho y nodo padre.
 */
public class Nodo {

    private int dato;
    private Nodo hijoIzquierdo;
    private Nodo hijoDerecho;
    private Nodo padre;

    private int x;
    private int y;

    public Nodo(int dato) {
        this.dato = dato;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
        this.padre = null;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Determina si el nodo es una hoja (sin hijos).
     * Un elemento de un árbol binario es una hoja si sus hijos izquierdo
     * y derecho son vacíos.
     */
    public boolean esHoja() {
        return hijoIzquierdo == null && hijoDerecho == null;
    }

    /**
     * Cuenta la cantidad de hijos del nodo.
     */
    public int cantidadHijos() {
        int count = 0;
        if (hijoIzquierdo != null) count++;
        if (hijoDerecho != null) count++;
        return count;
    }

    public int getDato() { return dato; }
    public void setDato(int dato) { this.dato = dato; }

    public Nodo getHijoIzquierdo() { return hijoIzquierdo; }
    public void setHijoIzquierdo(Nodo hijoIzquierdo) { this.hijoIzquierdo = hijoIzquierdo; }

    public Nodo getHijoDerecho() { return hijoDerecho; }
    public void setHijoDerecho(Nodo hijoDerecho) { this.hijoDerecho = hijoDerecho; }

    public Nodo getPadre() { return padre; }
    public void setPadre(Nodo padre) { this.padre = padre; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}
