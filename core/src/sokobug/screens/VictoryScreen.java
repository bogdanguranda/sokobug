package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.MenuButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class VictoryScreen implements Screen, InputProcessor {
	private Sokobug game;
	private BitmapFont font;
	private Stage stage;
	private MenuButton backButton;
	private MenuButton forwardButton;
	private InputMultiplexer multiplexer;
	private Label victoryMessage;

	public VictoryScreen(Sokobug myGame) {
		game = myGame;
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();

		font = game.assetManager.get("fonts/Papyrus.fnt", BitmapFont.class);

		font.setScale(3.0f);
		victoryMessage = new Label("VICTORY!", new LabelStyle(font, Color.PINK));
		victoryMessage.setPosition(game.VIRTUAL_WIDTH / 2.f - victoryMessage.getWidth() / 2.f, game.VIRTUAL_HEIGHT / 2.f + victoryMessage.getHeight() / 2.f);
		
		backButton = new MenuButton(game, "", MenuButton.BACKTOCHOOSELEVEL, game.assetManager.get("ui/buttons/buttons.json", Skin.class), "default-back-btn");
		backButton.setPosition(victoryMessage.getX(), victoryMessage.getY() - backButton.getHeight());
		
		forwardButton = new MenuButton(game, "", MenuButton.FORWARD, game.assetManager.get("ui/buttons/buttons.json", Skin.class), "default-forward-btn");
		forwardButton.setPosition(victoryMessage.getX() + victoryMessage.getWidth() - forwardButton.getWidth(), backButton.getY());
	}

	@Override
	public void render(float delta) {
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);

		stage.act();
		stage.draw();
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
		font.setScale(3.0f);
		stage.addActor(backButton);
		stage.addActor(forwardButton);
		stage.addActor(victoryMessage);
		
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
