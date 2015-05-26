package sokobug.domain;

import sokobug.Sokobug;
import sokobug.screens.ChooseLevelScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuButton extends TextButton {
	public enum Type {
		PLAY, OPTIONS, CREDITS, EXIT, BACKTOMENU, BACKTOCHOOSELEVEL, LEVEL, 
		FORWARD, RESTART, LEVEL_LOCKED, SKIP_LEVEL_BUTTON, CHAPTER, BACKTOCHOOSECHAPTER, SOUNDONOFF
	}

	private static final String CLICK_SOUND = "buttonClick.ogg";
	private Sokobug game;
	private Type buttonType;

	public MenuButton(Sokobug game, String buttonText, Type buttonType, Skin skin, String styleName) {
		super(buttonText, skin, styleName);
		this.buttonType = buttonType;
		this.game = game;

		addButtonBehaviour();
	}

	public Type getType() {
		return buttonType;
	}

//	public void setType(Type buttonType) {
//		this.buttonType = buttonType;
//		this.getListeners().
//		addButtonBehaviour();
//	}

	private void addButtonBehaviour() {
		if (buttonType == Type.PLAY) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					game.setScreen(game.chooseChapterScreen);
				}
			});
		} else if (buttonType == Type.CREDITS) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					game.setScreen(game.creditsScreen);
				}
			});
		} else if (buttonType == Type.EXIT) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					Gdx.app.exit();
				}
			});
		} else if (buttonType == Type.BACKTOMENU) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					game.setScreen(game.mainMenuScreen);
				}
			});
		} else if (buttonType == Type.LEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					int level = Integer.parseInt(((MenuButton) event.getListenerActor()).getText().toString());

					game.ingameScreen.level.levelNumber = level;
					game.setScreen(game.ingameScreen);
				}
			});
		} else if (buttonType == Type.BACKTOCHOOSELEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					game.setScreen(game.chooseLevelScreen);
				}
			});
		} else if (buttonType == Type.BACKTOCHOOSECHAPTER) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					game.setScreen(game.chooseChapterScreen);
				}
			});
		} else if (buttonType == Type.FORWARD) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					int nextLevel = game.ingameScreen.level.levelNumber + 1;
					if (nextLevel <= ChooseLevelScreen.NUM_LEVELS) {
						game.ingameScreen.level.levelNumber = nextLevel;
						game.setScreen(game.ingameScreen);
					} else {
						game.setScreen(game.chooseLevelScreen);
					}
				}
			});
		} else if (buttonType == Type.RESTART) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					game.setScreen(game.ingameScreen);
				}
			});
		} else if (buttonType == Type.LEVEL_LOCKED) {
			// nothing
		} else if (buttonType == Type.SKIP_LEVEL_BUTTON) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					PlayerProgressManager.getPlayerProgressManager().skipCurentLevel();
					game.setScreen(game.chooseLevelScreen);
				}
			});
		} else if (buttonType == Type.CHAPTER) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
					game.setScreen(game.chooseLevelScreen);
				}
			});
		} else if (buttonType == Type.SOUNDONOFF) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(CLICK_SOUND);
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
