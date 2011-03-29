package ar.pegasus.framework.componentes;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;

public class TextAreaTabListener extends KeyAdapter {

    @Override
	public void keyPressed(KeyEvent evt) {
        if(evt.getKeyCode() == KeyEvent.VK_TAB) {
            ((JTextArea)evt.getSource()).setTabSize(0);
            ((JTextArea)evt.getSource()).transferFocus();
        }
    }

}