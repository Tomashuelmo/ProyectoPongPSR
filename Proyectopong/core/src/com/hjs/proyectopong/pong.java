package com.hjs.proyectopong;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input.Keys;

public class pong extends ApplicationAdapter {
    // Variables para la cámara y el renderizador de formas
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    // Variables para las paletas y la pelota
    private Paddle player1, player2;
    private Ball ball;
    
    @Override
    public void create () {
        // Inicia la cámara con una proyección ortográfica de 800x480 píxeles
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        // Inicia el renderizador de formas
        shapeRenderer = new ShapeRenderer();
        
        // Crea las paletas del jugador 1 y jugador 2 en las posiciones especificadas
        player1 = new Paddle(10, 240);
        player2 = new Paddle(780, 240);
        
        // Crea la pelota en la posición especificada
        ball = new Ball(400, 240);
    }

    @Override
    public void render () {
        // Hace el fondo negro de la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualiza la cámara y establece la matriz de proyección del renderizador
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
     // Dibuja la paleta del jugador 1, 2 y la pelota
        player1.draw(shapeRenderer);  
        player2.draw(shapeRenderer); 
        ball.draw(shapeRenderer);
        shapeRenderer.end(); // Termina el renderizado de formas llenas

        // Movimiento de las paletas a traves del usuario
        handleInput();
        
        // Actualiza las Posiciones de las paletas y la pelota
        updateGame();
    }

 // Entrada del Usuario
    private void handleInput() {
        // Movimiento de las teclas para el uso del usuario 1 y 2
        if (Gdx.input.isKeyPressed(Keys.W)) player1.moveUp();
        
        if (Gdx.input.isKeyPressed(Keys.S)) player1.moveDown();
        
        if (Gdx.input.isKeyPressed(Keys.UP)) player2.moveUp();
        
        if (Gdx.input.isKeyPressed(Keys.DOWN)) player2.moveDown();
    }
    
    
    private void updateGame() {
        // Actualiza el estado de la paleta para el jugador 1 y 2 
        player1.update();
        
        player2.update();
        
        // Actualiza el estado de la pelota en función de las paletas
        ball.update(player1, player2);
    }
    @Override
    public void dispose () {
        // Libera los recursos del renderizador de formas
        shapeRenderer.dispose();
    }
}

