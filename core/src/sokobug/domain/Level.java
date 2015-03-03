package sokobug.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import sokobug.Sokobug;

public class Level implements InputProcessor{
	
	private Sokobug game;
	public int levelNumber;
	
	private static final int LABYRINTH_ROWS = 9;
	private static final int LABYRINTH_COLUMNS = 16;
	
	private String[][] labyrinth = new String[LABYRINTH_ROWS][LABYRINTH_COLUMNS];
	
	private Map<String, Sprite> labyrinthSprites = new HashMap<String, Sprite>();

	private Bug bug;
	private ArrayList<Vase> vases = new ArrayList<Vase>();
	
	public Level(Sokobug game) {
		this.game = game;
		
		labyrinthSprites.put("F", new Sprite(game.assetManager.get("ingame/free.png", Texture.class)));
		labyrinthSprites.put("W", new Sprite(game.assetManager.get("ingame/wall.png", Texture.class)));
		labyrinthSprites.put("S", new Sprite(game.assetManager.get("ingame/spot.png", Texture.class)));
		labyrinthSprites.put("V", new Sprite(game.assetManager.get("ingame/vase.png", Texture.class)));
		labyrinthSprites.put("B", new Sprite(game.assetManager.get("ingame/bug.png", Texture.class)));
		
		bug  = new Bug(game.assetManager.get("ingame/bug.png", Texture.class));
	}

	public Vector2 getSize() {
		return new Vector2(LABYRINTH_COLUMNS * labyrinthSprites.get("F").getWidth(), LABYRINTH_ROWS * labyrinthSprites.get("F").getHeight());
	}
	
	public void load(int levelNumber) {
		this.levelNumber = levelNumber;
		this.vases.clear();
		
		FileHandle file = Gdx.files.internal("ingame/level" + String.valueOf(levelNumber) + ".txt");
		String text = file.readString();
		String[] lines = text.split("\\r?\\n");
		for (int i = 0; i < LABYRINTH_ROWS; i++) {
			String[] lineElements = lines[i].split("\\s");
			for (int j = 0; j < LABYRINTH_COLUMNS; j++) {
				if (lineElements[j].compareTo("B") == 0) {
					bug.positionLine = i;
					bug.positionColumn = j;
					labyrinth[(LABYRINTH_ROWS-1) - i][j] = "F"; // at the start the bug stays on a free spot always(he could stay on a spot(S) though...)
				}
				else if (lineElements[j].compareTo("V") == 0) {
					Vase v = new Vase(game.assetManager.get("ingame/vase.png", Texture.class));
					v.positionLine = i;
					v.positionColumn = j;
					vases.add(v);
					labyrinth[(LABYRINTH_ROWS-1) - i][j] = "F"; // at the start the bug stays on a free spot always(he could stay on a spot(S)
				}
				else
					labyrinth[(LABYRINTH_ROWS-1) - i][j] = lineElements[j];
			}
		}
		
	}
	
	public void render(float deltaTime) {
		game.batch.begin();
		
		for (int i = 0; i < LABYRINTH_ROWS; i++) {
			for (int j = 0; j < LABYRINTH_COLUMNS; j++) {
				float x = j * labyrinthSprites.get(labyrinth[i][j]).getWidth();
				float y = i * labyrinthSprites.get(labyrinth[i][j]).getHeight();
				labyrinthSprites.get(labyrinth[i][j]).setPosition(x, y);
				labyrinthSprites.get(labyrinth[i][j]).draw(game.batch);
			}
		}
		
		for (Vase v: vases) {
			v.sprite.setPosition(v.positionColumn * v.sprite.getWidth(), v.positionLine * v.sprite.getHeight());
			v.sprite.draw(game.batch);
		}
		
		bug.sprite.setPosition(bug.positionColumn * bug.sprite.getWidth(), bug.positionLine * bug.sprite.getHeight());
		bug.sprite.draw(game.batch);
		
		game.batch.end();
	}
	
	public void updateBug(int MOVE_DIRECTION, float deltaTime) { // collision detection should be here
		
	}
	
	public boolean isVictory() {
		return false;
	}
	
	public boolean isGameOver() {
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
