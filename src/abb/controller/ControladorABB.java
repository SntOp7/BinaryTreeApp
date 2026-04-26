package abb.controller;

import abb.model.ArbolABB;
import abb.model.Nodo;
import abb.view.PanelArbol;
import abb.view.PanelConsola;
import abb.view.PanelControles;
import abb.view.VentanaPrincipal;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

/**
 * Controlador principal del patrón MVC para la aplicación ABB.
 * Gestiona todos los eventos de la interfaz y los delega al modelo (ArbolABB).
 * Actualiza la vista tras cada operación.
 *
 * Operaciones implementadas según material teórico:
 *  - Insertar, Buscar, Eliminar, Existe
 *  - InOrden, PreOrden, PostOrden, Amplitud
 *  - Altura, Peso, Hojas, Mayor, Menor, Nivel, Camino
 *  - Borrar árbol, Ejemplo precargado
 */
public class ControladorABB {

    private final ArbolABB        arbol;
    private final VentanaPrincipal ventana;
    private final PanelControles   controles;
    private final PanelArbol       panelArbol;
    private final PanelConsola     consola;

    public ControladorABB(ArbolABB arbol, VentanaPrincipal ventana) {
        this.arbol      = arbol;
        this.ventana    = ventana;
        this.controles  = ventana.getPanelControles();
        this.panelArbol = ventana.getPanelArbol();
        this.consola    = ventana.getPanelConsola();

        registrarEventos();
    }

    private void registrarEventos() {
        controles.getCampoValor().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    accionInsertar();
                }
            }
        });

        controles.getBtnInsertar().addActionListener(e -> accionInsertar());
        controles.getBtnBuscar().addActionListener(e   -> accionBuscar());
        controles.getBtnEliminar().addActionListener(e -> accionEliminar());
        controles.getBtnExiste().addActionListener(e   -> accionExiste());

        controles.getBtnInOrden().addActionListener(e   -> accionInOrden());
        controles.getBtnPreOrden().addActionListener(e  -> accionPreOrden());
        controles.getBtnPostOrden().addActionListener(e -> accionPostOrden());
        controles.getBtnAmplitud().addActionListener(e  -> accionAmplitud());

        controles.getBtnAltura().addActionListener(e  -> accionAltura());
        controles.getBtnPeso().addActionListener(e    -> accionPeso());
        controles.getBtnHojas().addActionListener(e   -> accionHojas());
        controles.getBtnMayor().addActionListener(e   -> accionMayor());
        controles.getBtnMenor().addActionListener(e   -> accionMenor());
        controles.getBtnNivel().addActionListener(e   -> accionNivel());
        controles.getBtnCamino().addActionListener(e  -> accionCamino());

        controles.getBtnBorrar().addActionListener(e         -> accionBorrar());
        controles.getBtnLimpiarConsola().addActionListener(e -> accionLimpiarConsola());
        controles.getBtnEjemplo().addActionListener(e        -> accionEjemplo());
    }

    /**
     * Inserta un nuevo nodo en el ABB.
     * Regla: menores a la izquierda, mayores a la derecha.
     * No se permiten duplicados.
     */
    private void accionInsertar() {
        try {
            int valor = controles.obtenerValorIngresado();
            arbol.agregar(valor);
            consola.imprimirOK("Nodo [" + valor + "] insertado correctamente.");
            consola.imprimirDato("Peso actual", arbol.obtenerPeso());
            arbol.limpiarResaltado();
            refrescarVista();
            controles.limpiarCampo();
        } catch (NumberFormatException ex) {
            consola.imprimirError("Valor inválido: ingrese un número entero.");
        } catch (Exception ex) {
            consola.imprimirError(ex.getMessage());
        }
    }

    /**
     * Busca un nodo en el ABB y lo resalta visualmente junto con su camino.
     */
    private void accionBuscar() {
        try {
            int valor = controles.obtenerValorIngresado();
            consola.imprimirSeparador();
            consola.imprimirTitulo("── Búsqueda del nodo [" + valor + "] ──────────────");
            Nodo encontrado = arbol.buscar(valor);
            if (encontrado != null) {
                consola.imprimirOK("Nodo [" + valor + "] ENCONTRADO en el árbol.");
                consola.imprimirDato("Nivel del nodo",     arbol.obtenerNivel(valor));
                consola.imprimirDato("Longitud camino",    arbol.obtenerLongitudCamino(valor));
                List<Integer> camino = arbol.obtenerCamino(valor);
                consola.imprimirDato("Camino desde raíz",  camino.toString());
                consola.imprimirDato("Es hoja",            encontrado.esHoja() ? "Sí" : "No");
            } else {
                consola.imprimirError("Nodo [" + valor + "] NO encontrado en el árbol.");
            }
            refrescarVista();
        } catch (NumberFormatException ex) {
            consola.imprimirError("Valor inválido: ingrese un número entero.");
        }
    }

    /**
     * Elimina un nodo del ABB. Aplica los tres casos de eliminación:
     * hoja, un hijo, dos hijos.
     */
    private void accionEliminar() {
        try {
            int valor = controles.obtenerValorIngresado();
            consola.imprimirSeparador();

            // Determinar el caso de eliminación antes de ejecutarla
            Nodo nodo = arbol.buscar(valor);
            arbol.limpiarResaltado();

            if (nodo == null) {
                consola.imprimirError("Nodo [" + valor + "] no existe. No se puede eliminar.");
                return;
            }

            String caso;
            if (nodo.esHoja())                                    caso = "Caso 1 - Nodo hoja";
            else if (nodo.cantidadHijos() == 1)                   caso = "Caso 2 - Un hijo";
            else                                                   caso = "Caso 3 - Dos hijos";

            arbol.eliminar(valor);
            consola.imprimirOK("Nodo [" + valor + "] eliminado. (" + caso + ")");
            consola.imprimirDato("Peso actual", arbol.obtenerPeso());
            refrescarVista();
            controles.limpiarCampo();
        } catch (NumberFormatException ex) {
            consola.imprimirError("Valor inválido: ingrese un número entero.");
        } catch (Exception ex) {
            consola.imprimirError(ex.getMessage());
        }
    }

    /** Verifica si un dato existe en el árbol sin modificarlo. */
    private void accionExiste() {
        try {
            int valor = controles.obtenerValorIngresado();
            boolean existe = arbol.existe(valor);
            consola.imprimirSeparador();
            if (existe) {
                consola.imprimirOK("El nodo [" + valor + "] SÍ existe en el árbol.");
            } else {
                consola.imprimirInfo("El nodo [" + valor + "] NO existe en el árbol.");
            }
            refrescarVista();
        } catch (NumberFormatException ex) {
            consola.imprimirError("Valor inválido: ingrese un número entero.");
        }
    }

    private void accionInOrden() {
        verificarNoVacio(() -> {
            arbol.limpiarResaltado();
            consola.imprimirSeparador();
            consola.imprimirRecorrido("InOrden  (IZ → R → DR)", arbol.recorrerInOrden());
            consola.imprimirInfo("El InOrden de un ABB siempre produce elementos en orden ascendente.");
            refrescarVista();
        });
    }

    private void accionPreOrden() {
        verificarNoVacio(() -> {
            arbol.limpiarResaltado();
            consola.imprimirSeparador();
            consola.imprimirRecorrido("PreOrden (R → IZ → DR)", arbol.recorrerPreOrden());
            refrescarVista();
        });
    }

    private void accionPostOrden() {
        verificarNoVacio(() -> {
            arbol.limpiarResaltado();
            consola.imprimirSeparador();
            consola.imprimirRecorrido("PostOrden (IZ → DR → R)", arbol.recorrerPostOrden());
            refrescarVista();
        });
    }

    private void accionAmplitud() {
        verificarNoVacio(() -> {
            arbol.limpiarResaltado();
            consola.imprimirSeparador();
            consola.imprimirNiveles(arbol.recorrerPorNiveles());
            refrescarVista();
        });
    }

    private void accionAltura() {
        verificarNoVacio(() -> {
            int altura = arbol.obtenerAltura();
            consola.imprimirSeparador();
            consola.imprimirTitulo("── Altura del Árbol ──────────────────────────");
            consola.imprimirDato("Altura", altura);
            consola.imprimirInfo("Longitud del camino más largo desde la raíz + 1.");
        });
    }

    private void accionPeso() {
        consola.imprimirSeparador();
        consola.imprimirTitulo("── Peso del Árbol ────────────────────────────");
        consola.imprimirDato("Peso (total nodos)", arbol.obtenerPeso());
    }

    private void accionHojas() {
        verificarNoVacio(() -> {
            consola.imprimirSeparador();
            consola.imprimirTitulo("── Hojas del Árbol ───────────────────────────");
            consola.imprimirDato("Cantidad de hojas", arbol.contarHojas());
            consola.imprimirDato("Nodos hoja", arbol.obtenerHojas().toString());
            consola.imprimirInfo("Una hoja es un nodo cuyos hijos izquierdo y derecho son null.");
        });
    }

    private void accionMayor() {
        verificarNoVacio(() -> {
            Nodo mayor = arbol.obtenerNodoMayor();
            consola.imprimirSeparador();
            consola.imprimirTitulo("── Nodo Mayor ────────────────────────────────");
            consola.imprimirDato("Mayor valor", mayor.getDato());
            consola.imprimirInfo("El nodo mayor es el más a la derecha del árbol.");
            arbol.limpiarResaltado();
            arbol.buscar(mayor.getDato());
            refrescarVista();
        });
    }

    private void accionMenor() {
        verificarNoVacio(() -> {
            Nodo menor = arbol.obtenerNodoMenor();
            consola.imprimirSeparador();
            consola.imprimirTitulo("── Nodo Menor ────────────────────────────────");
            consola.imprimirDato("Menor valor", menor.getDato());
            consola.imprimirInfo("El nodo menor es el más a la izquierda del árbol.");
            arbol.limpiarResaltado();
            arbol.buscar(menor.getDato());
            refrescarVista();
        });
    }

    private void accionNivel() {
        try {
            int valor = controles.obtenerValorIngresado();
            if (!arbol.existe(valor)) {
                consola.imprimirError("Nodo [" + valor + "] no existe en el árbol.");
                return;
            }
            int nivel = arbol.obtenerNivel(valor);
            consola.imprimirSeparador();
            consola.imprimirTitulo("── Nivel del Nodo ────────────────────────────");
            consola.imprimirDato("Nodo", valor);
            consola.imprimirDato("Nivel", nivel);
            consola.imprimirInfo("El nivel de la raíz es 0; el de sus hijos es 1, etc.");
        } catch (NumberFormatException ex) {
            consola.imprimirError("Valor inválido: ingrese un número entero.");
        }
    }

    private void accionCamino() {
        try {
            int valor = controles.obtenerValorIngresado();
            if (!arbol.existe(valor)) {
                consola.imprimirError("Nodo [" + valor + "] no existe en el árbol.");
                return;
            }
            List<Integer> camino = arbol.obtenerCamino(valor);
            consola.imprimirSeparador();
            consola.imprimirTitulo("── Camino hasta el Nodo ──────────────────────");
            consola.imprimirDato("Nodo destino",      valor);
            consola.imprimirDato("Camino (raíz→nodo)", camino.toString());
            consola.imprimirDato("Longitud del camino", camino.size() - 1);

            // Resaltar el camino en el árbol visual
            arbol.limpiarResaltado();
            arbol.buscar(valor);
            refrescarVista();
        } catch (NumberFormatException ex) {
            consola.imprimirError("Valor inválido: ingrese un número entero.");
        }
    }

    private void accionBorrar() {
        if (arbol.estaVacio()) {
            consola.imprimirInfo("El árbol ya está vacío.");
            return;
        }
        int res = JOptionPane.showConfirmDialog(ventana,
            "¿Confirma que desea borrar todos los nodos del árbol?",
            "Confirmar Borrado",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            arbol.borrarArbol();
            consola.imprimirSeparador();
            consola.imprimirOK("Árbol borrado completamente.");
            refrescarVista();
        }
    }

    private void accionLimpiarConsola() {
        consola.limpiar();
    }

    /**
     * Carga un árbol de ejemplo con valores del material del curso.
     * Secuencia: 400, 100, 700, 300, 500, 220 (del ejemplo de inserción).
     */
    private void accionEjemplo() {
        if (!arbol.estaVacio()) {
            int res = JOptionPane.showConfirmDialog(ventana,
                "Se borrará el árbol actual. ¿Continuar?",
                "Cargar Ejemplo",
                JOptionPane.YES_NO_OPTION);
            if (res != JOptionPane.YES_OPTION) return;
            arbol.borrarArbol();
        }

        int[] valores = {400, 100, 700, 300, 500, 220, 50, 150, 600, 800};
        consola.imprimirSeparador();
        consola.imprimirTitulo("── Árbol de Ejemplo (ABB) ────────────────────");
        consola.imprimirInfo("Insertando: 400, 100, 700, 300, 500, 220, 50, 150, 600, 800");

        for (int v : valores) {
            try {
                arbol.agregar(v);
            } catch (Exception ignored) {}
        }

        consola.imprimirOK("Árbol de ejemplo creado con " + arbol.obtenerPeso() + " nodos.");
        consola.imprimirDato("Altura", arbol.obtenerAltura());
        consola.imprimirDato("Raíz",   arbol.getRaiz().getDato());
        arbol.limpiarResaltado();
        refrescarVista();
    }

    private void refrescarVista() {
        panelArbol.refrescar();
    }

    /**
     * Ejecuta una acción solo si el árbol no está vacío.
     * Si está vacío, muestra un mensaje en consola.
     */
    private void verificarNoVacio(Runnable accion) {
        if (arbol.estaVacio()) {
            consola.imprimirError("El árbol está vacío. Inserte nodos primero.");
            return;
        }
        accion.run();
    }
}
