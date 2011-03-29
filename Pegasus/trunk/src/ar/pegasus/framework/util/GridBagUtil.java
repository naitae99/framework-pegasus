package ar.pegasus.framework.util;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.WEST;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagUtil {
    private static final Insets defaultInsets = new Insets(5,5,5,5);
    
    /**
     * 
     * @param gridX
     * @param gridY
     * @param gridWidth
     * @param gridHeight
     * @param weightX
     * @return
     */
    public static GridBagConstraints createSoftHorizontalContraints(int gridX, int gridY, int gridWidth, int gridHeight, double weightX) {
        return new GridBagConstraints(gridX,gridY,gridWidth,gridHeight,weightX,0.0,WEST,HORIZONTAL, defaultInsets,0,0);
    }
    
    public static GridBagConstraints createRigidContraints(int gridX, int gridY, int gridWidth, int gridHeight) {
        return new GridBagConstraints(gridX,gridY,gridWidth,gridHeight,0.0,0.0,WEST,NONE, defaultInsets,0,0);
    }
    
    public static GridBagConstraints createSoftContraints(int gridX, int gridY, int gridWidth, int gridHeight, double weightX, double weightY) {
        return new GridBagConstraints(gridX,gridY,gridWidth,gridHeight,weightX,weightY,WEST,BOTH, defaultInsets,0,0);
    }
}
