package abb.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel lateral izquierdo con todos los controles de operaciones del ABB.
 * Organizado por categorías según el material teórico.
 */
public class PanelControles extends JPanel {

    private JTextField campoValor;

    private JButton btnInsertar;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JButton btnExiste;

    private JButton btnInOrden;
    private JButton btnPreOrden;
    private JButton btnPostOrden;
    private JButton btnAmplitud;

    private JButton btnAltura;
    private JButton btnPeso;
    private JButton btnHojas;
    private JButton btnMayor;
    private JButton btnMenor;
    private JButton btnNivel;
    private JButton btnCamino;

    private JButton btnBorrar;
    private JButton btnLimpiarConsola;
    private JButton btnEjemplo;

    private static final Color FONDO_PANEL    = new Color(245, 245, 245);
    private static final Color FONDO_SECCION  = new Color(255, 255, 255);
    private static final Color BORDE_SECCION  = new Color(200, 200, 200);
    private static final Color COLOR_ENTRADA  = new Color(255, 255, 255);
    private static final Color TEXTO_ENTRADA  = new Color(30, 30, 30);
    private static final Color TITULO_COLOR   = new Color(80, 80, 80);

    public PanelControles() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(FONDO_PANEL);
        setBorder(new EmptyBorder(12, 10, 12, 10));
        setPreferredSize(new Dimension(220, 600));

        construirUI();
    }

    private void construirUI() {
        // Título
        JLabel lblApp = new JLabel("ABB — Editor");
        lblApp.setFont(new Font("Monospaced", Font.BOLD, 15));
        lblApp.setForeground(new Color(50, 50, 50));
        lblApp.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lblApp);

        JLabel lblSub = new JLabel("Árbol Binario de Búsqueda");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblSub.setForeground(new Color(130, 130, 130));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lblSub);

        add(Box.createVerticalStrut(12));

        JPanel secEntrada = crearSeccion("VALOR");
        campoValor = new JTextField();
        campoValor.setFont(new Font("Monospaced", Font.BOLD, 16));
        campoValor.setForeground(TEXTO_ENTRADA);
        campoValor.setBackground(COLOR_ENTRADA);
        campoValor.setCaretColor(new Color(80, 80, 80));
        campoValor.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(6, 8, 6, 8)
        ));
        campoValor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campoValor.setToolTipText("Ingrese un número entero");
        secEntrada.add(campoValor);
        add(secEntrada);

        add(Box.createVerticalStrut(8));

        JPanel secCRUD = crearSeccion("OPERACIONES PRINCIPALES");
        btnInsertar = crearBoton("  Insertar Nodo", new Color(80, 80, 80),  "Agrega un nuevo nodo al árbol");
        btnBuscar   = crearBoton("  Buscar Nodo",   new Color(110, 110, 110), "Busca y resalta un nodo");
        btnEliminar = crearBoton("  Eliminar Nodo", new Color(60, 60, 60),   "Elimina el nodo del árbol");
        btnExiste   = crearBoton("  ¿Existe?",      new Color(130, 130, 130), "Verifica si el nodo existe");
        secCRUD.add(btnInsertar);
        secCRUD.add(Box.createVerticalStrut(4));
        secCRUD.add(btnBuscar);
        secCRUD.add(Box.createVerticalStrut(4));
        secCRUD.add(btnEliminar);
        secCRUD.add(Box.createVerticalStrut(4));
        secCRUD.add(btnExiste);
        add(secCRUD);

        add(Box.createVerticalStrut(8));

        JPanel secRec = crearSeccion("RECORRIDOS");
        btnInOrden   = crearBoton("InOrden (IZ·R·DR)",  new Color(100, 100, 100), "Izquierda → Raíz → Derecha");
        btnPreOrden  = crearBoton("PreOrden (R·IZ·DR)",  new Color(100, 100, 100), "Raíz → Izquierda → Derecha");
        btnPostOrden = crearBoton("PostOrden (IZ·DR·R)", new Color(100, 100, 100), "Izquierda → Derecha → Raíz");
        btnAmplitud  = crearBoton("Por Niveles (BFS)",   new Color(120, 120, 120), "Recorrido en amplitud");
        secRec.add(btnInOrden);
        secRec.add(Box.createVerticalStrut(4));
        secRec.add(btnPreOrden);
        secRec.add(Box.createVerticalStrut(4));
        secRec.add(btnPostOrden);
        secRec.add(Box.createVerticalStrut(4));
        secRec.add(btnAmplitud);
        add(secRec);

        add(Box.createVerticalStrut(8));

        JPanel secProp = crearSeccion("PROPIEDADES");
        btnAltura  = crearBoton("Altura del árbol",   new Color(120, 120, 120), "Longitud del camino más largo + 1");
        btnPeso    = crearBoton("Peso (Nodos)",        new Color(120, 120, 120), "Número total de nodos");
        btnHojas   = crearBoton("Contar Hojas",        new Color(120, 120, 120), "Nodos sin hijos");
        btnMayor   = crearBoton("Nodo Mayor",          new Color(120, 120, 120), "Elemento más grande");
        btnMenor   = crearBoton("Nodo Menor",          new Color(120, 120, 120), "Elemento más pequeño");
        btnNivel   = crearBoton("Nivel del nodo",      new Color(120, 120, 120), "Nivel del valor ingresado");
        btnCamino  = crearBoton("Camino al nodo",      new Color(120, 120, 120), "Ruta desde raíz al nodo");
        secProp.add(btnAltura);
        secProp.add(Box.createVerticalStrut(3));
        secProp.add(btnPeso);
        secProp.add(Box.createVerticalStrut(3));
        secProp.add(btnHojas);
        secProp.add(Box.createVerticalStrut(3));
        secProp.add(btnMayor);
        secProp.add(Box.createVerticalStrut(3));
        secProp.add(btnMenor);
        secProp.add(Box.createVerticalStrut(3));
        secProp.add(btnNivel);
        secProp.add(Box.createVerticalStrut(3));
        secProp.add(btnCamino);
        add(secProp);

        add(Box.createVerticalStrut(8));

        JPanel secArbol = crearSeccion("ÁRBOL");
        btnEjemplo       = crearBoton("Cargar Ejemplo",   new Color(90, 90, 90),  "Carga un árbol de ejemplo");
        btnBorrar        = crearBoton("Borrar Árbol",     new Color(60, 60, 60),  "Elimina todos los nodos");
        btnLimpiarConsola= crearBoton("Limpiar Consola",  new Color(140, 140, 140), "Limpia el texto de la consola");
        secArbol.add(btnEjemplo);
        secArbol.add(Box.createVerticalStrut(4));
        secArbol.add(btnBorrar);
        secArbol.add(Box.createVerticalStrut(4));
        secArbol.add(btnLimpiarConsola);
        add(secArbol);

        add(Box.createVerticalGlue());
    }

    private JPanel crearSeccion(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(FONDO_SECCION);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDE_SECCION, 1),
                titulo,
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Monospaced", Font.BOLD, 9),
                TITULO_COLOR
            ),
            new EmptyBorder(4, 6, 6, 6)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return panel;
    }

    private JButton crearBoton(String texto, Color colorBase, String tooltip) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(colorBase.darker().darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(colorBase.brighter());
                } else {
                    g2.setColor(colorBase.darker());
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(colorBase);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 11));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText(tooltip);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    public JTextField getCampoValor() { return campoValor; }
    public JButton getBtnInsertar()   { return btnInsertar; }
    public JButton getBtnBuscar()     { return btnBuscar; }
    public JButton getBtnEliminar()   { return btnEliminar; }
    public JButton getBtnExiste()     { return btnExiste; }
    public JButton getBtnInOrden()    { return btnInOrden; }
    public JButton getBtnPreOrden()   { return btnPreOrden; }
    public JButton getBtnPostOrden()  { return btnPostOrden; }
    public JButton getBtnAmplitud()   { return btnAmplitud; }
    public JButton getBtnAltura()     { return btnAltura; }
    public JButton getBtnPeso()       { return btnPeso; }
    public JButton getBtnHojas()      { return btnHojas; }
    public JButton getBtnMayor()      { return btnMayor; }
    public JButton getBtnMenor()      { return btnMenor; }
    public JButton getBtnNivel()      { return btnNivel; }
    public JButton getBtnCamino()     { return btnCamino; }
    public JButton getBtnBorrar()     { return btnBorrar; }
    public JButton getBtnLimpiarConsola() { return btnLimpiarConsola; }
    public JButton getBtnEjemplo()    { return btnEjemplo; }

    /**
     * Obtiene y valida el valor numérico ingresado en el campo.
     * @throws NumberFormatException si el campo no contiene un entero válido.
     */
    public int obtenerValorIngresado() throws NumberFormatException {
        String texto = campoValor.getText().trim();
        if (texto.isEmpty()) throw new NumberFormatException("El campo de valor está vacío.");
        return Integer.parseInt(texto);
    }

    public void limpiarCampo() {
        campoValor.setText("");
        campoValor.requestFocusInWindow();
    }
}
