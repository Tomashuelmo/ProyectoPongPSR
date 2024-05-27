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
        // Inicializa la cámara con una proyección ortográfica de 800x480 píxeles
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        // Inicializa el renderizador de formas
        shapeRenderer = new ShapeRenderer();
        
        // Crea las paletas del jugador 1 y jugador 2 en las posiciones especificadas
        player1 = new Paddle(10, 240);
        player2 = new Paddle(780, 240);
        
        // Crea la pelota en la posición especificada
        ball = new Ball(400, 240);
    }

    @Override
    public void render () {
        // Limpia la pantalla con un color negro
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualiza la cámara y establece la matriz de proyección del renderizador
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Comienza el renderizado de formas llenas (paletas y pelota)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player1.draw(shapeRenderer); // Dibuja la paleta del jugador 1
        player2.draw(shapeRenderer); // Dibuja la paleta del jugador 2
        ball.draw(shapeRenderer); // Dibuja la pelota
        shapeRenderer.end(); // Termina el renderizado de formas llenas

        // Maneja la entrada del usuario (movimiento de las paletas)
        handleInput();
        
        // Actualiza el estado del juego (posiciones de las paletas y la pelota)
        updateGame();
    }

 // Método para manejar la entrada del usuario
    private void handleInput() {
        // Mueve la paleta del jugador 1 hacia arriba si se presiona la tecla W
        if (Gdx.input.isKeyPressed(Keys.W)) player1.moveUp();
        
        // Mueve la paleta del jugador 1 hacia abajo si se presiona la tecla S
        if (Gdx.input.isKeyPressed(Keys.S)) player1.moveDown();
        
        // Mueve la paleta del jugador 2 hacia arriba si se presiona la tecla flecha arriba
        if (Gdx.input.isKeyPressed(Keys.UP)) player2.moveUp();
        
        // Mueve la paleta del jugador 2 hacia abajo si se presiona la tecla flecha abajo
        if (Gdx.input.isKeyPressed(Keys.DOWN)) player2.moveDown();
    }
    
 // Método para actualizar el estado del juego
    
    private void updateGame() {
        // Actualiza el estado de la paleta del jugador 1
        player1.update();
        
        // Actualiza el estado de la paleta del jugador 2
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

