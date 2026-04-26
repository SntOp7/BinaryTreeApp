package abb.view;

import abb.model.ArbolABB;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal (JFrame) de la aplicación de Árbol Binario de Búsqueda.
 * Organiza los paneles: controles (izquierda), árbol (centro), consola (abajo).
 */
public class VentanaPrincipal extends JFrame {

    private PanelControles panelControles;
    private PanelArbol panelArbol;
    private PanelConsola panelConsola;

    public VentanaPrincipal(ArbolABB arbol) {
        configurarVentana();
        construirUI(arbol);
    }

    private void configurarVentana() {
        setTitle("Árbol Binario de Búsqueda (ABB) — Estructura de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 760);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setIconImage(crearIcono());

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    private void construirUI(ArbolABB arbol) {
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(240, 240, 240));

        panelControles = new PanelControles();
        JScrollPane scrollControles = new JScrollPane(panelControles);
        scrollControles.setBorder(BorderFactory.createEmptyBorder());
        scrollControles.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollControles.getVerticalScrollBar().setUnitIncrement(12);

        panelArbol = new PanelArbol(arbol);
        JScrollPane scrollArbol = new JScrollPane(panelArbol);
        scrollArbol.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollArbol.setBackground(new Color(250, 250, 250));

        panelConsola = new PanelConsola();
        panelConsola.setPreferredSize(new Dimension(0, 185));

        JSplitPane splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollArbol, panelConsola);
        splitVertical.setResizeWeight(0.65);
        splitVertical.setDividerSize(5);
        splitVertical.setDividerLocation(490);
        splitVertical.setBorder(null);
        splitVertical.setBackground(new Color(220, 220, 220));

        JSplitPane splitHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollControles, splitVertical);
        splitHorizontal.setResizeWeight(0.0);
        splitHorizontal.setDividerSize(5);
        splitHorizontal.setDividerLocation(230);
        splitHorizontal.setBorder(null);
        splitHorizontal.setBackground(new Color(220, 220, 220));

        add(splitHorizontal, BorderLayout.CENTER);

        add(crearBarraEstado(), BorderLayout.SOUTH);
    }

    private JPanel crearBarraEstado() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 3));
        barra.setBackground(new Color(235, 235, 235));
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        JLabel lbl = new JLabel("Ingeniería de Sistemas  ·  Estructura de Datos  ·  Árbol Binario de Búsqueda (ABB)");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lbl.setForeground(new Color(110, 110, 110));
        barra.add(lbl);
        return barra;
    }

    private Image crearIcono() {
        int size = 32;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(100, 60, 200));
        g2.fillOval(12, 2, 8, 8);
        g2.setColor(new Color(60, 130, 220));
        g2.fillOval(4, 14, 8, 8);
        g2.fillOval(20, 14, 8, 8);
        g2.setColor(new Color(40, 160, 100));
        g2.fillOval(0, 24, 7, 7);
        g2.fillOval(13, 24, 7, 7);
        g2.fillOval(26, 24, 6, 6);
        g2.setColor(new Color(80, 100, 160));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(16, 10, 8, 14);
        g2.drawLine(16, 10, 24, 14);
        g2.drawLine(8, 22, 3, 24);
        g2.drawLine(8, 22, 16, 24);
        g2.drawLine(24, 22, 29, 24);
        g2.dispose();
        return img;
    }

    public PanelControles getPanelControles() { return panelControles; }
    public PanelArbol     getPanelArbol()     { return panelArbol; }
    public PanelConsola   getPanelConsola()   { return panelConsola; }
}
