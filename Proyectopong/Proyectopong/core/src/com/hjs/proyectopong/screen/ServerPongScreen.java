package com.hjs.proyectopong.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hjs.proyectopong.Paleta;
import com.hjs.proyectopong.Pelota;
import com.hjs.proyectopong.Pong;
import com.hjs.proyectopong.PongGame;
import com.hjs.proyectopong.net.Redes;

public class ServerPongScreen implements PongGame {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    public static Paleta player1, player2;
    private Pelota ball;
    private int scorePlayer1, scorePlayer2;
    private Pong game;

    float contador = 1;

    public ServerPongScreen(Pong game) {
        this.game = game;
        Redes.empezarServidor();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
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

        contador += delta;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!Redes.server.iniciaJuego){
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
        ball.updateVelocity(contador);
        spriteBatch.begin();
        font.draw(spriteBatch, "PLAYER 1: " + scorePlayer1, 20, 460);
        font.draw(spriteBatch, "PLAYER 2: " + scorePlayer2, 700, 460);
        spriteBatch.end();
    }

    private void handleInput() {
        /*if (Gdx.input.isKeyPressed(Keys.W)) player1.moveUp();
        if (Gdx.input.isKeyPressed(Keys.S)) player1.moveDown();
        if (Gdx.input.isKeyPressed(Keys.UP)) player2.moveUp();
        if (Gdx.input.isKeyPressed(Keys.DOWN)) player2.moveDown();*/
    }

    private void updateGame() {
        if(scorePlayer1 >= 5 || scorePlayer2 >= 5) {
            Redes.server.terminarJuego(scorePlayer1 > scorePlayer2 ? 0 : 1);
            Redes.server.iniciaJuego = false;
            game.setScreen(new ServerPongScreen(game));
            return;
        }
        player1.update();
        player2.update();
        ball.update(player1, player2);
        if(player1.isMoving()){
            Redes.server.enviarMensaje("jugador#0#"+player1.getBounds().y);
        }
        if(player2.isMoving()) {
            Redes.server.enviarMensaje("jugador#1#" + player2.getBounds().y);
        }
        Redes.server.enviarMensaje("pelota#"+ball.getBounds().x+"#"+ball.getBounds().y);
    }

    public void addPointToPlayer1() {
        scorePlayer1++;
        Redes.server.enviarMensaje("score#0#"+scorePlayer1);
        ball.modifier = 1;
        contador = 1;
    }

    public void addPointToPlayer2() {
        scorePlayer2++;
        Redes.server.enviarMensaje("score#1#"+scorePlayer2);
        ball.modifier = 1;
        contador = 1;
    }

    @Override
    public void resize(int width, int height) {
        // Called when the screen size changes.
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
