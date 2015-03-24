package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.MenuButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CreditsScreen implements Screen, InputProcessor {

	Sokobug game;
	private BitmapFont font;
	private Stage stage;
	private MenuButton backToMenu;
	private InputMultiplexer multiplexer;

	public CreditsScreen(Sokobug myGame) {
		game = myGame;
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();
		game.assetManager.load("fonts/Papyrus58.fnt", BitmapFont.class);
		game.assetManager.finishLoading();

		font = game.assetManager.get("fonts/Papyrus58.fnt", BitmapFont.class);
		game.assetManager.load("ui/buttons/buttons.pack", TextureAtlas.class);
		game.assetManager.load("ui/buttons/buttons.json", Skin.class,
				new SkinLoader.SkinParameter("ui/buttons/buttons.pack"));
		game.assetManager.finishLoading();

		backToMenu = new MenuButton(game, "Back",
				MenuButton.BACKTOMENU, game.assetManager.get("ui/buttons/buttons.json", Skin.class));

		backToMenu.setPosition(0, 0);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b,
				Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);

		font.setColor(Color.WHITE);
		String title = "Credits";
		String text = "Developers: Potatoes(Bogdan Guranda, Ciprian-Corvin Tiperciuc)\n"
				+ "Artist: Aarts(Andrei Guranda)";

		game.batch.begin();
		font.setScale(1.f);
		font.draw(game.batch, title,
				(game.VIRTUAL_WIDTH / 2) - font.getBounds(title).width / 2,
				game.VIRTUAL_HEIGHT - font.getBounds(title).height / 2);
		font.setScale(0.5f);
		font.drawMultiLine(game.batch, text, 0,
				game.VIRTUAL_HEIGHT - font.getBounds(title).width * 2);
		game.batch.end();

		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		game.assetManager.unload("ui/buttons/buttons.pack");
		game.assetManager.unload("ui/buttons/buttons.json");
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f,
				game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		stage.addActor(backToMenu);
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
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
		if (keycode == Input.Keys.ESCAPE) {
			game.setScreen(game.mainMenuScreen);
			return true;
		}
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
