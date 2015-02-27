package sokobug.screens;

import sokobug.Sokobug;
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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenuScreen implements Screen, InputProcessor {
	private Table table;
	private Stage stage;
	private Sprite background;
	private InputMultiplexer multiplexer;
	private MenuButton play;
	private MenuButton options;
	private MenuButton credits;
	private MenuButton exit;

	public Sokobug game;
	public Boolean isPlayFocused;
	public Boolean isOptionFocused;
	public Boolean isCreditsFocused;
	public Boolean isExitFocused;
	public Skin focusedButtonSkin;
	public Skin normalButtonSkin;

	public MainMenuScreen(Sokobug game) {
		this.game = game;
		table = new Table();
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();

		game.assetManager.load("MainMenuScreen.png", Texture.class);
		game.assetManager.load("skins/uiskin.atlas", TextureAtlas.class);
		game.assetManager.load("skins/uiskin.json", Skin.class,
				new SkinLoader.SkinParameter("skins/uiskin.atlas"));
		game.assetManager.load("skins/focusedButton.json", Skin.class,
				new SkinLoader.SkinParameter("skins/focusedButton.atlas"));
		game.assetManager.finishLoading();

		background = new Sprite(game.assetManager.get("MainMenuScreen.png",
				Texture.class));
		play = new MenuButton(this, "Play", game.assetManager.get(
				"skins/uiskin.json", Skin.class));
		options = new MenuButton(this, "Options", game.assetManager.get(
				"skins/uiskin.json", Skin.class));
		credits = new MenuButton(this, "Credits", game.assetManager.get(
				"skins/uiskin.json", Skin.class));
		exit = new MenuButton(this, "Exit", game.assetManager.get(
				"skins/uiskin.json", Skin.class));

		focusedButtonSkin = game.assetManager.get("skins/focusedButton.json",
				Skin.class);
		normalButtonSkin = game.assetManager.get("skins/uiskin.json",
				Skin.class);
		
		table.add(play).row();
		table.add(options).row();
		table.add(credits).row();
		table.add(exit).row();
		table.setFillParent(true);
		// table.setDebug(true);

		stage.addActor(table);
	}

	public MenuButton getFocusedButton() {
		if (isOptionFocused)
			return options;
		else if (isCreditsFocused)
			return credits;
		else if (isExitFocused)
			return exit;
		else
			return play;
	}
	
	public void defocusButtons() {
		isPlayFocused = false;
		isOptionFocused = false;
		isCreditsFocused = false;
		isExitFocused = false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);

		game.batch.begin();
		background.draw(game.batch);
		game.batch.end();

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f,
				game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		//Each time MainMenuScreen is set again, the focused button
		//is set to none.
		defocusButtons();

		play.setStyle(normalButtonSkin.get(TextButtonStyle.class));
		options.setStyle(normalButtonSkin.get(TextButtonStyle.class));
		credits.setStyle(normalButtonSkin.get(TextButtonStyle.class));
		exit.setStyle(normalButtonSkin.get(TextButtonStyle.class));

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
	public void dispose() {
		stage.dispose();
		game.assetManager.unload("MainMenuScreen.png");
		game.assetManager.unload("skins/uiskin.atlas");
		game.assetManager.unload("skins/uiskin.json");
		game.assetManager.unload("skins/focusedButton.json");
	}

	@Override
	public boolean keyDown(int keycode) {
		//Reacting only if mouse is not over a menu widget
		if (!play.isOver() && !options.isOver() && !credits.isOver()
				&& !exit.isOver()) {
			if (keycode == Input.Keys.DOWN) {
				if (isPlayFocused) {
					isPlayFocused = false;
					isOptionFocused = true;
					play.setStyle(normalButtonSkin.get(TextButtonStyle.class));
					options.setStyle(focusedButtonSkin
							.get(TextButtonStyle.class));
				} else if (isOptionFocused) {
					isOptionFocused = false;
					isCreditsFocused = true;
					options.setStyle(normalButtonSkin
							.get(TextButtonStyle.class));
					credits.setStyle(focusedButtonSkin
							.get(TextButtonStyle.class));
				} else if (isCreditsFocused) {
					isCreditsFocused = false;
					isExitFocused = true;
					credits.setStyle(normalButtonSkin
							.get(TextButtonStyle.class));
					exit.setStyle(focusedButtonSkin.get(TextButtonStyle.class));
				} else if (isExitFocused) {
					isExitFocused = false;
					isPlayFocused = true;
					exit.setStyle(normalButtonSkin.get(TextButtonStyle.class));
					play.setStyle(focusedButtonSkin.get(TextButtonStyle.class));
				} else {
					isPlayFocused = true;
					play.setStyle(focusedButtonSkin.get(TextButtonStyle.class));
				}
				return true;

			} else if (keycode == Input.Keys.UP) {
				if (isPlayFocused) {
					isPlayFocused = false;
					isExitFocused = true;
					play.setStyle(normalButtonSkin.get(TextButtonStyle.class));
					exit.setStyle(focusedButtonSkin.get(TextButtonStyle.class));
				} else if (isExitFocused) {
					isExitFocused = false;
					isCreditsFocused = true;
					exit.setStyle(normalButtonSkin.get(TextButtonStyle.class));
					credits.setStyle(focusedButtonSkin
							.get(TextButtonStyle.class));
				} else if (isCreditsFocused) {
					isCreditsFocused = false;
					isOptionFocused = true;
					credits.setStyle(normalButtonSkin
							.get(TextButtonStyle.class));
					options.setStyle(focusedButtonSkin
							.get(TextButtonStyle.class));
				} else if (isOptionFocused) {
					isOptionFocused = false;
					isPlayFocused = true;
					options.setStyle(normalButtonSkin
							.get(TextButtonStyle.class));
					play.setStyle(focusedButtonSkin.get(TextButtonStyle.class));
				} else {
					isExitFocused = true;
					exit.setStyle(focusedButtonSkin.get(TextButtonStyle.class));
				}
				return true;

			}
		}

		if (keycode == Input.Keys.ENTER) {
			if (isPlayFocused)
				game.setScreen(game.chooseLevelScreen);
			else if (isOptionFocused)
				game.setScreen(game.optionsScreen);
			else if (isCreditsFocused)
				game.setScreen(game.creditsScreen);
			else if (isExitFocused)
				Gdx.app.exit();
			return true;
		}
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
