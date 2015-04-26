package ru.hackathon3.godxo.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

import ru.hackathon3.godxo.MyGame;
import ru.hackathon3.godxo.Variables;

public class MenuScreen2 implements Screen,InputProcessor {
    MyGame game;
    Variables varvar;
    private SpriteBatch spriteBatch;
    public Map<String, Texture> textures;
    float ppux,ppuy;
    boolean downBot,downFriends,downNetwork;
    int z;
    @Override
    public void show() {

        spriteBatch = new SpriteBatch();
        downBot=false;
        downFriends=false;
        downNetwork=false;
        textures = new HashMap<String, Texture>();
        textures.put("menuStatic", new Texture(Gdx.files.internal("menu2/backgroundClassic.png")));

        textures.put("bot", new Texture(Gdx.files.internal("menu2/buttonBotNoTouch.png")));
        textures.put("bot2", new Texture(Gdx.files.internal("menu2/buttonBotTouch.png")));
        textures.put("friends", new Texture(Gdx.files.internal("menu2/buttonFriendsNoTouch.png")));
        textures.put("friends2", new Texture(Gdx.files.internal("menu2/buttonFriendsTouch.png")));
        textures.put("network", new Texture(Gdx.files.internal("menu2/buttonNetworkNoTouch.png")));
        textures.put("network2", new Texture(Gdx.files.internal("menu2/buttonNetworkTouch.png")));
        textures.put("back", new Texture(Gdx.files.internal("menu2/buttonBack.png")));

        ppux = ((float)Gdx.graphics.getWidth())/480;
        ppuy = ((float)Gdx.graphics.getHeight())/800;
        Gdx.input.setCatchBackKey(true);
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

        if (downBot){spriteBatch.draw(textures.get("bot2"), 0,0,480*ppux,800*ppuy);}
            else spriteBatch.draw(textures.get("bot"), 0,0,480*ppux,800*ppuy);
        if (downFriends) {spriteBatch.draw(textures.get("friends2"), 0,0,480*ppux,800*ppuy);}
            else spriteBatch.draw(textures.get("friends"), 0,0,480*ppux,800*ppuy);
        if (downNetwork)  {spriteBatch.draw(textures.get("network2"), 0,0,480*ppux,800*ppuy);}
            else spriteBatch.draw(textures.get("network"), 0,0,480*ppux,800*ppuy);
        spriteBatch.draw(textures.get("back"), 0,0,480*ppux,800*ppuy);
        spriteBatch.end();
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if(y>162*ppuy && y<237*ppuy && x>124*ppux && x<369*ppux)
        { downBot = true;Gdx.input.vibrate(20);}
        if(y>259*ppuy && y<334*ppuy && x>124*ppux && x<369*ppux)
        {downFriends = true;Gdx.input.vibrate(20);}
        if(y>355*ppuy && y<430*ppuy && x>124*ppux && x<369*ppux)
        { downNetwork=true;Gdx.input.vibrate(20);}
        if((y>730*ppuy && y<790*ppuy && x>403*ppux && x<463*ppux)) {Gdx.input.vibrate(20);}
        //if(y>730*ppuy && y<790*ppuy && x>13*ppux && x<73*ppux) Sound = !Sound;
        // if((height-y)/ppuY >= 228 && (height-y)/ppuY <= 256 && x/ppuX>=228&& x/ppuX<=321)
        //    downCancelBtn = true;
        return true;
    }
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        //if (!Gdx.app.getType().equals(ApplicationType.Android))
        //    return false;
        if(downFriends&(y>259*ppuy && y<334*ppuy && x>124*ppux && x<369*ppux)) {
            varvar.cell=1;
            dispose();
            game.setScreen(game.game);
        }
        if(downBot&(y>162*ppuy && y<237*ppuy && x>124*ppux && x<369*ppux)) {
            varvar.cell=0;
            dispose();
            game.setScreen(game.game);
        }
        if((y>730*ppuy && y<790*ppuy && x>403*ppux && x<463*ppux)) {
            dispose();
            game.setScreen(game.menu);
        }
        downBot = false;
        downFriends = false;
        downNetwork=false;

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

    public MenuScreen2(MyGame game){
        this.game = game;
    }
    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        Gdx.input.setCatchBackKey(false);
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
        if(keycode == Input.Keys.BACK ){

            this.dispose();
            game.setScreen(game.menu);

        }
        return true;
    }
}
