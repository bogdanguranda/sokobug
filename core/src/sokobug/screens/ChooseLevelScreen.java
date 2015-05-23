package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.LvlBtnOrganizer;
import sokobug.domain.MenuButton;
import sokobug.domain.PlayerProgressManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class ChooseLevelScreen implements Screen, InputProcessor {

	public Sokobug game;

	public Skin uiSkin;
	private Stage stage;
	private Table table;
	private MenuButton backToChooseChapter;
	private MenuButton skipCurrentLevel;
	private MenuButton sound;
	private Sprite background;
	private InputMultiplexer multiplexer;

	public static final int NUM_LEVELS = 30;
	private static final int BUTTONS_PER_ROW = 6;
	private static final int BUTTONS_PER_COLLUMN = 5;

	private MenuButton focusedButton;
	public MenuButton[] levelButtons;
	
	private int currentMaxUnlockedLevel;

	public ChooseLevelScreen(Sokobug myGame) {
		game = myGame;
		table = new Table();
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();
		//levelButtons = new MenuButton[NUM_LEVELS];
		currentMaxUnlockedLevel = PlayerProgressManager.getPlayerProgressManager().getCurrentLevel();

		background = new Sprite(game.assetManager.get("backgrounds/menu.png", Texture.class));
		backToChooseChapter = new MenuButton(game, "", MenuButton.BACKTOCHOOSECHAPTER, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "menu-back");
		backToChooseChapter.setPosition(0, 0);
		
		skipCurrentLevel = new MenuButton(game, "Skip level", MenuButton.SKIP_LEVEL, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "default-menu");
		skipCurrentLevel.setPosition(game.VIRTUAL_WIDTH - skipCurrentLevel.getWidth(), 0);
		
		sound = new MenuButton(game, "", MenuButton.SOUNDONOFF, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "soundOn");
		sound.setPosition(game.VIRTUAL_WIDTH - sound.getWidth() * 3.f / 2.f, game.VIRTUAL_HEIGHT - sound.getHeight()
				* 3.f / 2.f);

		uiSkin = game.assetManager.get("ui/buttons/buttons.json", Skin.class);
		
		String styles[] = new String[NUM_LEVELS];
		for (int i = 0; i < NUM_LEVELS; i++) {
			if (PlayerProgressManager.getPlayerProgressManager().isLevelSkipped(i + 1)) {
				styles[i] = "default-skiped-level";
			}
			else if (i < currentMaxUnlockedLevel) {
				styles[i] = "default-level";
			}
			else {
				styles[i] = "level-locked";
			}
		}

		levelButtons = LvlBtnOrganizer.linkButtons(this, BUTTONS_PER_ROW, BUTTONS_PER_COLLUMN, NUM_LEVELS, styles);

		for (int i = 0; i < NUM_LEVELS; i++) {
			table.add(levelButtons[i]).pad(10);
			if ((i + 1) % BUTTONS_PER_ROW == 0)
				table.row();
		}
		
		stage.addActor(backToChooseChapter);
		stage.addActor(skipCurrentLevel);
		table.setFillParent(true);
		stage.addActor(table);
		stage.addActor(sound);
		
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
	}

	@Override
	public void render(float delta) {
		game.soundManager.updateMusicState();
		
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
		if (game.soundManager.isMuted()) {
			sound.setStyle(game.assetManager.get("ui/buttons/buttons.json", Skin.class).get("soundOff",
					TextButtonStyle.class));
		} else {
			sound.setStyle(game.assetManager.get("ui/buttons/buttons.json", Skin.class).get("soundOn",
					TextButtonStyle.class));
		}
		
		currentMaxUnlockedLevel = PlayerProgressManager.getPlayerProgressManager().getCurrentLevel();
		
		table.clear();
		String styles[] = new String[NUM_LEVELS];
		for (int i = 0; i < NUM_LEVELS; i++) {
			if (PlayerProgressManager.getPlayerProgressManager().isLevelSkipped(i + 1)) {
				styles[i] = "default-skiped-level";
			}
			else if (i < currentMaxUnlockedLevel) {
				styles[i] = "default-level";
			}
			else {
				styles[i] = "level-locked";
			}
		}

		levelButtons = LvlBtnOrganizer.linkButtons(this, BUTTONS_PER_ROW, BUTTONS_PER_COLLUMN, NUM_LEVELS, styles);

		for (int i = 0; i < NUM_LEVELS; i++) {
			table.add(levelButtons[i]).pad(10);
			if ((i + 1) % BUTTONS_PER_ROW == 0)
				table.row();
		}
		
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
			focusedButton = MenuButton.getFocusedButton(levelButtons);
			if (focusedButton != null) {
				if (keycode == Input.Keys.DOWN) {
					if (focusedButton.getDownNeighbour().getType() != MenuButton.LEVEL_LOCKED) {
						focusedButton.setStyle(uiSkin.get("default-level",
								TextButtonStyle.class));
						focusedButton.getDownNeighbour().setStyle(
								uiSkin.get("level-focused",
										TextButtonStyle.class));
						focusedButton.getDownNeighbour().setFocused(true);
						focusedButton.setFocused(false);
					}
	
					return true;
	
				} else if (keycode == Input.Keys.UP) {
					if (focusedButton.getUpNeighbour().getType() != MenuButton.LEVEL_LOCKED) {
						focusedButton.setStyle(uiSkin.get("default-level",
								TextButtonStyle.class));
						focusedButton.getUpNeighbour().setStyle(
								uiSkin.get("level-focused",
										TextButtonStyle.class));
						focusedButton.getUpNeighbour().setFocused(true);
						focusedButton.setFocused(false);
					} 
					
					return true;
	
				} else if (keycode == Input.Keys.RIGHT) {
					if (focusedButton.getRightNeighbour().getType() != MenuButton.LEVEL_LOCKED) {
						focusedButton.setStyle(uiSkin.get("default-level",
								TextButtonStyle.class));
						focusedButton.getRightNeighbour().setStyle(
								uiSkin.get("level-focused",
										TextButtonStyle.class));
						focusedButton.getRightNeighbour().setFocused(true);
						focusedButton.setFocused(false);
					}
	
					return true;
	
				} else if (keycode == Input.Keys.LEFT) {
					if (focusedButton.getLeftNeighbour().getType() != MenuButton.LEVEL_LOCKED) {
						focusedButton.setStyle(uiSkin.get("default-level",
								TextButtonStyle.class));
						focusedButton.getLeftNeighbour().setStyle(
								uiSkin.get("level-focused",
										TextButtonStyle.class));
						focusedButton.getLeftNeighbour().setFocused(true);
						focusedButton.setFocused(false);
					}
					
					return true;
				}
				else if (keycode == Input.Keys.ENTER) {
					if (focusedButton != null) {
						int level = Integer.parseInt(focusedButton.getText().toString());
	
						game.ingameScreen.level.levelNumber = level;
						game.setScreen(game.ingameScreen);
						return true;
					}
				}
			}
			else {
				levelButtons[0].setStyle(uiSkin.get("level-focused",
						TextButtonStyle.class));
				levelButtons[0].setFocused(true);
			}
		}

		if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACKSPACE) {
			game.setScreen(game.mainMenuScreen);
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
