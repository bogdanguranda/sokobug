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

	private void addButtonBehaviour() {
		if (buttonType == Type.PLAY) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					game.setScreen(game.chooseChapterScreen);
				}
			});
		} else if (buttonType == Type.CREDITS) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					game.setScreen(game.creditsScreen);
				}
			});
		} else if (buttonType == Type.EXIT) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					Gdx.app.exit();
				}
			});
		} else if (buttonType == Type.BACKTOMENU) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					game.setScreen(game.mainMenuScreen);
				}
			});
		} else if (buttonType == Type.LEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					int level = Integer.parseInt(((MenuButton) event.getListenerActor()).getText().toString());

					game.ingameScreen.level.levelNumber = level;
					game.ingameScreen.level.chapterNumber = game.chooseChapterScreen.currentChapter;
					game.setScreen(game.ingameScreen);
				}
			});
		} else if (buttonType == Type.BACKTOCHOOSELEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					game.setScreen(game.chooseLevelScreen);
				}
			});
		} else if (buttonType == Type.BACKTOCHOOSECHAPTER) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					game.setScreen(game.chooseChapterScreen);
				}
			});
		} else if (buttonType == Type.FORWARD) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					int nextLevel = game.ingameScreen.level.levelNumber + 1;
					if (nextLevel <= ChooseLevelScreen.NUMBER_OF_LEVELS) {
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
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					game.setScreen(game.ingameScreen);
				}
			});
		} else if (buttonType == Type.LEVEL_LOCKED) {
			// nothing
		} else if (buttonType == Type.SKIP_LEVEL_BUTTON) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					PlayerProgressManager.getPlayerProgressManager().skipCurentLevel(game.chooseChapterScreen.currentChapter);
					game.setScreen(game.chooseLevelScreen);
				}
			});
		} else if (buttonType == Type.CHAPTER) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					String chapterText = ((MenuButton) event.getListenerActor()).getText().toString();
					int chapter = Integer.parseInt(chapterText.substring(chapterText.length() - 1, chapterText.length()));
					game.chooseChapterScreen.currentChapter = chapter;
					game.setScreen(game.chooseLevelScreen);
				}
			});
		} else if (buttonType == Type.SOUNDONOFF) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.playSound(Resources.SOUND_BUTTONCLICK.getPath());
					if (game.soundManager.isMuted()) {
						game.soundManager.setMute(false);
						setStyle(game.assetManager.get(Resources.UI_BUTTONS_JSON.getPath(), Skin.class).get("soundOn",
								TextButtonStyle.class));
					} else {
						game.soundManager.setMute(true);
						setStyle(game.assetManager.get(Resources.UI_BUTTONS_JSON.getPath(), Skin.class).get("soundOff",
								TextButtonStyle.class));
					}
				}
			});
		}
	}
}
