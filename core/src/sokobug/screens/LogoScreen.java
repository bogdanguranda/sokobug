package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.AnimationWrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LogoScreen implements Screen {

	private Sokobug game;
	
	private Sprite titleImage;
	private float titleImageDuration;
	
	private AnimationWrapper logo1;
	private AnimationWrapper logo2;
	
	private boolean displayTitle, displayTitlelogo1, displayTitlelogo2;
	private float pasedTimeCounter;
	
	
	public LogoScreen(Sokobug myGame) {
		game = myGame;
		
		titleImage = new Sprite(new Texture(Gdx.files.internal("Title.png")));
		titleImage.setPosition(0.f, 0.f);
		titleImageDuration = 1.0f;
		
		logo1 = new AnimationWrapper("LogoAndrei.png", 9, 12);
		logo2 = new AnimationWrapper("LogoPotatoes.png", 9, 12);
		
		displayTitle = true;
		displayTitlelogo1 = false;
		displayTitlelogo2 = false;
		
		pasedTimeCounter = 0.0f;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
//        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
//        	if (displayTitle) {
//	        	displayTitle = false;
//		    	displayTitlelogo1 = true;
//		    	pasedTimeCounter = 0.0f;
//		    	return;
//        	}
//        	else if (displayTitlelogo1) {
//	        	displayTitlelogo1 = false;
//	        	displayTitlelogo2 = true;
//	        	pasedTimeCounter = 0.0f;
//	        	return;
//        	}
//        	else if (displayTitlelogo2) {
//	        	game.setScreen(game.mainMenuScreen);
//	        	return;
//        	}
//        } 
		
		pasedTimeCounter += delta;
		if (displayTitle) {
	        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || pasedTimeCounter > titleImageDuration) {
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
	        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || logo1.mAnimation.isAnimationFinished(pasedTimeCounter)) {
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
	        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || logo1.mAnimation.isAnimationFinished(pasedTimeCounter)) {
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
		titleImage.getTexture().dispose();
		logo1.mAnimation.getKeyFrame(0.f).getTexture().dispose();
		logo2.mAnimation.getKeyFrame(0.f).getTexture().dispose();
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
