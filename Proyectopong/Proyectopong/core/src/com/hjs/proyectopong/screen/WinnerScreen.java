package com.hjs.proyectopong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hjs.proyectopong.Pong;
import com.hjs.proyectopong.PongGame;

public class WinnerScreen implements Screen {
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private String winnerMessage;
    private Pong game;

    public WinnerScreen(Pong game, int winnerId) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        winnerMessage = "EL GANADOR ES EL JUGADOR " + winnerId;
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen for a Game.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        font.draw(spriteBatch, winnerMessage, 350, 240);
        font.draw(spriteBatch, "TOCA ENTER PARA VOLVER AL MENU.", 300, 200);
        spriteBatch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.ENTER)) {
            game.setScreen(new MainMenuScreen(game));
        }
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
        spriteBatch.dispose();
        font.dispose();
    }
}
