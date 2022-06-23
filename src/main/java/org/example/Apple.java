package org.example;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Guillermo Mora Bertó
 */
public class Apple extends PApplet {
    public PVector position = new PVector(0, 0);
    public Apple(){

    }

    //Le damos la nueva posicion a la manzana comprobando que no haya serpiente
    public void spawn(boolean[][] map){
        boolean done = false;
        while (!done){
            int x = (int) random(0,24);
            int y = (int) random(0, 24);

            if(map[y][x]){
                done = true;
                position.x = x;
                position.y = y;
            }
        }
    }

    public PVector getPosition() {
        return position;
    }
}
