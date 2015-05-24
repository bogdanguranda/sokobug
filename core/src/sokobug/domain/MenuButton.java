package sokobug.domain;

import sokobug.Sokobug;
import sokobug.screens.ChooseLevelScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuButton extends TextButton {
	private Sokobug game;
	private int buttonType;

	public static final int PLAY = 0;
	public static final int OPTIONS = 1;
	public static final int CREDITS = 2;
	public static final int EXIT = 3;
	public static final int BACKTOMENU = 4;
	public static final int BACKTOCHOOSELEVEL = 5;
	public static final int LEVEL = 6;
	public static final int FORWARD = 7;
	public static final int RESTART = 8;
	public static final int LEVEL_LOCKED = 9;
	public static final int SKIP_LEVEL = 10;
	public static final int CHAPTER = 11;
	public static final int BACKTOCHOOSECHAPTER = 12;
	public static final int SOUNDONOFF = 13;

	private static final String CLICK_SOUND = "buttonClick.ogg";
	private static final float DEFAULT_BUTTON_CLICK_VOLUME = 0.3f;

	public MenuButton(Sokobug game, String buttonText, int buttonType, Skin skin, String styleName) {
		super(buttonText, skin, styleName);
		this.buttonType = buttonType;
		this.game = game;

		addButtonBehaviour();
	}

	public int getType() {
		return buttonType;
	}

	private void addButtonBehaviour() {
		if (buttonType == PLAY) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.chooseChapterScreen);
				}
			});
		} else if (buttonType == CREDITS) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.creditsScreen);
				}
			});
		} else if (buttonType == EXIT) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					Gdx.app.exit();
				}
			});
		} else if (buttonType == BACKTOMENU) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.mainMenuScreen);
				}
			});
		} else if (buttonType == LEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					int level = Integer.parseInt(((MenuButton) event.getListenerActor()).getText().toString());

					game.ingameScreen.level.levelNumber = level;
					game.setScreen(game.ingameScreen);
				}
			});
		} else if (buttonType == BACKTOCHOOSELEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.chooseLevelScreen);
				}
			});
		} else if (buttonType == BACKTOCHOOSECHAPTER) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.chooseChapterScreen);
				}
			});
		} else if (buttonType == FORWARD) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					int nextLevel = game.ingameScreen.level.levelNumber + 1;
					if (nextLevel <= ChooseLevelScreen.NUM_LEVELS) {
						game.ingameScreen.level.levelNumber = nextLevel;
						game.setScreen(game.ingameScreen);
					} else {
						game.setScreen(game.chooseLevelScreen);
					}
				}
			});
		} else if (buttonType == RESTART) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.ingameScreen);
				}
			});
		} else if (buttonType == LEVEL_LOCKED) {
			// nothing
		} else if (buttonType == SKIP_LEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					PlayerProgressManager.getPlayerProgressManager().skipCurentLevel();
					game.setScreen(game.chooseLevelScreen);
				}
			});
		} else if (buttonType == CHAPTER) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.chooseLevelScreen);
				}
			});
		} else if (buttonType == SOUNDONOFF) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound(CLICK_SOUND).play(DEFAULT_BUTTON_CLICK_VOLUME);
					if (game.soundManager.isMuted()) {
						game.soundManager.setMute(false);
						setStyle(game.assetManager.get("ui/buttons/buttons.json", Skin.class).get("soundOn",
								TextButtonStyle.class));
					} else {
						game.soundManager.setMute(true);
						setStyle(game.assetManager.get("ui/buttons/buttons.json", Skin.class).get("soundOff",
								TextButtonStyle.class));
					}
				}
			});
		}
	}
}
