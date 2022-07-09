package org.example;

import processing.core.PVector;

import java.awt.image.VolatileImage;
import java.util.ArrayList;

/**
 * @author Guillermo Mora Bertó
 */
public class Snake {
    //Aqui se almacenan las posiciones del snake
    public ArrayList<Integer> posX = new ArrayList<>();
    public ArrayList<Integer> posY = new ArrayList<>();

    public boolean alive = true;
    private BotSnakeMovement movement = new BotSnakeMovement();

    //Posiciones de inicio
    private final PVector initialPosition1;
    private final PVector initialPosition2;

    public int r, g, b;

    public Snake( int r, int g, int b, PVector initialPosition1, PVector initialPosition2) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.initialPosition1 = initialPosition1;
        this.initialPosition2 = initialPosition2;

        restart();
    }

    //Movimiento de la serpiente por el jugador añadiendo la posicion nueva al inicio y eliminando la ultima
    //Lo que se le pasa son las coordenadas de un vector que solo puede tener 1 o -1 de forma que solo se mueva una casilla
    public void mover(int x, int y){
        posX.add(0, posX.get(0) + x);
        posY.add(0, posY.get(0) + y);

        posX.remove( posX.size() - 1);
        posY.remove( posY.size() - 1);
    }
    //Se producirá el movimeinto del bot
    public void mover(PVector apple, boolean[][] map){
        PVector headSnake = new PVector(posX.get(0),posY.get(0));
        PVector tailSnake = new PVector(posX.get(posX.size()-1),posY.get(posY.size()-1));

        PVector nextMove = movement.getNewPosition(map, headSnake, tailSnake, apple, posX.size());

        posX.add(0,posX.get(0) + (int)nextMove.x);
        posY.add(0,posY.get(0) + (int)nextMove.y);

        posX.remove(posX.size()-1);
        posY.remove(posY.size()-1);
    }

    //Añade una nueva posicion
    public void comer(){
        posX.add( posX.get(posX.size() - 1));
        posY.add(posY.get(posY.size() - 1));
    }

    //Poner todo default
    public void restart(){
        posX.clear();
        posY.clear();

        posX.add((int) initialPosition1.x);
        posY.add((int) initialPosition1.y);
        posX.add((int) initialPosition2.x);
        posY.add((int) initialPosition2.y);
    }
}
