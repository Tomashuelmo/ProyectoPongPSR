package com.hjs.proyectopong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input.Keys;



public class pong extends ApplicationAdapter {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private Paleta player1, player2;
    private Pelota ball;
    private int scorePlayer1, scorePlayer2; // Variables para los puntajes de los jugadores
    
    @Override
    public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        player1 = new Paleta(10, 240);
        player2 = new Paleta(780, 240);
        ball = new Pelota(400, 240, this); // Pasamos la instancia del juego a la pelota

        scorePlayer1 = 0; // Inicializamos el puntaje del jugador 1
        scorePlayer2 = 0; // Inicializamos el puntaje del jugador 2
    }

    @Override
    public void render () {
        // Limpiar la pantalla con color negro
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Dibujar las paletas y la pelota
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player1.draw(shapeRenderer);
        player2.draw(shapeRenderer);
        ball.draw(shapeRenderer);
        shapeRenderer.end();

        handleInput(); // Manejar la entrada del usuario
        updateGame();  // Actualizar el estado del juego

        // Dibujar el marcador de puntos
        spriteBatch.begin();
        // Ajustar las coordenadas para que el marcador se dibuje en la parte superior
        font.draw(spriteBatch, "PLAYER 1: " + scorePlayer1, 500, 700);
        font.draw(spriteBatch, "PLAYER 2: " + scorePlayer2, 700, 700);
        spriteBatch.end();
    }

    // Manejar la entrada del usuario para mover las paletas
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.W)) player1.moveUp();
        if (Gdx.input.isKeyPressed(Keys.S)) player1.moveDown();
        if (Gdx.input.isKeyPressed(Keys.UP)) player2.moveUp();
        if (Gdx.input.isKeyPressed(Keys.DOWN)) player2.moveDown();
    }

    // Actualizar el estado del juego
    private void updateGame() {
        player1.update();
        player2.update();
        ball.update(player1, player2);
    }

    // Incrementar el puntaje del jugador 1
    public void addPointToPlayer1() {
        scorePlayer1++;
    }

    // Incrementar el puntaje del jugador 2
    public void addPointToPlayer2() {
        scorePlayer2++;
    }

    @Override
    public void dispose () {
        // Liberar recursos
        shapeRenderer.dispose();
        spriteBatch.dispose();
        font.dispose();
    }
  
}
