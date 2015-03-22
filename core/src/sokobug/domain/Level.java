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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import sokobug.Sokobug;
import sokobug.domain.entities.AnimationMovingObject;
import sokobug.domain.entities.LabyrinthObject;
import sokobug.domain.entities.LabyrinthObject.Type;
import sokobug.domain.entities.MovingObject.MoveDirection;
import sokobug.domain.entities.MovingObject;
import sokobug.domain.entities.SpriteMovingObject;

public class Level implements InputProcessor{
	
	private Sokobug game;
	public int levelNumber;
	
	private static final int LABYRINTH_ROWS = 9;
	private static final int LABYRINTH_COLUMNS = 16;
	
	private String[][] labyrinth = new String[LABYRINTH_ROWS][LABYRINTH_COLUMNS];
	
	private Map<String, Sprite> labyrinthSprites = new HashMap<String, Sprite>();

	private AnimationMovingObject bug;
	private ArrayList<SpriteMovingObject> vases = new ArrayList<SpriteMovingObject>();
	private ArrayList<SpriteMovingObject> walls = new ArrayList<SpriteMovingObject>();
	
	public Level(Sokobug game) {
		this.game = game;
		
		labyrinthSprites.put("F", new Sprite(game.assetManager.get("ingame/free.png", Texture.class)));
		labyrinthSprites.put("W", new Sprite(game.assetManager.get("ingame/wall.png", Texture.class)));
		labyrinthSprites.put("S", new Sprite(game.assetManager.get("ingame/spot.png", Texture.class)));
		labyrinthSprites.put("V", new Sprite(game.assetManager.get("ingame/vase.png", Texture.class)));
		
		bug  = new AnimationMovingObject((TextureAtlas) game.assetManager.get("ingame/bug/bugAnimation.pack"), Type.BUG);
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
					SpriteMovingObject v = new SpriteMovingObject(game.assetManager.get("ingame/vase.png", Texture.class), Type.SARCOPHAGUS);
					v.setPositionLine((LABYRINTH_ROWS-1) - i);
					v.setPositionColumn(j);
					vases.add(v);
					labyrinth[(LABYRINTH_ROWS-1) - i][j] = "F"; // at the start the bug stays on a free spot always(he could stay on a spot(S)
				}
				else if (lineElements[j].compareTo("W") == 0) {
					SpriteMovingObject w = new SpriteMovingObject(game.assetManager.get("ingame/wall.png", Texture.class), Type.WALL);
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
		
		for (SpriteMovingObject w: walls) {
			w.draw(game.batch);
		}
		
		for (SpriteMovingObject v: vases) {
			v.draw(game.batch);
		}
		
		bug.draw(game.batch, deltaTime);
		
		game.batch.end();
	}
	
	public LabyrinthObject isCollidingWith(MovingObject lvlObj, MoveDirection direction) {
		int i = lvlObj.getPositionLine();
		int j = lvlObj.getPositionColumn();
		if (direction == MoveDirection.LEFT) {
			int iDest = i;
			int jDest = j - 1;
			
			for (SpriteMovingObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (SpriteMovingObject v: vases) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return v;
				}
			}
		}
		else if (direction == MoveDirection.RIGHT) {
			int iDest = i;
			int jDest = j + 1;
			
			for (SpriteMovingObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (SpriteMovingObject v: vases) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return v;
				}
			}
		}
		else if (direction == MoveDirection.UP) {
			int iDest = i + 1;
			int jDest = j;
			
			for (SpriteMovingObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (SpriteMovingObject v: vases) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return v;
				}
			}
		}
		else if (direction == MoveDirection.DOWN) {
			int iDest = i - 1;
			int jDest = j;
			
			for (SpriteMovingObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (SpriteMovingObject v: vases) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return v;
				}
			}
		}
		return null;
	}
	
	public void update(float deltaTime) {
		if (bug.isMoving()) {
			bug.updateMove(deltaTime);
		}
		
		for (SpriteMovingObject v: vases) {
			if (v.isMoving())
				v.updateMove(deltaTime);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			keyDown(Input.Keys.LEFT);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			keyDown(Input.Keys.RIGHT);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			keyDown(Input.Keys.UP);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			keyDown(Input.Keys.DOWN);
		}
		
		if (Gdx.input.isTouched()) {
			touchDown(Gdx.input.getX(), Gdx.input.getY(),0 , Input.Buttons.LEFT);
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
				MoveDirection direction = MoveDirection.LEFT;
				LabyrinthObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.move(direction);
				}
				else {
					if (obj.getType() == Type.SARCOPHAGUS) {
						LabyrinthObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.move(direction);
							((MovingObject)obj).move(direction);
						}
					}
				}
				return true;
			}
			else if (keycode == Input.Keys.RIGHT) {
				MoveDirection direction = MoveDirection.RIGHT;
				LabyrinthObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.move(direction);
				}
				else {
					if (obj.getType() == Type.SARCOPHAGUS) {
						LabyrinthObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.move(direction);
							((MovingObject)obj).move(direction);
						}
					}
				}
				return true;
			}
			else if (keycode == Input.Keys.UP) {
				MoveDirection direction = MoveDirection.UP;
				LabyrinthObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.move(direction);
				}
				else {
					if (obj.getType() == Type.SARCOPHAGUS) {
						LabyrinthObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.move(direction);
							((MovingObject)obj).move(direction);
						}
					}
				}
				return true;
			}
			else if (keycode == Input.Keys.DOWN) {
				MoveDirection direction = MoveDirection.DOWN;
				LabyrinthObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.move(direction);
				}
				else {
					if (obj.getType() == Type.SARCOPHAGUS) {
						LabyrinthObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.move(direction);
							((MovingObject)obj).move(direction);
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
		if (!bug.isMoving()) {
			Vector3 v = new Vector3(screenX, screenY, 0);
			game.viewport.unproject(v);
			int mouseX = (int)v.x, mouseY = (int)v.y;
			
			if (mouseX < bug.getPositionX()
					&& mouseY < bug.getPositionY() + bug.getHeight()
					&& mouseY > bug.getPositionY()) {
				MoveDirection direction = MoveDirection.LEFT;
				LabyrinthObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.move(direction);
				}
				else {
					if (obj.getType() == Type.SARCOPHAGUS) {
						LabyrinthObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.move(direction);
							((MovingObject)obj).move(direction);
						}
					}
				}
				return true;
			}
			else if (mouseX > bug.getPositionX() + bug.getWidth() 
					&& mouseY < bug.getPositionY() + bug.getHeight()
					&& mouseY > bug.getPositionY()) {
				MoveDirection direction = MoveDirection.RIGHT;
				LabyrinthObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.move(direction);
				}
				else {
					if (obj.getType() == Type.SARCOPHAGUS) {
						LabyrinthObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.move(direction);
							((MovingObject)obj).move(direction);
						}
					}
				}
				return true;
			}
			else if (mouseY > bug.getPositionY() + bug.getHeight() 
					&& mouseX < bug.getPositionX() + bug.getWidth()
					&& mouseX > bug.getPositionX()) {
				MoveDirection direction = MoveDirection.UP;
				LabyrinthObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.move(direction);
				}
				else {
					if (obj.getType() == Type.SARCOPHAGUS) {
						LabyrinthObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.move(direction);
							((MovingObject)obj).move(direction);
						}
					}
				}
				return true;
			}
			else if (mouseY < bug.getPositionY()
					&& mouseX < bug.getPositionX() + bug.getWidth()
					&& mouseX > bug.getPositionX()) {
				MoveDirection direction = MoveDirection.DOWN;
				LabyrinthObject obj = isCollidingWith(bug, direction);
				if (obj == null) {
					bug.move(direction);
				}
				else {
					if (obj.getType() == Type.SARCOPHAGUS) {
						LabyrinthObject obj2 = isCollidingWith((MovingObject)obj, direction);
						if (obj2 == null) {
							bug.move(direction);
							((MovingObject)obj).move(direction);
						}
					}
				}
				return true;
			}
		}
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
