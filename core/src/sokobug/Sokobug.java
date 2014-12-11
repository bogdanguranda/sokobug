package sokobug;

import sokobug.screens.LogoScreen;
import sokobug.screens.MainMenuScreen;

import com.badlogic.gdx.Game;

public class Sokobug extends Game {
	public LogoScreen logoScreen;
	public MainMenuScreen mainMenuScreen;
	//si asa mai departe, alte screens(game states... ex. OptionsScreen, InGameScreen, InGamePauseScreen etc. )
	// bla bla
	
	@Override
	public void create() {
		logoScreen = new LogoScreen(this);
		mainMenuScreen = new MainMenuScreen(this); // sau i-am putea da new doar in logoScreen, asa am face mai putin load dintr-odata...
		this.setScreen(logoScreen);
	}

}

//proba