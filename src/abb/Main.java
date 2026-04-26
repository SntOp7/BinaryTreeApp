package abb;

import abb.controller.ControladorABB;
import abb.model.ArbolABB;
import abb.view.VentanaPrincipal;

import javax.swing.*;

/**
 * Punto de entrada de la aplicación ABB.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArbolABB arbol = new ArbolABB();

            VentanaPrincipal ventana = new VentanaPrincipal(arbol);

            new ControladorABB(arbol, ventana);

            ventana.setVisible(true);
        });
    }
}
