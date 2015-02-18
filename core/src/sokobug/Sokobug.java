package sokobug;

import sokobug.screens.LogoScreen;
import sokobug.screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Sokobug extends Game {
	public LogoScreen logoScreen;
	public MainMenuScreen mainMenuScreen;
	//si asa mai departe, alte screens(game states... ex. OptionsScreen, InGameScreen, InGamePauseScreen etc. )
	// bla bla
	
	public AssetManager assetManager;
	
	public SpriteBatch batch;
	
	public final float VIRTUAL_WIDTH = 1366;
	public final float VIRTUAL_HEIGHT = 768;
	public OrthographicCamera camera;
	public FillViewport viewport;
	
	@Override
	public void create() {
		//float ratio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		camera = new OrthographicCamera();
		viewport = new FillViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
		viewport.apply();
		camera.position.set(VIRTUAL_WIDTH / 2.f, VIRTUAL_HEIGHT / 2.f, 0.f);
		
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.load("Papyrus.fnt", BitmapFont.class);
		
		logoScreen = new LogoScreen(this);
		mainMenuScreen = new MainMenuScreen(this); // sau i-am putea da new doar in logoScreen, asa am face mai putin load dintr-odata...
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
