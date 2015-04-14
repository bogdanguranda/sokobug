package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.LvlBtnOrganizer;
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

public class ChooseLevelScreen implements Screen, InputProcessor {

	public Sokobug game;

	public Skin uiSkin;
	private Stage stage;
	private Table table;
	private MenuButton backToMenu;
	private Sprite background;
	private InputMultiplexer multiplexer;

	private static final int NUM_LEVELS = 30;
	private static final int BUTTONS_PER_ROW = 10;
	private static final int BUTTONS_PER_COLLUMN = 3;

	private MenuButton focusedButton;
	public MenuButton[] levelButtons;

	public ChooseLevelScreen(Sokobug myGame) {
		game = myGame;
		table = new Table();
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();
		levelButtons = new MenuButton[NUM_LEVELS];

		game.assetManager.load("backgrounds/menu.png", Texture.class);
		game.assetManager.load("ui/buttons/buttons.pack", TextureAtlas.class);
		game.assetManager.load("ui/buttons/buttons.json", Skin.class,
				new SkinLoader.SkinParameter("ui/buttons/buttons.pack"));
		game.assetManager.finishLoading();

		background = new Sprite(game.assetManager.get("backgrounds/menu.png",
				Texture.class));
		backToMenu = new MenuButton(game, "", MenuButton.BACKTOMENU,
				game.assetManager.get("ui/buttons/buttons.json", Skin.class), "menu-back-btn");

		uiSkin = game.assetManager.get("ui/buttons/buttons.json", Skin.class);

		levelButtons = LvlBtnOrganizer.linkButtons(this, BUTTONS_PER_ROW,
				BUTTONS_PER_COLLUMN, NUM_LEVELS);

		for (int i = 0; i < NUM_LEVELS; i++) {
			table.add(levelButtons[i]).pad(25);
			if ((i + 1) % BUTTONS_PER_ROW == 0)
				table.row();
		}
		stage.addActor(backToMenu);

		table.setFillParent(true);
		//table.setDebug(true);
		stage.addActor(table);

		backToMenu.setPosition(0, 0);
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
	public void dispose() {
		stage.dispose();
		game.assetManager.unload("backgrounds/menu.png");
		game.assetManager.unload("ui/buttons/buttons.pack");
		game.assetManager.unload("ui/buttons/buttons.json");
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f,
				game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		MenuButton.defocusButtons(levelButtons);

		for (MenuButton button : levelButtons)
			button.setStyle(uiSkin.get("default-level-btn", TextButtonStyle.class));
		
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
		if (!MenuButton.isMouseOverButton(levelButtons)) {
			if (keycode == Input.Keys.DOWN) {
				focusedButton = MenuButton.getFocusedButton(levelButtons);

				if (focusedButton != null) {
					focusedButton.setStyle(uiSkin.get("default-level-btn",
							TextButtonStyle.class));
					focusedButton.getDownNeighbour().setStyle(
							uiSkin.get("level-button-focused",
									TextButtonStyle.class));
					focusedButton.getDownNeighbour().setFocused(true);
					focusedButton.setFocused(false);
				} else {
					levelButtons[0].setStyle(uiSkin.get("level-button-focused",
							TextButtonStyle.class));
					levelButtons[0].setFocused(true);
				}

				return true;

			} else if (keycode == Input.Keys.UP) {
				focusedButton = MenuButton.getFocusedButton(levelButtons);

				if (focusedButton != null) {
					focusedButton.setStyle(uiSkin.get("default-level-btn",
							TextButtonStyle.class));
					focusedButton.getUpNeighbour().setStyle(
							uiSkin.get("level-button-focused",
									TextButtonStyle.class));
					focusedButton.getUpNeighbour().setFocused(true);
					focusedButton.setFocused(false);
				} else {
					levelButtons[NUM_LEVELS - 1].setStyle(uiSkin.get(
							"level-button-focused", TextButtonStyle.class));
					levelButtons[NUM_LEVELS - 1].setFocused(true);
				}

				return true;

			} else if (keycode == Input.Keys.RIGHT) {
				focusedButton = MenuButton.getFocusedButton(levelButtons);

				if (focusedButton != null) {
					focusedButton.setStyle(uiSkin.get("default-level-btn",
							TextButtonStyle.class));
					focusedButton.getRightNeighbour().setStyle(
							uiSkin.get("level-button-focused",
									TextButtonStyle.class));
					focusedButton.getRightNeighbour().setFocused(true);
					focusedButton.setFocused(false);
				} else {
					levelButtons[0].setStyle(uiSkin.get("level-button-focused",
							TextButtonStyle.class));
					levelButtons[0].setFocused(true);
				}

				return true;

			} else if (keycode == Input.Keys.LEFT) {
				focusedButton = MenuButton.getFocusedButton(levelButtons);

				if (focusedButton != null) {
					focusedButton.setStyle(uiSkin.get("default-level-btn",
							TextButtonStyle.class));
					focusedButton.getLeftNeighbour().setStyle(
							uiSkin.get("level-button-focused",
									TextButtonStyle.class));
					focusedButton.getLeftNeighbour().setFocused(true);
					focusedButton.setFocused(false);
				} else {
					levelButtons[NUM_LEVELS - 1].setStyle(uiSkin.get(
							"level-button-focused", TextButtonStyle.class));
					levelButtons[NUM_LEVELS - 1].setFocused(true);
				}

				return true;
			}
		}

		if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACKSPACE) {
			game.setScreen(game.mainMenuScreen);
			return true;
		}
		
		if (keycode == Input.Keys.ENTER) {
			focusedButton = MenuButton.getFocusedButton(levelButtons);
			if (focusedButton != null) {
				int level = Integer.parseInt(focusedButton.getText().toString());

				game.ingameScreen.level.levelNumber = level;
				game.setScreen(game.ingameScreen);
				return true;
			}
		}
		

//		if (keycode == Input.Keys.NUM_1) {
//			game.ingameScreen.level.levelNumber = 1;
//			game.setScreen(game.ingameScreen);
//		}
//		if (keycode == Input.Keys.NUM_2) {
//			game.ingameScreen.level.levelNumber = 2;
//			game.setScreen(game.ingameScreen);
//		}
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
