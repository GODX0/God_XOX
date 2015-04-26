package ru.hackathon3.godxo.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.hackathon3.godxo.MyGame;

public class GameScreen implements Screen,InputProcessor {
    MyGame game;
    private SpriteBatch spriteBatch;
    public  Map<String, Texture> textures;
    float w,h,ppux,ppuy;
    private BitmapFont Font1;

    //анимация
    private static final int FRAME_COLS = 3; // #1
    private static final int FRAME_ROWS = 2; // #2
    Animation krestAnimation,krugAnimation; // #3
    Texture krestSheet,krugSheet; // #4
    TextureRegion[] krestFrames,krugFrames;
    TextureRegion currentFrame; // #7
    float animx,animy;
    float stateTime; // #8
    //камера
    public OrthographicCamera cam;
    float CAMERA_WIDTH = 480F;
    float CAMERA_HEIGHT = 800F;

    int pole_size=9;
    float offset;
    boolean b,anim;
    int symbol=1;
    int symbolAnim;
    float cell_width;
    int [][] vert =
            {
                    {-1,-1,-1,-1,1,1,-1,-1,-1,-1},
                    {-1,-1,-1,1,0,0,1,-1,-1,-1},
                    {-1,-1,1,0,0,0,0,1,-1,-1},
                    {-1,1,0,0,0,0,0,0,1,-1},
                    {1,1,0,0,0,0,0,0,1,1},
                    {-1,1,0,0,0,0,0,0,1,-1},
                    {-1,-1,1,0,0,0,0,1,-1,-1},
                    {-1,-1,-1,1,0,0,1,-1,-1,-1},
                    {-1,-1,-1,-1,1,1,-1,-1,-1,-1}
            };
    int [][] hor =
            {
                    {-1,-1,-1,-1,1,-1,-1,-1,-1},
                    {-1,-1,-1, 1,1, 1,-1,-1,-1},
                    {-1,-1, 1, 0,0, 0, 1,-1,-1},
                    {-1, 1, 0, 0,0, 0, 0, 1,-1},
                    { 1, 0, 0, 0,0, 0, 0, 0, 1},
                    { 1, 0, 0, 0, 0, 0, 0, 0,1},
                    {-1, 1, 0, 0,0, 0, 0, 1,-1},
                    {-1,-1, 1, 0,0, 0, 1,-1,-1},
                    {-1,-1,-1, 1,1, 1,-1,-1,-1},
                    {-1,-1,-1,-1,1,-1,-1,-1,-1},
            };
    int [][] pole =
            {
                    {-1,-1,-1,-1,1,-1,-1,-1,-1},
                    {-1,-1,-1,0,0,0,-1,-1,-1},
                    {-1,-1,0,0,0,0,0,-1,-1},
                    {-1,0,0,0,0,0,0,0,-1},
                    {2,0,0,0,0,0,0,0,2},
                    {-1,0,0,0,0,0,0,0,-1},
                    {-1,-1,0,0,0,0,0,-1,-1},
                    {-1,-1,-1,0,0,0,-1,-1,-1},
                    {-1,-1,-1,-1,1,-1,-1,-1,-1}
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

        Font1 = new BitmapFont();
        Font1.setColor(Color.RED);
        clearBoard();

        textures.put("gray", new Texture(Gdx.files.internal("gray.png")));
        textures.put("stickGor", new Texture(Gdx.files.internal("stickGor.png")));
        textures.put("stickVert", new Texture(Gdx.files.internal("stickVert.png")));
        textures.put("gray1", new Texture(Gdx.files.internal("gray1.png")));
        textures.put("back", new Texture(Gdx.files.internal("back.png")));

        //Анимация
        krestSheet = new Texture(Gdx.files.internal("krestAnim.png")); // #9
        krugSheet = new Texture(Gdx.files.internal("krugAnim.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(krestSheet, krestSheet.getWidth()/FRAME_COLS, krestSheet.getHeight()/FRAME_ROWS); // #10
        TextureRegion[][] tmp2 = TextureRegion.split(krugSheet, krugSheet.getWidth()/FRAME_COLS, krugSheet.getHeight()/FRAME_ROWS); // #10
        krestFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        krugFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                krestFrames[index] = tmp[i][j];
                krugFrames[index++] = tmp2[i][j];
            }
        }
        krestAnimation = new Animation(0.083f, krestFrames); // #11
        krugAnimation = new Animation(0.083f, krugFrames); // #11
        stateTime = 0f; // #13
        anim=false;

    }
    public GameScreen(MyGame game){
        this.game = game;
    }

    public void clearBoard(){
        animx=0;animy=0;
        symbol=1;
        for (int i = 0; i < pole_size; i++) {
            for (int j = 0; j < pole_size; j++) {
                if ((i>0)&(j>0)&(i<pole_size-1)&(j<pole_size-1)&(pole[i][j]>0)) pole[i][j]=0;
                if (vert[i][j]==2) vert[i][j]=0;
                if (hor[i][j]==2) hor[i][j]=0;
            }
        }
    }
    public void showBoard(){
        for (int i = 0; i < pole_size; i++) {
            for (int j = 0; j < pole_size+1; j++) {
                if (vert[i][j]>0 ) spriteBatch.draw(textures.get("stickVert"), cell_width*j, cell_width*i+cell_width/2+offset, cell_width, cell_width);
                if (vert[i][j]==0 ) spriteBatch.draw(textures.get("gray"), cell_width*j, cell_width*i+cell_width/2+offset, cell_width, cell_width);
            }
        }
        for (int i = 0; i < pole_size+1; i++) {
            for (int j = 0; j < pole_size; j++) {
                if (hor[i][j]>0 ) spriteBatch.draw(textures.get("stickGor"), cell_width*j+cell_width/2, cell_width*i+offset, cell_width, cell_width);
                if (hor[i][j]==0 ) spriteBatch.draw(textures.get("gray1"), cell_width*j+cell_width/2, cell_width*i+offset, cell_width, cell_width);
            }
        }
        for (int i = 0; i < pole_size; i++) {
            for (int j = 0; j < pole_size; j++) {
                if ((pole[i][j]==2 )&((i!=animy)|(j!=animx))) spriteBatch.draw(krestFrames[5], cell_width*j+cell_width/2, cell_width*i+cell_width/2+offset, cell_width, cell_width);
                if ((pole[i][j]==1 )&((i!=animy)|(j!=animx))) spriteBatch.draw(krugFrames[5], cell_width*j+cell_width/2, cell_width*i+cell_width/2+offset, cell_width, cell_width);
            }
        }
    }

    public void SetCamera(float x, float y){
        this.cam.position.set(x, y,0);
        this.cam.update();
    }

    public void setSymbol(int xx, int yy)
    {
        if (symbol==1) symbolAnim =2;
        if (symbol==2) symbolAnim =1;
        stateTime = 0f;
        animx =yy;
        animy = xx;
        pole[xx][yy]=symbol;
    }
    @Override
    public void render(float delta) {
        SetCamera(CAMERA_WIDTH/2, CAMERA_HEIGHT / 2f);
        spriteBatch.setProjectionMatrix(this.cam.combined);

        Gdx.gl.glClearColor(1,1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime(); // #15
        if (symbolAnim==1)  currentFrame = krestAnimation.getKeyFrame(stateTime, false);
        if (symbolAnim==2)   currentFrame = krugAnimation.getKeyFrame(stateTime, false);

        spriteBatch.begin();
            spriteBatch.draw(textures.get("back"), 0,0);
            Font1.draw(spriteBatch,"Player: "+symbol+"",10,CAMERA_HEIGHT-50);
            if ((symbolAnim==1)&((animx!=0)|(animy!=0))) spriteBatch.draw(currentFrame,cell_width*animx+cell_width/2 , cell_width*animy+cell_width/2+offset,cell_width,cell_width);
            if ((symbolAnim==2)&((animx!=0)|(animy!=0))) spriteBatch.draw(currentFrame,cell_width*animx+cell_width/2 , cell_width*animy+cell_width/2+offset,cell_width,cell_width);
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
                            vert[j][i]=2;//[y][x]
                            b=false;
                            if ( (vert[j][i-1]>0)&(hor[j][i-1]>0)&(hor[j+1][i-1]>0) ) {setSymbol(j,i-1);b=true;}
                            if ( (vert[j][i+1]>0)&(hor[j][i]>0)  &(hor[j+1][i]>0)   ) {setSymbol(j,i);b=true;}
                            if (!b) symbol+=1;
                            if (symbol>2) symbol=1;
                        }
                }
        }
        for (int i = 0; i < pole_size+1; i++) {
            if ((h-y>(i*cell_width+cell_width/4)*ppuy+offset*ppuy)&(h-y<(i*cell_width+cell_width-cell_width/4)*ppuy+offset*ppuy))
                for (int j = 0; j <pole_size; j++) {
                    if ((x>(j*cell_width+cell_width/4)*ppux+cell_width)&(x<(j*cell_width+cell_width-cell_width/4)*ppux+cell_width))
                        if(hor[i][j]==0) {
                            hor[i][j]=2;  //[y][x]
                            b=false;
                            if ( (hor[i-1][j]>0)&(vert[i-1][j]>0)&(vert[i-1][j+1]>0) ){setSymbol(i-1,j);b=true;}
                            if ( (hor[i+1][j]>0)&(vert[i][j]>0)  &(vert[i][j+1]>0)   ) {setSymbol(i,j);b=true;}
                            if (!b) symbol+=1;
                            if (symbol>2) symbol=1;
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
        if(keycode == Input.Keys.BACK ){

            this.dispose();
            game.setScreen(game.menu);

        }
        return true;
    }
}
