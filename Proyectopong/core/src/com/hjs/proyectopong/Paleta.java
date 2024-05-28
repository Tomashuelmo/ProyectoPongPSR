package com.hjs.proyectopong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Paleta {
    // Las dimensiones y la velocidad de la paleta
    private static final int WIDTH = 10;
    private static final int HEIGHT = 60;
    private static final int SPEED = 200;

    //  Las dimensiones y posición de la paleta
    private Rectangle bounds;
    // Es la velocidad vertical de la paleta
    private float yVelocity;

 //  Inicializa la paleta en la posición (x, y) con las dimensiones especificadas
    public Paleta(float x, float y) {
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public void update() {
        // Actualiza la posición vertical de la paleta en función de la velocidad y el tiempo transcurrido
        bounds.y += yVelocity * Gdx.graphics.getDeltaTime();
        
        // Hace que la paleta no se salga de los límites superiores e inferiores de la pantalla
        if (bounds.y < 0) bounds.y = 0;
        if (bounds.y > 480 - HEIGHT) bounds.y = 480 - HEIGHT;
    }

 //  Dibuja la paleta en la pantalla
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
 //  Mueve la paleta hacia arriba estableciendo la velocidad positiva
    public void moveUp() {
        yVelocity = SPEED;
    }

    // Mueve la paleta hacia abajo estableciendo la velocidad negativa
    public void moveDown() {
        yVelocity = -SPEED;
    }

 // El rectángulo que representa los límites de la paleta
    public Rectangle getBounds() {
        return bounds;
    }
}