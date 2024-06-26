package com.hjs.proyectopong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hjs.proyectopong.Pong;

public class MainMenuScreen implements Screen {

    private final Pong game;
    private Stage stage;
    private BitmapFont font;
    private SpriteBatch batch;

    public MainMenuScreen(final Pong game) {
        this.game = game;
        
        // Crea una nueva etapa con una vista de pantalla
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        font = new BitmapFont();
        
        // Define el estilo para los botones de texto
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        //textButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("button_up.png")));
        //textButtonStyle.down = new TextureRegionDrawable(new Texture(Gdx.files.internal("button_down.png")));

        
        // Crea botones para el menú
        TextButton playButton = new TextButton("Jugar", textButtonStyle);
        TextButton serverButton = new TextButton("Server", textButtonStyle);
        TextButton quitButton = new TextButton("Salir", textButtonStyle);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        // Añade botones a la tabla
        table.add(playButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(serverButton).fillX().uniformX();
        table.row();
        table.add(quitButton).fillX().uniformX();
        
        // Añade listeners a los botones para manejar eventos de clic
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new PongScreen(game));

            }
        });

        serverButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new ServerPongScreen(game));
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    //limpia la pantalla y lo actualiza 
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
 // Libera recursos
    public void dispose() {
        stage.dispose();
        font.dispose();
        batch.dispose();
    }
}

