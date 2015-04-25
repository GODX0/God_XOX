package ru.hackathon3.godxo.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import ru.hackathon3.godxo.MyGame;

public class SplashScreen implements Screen,InputProcessor {
    MyGame game;
    private SpriteBatch spriteBatch;
    Texture pushImage,backImage;
    float ppux,ppuy;
    int z;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        backImage = new Texture(Gdx.files.internal("splash.png"));
        pushImage = new Texture(Gdx.files.internal("push.png"));
        ppux = ((float)Gdx.graphics.getWidth())/480;
        ppuy = ((float)Gdx.graphics.getHeight())/800;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        z++;
        if (z>100) z=0;
        Gdx.gl.glClearColor(1,1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(backImage, 0,0,480*ppux,800*ppuy);
        if (z>50)spriteBatch.draw(pushImage, 0,80,480*ppux,53*ppuy);
        spriteBatch.end();
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        return true;
    }
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (!Gdx.app.getType().equals(ApplicationType.Android))
            return false;
        dispose();
        game.setScreen(game.game);
        return true;
    }
    @Override
    public void hide() {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void resize(int width, int height) {
    }

    public SplashScreen(MyGame game){
        this.game = game;
    }
    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        return true;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        return true;
    }
}
