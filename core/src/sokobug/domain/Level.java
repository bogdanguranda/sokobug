package sokobug.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

	private MovingObject bug;
	private ArrayList<MovingObject> vases = new ArrayList<MovingObject>();
	private ArrayList<LevelObject> walls = new ArrayList<LevelObject>();
	
	public Level(Sokobug game) {
		this.game = game;
		
		labyrinthSprites.put("F", new Sprite(game.assetManager.get("ingame/free.png", Texture.class)));
		labyrinthSprites.put("W", new Sprite(game.assetManager.get("ingame/wall.png", Texture.class)));
		labyrinthSprites.put("S", new Sprite(game.assetManager.get("ingame/spot.png", Texture.class)));
		labyrinthSprites.put("V", new Sprite(game.assetManager.get("ingame/vase.png", Texture.class)));
		labyrinthSprites.put("B", new Sprite(game.assetManager.get("ingame/bug.png", Texture.class)));
		
		bug  = new MovingObject(game.assetManager.get("ingame/bug.png", Texture.class), LevelObject.TYPE_BUG);
	}

	public Vector2 getSize() {
		return new Vector2(LABYRINTH_COLUMNS * labyrinthSprites.get("F").getWidth(), LABYRINTH_ROWS * labyrinthSprites.get("F").getHeight());
	}
	
	public void load(int levelNumber) {
		this.levelNumber = levelNumber;
		this.vases.clear();
		this.walls.clear();
		
		FileHandle file = Gdx.files.internal("ingame/level" + String.valueOf(levelNumber) + ".txt");
		String text = file.readString();
		String[] lines = text.split("\\r?\\n");
		for (int i = 0; i < LABYRINTH_ROWS; i++) {
			String[] lineElements = lines[i].split("\\s");
			for (int j = 0; j < LABYRINTH_COLUMNS; j++) {
				if (lineElements[j].compareTo("B") == 0) {
					bug.setPositionLine((LABYRINTH_ROWS-1) - i);
					bug.setPositionColumn(j);
					labyrinth[(LABYRINTH_ROWS-1) - i][j] = "F"; // at the start the bug stays on a free spot always(he could stay on a spot(S) though...)
				}
				else if (lineElements[j].compareTo("V") == 0) {
					MovingObject v = new MovingObject(game.assetManager.get("ingame/vase.png", Texture.class), LevelObject.TYPE_VASE);
					v.setPositionLine((LABYRINTH_ROWS-1) - i);
					v.setPositionColumn(j);
					vases.add(v);
					labyrinth[(LABYRINTH_ROWS-1) - i][j] = "F"; // at the start the bug stays on a free spot always(he could stay on a spot(S)
				}
				else if (lineElements[j].compareTo("W") == 0) {
					LevelObject w = new LevelObject(game.assetManager.get("ingame/wall.png", Texture.class), LevelObject.TYPE_WALL);
					w.setPositionLine((LABYRINTH_ROWS-1) - i);
					w.setPositionColumn(j);
					walls.add(w);
					labyrinth[(LABYRINTH_ROWS-1) - i][j] = "F"; // at the start the bug stays on a free spot always(he could stay on a spot(S)
				}
				else
					labyrinth[(LABYRINTH_ROWS-1) - i][j] = lineElements[j];
			}
		}
		
	}
	
	public void render(float deltaTime) {
		update(deltaTime);
		
		game.batch.begin();
		
		for (int i = 0; i < LABYRINTH_ROWS; i++) {
			for (int j = 0; j < LABYRINTH_COLUMNS; j++) {
				float x = j * labyrinthSprites.get(labyrinth[i][j]).getWidth();
				float y = i * labyrinthSprites.get(labyrinth[i][j]).getHeight();
				labyrinthSprites.get(labyrinth[i][j]).setPosition(x, y);
				labyrinthSprites.get(labyrinth[i][j]).draw(game.batch);
			}
		}
		
		for (LevelObject w: walls) {
			w.getSprite().draw(game.batch);
		}
		
		for (MovingObject v: vases) {
			v.getSprite().draw(game.batch);
		}
		
		bug.getSprite().draw(game.batch);
		
		game.batch.end();
	}
	
	public LevelObject isCollidingWith(MovingObject lvlObj, int MOVE_DIRECTION) {
		int i = lvlObj.getPositionLine();
		int j = lvlObj.getPositionColumn();
		if (MOVE_DIRECTION == MovingObject.MOVE_LEFT) {
			int iDest = i;
			int jDest = j - 1;
			
			for (LevelObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (MovingObject v: vases) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return (LevelObject)v;
				}
			}
		}
		else if (MOVE_DIRECTION == MovingObject.MOVE_RIGHT) {
			int iDest = i;
			int jDest = j + 1;
			
			for (LevelObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (MovingObject v: vases) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return (LevelObject)v;
				}
			}
		}
		else if (MOVE_DIRECTION == MovingObject.MOVE_UP) {
			int iDest = i + 1;
			int jDest = j;
			
			for (LevelObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (MovingObject v: vases) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return (LevelObject)v;
				}
			}
		}
		else if (MOVE_DIRECTION == MovingObject.MOVE_DOWN) {
			int iDest = i - 1;
			int jDest = j;
			
			for (LevelObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (MovingObject v: vases) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return (LevelObject)v;
				}
			}
		}
		return null;
	}
	
	public void update(float deltaTime) {
		if (bug.isMoving()) {
			bug.updateMove(deltaTime);
		}
		
		for (MovingObject v: vases) {
			if (v.isMoving())
				v.updateMove(deltaTime);
		}
	}
	
	public boolean isVictory() {
		return false;
	}
	
	public boolean isGameOver() {
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (!bug.isMoving()) {
			if (keycode == Input.Keys.LEFT) {
				int direction = MovingObject.MOVE_LEFT;
				LevelObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.setMove(direction);
				}
				else {
					if (obj.getType() == LevelObject.TYPE_VASE) {
						LevelObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.setMove(direction);
							((MovingObject)obj).setMove(direction);
						}
					}
				}
				return true;
			}
			else if (keycode == Input.Keys.RIGHT) {
				int direction = MovingObject.MOVE_RIGHT;
				LevelObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.setMove(direction);
				}
				else {
					if (obj.getType() == LevelObject.TYPE_VASE) {
						LevelObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.setMove(direction);
							((MovingObject)obj).setMove(direction);
						}
					}
				}
				return true;
			}
			else if (keycode == Input.Keys.UP) {
				int direction = MovingObject.MOVE_UP;
				LevelObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.setMove(direction);
				}
				else {
					if (obj.getType() == LevelObject.TYPE_VASE) {
						LevelObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.setMove(direction);
							((MovingObject)obj).setMove(direction);
						}
					}
				}
				return true;
			}
			else if (keycode == Input.Keys.DOWN) {
				int direction = MovingObject.MOVE_DOWN;
				LevelObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.setMove(direction);
				}
				else {
					if (obj.getType() == LevelObject.TYPE_VASE) {
						LevelObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.setMove(direction);
							((MovingObject)obj).setMove(direction);
						}
					}
				}
				return true;
			}
		}
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
