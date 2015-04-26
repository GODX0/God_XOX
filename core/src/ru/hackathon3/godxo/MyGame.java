package ru.hackathon3.godxo;

import ru.hackathon3.godxo.screens.*;
import com.badlogic.gdx.Game;

public class MyGame extends Game {
    public GameScreen game;
    public SplashScreen splash;
    public ChooseSizeGod chooseGod;
    public MenuScreen menu;
    public MenuScreen2 menu2;
    public MenuScreenGod menuGod;

	@Override
	public void create () {
        game = new GameScreen(this);
        menu = new MenuScreen(this);
        menu2 = new MenuScreen2(this);
        menuGod = new MenuScreenGod(this);
        chooseGod = new ChooseSizeGod(this);
        splash = new SplashScreen(this);
        setScreen(splash);
	}

    @Override
    public void resize(int width, int height) {
    }
}
