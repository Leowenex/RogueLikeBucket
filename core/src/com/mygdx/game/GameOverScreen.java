package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {

    final GameLauncher game;

    final Music music;

    OrthographicCamera camera;

    public GameOverScreen(final GameLauncher game) {
        this.game = game;

        music = Gdx.audio.newMusic(Gdx.files.internal("musics/CursedVillage.ogg"));
        music.setLooping(true);
        music.setVolume(0.5f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);
    }


    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Game Over !! ", 100, 150);
        game.font.draw(game.batch, "You were killed by the monster, click anywhere to restart", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

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

    }

}
