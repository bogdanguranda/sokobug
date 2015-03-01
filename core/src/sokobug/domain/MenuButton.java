package sokobug.domain;

import sokobug.screens.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuButton extends TextButton {
	private String buttonType;
	private MainMenuScreen menu;

	public MenuButton(MainMenuScreen menu, String buttonType, Skin skin) {
		super(buttonType, skin);
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
		if (buttonType.compareTo("Play") == 0) 
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					menu.game.setScreen(menu.game.chooseLevelScreen);
				}
				
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
					menu.getFocusedButton().setStyle(menu.normalButtonSkin.get(TextButtonStyle.class));
					menu.defocusButtons();		
				}
			});
		else if (buttonType.compareTo("Options") == 0)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					menu.game.setScreen(menu.game.optionsScreen);
				}
				
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					menu.getFocusedButton().setStyle(menu.normalButtonSkin.get(TextButtonStyle.class));
					menu.defocusButtons();				
				}
			});
		else if (buttonType.compareTo("Credits") == 0)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					menu.game.setScreen(menu.game.creditsScreen);
				}
				
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					menu.getFocusedButton().setStyle(menu.normalButtonSkin.get(TextButtonStyle.class));
					menu.defocusButtons();
				}
			});
		else if (buttonType.compareTo("Exit") == 0)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Gdx.app.exit();
				}
				
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					menu.getFocusedButton().setStyle(menu.normalButtonSkin.get(TextButtonStyle.class));
					menu.defocusButtons();
				}
			});
		else if (buttonType.compareTo("BackToMenu") == 0)
			this.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					menu.game.setScreen(menu.game.mainMenuScreen);
				}
			});
		else
			throw new Exception("Error: Wrong button type...");
	}
}
