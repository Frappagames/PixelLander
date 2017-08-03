package com.frappagames.pixellander.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Assets management class
 *
 * Created by Jérémy MOREAU on 19/08/15.
 */
public class Assets {
    private static TextureAtlas itemsAtlas;
    public static TextureRegionDrawable lander, landerEngine1, landerEngine2;
    public static Animation landerAnim;

//    public static Sound clickSound;
//    public static Music music;

//    public static Label.LabelStyle fontScore;

    public static void load() {
//        //Fonts
//        BitmapFont souses20Font = new BitmapFont(Gdx.files.internal("fontScore.fnt"), false);
//        fontScore = new Label.LabelStyle(souses20Font, Color.WHITE);

        // Load Textures
        itemsAtlas  = new TextureAtlas(Gdx.files.internal("pixel-lander.pack"));

        lander      = new TextureRegionDrawable(itemsAtlas.findRegion("lander-iddle"));

        TextureRegion[] frames = new TextureRegion[2];
        frames[0]  = new TextureRegionDrawable(itemsAtlas.findRegion("lander-engine1")).getRegion();
        frames[1]  = new TextureRegionDrawable(itemsAtlas.findRegion("lander-engine2")).getRegion();
        landerAnim = new Animation(0.1f, frames);

//        // Load Music and sounds
//        music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
//        music.setLooping(true);
//        music.setVolume(0.5f);
//
//        clickSound = Gdx.audio.newSound(Gdx.files.internal("sound-click.mp3"));
    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }

    public static void dispose() {
        itemsAtlas.dispose();
//        clickSound.dispose();
//        music.dispose();
    }
}
