package com.hjs.proyectopong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Paleta {
	//Tamaños y velocidad de las paleta
    private static final int WIDTH = 10;
    private static final int HEIGHT = 60;
    private static final int SPEED = 200;

    private Rectangle bounds;
    private float yVelocity;

    private boolean isMoving = false;
    
    //Se inicializa la posicion de la paleta y el tamaño
    public Paleta(float x, float y) {
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    // Método para actualizar la posición de la paleta
    public void update() {
        bounds.y += yVelocity * Gdx.graphics.getDeltaTime();
        isMoving= true;
     // Limita la paleta para que no se salga de la pantalla e indicando que no este en movimiento al llegar al borde
        if (bounds.y < 0){
            bounds.y = 0;
            isMoving = false;
        }
        
        //Lo mismo pero con la otra paleta 
        if (bounds.y > 480 - HEIGHT){
            bounds.y = 480 - HEIGHT;
            isMoving = false;
        }

    }
    //Dibuja la paleta 
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    //Metodo para mover la paleta hacia arriba
    public void moveUp() {
        yVelocity = SPEED;
    }
    //Metodo para mover la paleta hacia abajo
    public void moveDown() {
        yVelocity = -SPEED;
    }
    // Método para obtener los límites de la paleta
    public Rectangle getBounds() {
        return bounds;
    }
 // Método para verificar si la paleta está en movimiento y devuelve true o false segun si se esta moviendo la paleta 
    public boolean isMoving() {
        return isMoving;
    }
}
