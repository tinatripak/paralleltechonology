package Billiards_ex1_4;

import java.awt.*;
import java.awt.geom.Ellipse2D;

class Hole {
    private final Color pocketColor;
    private static final int XSIZE = 22;
    private static final int YSIZE = 22;
    int [] xCoordinates;
    int[] yCoordinates;


    public Hole(Component c, Color color, int[] x, int[] y){
        this.pocketColor = color;
        this.xCoordinates = x;
        this.yCoordinates = y;

    }

    public void draw (Graphics2D g){
        for (int i=0; i<xCoordinates.length; i++){
            g.setColor(pocketColor);
            g.fill(new Ellipse2D.Double(xCoordinates[i],yCoordinates[i],XSIZE,YSIZE));
        }
    }
}