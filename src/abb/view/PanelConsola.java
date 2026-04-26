package abb.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.util.List;

/**
 * Panel de consola que muestra resultados de operaciones sobre el ABB.
 * Soporta salida con colores para distinguir tipos de mensajes.
 */
public class PanelConsola extends JPanel {

    private JTextPane areaTexto;
    private StyledDocument doc;

    // Estilos de color — blanco y grises
    private static final Color COLOR_FONDO   = new Color(255, 255, 255);
    private static final Color COLOR_OK      = new Color(50, 50, 50);
    private static final Color COLOR_ERROR   = new Color(80, 80, 80);
    private static final Color COLOR_INFO    = new Color(100, 100, 100);
    private static final Color COLOR_TITULO  = new Color(30, 30, 30);
    private static final Color COLOR_DATO    = new Color(40, 40, 40);
    private static final Color COLOR_NEUTRO  = new Color(120, 120, 120);

    public PanelConsola() {
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);

        areaTexto = new JTextPane();
        areaTexto.setEditable(false);
        areaTexto.setBackground(COLOR_FONDO);
        areaTexto.setBorder(new EmptyBorder(8, 10, 8, 10));
        doc = areaTexto.getStyledDocument();

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scroll.setBackground(COLOR_FONDO);
        scroll.getVerticalScrollBar().setBackground(COLOR_FONDO);

        add(scroll, BorderLayout.CENTER);

        JLabel titulo = new JLabel("  CONSOLA DE OPERACIONES");
        titulo.setFont(new Font("Monospaced", Font.BOLD, 11));
        titulo.setForeground(new Color(80, 80, 80));
        titulo.setBackground(new Color(235, 235, 235));
        titulo.setOpaque(true);
        titulo.setBorder(new EmptyBorder(5, 8, 5, 8));
        add(titulo, BorderLayout.NORTH);

        imprimirBienvenida();
    }

    private void imprimirBienvenida() {
        imprimirTitulo("═══════════════════════════════════════════════");
        imprimirTitulo("   ÁRBOL BINARIO DE BÚSQUEDA (ABB) — Editor   ");
        imprimirTitulo("═══════════════════════════════════════════════");
        imprimirInfo("Ingrese valores y utilice las operaciones del panel izquierdo.");
        imprimirNeutro("Regla ABB: menores a la izquierda · mayores a la derecha\n");
    }

    public void imprimirOK(String texto) {
        agregar("✓ " + texto + "\n", COLOR_OK, Font.PLAIN);
        scrollAbajo();
    }

    public void imprimirError(String texto) {
        agregar("✗ " + texto + "\n", COLOR_ERROR, Font.BOLD);
        scrollAbajo();
    }

    public void imprimirInfo(String texto) {
        agregar("ℹ " + texto + "\n", COLOR_INFO, Font.PLAIN);
        scrollAbajo();
    }

    public void imprimirTitulo(String texto) {
        agregar(texto + "\n", COLOR_TITULO, Font.BOLD);
        scrollAbajo();
    }

    public void imprimirDato(String etiqueta, Object valor) {
        agregar("  " + etiqueta + ": ", COLOR_NEUTRO, Font.PLAIN);
        agregar(String.valueOf(valor) + "\n", COLOR_DATO, Font.BOLD);
        scrollAbajo();
    }

    public void imprimirNeutro(String texto) {
        agregar(texto + "\n", COLOR_NEUTRO, Font.PLAIN);
        scrollAbajo();
    }

    public void imprimirRecorrido(String tipo, List<Integer> elementos) {
        imprimirTitulo("── Recorrido " + tipo + " ──────────────────");
        if (elementos.isEmpty()) {
            imprimirInfo("(árbol vacío)");
            return;
        }
        StringBuilder sb = new StringBuilder("  [ ");
        for (int i = 0; i < elementos.size(); i++) {
            sb.append(elementos.get(i));
            if (i < elementos.size() - 1) sb.append(" → ");
        }
        sb.append(" ]");
        agregar(sb.toString() + "\n", COLOR_DATO, Font.BOLD);
        scrollAbajo();
    }

    public void imprimirNiveles(List<List<Integer>> niveles) {
        imprimirTitulo("── Recorrido por Niveles (Amplitud) ──────────");
        if (niveles.isEmpty()) {
            imprimirInfo("(árbol vacío)");
            return;
        }
        for (int i = 0; i < niveles.size(); i++) {
            agregar("  Nivel " + i + ": ", COLOR_NEUTRO, Font.PLAIN);
            agregar(niveles.get(i).toString() + "\n", COLOR_DATO, Font.BOLD);
        }
        scrollAbajo();
    }

    public void imprimirSeparador() {
        agregar("  ─────────────────────────────────────────────\n", new Color(190, 190, 190), Font.PLAIN);
        scrollAbajo();
    }

    public void limpiar() {
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException ignored) {}
        imprimirBienvenida();
    }

    private void agregar(String texto, Color color, int estilo) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, color);
        StyleConstants.setBold(attrs, estilo == Font.BOLD);
        StyleConstants.setFontFamily(attrs, "Monospaced");
        StyleConstants.setFontSize(attrs, 12);
        try {
            doc.insertString(doc.getLength(), texto, attrs);
        } catch (BadLocationException ignored) {}
    }

    private void scrollAbajo() {
        SwingUtilities.invokeLater(() -> {
            areaTexto.setCaretPosition(doc.getLength());
        });
    }
}
