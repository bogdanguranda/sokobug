package sokobug;

import sokobug.screens.ChooseLevelScreen;
import sokobug.screens.CreditsScreen;
import sokobug.screens.LogoScreen;
import sokobug.screens.MainMenuScreen;
import sokobug.screens.OptionsScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Sokobug extends Game {
	public LogoScreen logoScreen;
	public MainMenuScreen mainMenuScreen;
	public CreditsScreen creditsScreen;
	public OptionsScreen optionsScreen;
	public ChooseLevelScreen chooseLevelScreen;
	//si asa mai departe, alte screens(game states... ex. OptionsScreen, InGameScreen, InGamePauseScreen etc. )
	// bla bla
	
	public AssetManager assetManager;
	
	public SpriteBatch batch;
	
	public final float VIRTUAL_WIDTH = 1280;
	public final float VIRTUAL_HEIGHT = 800;
	public OrthographicCamera camera;
	public FitViewport viewport;
	
	@Override
	public void create() {
		//float ratio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		camera = new OrthographicCamera();
		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
		viewport.apply();
		
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.load("fonts/Papyrus.fnt", BitmapFont.class);
		
		logoScreen = new LogoScreen(this);
		mainMenuScreen = new MainMenuScreen(this); // sau i-am putea da new doar in logoScreen, asa am face mai putin load dintr-odata...
		creditsScreen = new CreditsScreen(this);
		optionsScreen = new OptionsScreen(this);
		chooseLevelScreen = new ChooseLevelScreen(this);
		this.setScreen(logoScreen);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		batch.dispose();
		assetManager.dispose();
	}
	
}
