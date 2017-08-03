package com.frappagames.pixellander.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.frappagames.pixellander.PixelLander;
import com.frappagames.pixellander.Tools.Assets;
import com.frappagames.pixellander.Tools.abstractGameScreen;

/**
 * Game class
 *
 * Created by Jérémy MOREAU on 14/08/15.
 */
public class GameScreen extends abstractGameScreen {
    private Stage uiStage;

    private float stateTime;

    public GameScreen(final PixelLander gameApp) {
        super(gameApp);

        stateTime = 0f;

        uiStage = new Stage(viewport);
        Gdx.input.setInputProcessor(uiStage);

        // Play Music ♫
//        if (Settings.soundEnabled) Assets.music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // Exit to game menu on ESCAPE
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            Assets.playSound(Assets.clickSound);
//            game.setScreen(new MenuScreen(game));
            Gdx.app.exit();
        }

        TextureRegion landerRegion;

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            landerRegion = (TextureRegion) Assets.landerAnim.getKeyFrame(stateTime, true);

            stateTime += Gdx.graphics.getDeltaTime();
        } else {
            stateTime = 0f;
            landerRegion = Assets.lander.getRegion();
        }

        game.batch.begin();
        game.batch.draw(landerRegion, 10, 10);
        game.batch.end();

        uiStage.act(delta);
        uiStage.draw();
    }
}
