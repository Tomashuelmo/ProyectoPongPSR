package com.hjs.proyectopong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Paddle {
    // Constantes para las dimensiones y la velocidad de la paleta
    private static final int WIDTH = 10;
    private static final int HEIGHT = 60;
    private static final int SPEED = 200;

    // Variable para las dimensiones y posición de la paleta
    private Rectangle bounds;
    // Variable para la velocidad vertical de la paleta
    private float yVelocity;

 // Constructor que inicializa la paleta en la posición (x, y) con las dimensiones especificadas
    public Paddle(float x, float y) {
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
 // Método para actualizar la posición de la paleta
    public void update() {
        // Actualiza la posición vertical de la paleta en función de la velocidad y el tiempo transcurrido
        bounds.y += yVelocity * Gdx.graphics.getDeltaTime();
        
        // Asegura que la paleta no se salga de los límites superiores e inferiores de la pantalla
        if (bounds.y < 0) bounds.y = 0;
        if (bounds.y > 480 - HEIGHT) bounds.y = 480 - HEIGHT;
    }

 // Método para dibujar la paleta en la pantalla
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
 // Método para mover la paleta hacia arriba estableciendo la velocidad positiva
    public void moveUp() {
        yVelocity = SPEED;
    }

    // Método para mover la paleta hacia abajo estableciendo la velocidad negativa
    public void moveDown() {
        yVelocity = -SPEED;
    }

 // Método para obtener el rectángulo que representa los límites de la paleta
    public Rectangle getBounds() {
        return bounds;
    }
}
