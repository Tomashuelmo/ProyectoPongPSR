package com.hjs.proyectopong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.hjs.proyectopong.screen.PongScreen;

public class Pelota {
    private static final int SIZE = 10; // Tamaño de la pelota
    private Rectangle bounds; // Límites y posición de la pelota
    private float xVelocity = 200; // Velocidad en el eje X
    private float yVelocity = 200; // Velocidad en el eje Y
    private PongGame game; // Referencia a la instancia del juego
    public float modifier = 1;

    // Constructor que inicializa la pelota en la posición (x, y) y guarda la instancia del juego
    public Pelota(float x, float y, PongGame game) {
        bounds = new Rectangle(x, y, SIZE, SIZE);
        this.game = game;
    }

    // Actualizar la posición de la pelota
    public void update(Paleta p1, Paleta p2) {
        bounds.x += xVelocity * Gdx.graphics.getDeltaTime() * modifier;
        bounds.y += yVelocity * Gdx.graphics.getDeltaTime()* modifier;

        // Rebota en los límites superior e inferior de la pantalla
        if (bounds.y <= 0 || bounds.y >= 480 - SIZE) {
            yVelocity = -yVelocity;
        }

        // Rebota en las paletas si hay colisión
        if (Intersector.overlaps(bounds, p1.getBounds()) || Intersector.overlaps(bounds, p2.getBounds())) {
            xVelocity = -xVelocity;
        }

        // Si la pelota sale de los límites izquierdo o derecho de la pantalla, actualiza el puntaje y reinicia la posición
        if (bounds.x <= 0) {
            game.addPointToPlayer2();
            resetPosition();
        } else if (bounds.x >= 800 - SIZE) {
            game.addPointToPlayer1();
            resetPosition();
        }
    }

    // Reinicia la posición de la pelota al centro de la pantalla y cambiar la dirección
    private void resetPosition() {
        bounds.x = 400;
        bounds.y = 240;
        xVelocity = -xVelocity; // Cambia la dirección de la pelota para el próximo saque
    }

    // Dibuja la pelota en la pantalla
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
    
    //Metodo para actualizar la velocidad de la pelota para que vaya aumentando 
    public void updateVelocity(float contador) {
        modifier += contador/10000;
        //xVelocity += contador;
        //yVelocity += contador;	bug
    }
}
