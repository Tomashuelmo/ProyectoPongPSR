package com.hjs.proyectopong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pelota {
    private static final int SIZE = 10; // Tamaño de la pelota
    private Rectangle bounds; // Límites y posición de la pelota
    private float xVelocity = 200; // Velocidad en el eje X
    private float yVelocity = 200; // Velocidad en el eje Y
    private pong game; // Referencia a la instancia del juego

    // Constructor que inicializa la pelota en la posición (x, y) y guarda la instancia del juego
    public Pelota(float x, float y, pong game) {
        bounds = new Rectangle(x, y, SIZE, SIZE);
        this.game = game;
    }

    // Actualizar la posición de la pelota
    public void update(Paleta p1, Paleta p2) {
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

        // Si la pelota sale de los límites izquierdo o derecho de la pantalla, actualizar el puntaje y reiniciar posición
        if (bounds.x <= 0) {
            game.addPointToPlayer2();
            resetPosition();
        } else if (bounds.x >= 800 - SIZE) {
            game.addPointToPlayer1();
            resetPosition();
        }
    }

    // Reiniciar la posición de la pelota al centro de la pantalla y cambiar la dirección
    private void resetPosition() {
        bounds.x = 400;
        bounds.y = 240;
        xVelocity = -xVelocity; // Cambia la dirección de la pelota para el próximo saque
    }

    // Dibujar la pelota en la pantalla
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
