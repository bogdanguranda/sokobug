package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.Level;
import sokobug.domain.MenuButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class IngameScreen implements Screen, InputProcessor {

	private Sokobug game;
	private BitmapFont font;
	private Stage stage;
	private MenuButton backToMenu;
	private InputMultiplexer multiplexer;
	
	public Level level; // by default 1(should be loaded before changing to ingameScreen, using level.load(int levelNumber) method)
	
	public IngameScreen(Sokobug myGame) {
		game = myGame;
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();

		font = game.assetManager.get("fonts/Papyrus.fnt", BitmapFont.class);
		game.assetManager.load("skins/uiskin.atlas", TextureAtlas.class);
		game.assetManager.load("skins/uiskin.json", Skin.class, new SkinLoader.SkinParameter("skins/uiskin.atlas"));
		
		game.assetManager.load("ingame/bug.png", Texture.class);
		game.assetManager.load("ingame/free.png", Texture.class);
		game.assetManager.load("ingame/wall.png", Texture.class);
		game.assetManager.load("ingame/spot.png", Texture.class);
		game.assetManager.load("ingame/vase.png", Texture.class);
		
		game.assetManager.finishLoading();

		backToMenu = new MenuButton(game.mainMenuScreen, "Back", MenuButton.BACKTOCHOOSELEVEL, game.assetManager.get("skins/uiskin.json", Skin.class));
		backToMenu.setPosition(0, game.VIRTUAL_HEIGHT - backToMenu.getHeight());// backToMenu.setPosition(0, level.getSize().y - (topBar.height() * 2/3)); // sa fie centrat la mijlocul lui top bar cam asa(cand o sa fie butonul mai mic)
	
		level = new Level(game); // we pass game to give acces to assetManager for drawings and maybe other needs
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b,
				Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);

		font.setColor(Color.YELLOW);
		String levelText = "Level " + String.valueOf(level.levelNumber);

		game.batch.begin();
		font.draw(game.batch, levelText, game.VIRTUAL_WIDTH / 2 - font.getBounds(levelText).width, game.VIRTUAL_HEIGHT - font.getBounds(levelText).height);
		game.batch.end();

		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		game.assetManager.unload("skins/uiskin.atlas");
		game.assetManager.unload("skins/uiskin.json");
		
		game.assetManager.unload("ingame/bug.png");
		game.assetManager.unload("ingame/free.png");
		game.assetManager.unload("ingame/wall.png");
		game.assetManager.unload("ingame/spot.png");
		game.assetManager.unload("ingame/vase.png");
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f,
				game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		level.load(level.levelNumber);
		stage.addActor(backToMenu);
		
		multiplexer.addProcessor(level);
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void hide() {
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

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE) {
			game.setScreen(game.chooseLevelScreen);
			return true;
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
