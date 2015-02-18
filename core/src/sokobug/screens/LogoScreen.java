package sokobug.screens;

import sokobug.Sokobug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LogoScreen implements Screen {

	private Sokobug game;
	private Sprite background;
	float pasedTimeCounter;
	
	public LogoScreen(Sokobug myGame) {
		game = myGame;
		
		background = new Sprite(new Texture(Gdx.files.internal("Title.png")));
		background.setPosition(0.f, 0.f);
		pasedTimeCounter = 0.0f;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, Color.DARK_GRAY.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
		pasedTimeCounter += delta;
		if(pasedTimeCounter <= 7.0f) {
			game.batch.begin();
			background.draw(game.batch);
			game.batch.end();
				
	        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
	        	game.setScreen(game.mainMenuScreen);
	        	return;
	        }
		}
		else
			game.setScreen(game.mainMenuScreen);
		
	}

	@Override
	public void show() {
		pasedTimeCounter = 0.0f;

	}

	@Override
	public void hide() {
		
	}
	
	@Override
	public void dispose() {
		background.getTexture().dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f, game.VIRTUAL_HEIGHT / 2.f, 0.f);
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
