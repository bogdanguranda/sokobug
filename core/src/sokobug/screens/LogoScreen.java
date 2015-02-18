package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.AnimationWrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LogoScreen implements Screen, InputProcessor {

	private Sokobug game;
	
	private Sprite titleImage;
	private float titleImageDuration;
	
	private AnimationWrapper logo1;
	private AnimationWrapper logo2;
	
	private boolean displayTitle, displayTitlelogo1, displayTitlelogo2;
	private float pasedTimeCounter;
	
	
	public LogoScreen(Sokobug myGame) {
		game = myGame;
		
		TextureParameter param = new TextureParameter();
		param.minFilter = TextureFilter.Linear;
		game.assetManager.load("Title.png", Texture.class, param);
		game.assetManager.load("LogoAndrei.png", Texture.class, param);
		game.assetManager.load("LogoPotatoes.png", Texture.class, param);
		
		game.assetManager.finishLoading();
		
		titleImage = new Sprite(game.assetManager.get("Title.png", Texture.class));
		titleImage.setPosition(0.f, 0.f);
		titleImageDuration = 7.0f;
		
		logo1 = new AnimationWrapper(game.assetManager.get("LogoAndrei.png", Texture.class), 9, 12);
		logo2 = new AnimationWrapper(game.assetManager.get("LogoPotatoes.png", Texture.class), 10, 10);
		
		displayTitle = true;
		displayTitlelogo1 = false;
		displayTitlelogo2 = false;
		pasedTimeCounter = 0.0f;
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
		pasedTimeCounter += delta;
		if (displayTitle) {
	        if(pasedTimeCounter > titleImageDuration) {
	        	displayTitle = false;
	        	displayTitlelogo1 = true;
	        	pasedTimeCounter = 0.0f;
	        	return;
	        }   
	        
			game.batch.begin();
			titleImage.draw(game.batch);
			game.batch.end();
		}
		else if (displayTitlelogo1) {
	        if(logo1.mAnimation.isAnimationFinished(pasedTimeCounter)) {
	        	displayTitlelogo1 = false;
	        	displayTitlelogo2 = true;
	        	pasedTimeCounter = 0.0f;
	        	return;
	        }  
			
			TextureRegion currentFrame = logo1.mAnimation.getKeyFrame(pasedTimeCounter);
			game.batch.begin();
			game.batch.draw(currentFrame, (game.VIRTUAL_WIDTH / 2) - currentFrame.getRegionWidth() / 2, (game.VIRTUAL_HEIGHT / 2) - currentFrame.getRegionHeight() / 2);
			game.batch.end();
		}
		else if (displayTitlelogo2) {
	        if(logo2.mAnimation.isAnimationFinished(pasedTimeCounter)) {
	        	game.setScreen(game.mainMenuScreen);
	        	return;
	        }  
			
			TextureRegion currentFrame = logo2.mAnimation.getKeyFrame(pasedTimeCounter);
			game.batch.begin();
			game.batch.draw(currentFrame, (game.VIRTUAL_WIDTH / 2) - currentFrame.getRegionWidth() / 2, (game.VIRTUAL_HEIGHT / 2) - currentFrame.getRegionHeight() / 2);
			game.batch.end();
		}
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void dispose() {
		game.assetManager.unload("Title.png");
		game.assetManager.unload("LogoAndrei.png");
		game.assetManager.unload("LogoPotatoes.png");
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

	@Override
	public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
        	if (displayTitle) {
	        	displayTitle = false;
		    	displayTitlelogo1 = true;
		    	pasedTimeCounter = 0.0f;
        	}
        	else if (displayTitlelogo1) {
	        	displayTitlelogo1 = false;
	        	displayTitlelogo2 = true;
	        	pasedTimeCounter = 0.0f;
        	}
        	else if (displayTitlelogo2) {
	        	game.setScreen(game.mainMenuScreen);
        	}
        	return true; // ca sa arat ca am rezolvat evenimentul
        } 
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}



}
