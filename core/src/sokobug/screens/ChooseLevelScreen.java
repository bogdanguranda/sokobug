package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.MenuButton;
import sokobug.domain.PlayerProgress;
import sokobug.domain.Resources;
import sokobug.domain.PlayerProgressManager;

import com.badlogic.gdx.Gdx;
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
	private Sokobug game;
	private Stage stage;
	private Table table;
	private MenuButton backToChooseChapter;
	private MenuButton skipCurrentLevel;
	private MenuButton sound;
	private MenuButton[] levelButtons;
	private Sprite background;
	private InputMultiplexer multiplexer;

	public static final int NUMBER_OF_LEVELS = PlayerProgress.NUMBER_OF_LEVELS;
	private static final int BUTTONS_PER_ROW = 8;

	private int currentMaxUnlockedLevel;

	public ChooseLevelScreen(Sokobug myGame) {
		game = myGame;
		table = new Table();
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();
		levelButtons = new MenuButton[NUMBER_OF_LEVELS];
		
		background = new Sprite(game.assetManager.get(Resources.BACKGROUNDS_MENU.getPath(), Texture.class));

		backToChooseChapter = new MenuButton(game, "", MenuButton.Type.BACKTOCHOOSECHAPTER, game.assetManager.get(
				Resources.UI_BUTTONS_JSON.getPath(), Skin.class), "menu-back");
		backToChooseChapter.setPosition(0, 0);

		skipCurrentLevel = new MenuButton(game, "Skip level", MenuButton.Type.SKIP_LEVEL_BUTTON, game.assetManager.get(
				Resources.UI_BUTTONS_JSON.getPath(), Skin.class), "default-menu");
		skipCurrentLevel.setPosition(game.VIRTUAL_WIDTH - skipCurrentLevel.getWidth(), 0);

		sound = new MenuButton(game, "", MenuButton.Type.SOUNDONOFF, game.assetManager.get(
				Resources.UI_BUTTONS_JSON.getPath(), Skin.class), "soundOn");
		sound.setPosition(game.VIRTUAL_WIDTH - sound.getWidth() * 3.f / 2.f, game.VIRTUAL_HEIGHT - sound.getHeight()
				* 3.f / 2.f);

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
			sound.setStyle(game.assetManager.get(Resources.UI_BUTTONS_JSON.getPath(), Skin.class).get("soundOff",
					TextButtonStyle.class));
		} else {
			sound.setStyle(game.assetManager.get(Resources.UI_BUTTONS_JSON.getPath(), Skin.class).get("soundOn",
					TextButtonStyle.class));
		}

		currentMaxUnlockedLevel = PlayerProgressManager.getPlayerProgressManager().getCurrentLevel(
				game.chooseChapterScreen.currentChapter);

		for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
			if (PlayerProgressManager.getPlayerProgressManager().isLevelSkipped(i + 1,
					game.chooseChapterScreen.currentChapter)) {
				levelButtons[i] = new MenuButton(game, Integer.toString(i + 1), MenuButton.Type.LEVEL,
						game.assetManager.get(Resources.UI_BUTTONS_JSON.getPath(), Skin.class), "default-skiped-level");
			} else if (i + 1 <= currentMaxUnlockedLevel) {
				levelButtons[i] = new MenuButton(game, Integer.toString(i + 1), MenuButton.Type.LEVEL,
						game.assetManager.get(Resources.UI_BUTTONS_JSON.getPath(), Skin.class), "default-level");
			} else {
				levelButtons[i] = new MenuButton(game, Integer.toString(i + 1), MenuButton.Type.LEVEL_LOCKED,
						game.assetManager.get(Resources.UI_BUTTONS_JSON.getPath(), Skin.class), "level-locked");
			}
		}
		table.clear();
		for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
			table.add(levelButtons[i]).pad(15);
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
