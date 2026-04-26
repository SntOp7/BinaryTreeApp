package abb.view;

import abb.model.ArbolABB;
import abb.model.Nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;

/**
 * Panel que renderiza gráficamente el Árbol Binario de Búsqueda.
 * Dibuja nodos, aristas, resaltados de búsqueda y el nodo seleccionado.
 */
public class PanelArbol extends JPanel {

    private static final int RADIO_NODO       = 26;
    private static final int SEPARACION_H     = 60;
    private static final int SEPARACION_V     = 72;
    private static final int PADDING_TOP      = 50;

    // Paleta de colores — blanco y grises
    private static final Color COLOR_FONDO         = new Color(250, 250, 250);
    private static final Color COLOR_ARISTA        = new Color(140, 140, 140);
    private static final Color COLOR_NODO          = new Color(139, 90, 43);
    private static final Color COLOR_NODO_BORDE    = new Color(80, 50, 20);
    private static final Color COLOR_NODO_TEXTO    = new Color(255, 255, 255);
    private static final Color COLOR_RESALTADO     = new Color(180, 120, 60);
    private static final Color COLOR_ENCONTRADO    = new Color(50, 30, 10);
    private static final Color COLOR_RUTA          = new Color(180, 120, 60);
    private static final Color COLOR_HOJA          = new Color(56, 142, 60);
    private static final Color COLOR_RAIZ          = new Color(90, 50, 15);

    private ArbolABB arbol;
    private Nodo nodoAnimado;

    public PanelArbol(ArbolABB arbol) {
        this.arbol = arbol;
        setBackground(COLOR_FONDO);
        setPreferredSize(new Dimension(900, 550));
    }

    public void setArbol(ArbolABB arbol) {
        this.arbol = arbol;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        dibujarFondo(g2);

        if (arbol == null || arbol.estaVacio()) {
            dibujarMensajeVacio(g2);
            return;
        }

        calcularPosiciones(arbol.getRaiz(), getWidth() / 2, PADDING_TOP, getWidth() / 4);

        dibujarAristas(g2, arbol.getRaiz());

        dibujarNodos(g2, arbol.getRaiz());

        dibujarLeyenda(g2);
    }

    private void dibujarFondo(Graphics2D g2) {
        g2.setColor(COLOR_FONDO);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(200, 200, 200, 120));
        for (int x = 0; x < getWidth(); x += 30) {
            for (int y = 0; y < getHeight(); y += 30) {
                g2.fillOval(x - 1, y - 1, 2, 2);
            }
        }
    }

    private void dibujarMensajeVacio(Graphics2D g2) {
        g2.setColor(new Color(160, 160, 160));
        g2.setFont(new Font("Monospaced", Font.PLAIN, 16));
        String msg = "[ Árbol vacío — Ingrese un valor para comenzar ]";
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(msg)) / 2;
        int y = getHeight() / 2;
        g2.drawString(msg, x, y);
    }

    /**
     * Calcula las posiciones X, Y de cada nodo de forma recursiva.
     * Se usa un enfoque de anchura adaptativa para distribuir el árbol.
     */
    private void calcularPosiciones(Nodo nodo, int x, int y, int offset) {
        if (nodo == null) return;
        nodo.setX(x);
        nodo.setY(y);
        if (nodo.getHijoIzquierdo() != null) {
            calcularPosiciones(nodo.getHijoIzquierdo(), x - offset, y + SEPARACION_V, Math.max(offset / 2, SEPARACION_H));
        }
        if (nodo.getHijoDerecho() != null) {
            calcularPosiciones(nodo.getHijoDerecho(), x + offset, y + SEPARACION_V, Math.max(offset / 2, SEPARACION_H));
        }
    }

    /**
     * Dibuja las aristas (ramas) entre nodos padre e hijo.
     */
    private void dibujarAristas(Graphics2D g2, Nodo nodo) {
        if (nodo == null) return;
        g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (nodo.getHijoIzquierdo() != null) {
            boolean enRuta = estaEnRuta(nodo) && estaEnRuta(nodo.getHijoIzquierdo());
            g2.setColor(enRuta ? COLOR_RUTA : COLOR_ARISTA);
            dibujarArista(g2, nodo, nodo.getHijoIzquierdo());
            dibujarAristas(g2, nodo.getHijoIzquierdo());
        }
        if (nodo.getHijoDerecho() != null) {
            boolean enRuta = estaEnRuta(nodo) && estaEnRuta(nodo.getHijoDerecho());
            g2.setColor(enRuta ? COLOR_RUTA : COLOR_ARISTA);
            dibujarArista(g2, nodo, nodo.getHijoDerecho());
            dibujarAristas(g2, nodo.getHijoDerecho());
        }
    }

    private void dibujarArista(Graphics2D g2, Nodo desde, Nodo hasta) {
        double dx = hasta.getX() - desde.getX();
        double dy = hasta.getY() - desde.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist == 0) return;

        int x1 = (int) (desde.getX() + dx / dist * RADIO_NODO);
        int y1 = (int) (desde.getY() + dy / dist * RADIO_NODO);
        int x2 = (int) (hasta.getX() - dx / dist * RADIO_NODO);
        int y2 = (int) (hasta.getY() - dy / dist * RADIO_NODO);

        g2.drawLine(x1, y1, x2, y2);
    }

    /**
     * Dibuja todos los nodos del árbol con su estilo correspondiente.
     */
    private void dibujarNodos(Graphics2D g2, Nodo nodo) {
        if (nodo == null) return;
        dibujarNodo(g2, nodo);
        dibujarNodos(g2, nodo.getHijoIzquierdo());
        dibujarNodos(g2, nodo.getHijoDerecho());
    }

    private void dibujarNodo(Graphics2D g2, Nodo nodo) {
        int cx = nodo.getX();
        int cy = nodo.getY();
        int r  = RADIO_NODO;

        boolean esEncontrado  = (arbol.getNodoResaltado() != null && arbol.getNodoResaltado() == nodo);
        boolean esEnCamino    = estaEnRuta(nodo) && !esEncontrado;
        boolean esRaiz        = (nodo == arbol.getRaiz());
        boolean esHoja        = nodo.esHoja();

        g2.setColor(new Color(180, 180, 180, 100));
        g2.fillOval(cx - r + 3, cy - r + 4, r * 2, r * 2);

        Color colorRelleno;
        if (esEncontrado)     colorRelleno = COLOR_ENCONTRADO;
        else if (esEnCamino)  colorRelleno = COLOR_RUTA;
        else if (esRaiz)      colorRelleno = COLOR_RAIZ;
        else if (esHoja)      colorRelleno = COLOR_HOJA;
        else                  colorRelleno = COLOR_NODO;

        g2.setColor(colorRelleno.darker());
        g2.fillOval(cx - r, cy - r, r * 2, r * 2);

        g2.setColor(colorRelleno);
        g2.fillOval(cx - r + 1, cy - r + 1, r * 2 - 2, r * 2 - 2);

        Color colorBorde;
        if (esEncontrado)     colorBorde = new Color(30, 30, 30);
        else if (esEnCamino)  colorBorde = new Color(80, 80, 80);
        else if (esRaiz)      colorBorde = new Color(20, 20, 20);
        else if (esHoja)      colorBorde = new Color(110, 110, 110);
        else                  colorBorde = COLOR_NODO_BORDE;

        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(2.2f));
        g2.drawOval(cx - r, cy - r, r * 2, r * 2);

        Color textoColor = (esEncontrado || esRaiz) ? Color.WHITE : COLOR_NODO_TEXTO;
        g2.setColor(textoColor);
        g2.setFont(new Font("Monospaced", Font.BOLD, 13));
        FontMetrics fm = g2.getFontMetrics();
        String texto = String.valueOf(nodo.getDato());
        g2.drawString(texto, cx - fm.stringWidth(texto) / 2, cy + fm.getAscent() / 2 - 1);

        if (esRaiz) {
            g2.setFont(new Font("SansSerif", Font.BOLD, 9));
            g2.setColor(new Color(80, 80, 80));
            g2.drawString("RAÍZ", cx - 12, cy - r - 5);
        }
    }

    private boolean estaEnRuta(Nodo nodo) {
        List<Nodo> camino = arbol.getCaminoResaltado();
        return camino != null && camino.contains(nodo);
    }

    private void dibujarLeyenda(Graphics2D g2) {
        int x = 14;
        int y = getHeight() - 100;

        g2.setFont(new Font("SansSerif", Font.BOLD, 10));

        dibujarItemLeyenda(g2, x, y,      COLOR_RAIZ,      "Raíz");
        dibujarItemLeyenda(g2, x, y + 18, COLOR_HOJA,      "Hoja");
        dibujarItemLeyenda(g2, x, y + 36, COLOR_RUTA,      "Camino búsqueda");
        dibujarItemLeyenda(g2, x, y + 54, COLOR_ENCONTRADO,"Nodo encontrado");
    }

    private void dibujarItemLeyenda(Graphics2D g2, int x, int y, Color color, String label) {
        g2.setColor(color);
        g2.fillOval(x, y - 9, 12, 12);
        g2.setColor(new Color(50, 50, 50));
        g2.setStroke(new BasicStroke(1f));
        g2.drawOval(x, y - 9, 12, 12);
        g2.setColor(new Color(60, 60, 60));
        g2.drawString(label, x + 17, y + 1);
    }

    public void refrescar() {
        repaint();
    }
}
