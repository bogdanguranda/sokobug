package sokobug.screens;

import sokobug.Sokobug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MainMenuScreen implements Screen{

	Sokobug game;
    private BitmapFont font;
	
	public MainMenuScreen(Sokobug myGame) {
		game = myGame;
		
		font = game.assetManager.get("Papyrus.fnt", BitmapFont.class);
		font.setColor(Color.CYAN);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, Color.DARK_GRAY.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
		game.batch.begin();
		String text = "MAIN MENU";
		font.draw(game.batch, text, (game.VIRTUAL_WIDTH / 2) - font.getBounds(text).width / 2, (game.VIRTUAL_HEIGHT / 2) - font.getBounds(text).height / 2);
		game.batch.end();
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f, game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
