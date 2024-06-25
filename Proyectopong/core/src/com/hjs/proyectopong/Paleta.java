package com.hjs.proyectopong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Paleta {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 60;
    private static final int SPEED = 200;

    private Rectangle bounds;
    private float yVelocity;

    private boolean isMoving = false;

    public Paleta(float x, float y) {
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void update() {
        bounds.y += yVelocity * Gdx.graphics.getDeltaTime();
        isMoving= true;
        if (bounds.y < 0){
            bounds.y = 0;
            isMoving = false;
        }
        if (bounds.y > 480 - HEIGHT){
            bounds.y = 480 - HEIGHT;
            isMoving = false;
        }

    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void moveUp() {
        yVelocity = SPEED;
    }

    public void moveDown() {
        yVelocity = -SPEED;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isMoving() {
        return isMoving;
    }
}
