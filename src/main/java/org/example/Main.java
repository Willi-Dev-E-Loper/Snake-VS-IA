package org.example;


import processing.core.*;

/**
 * @author Guillermo Mora Bertó
 */
public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main(new String[] {org.example.Main.class.getName()});
    }

    int filas = 25;
    int columnas = 25;
    int bs = 20;

    boolean map [][] = new boolean[filas][columnas];
    PVector direction = new PVector(1, 0);

    boolean greenBox = true;
    boolean purpleBox = true;
    Apple apple = new Apple();

    Snake humanSnake = new Snake(100,200, 100, new PVector(2,2), new PVector(2,1));

    @Override
    public void settings(){
        size(500, 540);
    }

    @Override
    public void setup(){
        frameRate(25);
        initGame();
    }

    @Override
    public void draw(){
        background(25);
        drawMap();
        drawApple();

        plaHumanSnake();
    }

    void initGame(){
        updateMap();
        apple.spawn(map);
    }

    void detectBorder(Snake snake){
        if (snake.posX.get(0) < 0 || snake.posX.get(0) > (columnas -1) || snake.posY.get(0) < 0  || snake.posY.get(0) > (filas - 1)){
            snake.restart();
            initGame();
        }
    }
    void crashSnake (Snake s ){
        //Comprueba si se choca consigo mismo
        if (s.alive){
            for (int i=0; i<s.posX.size(); i++){
                if (s.posX.get(0) == s.posX.get(i) && s.posY.get(0) == s.posY.get(i)){
                    s.restart();
                }
            }
        }
    }

    void updateMap(){
        //Asignamos todo el mapa a true primero
        for (int i=0; i<filas;i++){
            for (int j = 0; j < columnas; j++){
                map[i][j] = true;
            }
        }

    }

    void drawMap(){
        //Dibujamos el cuadrado gris de abajo
        fill(200, 100, 100);
        rect(0, 500, width, 40);

        //Dibujamos las dos casillas de el marcador
        fill(100, 200, 100);
        rect(30, 510, 210, 20);

        fill(100, 100, 200);
        rect(270, 510, 210, 20);

        if (!greenBox){
            fill(250, 50, 50);
            rect(30,510, 210, 20);
        }
        if(!purpleBox){
            fill(250, 50, 50);
            rect(270, 510, 210, 20);
        }
    }

    void drawApple(){
        fill(215,0,75);
        rect(apple.position.x * bs, apple.position.y * bs, bs, bs);
    }

    void plaHumanSnake(){
        //Si esta viva que juegue
        if (humanSnake.alive){
            moveHumanSnake();
            drawSnake(humanSnake);
            detectBorder(humanSnake);
            crashSnake(humanSnake);
        }
    }

    void moveHumanSnake(){
        humanSnake.mover((int) direction.x, (int) direction.y);
        eat(humanSnake);
    }
    void eat(Snake snake){
        //Comprueba si la cabeza de la serpiente (popsicion 0) es la misma que la de la manzana
        if(snake.posX.get(0) == apple.position.x && snake.posY.get(0) == apple.position.y){
            apple.spawn(map);
            snake.comer();
        }
    }

    void drawSnake(Snake snake){
        //Rellenamos con el color asignado y luego recorremos todas sus posciones dibujando en pantalla
        fill(snake.r, snake.g, snake.b);
        for (int i = 0; i< snake.posX.size(); i++){
            rect(snake.posX.get(i) * bs, snake.posY.get(i) * bs,bs,bs);
        }
    }
    void restartGame(){
        //Comienza la partida de 0
        humanSnake.restart();
        initGame();
    }

    @Override
    public void keyPressed(){
        if (key == 'w' || keyCode == UP){
            if (direction.y != 1){
                direction.set(0, -1);
            }
        }
        if (key == 's' || keyCode == DOWN){
            if (direction.y != -1){
                direction.set(0, 1);
            }
        }
        if (key == 'a' || keyCode == LEFT){
            if (direction.x != 1){
                direction.set(-1, 0);
            }
        }
        if (key == 'd' || keyCode == RIGHT){
            if (direction.x != -1){
                direction.set(1, 0);
            }
        }
        //Reiniciiar el juego
        if (key == ' '){
            restartGame();
        }
    }

    @Override
    public void mouseClicked(){
        //Comprueba si esta en el cuadrado verde el click, Si es asai cambiara el estado del snake muerto-vivo
        if (mouseX >= 30 && mouseX <= 240 && mouseY >= 510 && mouseY <= 530){
            greenBox = !greenBox;
        }
        if (mouseX >= 270 && mouseX <= 480 && mouseY >= 510 && mouseY <= 530){
            purpleBox = !purpleBox;
        }
    }
}