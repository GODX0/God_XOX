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

public class MenuScreen implements Screen,InputProcessor {
    MyGame game;
    private SpriteBatch spriteBatch;
    public Map<String, Texture> textures;
    float ppux,ppuy;
    boolean downClassic,downGod,Sound;
    int z;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        downClassic=false;
        downGod=false;
        Sound=true;
        textures = new HashMap<String, Texture>();
        textures.put("menuStatic", new Texture(Gdx.files.internal("menu/menuStatic.png")));
        textures.put("lighting1", new Texture(Gdx.files.internal("menu/menuLightingScene1.png")));
        textures.put("lighting2", new Texture(Gdx.files.internal("menu/menuLightingScene2.png")));
        textures.put("lighting3", new Texture(Gdx.files.internal("menu/menuLightingScene3.png")));

        textures.put("classic", new Texture(Gdx.files.internal("menu/buttonCassicModNoTouch.png")));
        textures.put("classic2", new Texture(Gdx.files.internal("menu/buttonCassicModTouch.png")));
        textures.put("buttonExit", new Texture(Gdx.files.internal("menu/buttonExit.png")));
        textures.put("god", new Texture(Gdx.files.internal("menu/buttonGodModNoTouch.png")));
        textures.put("god2", new Texture(Gdx.files.internal("menu/buttonGodModTouch.png")));
        textures.put("sound", new Texture(Gdx.files.internal("menu/buttonSoundOff.png")));
        textures.put("sound2", new Texture(Gdx.files.internal("menu/buttonSoundOn.png")));

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

        if (z>290){spriteBatch.draw(textures.get("lighting3"), 0,0,480*ppux,800*ppuy);}
        else if (z>285){spriteBatch.draw(textures.get("lighting2"), 0,0,480*ppux,800*ppuy);}
        else if (z>275) spriteBatch.draw(textures.get("menuStatic"), 0,0,480*ppux,800*ppuy);
        else if (z>270){spriteBatch.draw(textures.get("lighting2"), 0,0,480*ppux,800*ppuy);}
        else if (z>265){spriteBatch.draw(textures.get("lighting1"), 0,0,480*ppux,800*ppuy);}
        else spriteBatch.draw(textures.get("menuStatic"), 0,0,480*ppux,800*ppuy);

        if (downClassic){spriteBatch.draw(textures.get("classic2"), 0,0,480*ppux,800*ppuy);}
            else spriteBatch.draw(textures.get("classic"), 0,0,480*ppux,800*ppuy);
        if (downGod) {spriteBatch.draw(textures.get("god2"), 0,0,480*ppux,800*ppuy);}
            else spriteBatch.draw(textures.get("god"), 0,0,480*ppux,800*ppuy);
        if (Sound)  {spriteBatch.draw(textures.get("sound2"), 0,0,480*ppux,800*ppuy);}
            else spriteBatch.draw(textures.get("sound"), 0,0,480*ppux,800*ppuy);
        spriteBatch.draw(textures.get("buttonExit"), 0,0,480*ppux,800*ppuy);
        spriteBatch.end();
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if(y>112*ppuy && y<282*ppuy && x>272*ppux && x<442*ppux) {downClassic = true;Gdx.input.vibrate(20);}
        if(y>497*ppuy && y<647*ppuy && x>52*ppux && x<202*ppux) {downGod = true;Gdx.input.vibrate(20);}
        if(y>730*ppuy && y<790*ppuy && x>13*ppux && x<73*ppux) {Sound = !Sound;Gdx.input.vibrate(20);}
        if((y>730*ppuy && y<790*ppuy && x>403*ppux && x<463*ppux)) {Gdx.input.vibrate(20);}
       // if((height-y)/ppuY >= 228 && (height-y)/ppuY <= 256 && x/ppuX>=228&& x/ppuX<=321)
        //    downCancelBtn = true;
        return true;
    }
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (!Gdx.app.getType().equals(ApplicationType.Android))
            return false;

        if((downClassic)&(y>112*ppuy && y<282*ppuy && x>272*ppux && x<442*ppux)) {
            dispose();
            game.setScreen(game.menu2);

        }
        if((downGod)&(y>497*ppuy && y<647*ppuy && x>52*ppux && x<202*ppux)) {
            dispose();
            game.setScreen(game.menuGod);

        }
        if((y>730*ppuy && y<790*ppuy && x>403*ppux && x<463*ppux)) {
            Gdx.app.exit();
            dispose();
        }
        downClassic = false;
        downGod = false;
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

    public MenuScreen(MyGame game){
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
