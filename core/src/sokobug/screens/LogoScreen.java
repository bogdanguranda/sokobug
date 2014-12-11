package sokobug.screens;

import sokobug.Sokobug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LogoScreen implements Screen {

	Sokobug game;
	SpriteBatch batch;
	Texture img;
	float pasedTimeCounter;
	
	public LogoScreen(Sokobug myGame) {
		game = myGame;
		
		batch = new SpriteBatch();
		img = new Texture("KorinCircle.png");
		pasedTimeCounter = 0.0f;
	}

	@Override
	public void render(float delta) {
		pasedTimeCounter += delta;
		if (pasedTimeCounter >= 2.0f) { // dupa ce au trecut 2 sec trece la Main Menu
			game.setScreen(game.mainMenuScreen); 
		}
		
		Gdx.gl.glClearColor(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, Color.DARK_GRAY.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, (Gdx.graphics.getWidth() / 2) - img.getWidth() / 2 , (Gdx.graphics.getHeight() / 2) - img.getHeight() / 2);
		batch.end();
	}

	@Override
	public void show() {
		pasedTimeCounter = 0.0f;

	}

	@Override
	public void hide() {
		dispose();

	}
	
	@Override
	public void dispose() {
		img.dispose();
		batch.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}



}
