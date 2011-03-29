package ar.pegasus.framework.util.jasper;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PException;

/**
 * @author oarias
 */
public class JasperPanel extends PJRViewer {
	private static final long serialVersionUID = 445958334737858423L;

	protected JButton printButton = new JButton();
    private boolean esRecibo;
    private JRPrintServiceExporter exporter;

    /**
     * Método constructor.
     * @param exporter PrintService Exporter.
     * @param jrPrint Objeto JasperPrint.
     * @param esRecibo
     * @throws JRException
     */
    public JasperPanel(final JRPrintServiceExporter exporter, final JasperPrint jrPrint) throws JRException {
        super(jrPrint);
        this.exporter = exporter;
        tlbToolBar.remove(btnPrint);
        printButton = new javax.swing.JButton();
        printButton.setToolTipText(btnPrint.getToolTipText());
        printButton.setText(btnPrint.getText());
        printButton.setIcon(btnPrint.getIcon());
        printButton.setPreferredSize(new Dimension(23, 23));
        printButton.setMaximumSize(new Dimension(23, 23));
        printButton.setMinimumSize(new Dimension(23, 23));
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                	exporter.exportReport();
                    JasperWrapper.seImprimio(true);
                } catch(JRException ex) {
					if (ex.getCause().getMessage().equals("Printer is not accepting job.")) {
						BossError.gestionarError(new PException(BossError.ERR_CONEXION, "No se puede imprimir", "La Impresora no responde o no está accesible", ex, new String[] {"Verifique el estado de la impresora"}));
					} else {
						String msgError = java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.printing");
						BossError.gestionarError(new PException(BossError.ERR_CONEXION, msgError, msgError, ex, new String[] {}));
					}
                }
            }
        });
        tlbToolBar.add(printButton, 0);
    }

}