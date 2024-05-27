package com.hjs.proyectopong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;

public class Ball {
    // Constante para el tamaño de la pelota
    private static final int SIZE = 10;
    
    // Variable para los límites y la posición de la pelota
    private Rectangle bounds;
    
    // Variables para la velocidad en los ejes X e Y
    private float xVelocity = 200;
    private float yVelocity = 200;

 // Constructor que inicializa la pelota en la posición (x, y) con el tamaño especificado
    public Ball(float x, float y) {
        bounds = new Rectangle(x, y, SIZE, SIZE);
    }
    
 // Método para actualizar la posición de la pelota
    public void update(Paddle p1, Paddle p2) {
        // Actualiza la posición de la pelota en función de la velocidad y el tiempo transcurrido
        bounds.x += xVelocity * Gdx.graphics.getDeltaTime();
        bounds.y += yVelocity * Gdx.graphics.getDeltaTime();

        // Rebotar en los límites superior e inferior de la pantalla
        if (bounds.y <= 0 || bounds.y >= 480 - SIZE) {
            yVelocity = -yVelocity;
        }

        // Rebotar en las paletas si hay colisión
        if (Intersector.overlaps(bounds, p1.getBounds()) || Intersector.overlaps(bounds, p2.getBounds())) {
            xVelocity = -xVelocity;
        }

        // Si la pelota sale de los límites izquierdo o derecho de la pantalla, reiniciar posición
        if (bounds.x <= 0 || bounds.x >= 800 - SIZE) {
            bounds.x = 400;
            bounds.y = 240;
        }
    }

 // Método para dibujar la pelota en la pantalla
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
