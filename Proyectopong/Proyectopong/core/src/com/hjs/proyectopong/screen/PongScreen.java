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
        Redes.empezarCliente();
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        viewport = new FitViewport(800, 480, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        player1 = new Paleta(10, 240);
        player2 = new Paleta(780, 240);
        ball = new Pelota(400, 240, this);

        scorePlayer1 = 0;
        scorePlayer2 = 0;
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen for a Game.
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Redes.cliente.estado.equals(Cliente.EstadoCliente.ESPERANDO)) {
            spriteBatch.begin();
            font.draw(spriteBatch, "ESPERANDO JUGADORES", 700, 460);
            spriteBatch.end();
            return;
        }
        if (Redes.cliente.estado.equals(Cliente.EstadoCliente.FIN)) {
            System.out.println("GANADOR :" + Redes.ganador);
            game.setScreen(new WinnerScreen(game, Redes.ganador));
            return;
        }

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player1.draw(shapeRenderer);
        player2.draw(shapeRenderer);
        ball.draw(shapeRenderer);
        shapeRenderer.end();

        handleInput();
        updateGame();

        spriteBatch.begin();
        font.draw(spriteBatch, "CLIENTE" + scorePlayer1, 450, 460);
        font.draw(spriteBatch, "PLAYER 1: " + scorePlayer1, 20, 460);
        font.draw(spriteBatch, "PLAYER 2: " + scorePlayer2, 700, 460);
        spriteBatch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.W)) Redes.cliente.enviarMensaje("arriba");
        if (Gdx.input.isKeyPressed(Keys.S)) Redes.cliente.enviarMensaje("abajo");
    }

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
        // Called when the screen size changes.
        viewport.update(width, height
        );
    }

    @Override
    public void pause() {
        // Called when the game is paused.
    }

    @Override
    public void resume() {
        // Called when the game is resumed from a paused state.
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen for a Game.
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        font.dispose();
    }
}
