package ru.hackathon3.godxo.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

import ru.hackathon3.godxo.MyGame;

public class SplashScreen implements Screen,InputProcessor {
    MyGame game;
    private SpriteBatch spriteBatch;
    public Map<String, Texture> textures;
    Texture pushImage,backImage;
    float ppux,ppuy;
    int z,y,x;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        textures = new HashMap<String, Texture>();
        textures.put("splashStatic", new Texture(Gdx.files.internal("splash/loadingScreenStatic.png")));
        textures.put("lighting1", new Texture(Gdx.files.internal("splash/lightingScene1.png")));
        textures.put("lighting2", new Texture(Gdx.files.internal("splash/lightingScene2.png")));
        textures.put("lighting3", new Texture(Gdx.files.internal("splash/lightingScene3.png")));
        textures.put("clouds1", new Texture(Gdx.files.internal("splash/cloadSliding1LeftToRight.png")));
        textures.put("clouds2", new Texture(Gdx.files.internal("splash/cloadSliding2RightToLeft.png")));
        textures.put("clouds3", new Texture(Gdx.files.internal("splash/cloadSliding3LeftToRight.png")));
        textures.put("touch", new Texture(Gdx.files.internal("splash/touch.png")));

        ppux = ((float)Gdx.graphics.getWidth())/480;
        ppuy = ((float)Gdx.graphics.getHeight())/800;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        z++;y++;
        if (x<480) x+=5;
        if (z>300) z=0;
        if (y>100) y=0;
        Gdx.gl.glClearColor(1,1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        if (z>290){spriteBatch.draw(textures.get("lighting3"), 0,0,480*ppux,800*ppuy);}
        else if (z>285){spriteBatch.draw(textures.get("lighting2"), 0,0,480*ppux,800*ppuy);}
        else if (z>275) spriteBatch.draw(textures.get("splashStatic"), 0,0,480*ppux,800*ppuy);
        else if (z>270){spriteBatch.draw(textures.get("lighting2"), 0,0,480*ppux,800*ppuy);}
        else if (z>265){spriteBatch.draw(textures.get("lighting1"), 0,0,480*ppux,800*ppuy);}
        else spriteBatch.draw(textures.get("splashStatic"), 0,0,480*ppux,800*ppuy);

        if ((x>479)&(y>50))spriteBatch.draw(textures.get("touch"), 0,0,480*ppux,800*ppuy);

        if (x<479) {
            spriteBatch.draw(textures.get("clouds1"), x* ppux, 0, 480 * ppux, 800 * ppuy);
            spriteBatch.draw(textures.get("clouds2"), 0- x* ppux, 0, 480 * ppux, 800 * ppuy);
            spriteBatch.draw(textures.get("clouds3"), x* ppux, 0, 480 * ppux, 800 * ppuy);
        }
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
