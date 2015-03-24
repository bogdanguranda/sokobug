package sokobug.domain;

import sokobug.Sokobug;

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
	
	public MenuButton(Sokobug game, String buttonText, int buttonType, Skin skin) {
		this(game, buttonText, buttonType, skin, "default");
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
		if (buttonType == PLAY)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.setScreen(game.chooseLevelScreen);
				}

				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {

					MenuButton focusedButton = getFocusedButton(game.mainMenuScreen.menuButtons);

					if (focusedButton != null)
						focusedButton.setStyle(game.mainMenuScreen.uiSkin
								.get(TextButtonStyle.class));

					defocusButtons(game.mainMenuScreen.menuButtons);
				}
			});
		else if (buttonType == OPTIONS)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.setScreen(game.optionsScreen);
				}

				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {

					MenuButton focusedButton = getFocusedButton(game.mainMenuScreen.menuButtons);

					if (focusedButton != null)
						focusedButton.setStyle(game.mainMenuScreen.uiSkin
								.get(TextButtonStyle.class));

					defocusButtons(game.mainMenuScreen.menuButtons);
				}
			});
		else if (buttonType == CREDITS)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.setScreen(game.creditsScreen);
				}

				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {

					MenuButton focusedButton = getFocusedButton(game.mainMenuScreen.menuButtons);

					if (focusedButton != null)
						focusedButton.setStyle(game.mainMenuScreen.uiSkin
								.get(TextButtonStyle.class));

					defocusButtons(game.mainMenuScreen.menuButtons);
				}
			});
		else if (buttonType == EXIT)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Gdx.app.exit();
				}

				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {

					MenuButton focusedButton = getFocusedButton(game.mainMenuScreen.menuButtons);

					if (focusedButton != null)
						focusedButton.setStyle(game.mainMenuScreen.uiSkin
								.get(TextButtonStyle.class));

					defocusButtons(game.mainMenuScreen.menuButtons);
				}
			});
		else if (buttonType == BACKTOMENU)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.setScreen(game.mainMenuScreen);
				}
			});
		else if (buttonType == LEVEL) {
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					int level = Integer.parseInt(((MenuButton)event.getListenerActor()).getText().toString());
					
					game.ingameScreen.level.levelNumber = level;
					game.setScreen(game.ingameScreen);
				}
				
				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
			
					MenuButton focusedButton = getFocusedButton(game.chooseLevelScreen.levelButtons);

					if (focusedButton != null)
						focusedButton.setStyle(game.mainMenuScreen.uiSkin
								.get("default-level-btn", TextButtonStyle.class));

					defocusButtons(game.mainMenuScreen.menuButtons);
				}
			});
		}
		else if (buttonType == BACKTOCHOOSELEVEL)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.setScreen(game.chooseLevelScreen);
				}
			});
		else
			throw new Exception("Error: Wrong button type...");
	}
}
