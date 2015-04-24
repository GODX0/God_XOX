package ru.hackathon3.godxo;

import ru.hackathon3.godxo.screens.*;
import com.badlogic.gdx.Game;

public class MyGame extends Game {
    public GameScreen game;

	@Override
	public void create () {
        game = new GameScreen(this);
        setScreen(game);
	}

    @Override
    public void resize(int width, int height) {
    }
}
