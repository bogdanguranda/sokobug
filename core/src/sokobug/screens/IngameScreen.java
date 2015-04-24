package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.Level;
import sokobug.domain.MenuButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class IngameScreen implements Screen, InputProcessor {

	private Sokobug game;
	private BitmapFont font;
	private Stage stage;
	private MenuButton backToMenu;
	private MenuButton restart;
	private InputMultiplexer multiplexer;
	
	public Sprite topBar;
	public Level level;
	
	public IngameScreen(Sokobug myGame) {
		game = myGame;
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();

		font = game.assetManager.get("fonts/Japonesa60.fnt", BitmapFont.class);
		
		level = new Level(game);
		
		topBar = new Sprite(game.assetManager.get("level/topBar.png", Texture.class));
		topBar.setPosition(0, level.getSize().y);
		
		backToMenu = new MenuButton(game, "", MenuButton.BACKTOCHOOSELEVEL, game.assetManager.get("ui/buttons/buttons.json", Skin.class), "ingame-back-btn");
		backToMenu.setPosition(backToMenu.getWidth() / 2, level.getSize().y + (topBar.getHeight() / 2) - (backToMenu.getHeight() / 2)); 
	
		restart = new MenuButton(game, "", MenuButton.RESTART, game.assetManager.get("ui/buttons/buttons.json", Skin.class), "default-restart-btn");
		restart.setPosition(game.VIRTUAL_WIDTH - restart.getWidth() * 3.f/2.f, level.getSize().y + (topBar.getHeight() / 2) - (backToMenu.getHeight() / 2));
		
		stage.addActor(backToMenu);
		stage.addActor(restart);
		
		multiplexer.addProcessor(level);
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
		font.setColor(Color.BLACK);
		font.setScale(1);
		String levelText = "Level " + String.valueOf(level.levelNumber);

		game.batch.begin();
		topBar.draw(game.batch);
		font.draw(game.batch, levelText, game.VIRTUAL_WIDTH / 2 - font.getBounds(levelText).width / 2, game.VIRTUAL_HEIGHT - 12.f);
		game.batch.end();
		
		stage.act();
		stage.draw();
		
		level.render(delta);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f, game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		level.load(level.levelNumber);
		
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
