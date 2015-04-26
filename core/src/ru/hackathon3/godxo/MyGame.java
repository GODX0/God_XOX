package ru.hackathon3.godxo;

import ru.hackathon3.godxo.screens.*;
import com.badlogic.gdx.Game;

public class MyGame extends Game {
    public GameScreen game;
    public SplashScreen splash;
    public MenuScreen menu;

	@Override
	public void create () {
        game = new GameScreen(this);
        menu = new MenuScreen(this);
        splash = new SplashScreen(this);
        setScreen(splash);
	}

    @Override
    public void resize(int width, int height) {
    }
}
