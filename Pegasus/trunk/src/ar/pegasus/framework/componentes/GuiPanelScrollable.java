package ar.pegasus.framework.componentes;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.Scrollable;

/**
 * Componente que extiende de GuiPanel e implementa la interfaz Scrollable.
 * Implementa un panel con capacidad de scroll para su contenido.
 * @author oarias
 * @see javax.swing.Scrollable
 */
public class GuiPanelScrollable extends GuiPanel implements Scrollable {

    private static final int SCROLLABLE_UNIT_INCREMENT = 16;
    private static final int SCROLLABLE_BLOCK_INCREMENT = 30;

    /**
     * M�todo constructor.
     */
    public GuiPanelScrollable() {
        super();
        setAutoscrolls(true);
        setOpaque(true);
    }

    /**
     * M�todo constructor.
     * @param x La coordenada X a ubicar el componente.
     * @param y La coordenada Y a ubicar el componente.
     * @param ancho El ancho en pixeles del componente.
     * @param alto El alto en pixeles del componente.
     */
    public GuiPanelScrollable(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
        setAutoscrolls(true);
        setOpaque(true);
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
     */
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
     */
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
     */
    public Dimension getPreferredScrollableViewportSize() {
        return (this.getPreferredSize());
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
        return SCROLLABLE_BLOCK_INCREMENT;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
        return SCROLLABLE_UNIT_INCREMENT;
    }

}