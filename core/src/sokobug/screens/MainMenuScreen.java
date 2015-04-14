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

	public Skin uiSkin;
	public Sokobug game;

	private MenuButton play;
	private MenuButton options;
	private MenuButton credits;
	private MenuButton exit;
	private MenuButton focusedButton;
	public MenuButton[] menuButtons;

	public MainMenuScreen(Sokobug game) {
		this.game = game;
		table = new Table();
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();

		game.assetManager.load("backgrounds/menu.png", Texture.class);
		game.assetManager.load("ui/buttons/buttons.pack", TextureAtlas.class);
		game.assetManager.load("ui/buttons/buttons.json", Skin.class,
				new SkinLoader.SkinParameter("ui/buttons/buttons.pack"));
		game.assetManager.finishLoading();

		background = new Sprite(game.assetManager.get("backgrounds/menu.png",
				Texture.class));

		play = new MenuButton(game, "Play", MenuButton.PLAY,
				game.assetManager.get("ui/buttons/buttons.json", Skin.class));
		options = new MenuButton(game, "Options", MenuButton.OPTIONS,
				game.assetManager.get("ui/buttons/buttons.json", Skin.class));
		credits = new MenuButton(game, "Credits", MenuButton.CREDITS,
				game.assetManager.get("ui/buttons/buttons.json", Skin.class));
		exit = new MenuButton(game, "Exit", MenuButton.EXIT,
				game.assetManager.get("ui/buttons/buttons.json", Skin.class));

		play.setUpNeighbour(exit);
		play.setDownNeighbour(options);
		options.setUpNeighbour(play);
		options.setDownNeighbour(credits);
		credits.setUpNeighbour(options);
		credits.setDownNeighbour(exit);
		exit.setUpNeighbour(credits);
		exit.setDownNeighbour(play);

		menuButtons = new MenuButton[4];
		menuButtons[0] = play;
		menuButtons[1] = options;
		menuButtons[2] = credits;
		menuButtons[3] = exit;

		uiSkin = game.assetManager.get("ui/buttons/buttons.json", Skin.class);

		table.add(play).row();
		table.add(options).row();
		table.add(credits).row();
		table.add(exit).row();
		table.setFillParent(true);
		// table.setDebug(true);

		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b,
				Color.BLACK.a);
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
		// Each time MainMenuScreen is set again, the focused button
		// is set to none.
		MenuButton.defocusButtons(menuButtons);

		for (MenuButton button : menuButtons)
			button.setStyle(uiSkin.get("default", TextButtonStyle.class));

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
		game.assetManager.unload("backgrounds/menu.png");
		game.assetManager.unload("ui/buttons/buttons.pack");
		game.assetManager.unload("ui/buttons/buttons.json");
	}

	@Override
	public boolean keyDown(int keycode) {
		// Reacting only if mouse is not over a menu widget
		if (!MenuButton.isMouseOverButton(menuButtons)) {
			if (keycode == Input.Keys.DOWN) {
				focusedButton = MenuButton.getFocusedButton(menuButtons);

				if (focusedButton != null) {
					focusedButton.setStyle(uiSkin.get("default",
							TextButtonStyle.class));
					focusedButton.getDownNeighbour().setStyle(
							uiSkin.get("menu-button-focused", TextButtonStyle.class));
					focusedButton.getDownNeighbour().setFocused(true);
					focusedButton.setFocused(false);
				} else {
					play.setStyle(uiSkin.get("menu-button-focused",
							TextButtonStyle.class));
					play.setFocused(true);
				}

				return true;

			} else if (keycode == Input.Keys.UP) {
				focusedButton = MenuButton.getFocusedButton(menuButtons);

				if (focusedButton != null) {
					focusedButton.setStyle(uiSkin.get("default",
							TextButtonStyle.class));
					focusedButton.getUpNeighbour().setStyle(
							uiSkin.get("menu-button-focused", TextButtonStyle.class));
					focusedButton.getUpNeighbour().setFocused(true);
					focusedButton.setFocused(false);
				} else {
					exit.setStyle(uiSkin.get("menu-button-focused",
							TextButtonStyle.class));
					exit.setFocused(true);
				}

				return true;
			}
		}

		if (keycode == Input.Keys.ENTER) {
			focusedButton = MenuButton.getFocusedButton(menuButtons);
			if (focusedButton != null) {
				if (focusedButton.getType() == MenuButton.PLAY)
					game.setScreen(game.chooseLevelScreen);
				else if (focusedButton.getType() == MenuButton.OPTIONS)
					game.setScreen(game.optionsScreen);
				else if (focusedButton.getType() == MenuButton.CREDITS)
					game.setScreen(game.creditsScreen);
				else if (focusedButton.getType() == MenuButton.EXIT)
					Gdx.app.exit();
				return true;
			}
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
