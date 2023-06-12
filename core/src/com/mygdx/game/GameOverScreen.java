package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameOverScreen implements Screen {

    final GameLauncher game;

    final Music music;

    final Texture background;

    final ShapeRenderer shapeRenderer;

    long lastSwitch;

    OrthographicCamera camera;

    int selected;

    public GameOverScreen(final GameLauncher game) {
        this.game = game;

        background = new Texture(Gdx.files.internal("game-over-bg.png"));
        shapeRenderer = new ShapeRenderer();

        selected = 0;

        lastSwitch = TimeUtils.nanoTime();

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
        game.batch.draw(background, 0, 0);
        game.batch.end();

        switch (selected) {
            case 0:
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.rect(405, 245, 800, 100);
                shapeRenderer.end();
                break;
            case 1:
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.rect(620, 80, 365, 100);
                shapeRenderer.end();
                break;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) && TimeUtils.nanoTime() - lastSwitch > 150000000){
            selected = (selected + 1 ) % 2;
            lastSwitch = TimeUtils.nanoTime();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && TimeUtils.nanoTime() - lastSwitch > 150000000){
            selected = (selected + 1) % 2;
            lastSwitch = TimeUtils.nanoTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && TimeUtils.nanoTime() - lastSwitch > 150000000) {
            switch (selected) {
                case 0:
                    game.setScreen(new GameScreen(game));
                    dispose();
                    break;
                case 1:
                    Gdx.app.exit();
                    break;
            }
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
        music.dispose();
        background.dispose();
        shapeRenderer.dispose();
    }

}
