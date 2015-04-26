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
import ru.hackathon3.godxo.Variables;

public class ChooseSizeGod implements Screen,InputProcessor {
    MyGame game;
    Variables varvar;
    private SpriteBatch spriteBatch;
    public Map<String, Texture> textures;
    float ppux,ppuy;
    int z;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        textures = new HashMap<String, Texture>();
        textures.put("menuStatic", new Texture(Gdx.files.internal("menu2/backgroundGodMod.png")));
        textures.put("buttons", new Texture(Gdx.files.internal("chooseSize/panelHod.png")));

        ppux = ((float)Gdx.graphics.getWidth())/480;
        ppuy = ((float)Gdx.graphics.getHeight())/800;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        z++;
        if (z>300) z=0;
        Gdx.gl.glClearColor(1,1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        spriteBatch.draw(textures.get("menuStatic"), 0,0,480*ppux,800*ppuy);
        spriteBatch.draw(textures.get("buttons"), 0,0,480*ppux,800*ppuy);

        spriteBatch.end();
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if((y>730*ppuy && y<790*ppuy && x>403*ppux && x<463*ppux)) {Gdx.input.vibrate(20);}
        // if((height-y)/ppuY >= 228 && (height-y)/ppuY <= 256 && x/ppuX>=228&& x/ppuX<=321)
        //    downCancelBtn = true;
        return true;
    }
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (!Gdx.app.getType().equals(ApplicationType.Android))
            return false;
        if(y>285*ppuy && y<(285+85)*ppuy && x>289*ppux && x<(289+85)*ppux) {
            varvar.cell=1;
            dispose();
            game.setScreen(game.game);
        }
        if(y>243*ppuy && y<328*ppuy && x>135*ppux && x<220*ppux) {
            varvar.cell=0;
            dispose();
            game.setScreen(game.game);
        }
        if(y>409*ppuy && y<(409+85)*ppuy && x>135*ppux && x<(135+85)*ppux) {
            varvar.cell=2;
            dispose();
            game.setScreen(game.game);
        }
        if(y>483*ppuy && y<(483+85)*ppuy && x>288*ppux && x<(288+85)*ppux) {
            varvar.cell=3;
            dispose();
            game.setScreen(game.game);
        }
        if((y>730*ppuy && y<790*ppuy && x>403*ppux && x<463*ppux)) {
            Gdx.app.exit();
            dispose();
        }
        //dispose();
        //game.setScreen(game.game);
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

    public ChooseSizeGod(MyGame game){
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
