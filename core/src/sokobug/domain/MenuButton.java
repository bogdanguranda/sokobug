package sokobug.domain;

import sokobug.Sokobug;
import sokobug.screens.ChooseLevelScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuButton extends TextButton {
	private int buttonType;
	private boolean focused;
	private Sokobug game;

	private MenuButton upNeighbour;
	private MenuButton downNeighbour;
	private MenuButton rightNeighbour;
	private MenuButton leftNeighbour;

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
	
	private static final float DEFAULT_BUTTON_CLICK_VOLUME = 0.3f;
	
	public MenuButton(Sokobug game, String buttonText, int buttonType, Skin skin) {
		this(game, buttonText, buttonType, skin, "default-menu");
	}
	
	public MenuButton(Sokobug game, String buttonText, int buttonType, Skin skin, String styleName) {
		super(buttonText, skin, styleName);
		this.buttonType = buttonType;
		this.game = game;
		focused = false;
		upNeighbour = null;
		downNeighbour = null;
		rightNeighbour = null;
		leftNeighbour = null;
		
		try {
			manageEvents();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public int getType() {
		return buttonType;
	}
	
	public boolean isFocused() {
		return focused;
	}

	public void setFocused(boolean type) {
		focused = type;
	}

	public MenuButton getUpNeighbour() {
		return upNeighbour;
	}

	public MenuButton getDownNeighbour() {
		return downNeighbour;
	}

	public MenuButton getRightNeighbour() {
		return rightNeighbour;
	}

	public MenuButton getLeftNeighbour() {
		return leftNeighbour;
	}

	public void setUpNeighbour(MenuButton upNeighbour) {
		this.upNeighbour = upNeighbour;
	}

	public void setDownNeighbour(MenuButton downNeighbour) {
		this.downNeighbour = downNeighbour;
	}

	public void setRightNeighbour(MenuButton rightNeighbour) {
		this.rightNeighbour = rightNeighbour;
	}

	public void setLeftNeighbour(MenuButton leftNeighbour) {
		this.leftNeighbour = leftNeighbour;
	}
	
	public static MenuButton getFocusedButton(MenuButton[] buttons) {
		for (MenuButton button : buttons)
			if (button.isFocused())
				return button;

		return null;
	}
	
	public static void defocusButtons(MenuButton[] buttons) {
		for (MenuButton button : buttons)
			button.setFocused(false);
	}
	
	public static boolean isMouseOverButton(MenuButton[] buttons) {
		for (MenuButton button : buttons)
			if (button.isOver())
				return true;
		return false;
	}

	private void manageEvents() throws Exception {
		if (buttonType == PLAY) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.chooseChapterScreen);
				}

				@Override
				//this is called when mouse if over or touch is over a widget (button)
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {

					MenuButton focusedButton = getFocusedButton(game.mainMenuScreen.menuButtons);

					if (focusedButton != null)
						focusedButton.setStyle(game.mainMenuScreen.uiSkin
								.get(TextButtonStyle.class));

					defocusButtons(game.mainMenuScreen.menuButtons);
				}
			});
		}
		else if (buttonType == CREDITS) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.creditsScreen);
				}

				@Override
				//this is called when mouse if over or touch is over a widget (button)
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {

					MenuButton focusedButton = getFocusedButton(game.mainMenuScreen.menuButtons);

					if (focusedButton != null)
						focusedButton.setStyle(game.mainMenuScreen.uiSkin
								.get(TextButtonStyle.class));

					defocusButtons(game.mainMenuScreen.menuButtons);
				}
			});
		}
		else if (buttonType == EXIT) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					Gdx.app.exit();
				}

				@Override
				//this is called when mouse if over or touch is over a widget (button)
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {

					MenuButton focusedButton = getFocusedButton(game.mainMenuScreen.menuButtons);

					if (focusedButton != null)
						focusedButton.setStyle(game.mainMenuScreen.uiSkin
								.get(TextButtonStyle.class));

					defocusButtons(game.mainMenuScreen.menuButtons);
				}
			});
		}
		else if (buttonType == BACKTOMENU) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.mainMenuScreen);
				}
			});
		}
		else if (buttonType == LEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					int level = Integer.parseInt(((MenuButton)event.getListenerActor()).getText().toString());
					
					game.ingameScreen.level.levelNumber = level;
					game.setScreen(game.ingameScreen);
				}
				
				@Override
				//this is called when mouse if over or touch is over a widget (button)
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
			
					MenuButton focusedButton = getFocusedButton(game.chooseLevelScreen.levelButtons);

					if (focusedButton != null)
						focusedButton.setStyle(game.mainMenuScreen.uiSkin
								.get("default-level", TextButtonStyle.class));

					defocusButtons(game.mainMenuScreen.menuButtons);
				}
			});
		}
		else if (buttonType == BACKTOCHOOSELEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.chooseLevelScreen);
				}
			});
		}
		else if (buttonType == BACKTOCHOOSECHAPTER) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.chooseChapterScreen);
				}
			});
		}
		else if (buttonType == FORWARD) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					int nextLevel = game.ingameScreen.level.levelNumber + 1;
					if (nextLevel <= ChooseLevelScreen.NUM_LEVELS) {
						game.ingameScreen.level.levelNumber = nextLevel;
						game.setScreen(game.ingameScreen);
					}
					else {
						game.setScreen(game.chooseLevelScreen);
					}
				}
			});
		}
		else if (buttonType == RESTART) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.ingameScreen);
				}
			});
		}
		else if (buttonType == LEVEL_LOCKED) {
			// nothing
		}
		else if (buttonType == SKIP_LEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					PlayerProgressManager.getPlayerProgressManager().skipCurentLevel();
					game.setScreen(game.chooseLevelScreen);
				}
			});
		}
		else if (buttonType == CHAPTER) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.soundManager.getSound("buttonClick.wav").play(DEFAULT_BUTTON_CLICK_VOLUME);
					game.setScreen(game.chooseLevelScreen);
				}
			});
		}
		else
			throw new Exception("Error: Wrong button type...");
	}
}
