package sokobug.screens;

import sokobug.Sokobug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TitleScreen implements Screen, InputProcessor {

	private Sokobug game;
	
	private Sprite titleImage;
	private float titleImageMinimumDuration;
	private float pasedTimeCounter;
	private boolean readyToSkip;
	
	public TitleScreen(Sokobug myGame) {
		game = myGame;
		readyToSkip = false;
		
		TextureParameter param = new TextureParameter();
		param.minFilter = TextureFilter.Linear;
		game.assetManager.load("backgrounds/title.png", Texture.class, param);
		game.assetManager.finishLoading();
		
		titleImage = new Sprite(game.assetManager.get("backgrounds/title.png", Texture.class));
		titleImage.setPosition(0.f, 0.f);
		titleImageMinimumDuration = 2.0f;
		pasedTimeCounter = 0.0f;
		
		game.assetManager.load("fonts/Papyrus.fnt", BitmapFont.class);
		game.assetManager.load("fonts/Papyrus58.fnt", BitmapFont.class);
		
		game.assetManager.load("ui/buttons/buttons.pack", TextureAtlas.class);
		game.assetManager.load("ui/buttons/buttons.json", Skin.class, new SkinLoader.SkinParameter("ui/buttons/buttons.pack"));
		
		game.assetManager.load("backgrounds/menu.png", Texture.class);
		game.assetManager.load("logos/potatoes.png", Texture.class, param);
		
		game.assetManager.load("level/animations/bug/bug.pack", TextureAtlas.class);
		game.assetManager.load("level/tiles/free.png", Texture.class);
		game.assetManager.load("level/tiles/wall.png", Texture.class);
		game.assetManager.load("level/animations/spot/spot.pack", TextureAtlas.class);
		game.assetManager.load("level/tiles/sarcophagus.png", Texture.class);
		game.assetManager.load("level/topBar.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
		pasedTimeCounter += delta;
		if (game.assetManager.update()) {
	        if(pasedTimeCounter > titleImageMinimumDuration) {
	        	if (!readyToSkip) {
	        		readyToSkip = true;
	        	}
	        }
		}

		game.batch.begin();
		titleImage.draw(game.batch);
		game.batch.end();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		game.mainMenuScreen = new MainMenuScreen(game);
		game.creditsScreen = new CreditsScreen(game);
		game.optionsScreen = new OptionsScreen(game);
		game.chooseLevelScreen = new ChooseLevelScreen(game);
		game.ingameScreen = new IngameScreen(game);
		game.victoryScreen = new VictoryScreen(game);
		dispose();
	}
	
	@Override
	public void dispose() {
		game.assetManager.unload("backgrounds/title.png");
		game.titleScreen = null; /* we are not going to use the TitleScreen so we can set this to null
									for the garbage collector if it happens that it needs to clear memory */
	}
	
	@Override
	public void resize(int width, int height) {
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
    	if (readyToSkip) {
	    	game.setScreen(game.mainMenuScreen);
    	}
    	return true;
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
    	if (readyToSkip) {
	    	game.setScreen(game.mainMenuScreen);
    	}
    	return true;
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
