package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.MenuButton;
import sokobug.domain.PlayerProgressManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class VictoryScreen implements Screen, InputProcessor {
	private Sokobug game;
	private BitmapFont font;
	private Stage stage;
	
	private MenuButton backButton;
	private MenuButton forwardButton;
	private Sprite victoryMessage;
	private Sprite victoryWing;
	private Animation victoryGlow;
	private float animationTime;
	
	private InputMultiplexer multiplexer;

	public VictoryScreen(Sokobug myGame) {
		game = myGame;
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();

		font = game.assetManager.get("fonts/Japonesa120.fnt", BitmapFont.class);

		font.setScale(1.0f);
		victoryMessage = new Sprite(game.assetManager.get("level/finish.png", Texture.class));
		victoryMessage.setPosition(game.VIRTUAL_WIDTH / 2.f - victoryMessage.getWidth() / 2.f, game.VIRTUAL_HEIGHT / 2.f + victoryMessage.getHeight() / 2.f);
		
		victoryWing = new Sprite(game.assetManager.get("level/victoryWing.png", Texture.class));
		victoryWing.setPosition(victoryMessage.getX() + (victoryMessage.getWidth() - victoryWing.getWidth()) / 2.f, victoryMessage.getY() - victoryWing.getHeight());
		
		backButton = new MenuButton(game, "", MenuButton.BACKTOCHOOSELEVEL, game.assetManager.get("ui/buttons/buttons.json", Skin.class), "ingame-back");
		backButton.setPosition(victoryWing.getX(), victoryWing.getY() - backButton.getHeight());
		
		forwardButton = new MenuButton(game, "", MenuButton.FORWARD, game.assetManager.get("ui/buttons/buttons.json", Skin.class), "default-forward");
		forwardButton.setPosition(victoryWing.getX() + victoryWing.getWidth() - forwardButton.getWidth(), backButton.getY());
		
		victoryGlow = new Animation(1/24.f, game.assetManager.get("level/animations/victory/victoryGlow.pack", TextureAtlas.class).getRegions());
        victoryGlow.setPlayMode(PlayMode.LOOP);
        animationTime = 0.f;
        
		stage.addActor(backButton);
		stage.addActor(forwardButton);
		
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
	}

	@Override
	public void render(float delta) {
		game.ingameScreen.render(delta);
		
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
		animationTime += delta;

		game.batch.begin();
		victoryMessage.draw(game.batch);
		victoryWing.draw(game.batch);
		game.batch.draw(victoryGlow.getKeyFrame(animationTime), 
				victoryWing.getX() + (victoryWing.getWidth() - victoryGlow.getKeyFrames()[0].getRegionWidth()) / 2.f, 
				(victoryWing.getY() + victoryWing.getHeight() / 2) - (victoryGlow.getKeyFrames()[0].getRegionHeight() / 2) + 5.f);
		game.batch.end();

		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f, game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		int currentMaxLevelUnlocked = PlayerProgressManager.getPlayerProgressManager().getCurrentLevel();
		int levelFinished = game.ingameScreen.level.levelNumber;
		if (levelFinished == currentMaxLevelUnlocked) {
			PlayerProgressManager.getPlayerProgressManager().setCurrentLevel(currentMaxLevelUnlocked + 1);
		}
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
