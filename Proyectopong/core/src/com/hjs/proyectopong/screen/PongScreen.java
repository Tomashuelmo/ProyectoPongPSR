package com.hjs.proyectopong.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hjs.proyectopong.Paleta;
import com.hjs.proyectopong.Pelota;
import com.hjs.proyectopong.Pong;
import com.hjs.proyectopong.PongGame;
import com.hjs.proyectopong.net.Cliente;
import com.hjs.proyectopong.net.Redes;

public class PongScreen implements PongGame {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    public static Paleta player1, player2;
    public static Pelota ball;
    public static int scorePlayer1, scorePlayer2;
    private Pong game;

    private FitViewport viewport;

    public PongScreen(Pong game) {
        //inicia el cliente de red
    	Redes.empezarCliente();
        this.game = game;
        //crea y configura la cámara con un tamaño de 800x480 píxeles
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        //configura la camara la posiciona centrandola en la pantalla 
        viewport = new FitViewport(800, 480, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        //incializa el shaperender, el spritebatch, y la bitmapfont
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        //crea la pelota y las paletas en las posiciones iniciales
        player1 = new Paleta(10, 240);
        player2 = new Paleta(780, 240);
        ball = new Pelota(400, 240, this);
       
        //inicia el contador de puntos de los jugadores 
        scorePlayer1 = 0;
        scorePlayer2 = 0; 
        
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
    	// Limpia la pantalla con color negro
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //Cuando el cliente esta en estado "ESPERANDO" muestra un mensaje
        if (Redes.cliente.estado.equals(Cliente.EstadoCliente.ESPERANDO)) {
            spriteBatch.begin();
            font.draw(spriteBatch, "ESPERANDO JUGADORES", 700, 460);
            spriteBatch.end();
            return;
        }
        //Cuando el cliente esta en estado "FIN" muestra un mensaje con el ganador y el id
        if (Redes.cliente.estado.equals(Cliente.EstadoCliente.FIN)) {
            System.out.println("GANADOR :" + Redes.ganador);
            game.setScreen(new WinnerScreen(game, Redes.ganador));
            return;
        }

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
     
       //Dibuja las paletas y la pelota
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player1.draw(shapeRenderer);
        player2.draw(shapeRenderer);
        ball.draw(shapeRenderer);
        shapeRenderer.end();

        //Maneja las entradas de usuario (las teclas) y actualiza el estado del juego
        handleInput();
        updateGame();
        
        //Crea el marcador y los posiciona en la pantalla
        spriteBatch.begin();
        font.draw(spriteBatch, "PLAYER 1: " + scorePlayer1, 300, 700);
        font.draw(spriteBatch, "PLAYER 2: " + scorePlayer2, 700, 700);
        spriteBatch.end();
    }
    //Son las entradas del usuario y mandan el mensaje al servidor si se mueven para arriba(W) o abajo (S)
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.W)) Redes.cliente.enviarMensaje("arriba");
        if (Gdx.input.isKeyPressed(Keys.S)) Redes.cliente.enviarMensaje("abajo");
    }

    //Actualiza el estado del juego 
    private void updateGame() {
        player1.update();
        player2.update();
        ball.update(player1, player2);
    }

    public void addPointToPlayer1() {
        //   scorePlayer1++;
    }

    public void addPointToPlayer2() {
        //   scorePlayer2++;
    }

    @Override
    public void resize(int width, int height) {
        // Se llama cuando cambia el tamaño de la pantalla.
        viewport.update(width, height
        );
    }

    @Override
    public void pause() {
  
    }

    @Override
    public void resume() {
     
    }

    @Override
    public void hide() {
    	
    }

    @Override
    public void dispose() {
    	// Libera recursos
        shapeRenderer.dispose();
        spriteBatch.dispose();
        font.dispose();
    }
}
