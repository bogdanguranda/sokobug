package sokobug.domain;

import sokobug.screens.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuButton extends TextButton {
	private int buttonType;
	private MainMenuScreen menu;

	public static final int PLAY = 0;
	public static final int OPTIONS = 1;
	public static final int CREDITS = 2;
	public static final int EXIT = 3;
	public static final int BACK = 4;

	public MenuButton(MainMenuScreen menu, String buttonText, int buttonType,
			Skin skin) {
		super(buttonText, skin);
		this.buttonType = buttonType;
		this.menu = menu;

		try {
			manageEvents();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void manageEvents() throws Exception {
		if (buttonType == PLAY)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					menu.game.setScreen(menu.game.chooseLevelScreen);
				}

				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					menu.getFocusedButton().setStyle(
							menu.normalButtonSkin.get(TextButtonStyle.class));
					menu.defocusButtons();
				}
			});
		else if (buttonType == OPTIONS)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					menu.game.setScreen(menu.game.optionsScreen);
				}

				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					menu.getFocusedButton().setStyle(
							menu.normalButtonSkin.get(TextButtonStyle.class));
					menu.defocusButtons();
				}
			});
		else if (buttonType == CREDITS)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					menu.game.setScreen(menu.game.creditsScreen);
				}

				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					menu.getFocusedButton().setStyle(
							menu.normalButtonSkin.get(TextButtonStyle.class));
					menu.defocusButtons();
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
					menu.getFocusedButton().setStyle(
							menu.normalButtonSkin.get(TextButtonStyle.class));
					menu.defocusButtons();
				}
			});
		else if (buttonType == BACK)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					menu.game.setScreen(menu);
				}
			});
		else
			throw new Exception("Error: Wrong button type...");
	}
}
