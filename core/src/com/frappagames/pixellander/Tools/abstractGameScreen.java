package com.frappagames.pixellander.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frappagames.pixellander.PixelLander;

/**
 * Default game screen
 * Created by jmoreau on 12/01/16.
 */
public abstract class abstractGameScreen implements Screen {
    protected final Viewport viewport;
    protected final OrthographicCamera camera;
    protected PixelLander game;
    private Stage stage;
    protected Table table;

    public abstractGameScreen(PixelLander game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.position.set(PixelLander.WIDTH / 2, PixelLander.HEIGHT / 2, 0);
        viewport = new FitViewport(PixelLander.WIDTH, PixelLander.HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    public void draw(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void render(float delta) {
        Color clearColor = Color.valueOf("#000000FF");
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        this.draw(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
    public void dispose() {
        stage.dispose();
    }
}
