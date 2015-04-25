package ru.hackathon3.godxo.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import ru.hackathon3.godxo.MyGame;
import ru.hackathon3.godxo.screens.SplashScreen;

public class GameScreen implements Screen,InputProcessor {
    MyGame game;
    private SpriteBatch spriteBatch;
    public  Map<String, Texture> textures;
    float w,h,ppux,ppuy;

    public OrthographicCamera cam;
    float CAMERA_WIDTH = 480F;
    float CAMERA_HEIGHT = 800F;

    int pole_size=7;
    float offset;
    float cell_width;
    int [][] vert =
            {
                    {-1,-1,-1,1,1,-1,-1,-1},
                    {-1,-1, 1,0,0, 1,-1,-1},
                    {-1, 1, 0,0,0, 0, 1,-1},
                    { 1, 1, 0,0,0, 0, 1, 1},
                    {-1, 1, 0,0,0, 0, 1,-1},
                    {-1,-1, 1,0,0, 1,-1,-1},
                    {-1,-1,-1,1,1,-1,-1,-1}
            };
    int [][] hor =
            {
                    {-1,-1,-1,1,-1,-1,-1},
                    {-1,-1, 1,1, 1,-1,-1},
                    {-1, 1, 0,0, 0, 1,-1},
                    { 1, 0, 0,0, 0, 0, 1},
                    { 1, 0, 0,0, 0, 0, 1},
                    {-1, 1, 0,0, 0, 1,-1},
                    {-1,-1, 1,1, 1,-1,-1},
                    {-1,-1,-1,1,-1,-1,-1}
            };
    int [][] pole =
            {
                    {-1,-1,-1,1,-1,-1,-1},
                    {-1,-1, 0,0, 0,-1,-1},
                    {-1, 0, 0,0, 0, 0,-1},
                    { 1, 0, 0,0, 0, 0, 2},
                    {-1, 0, 0,0, 0, 0,-1},
                    {-1,-1, 0,0, 0,-1,-1},
                    {-1,-1,-1,2,-1,-1,-1}
            };

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        textures = new HashMap<String, Texture>();

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        w = (float)Gdx.graphics.getWidth();
        h = (float)Gdx.graphics.getHeight();
        cell_width= (CAMERA_WIDTH/(pole_size+1));
        offset=(CAMERA_HEIGHT/2-(cell_width*(pole_size+1))/2)-13;
        ppux = ((float)Gdx.graphics.getWidth())/480;
        ppuy = ((float)Gdx.graphics.getHeight())/800;

        textures.put("logo", new Texture(Gdx.files.internal("badlogic.jpg")));
        textures.put("gray", new Texture(Gdx.files.internal("gray.png")));
        textures.put("blue", new Texture(Gdx.files.internal("blue.png")));
        textures.put("blue1", new Texture(Gdx.files.internal("blue1.png")));
        textures.put("gray1", new Texture(Gdx.files.internal("gray1.png")));
        textures.put("X", new Texture(Gdx.files.internal("X.png")));
        textures.put("O", new Texture(Gdx.files.internal("O.png")));
        textures.put("back", new Texture(Gdx.files.internal("back.png")));
    }
    public GameScreen(MyGame game){
        this.game = game;
    }

    public void showBoard(){
        for (int i = 0; i < pole_size; i++) {
            for (int j = 0; j < pole_size+1; j++) {
                if (vert[i][j]==1 ) spriteBatch.draw(textures.get("blue"), cell_width*j, cell_width*i+cell_width/2+offset, cell_width, cell_width);
                if (vert[i][j]==0 ) spriteBatch.draw(textures.get("gray"), cell_width*j, cell_width*i+cell_width/2+offset, cell_width, cell_width);
            }
        }
        for (int i = 0; i < pole_size+1; i++) {
            for (int j = 0; j < pole_size; j++) {
                if (hor[i][j]==1 ) spriteBatch.draw(textures.get("blue1"), cell_width*j+cell_width/2, cell_width*i+offset, cell_width, cell_width);
                if (hor[i][j]==0 ) spriteBatch.draw(textures.get("gray1"), cell_width*j+cell_width/2, cell_width*i+offset, cell_width, cell_width);
            }
        }
        for (int i = 0; i < pole_size; i++) {
            for (int j = 0; j < pole_size; j++) {
                if (pole[i][j]==2  ) spriteBatch.draw(textures.get("X"), cell_width*j+cell_width/2, cell_width*i+cell_width/2+offset, cell_width, cell_width);
                if (pole[i][j]==1 ) spriteBatch.draw(textures.get("O"), cell_width*j+cell_width/2, cell_width*i+cell_width/2+offset, cell_width, cell_width);
            }
        }
    }

    public void SetCamera(float x, float y){
        this.cam.position.set(x, y,0);
        this.cam.update();
    }

    @Override
    public void render(float delta) {
        SetCamera(CAMERA_WIDTH/2, CAMERA_HEIGHT / 2f);
        spriteBatch.setProjectionMatrix(this.cam.combined);

        Gdx.gl.glClearColor(1,1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(textures.get("back"), 0,0);
        showBoard();
        spriteBatch.end();
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        return true;
    }
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        for (int i = 0; i < pole_size+1; i++) {
            if ((x>(i*cell_width+cell_width/4)*ppux)&(x<(i*cell_width+cell_width-cell_width/4)*ppux))
                for (int j = 0; j <pole_size; j++) {
                    if ((h-y>(j*cell_width+cell_width/4)*ppuy+cell_width+offset*ppuy)&(h-y<(j*cell_width+cell_width-cell_width/4)*ppuy+cell_width+offset*ppuy))
                        if(vert[j][i]==0) {
                            vert[j][i]=1;//[y][x]
                            if ( (vert[j][i-1]==1)&(hor[j][i-1]==1)&(hor[j+1][i-1]==1) ) pole[j][i-1]=1;
                            if ( (vert[j][i+1]==1)&(hor[j][i]==1)  &(hor[j+1][i]==1)   ) pole[j][i]=1;
                        }
                }
        }
        for (int i = 0; i < pole_size+1; i++) {
            if ((h-y>(i*cell_width+cell_width/4)*ppuy+offset*ppuy)&(h-y<(i*cell_width+cell_width-cell_width/4)*ppuy+offset*ppuy))
                for (int j = 0; j <pole_size; j++) {
                    if ((x>(j*cell_width+cell_width/4)*ppux+cell_width)&(x<(j*cell_width+cell_width-cell_width/4)*ppux+cell_width))
                        if(hor[i][j]==0) {
                            hor[i][j]=1;  //[y][x]
                            if ( (hor[i-1][j]==1)&(vert[i-1][j]==1)&(vert[i-1][j+1]==1) ) pole[i-1][j]=2;
                            if ( (hor[i+1][j]==1)&(vert[i][j]==1)  &(vert[i][j+1]==1)   ) pole[i][j]=2;
                        }
                }
        }
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
        return true;
    }
}
