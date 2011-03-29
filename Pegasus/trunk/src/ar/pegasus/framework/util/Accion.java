package ar.pegasus.framework.util;


import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;

import ar.pegasus.framework.templates.LayeredDialog;

 
public class Accion extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	List<LayeredDialog> dialogs = new ArrayList<LayeredDialog> () ;
    Map<LayeredDialog, Rectangle> bounds = new HashMap<LayeredDialog, Rectangle> () ;

    public Accion() {
        super("example");
    }
 
    int level ;
    public void actionPerformed(ActionEvent evt) {
//        System.out.println("action triggered with level " + level);
        level-- ;
        if (level == -1) {
        	level = dialogs.size();
        }
        
        // Mostrar los di�logos con nivel <= level
        for (int l = 0; l <= level-1; l++) {
        	if (dialogs.get(l).isHide()) {
//        		System.out.println("[Mostrando] Seteando tama�o di�logo " + panes.get(l) + " en " + bounds.get(panes.get(l)));
        		dialogs.get(l).setBounds(bounds.get(dialogs.get(l)));
        		dialogs.get(l).setHide(false) ;
        	} else {
//        		System.out.println("[Mostrando] Dejando el tama�o del di�logo " + panes.get(l) + " como estaba.");
        	}
        }
        // Ocultar los di�logos con nivel > level
        for (int l = level; l < dialogs.size(); l++) {
        	// Si no est� oculto y es distinto del guardado quiere decir que el dialogo se movio asi que hay que guardar el bounds actual
        	if (!dialogs.get(l).isHide() && !dialogs.get(l).getBounds().equals(bounds.get(dialogs.get(l)))) {
//        		System.out.println("Actualizando bounds di�logo " + dialogs.get(l) + " en " + dialogs.get(l).getBounds());
        		bounds.put (dialogs.get(l), dialogs.get(l).getBounds()) ;
        	}
//        	System.out.println("[Ocultando] Seteando tama�o di�logo " + dialogs.get(l) + " en (0,0,0,0)");
        	dialogs.get(l).setBounds(0, 0, 0, 0);
        	dialogs.get(l).setHide(true) ;
        }

    }

    public void add(LayeredDialog dialog) {
    	System.out.println("Add di�logo " + dialog + " en " + dialog.getBounds());
    	dialogs.add(dialog) ;
    	bounds.put (dialog, dialog.getBounds()) ;
    	level = dialogs.size() ;
    }

	public void remove(LayeredDialog dialog) {
		System.out.println("Remove di�logo " + dialog + " con " + dialog.getBounds());
		dialogs.remove(dialog) ;
		bounds.remove(dialog) ;
		level = dialogs.size() ;		
	}

}
