package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.MenuButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainMenuScreen implements Screen {
	private Sokobug game;
	private Table table;
	private Stage stage;
	private Sprite background;
	private MenuButton play;
	private MenuButton options;
	private MenuButton credits;
	private MenuButton exit;

	public MainMenuScreen(Sokobug game) {
		this.game = game;
		table = new Table();
		stage = new Stage(game.viewport);

		game.assetManager.load("MainMenuScreen.png", Texture.class);
		game.assetManager.load("skins/uiskin.atlas", TextureAtlas.class);
		game.assetManager.load("skins/uiskin.json", Skin.class,
				new SkinLoader.SkinParameter("skins/uiskin.atlas"));
		game.assetManager.finishLoading();

		background = new Sprite(game.assetManager.get("MainMenuScreen.png",
				Texture.class));
		play = new MenuButton(game, "Play", game.assetManager.get(
				"skins/uiskin.json", Skin.class));
		options = new MenuButton(game, "Options", game.assetManager.get(
				"skins/uiskin.json", Skin.class));
		credits = new MenuButton(game, "Credits", game.assetManager.get(
				"skins/uiskin.json", Skin.class));
		exit = new MenuButton(game, "Exit", game.assetManager.get(
				"skins/uiskin.json", Skin.class));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);

		game.batch.begin();
		background.draw(game.batch);
		game.batch.end();

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f,
				game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		table.add(play).row();
		table.add(options).row();
		table.add(credits).row();
		table.add(exit).row();
		table.setFillParent(true);
		// table.setDebug(true);

		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
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
	public void dispose() {
		stage.dispose();
		game.assetManager.unload("MainMenuScreen.png");
		game.assetManager.unload("skins/uiskin.atlas");
		game.assetManager.unload("skins/uiskin.json");
	}
}
