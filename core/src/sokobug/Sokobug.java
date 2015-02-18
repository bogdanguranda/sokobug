package sokobug;

import sokobug.screens.LogoScreen;
import sokobug.screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Sokobug extends Game {
	public LogoScreen logoScreen;
	public MainMenuScreen mainMenuScreen;
	//si asa mai departe, alte screens(game states... ex. OptionsScreen, InGameScreen, InGamePauseScreen etc. )
	// bla bla
	
	public SpriteBatch batch;
	public BitmapFont font;
	
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
		font = new BitmapFont();
		
		logoScreen = new LogoScreen(this);
		mainMenuScreen = new MainMenuScreen(this); // sau i-am putea da new doar in logoScreen, asa am face mai putin load dintr-odata...
		this.setScreen(logoScreen);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		
		font.dispose();
		batch.dispose();
	}
	
}
