package sokobug.domain;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import sokobug.Sokobug;
import sokobug.domain.entities.AnimationMovingObject;
import sokobug.domain.entities.AnimationStaticObject;
import sokobug.domain.entities.LabyrinthObject;
import sokobug.domain.entities.LabyrinthObject.Type;
import sokobug.domain.entities.MovingObject.MoveDirection;
import sokobug.domain.entities.MovingObject;
import sokobug.domain.entities.SpriteMovingObject;
import sokobug.domain.entities.SpriteStaticObject;

public class Level implements InputProcessor{
	
	private Sokobug game;
	public int levelNumber;
	
	private static final int LABYRINTH_ROWS = 9;
	private static final int LABYRINTH_COLUMNS = 16;

	private AnimationMovingObject bug;
	private List<SpriteMovingObject> sarcophaguses = new ArrayList<SpriteMovingObject>();
	private List<SpriteStaticObject> walls = new ArrayList<SpriteStaticObject>();
	private List<SpriteStaticObject> freeGrounds = new ArrayList<SpriteStaticObject>();
	private List<AnimationStaticObject> spots = new ArrayList<AnimationStaticObject>();
	
	public Level(Sokobug game) {
		this.game = game;
		
		bug  = new AnimationMovingObject((TextureAtlas) game.assetManager.get("ingame/bug/bugAnimation.pack"), Type.BUG);
	}

	public Vector2 getSize() {
		return new Vector2(LABYRINTH_COLUMNS * LabyrinthObject.OBJECT_SIZE, LABYRINTH_ROWS * LabyrinthObject.OBJECT_SIZE);
	}
	
	public void load(int levelNumber) {
		this.levelNumber = levelNumber;
		this.sarcophaguses.clear();
		this.spots.clear();
		this.walls.clear();
		this.freeGrounds.clear();
		
		FileHandle file = Gdx.files.internal("ingame/level" + String.valueOf(levelNumber) + ".txt");
		String text = file.readString();
		String[] lines = text.split("\\r?\\n");
		for (int i = 0; i < LABYRINTH_ROWS; i++) {
			String[] lineElements = lines[i].split("\\s");
			for (int j = 0; j < LABYRINTH_COLUMNS; j++) {
				if (lineElements[j].compareTo("B") == 0) {
					bug.setPositionLine((LABYRINTH_ROWS-1) - i);
					bug.setPositionColumn(j);
					SpriteStaticObject freeGround = new SpriteStaticObject(game.assetManager.get("ingame/free.png", Texture.class), Type.FREE);
					freeGround.setPositionLine((LABYRINTH_ROWS-1) - i);
					freeGround.setPositionColumn(j);
					freeGrounds.add(freeGround); // the bug stays on a free spot by default at the begining the same as any moving object
				}
				else if (lineElements[j].compareTo("P") == 0) {
					SpriteMovingObject v = new SpriteMovingObject(game.assetManager.get("ingame/vase.png", Texture.class), Type.SARCOPHAGUS);
					v.setPositionLine((LABYRINTH_ROWS-1) - i);
					v.setPositionColumn(j);
					sarcophaguses.add(v);
					SpriteStaticObject freeGround = new SpriteStaticObject(game.assetManager.get("ingame/free.png", Texture.class), Type.FREE);
					freeGround.setPositionLine((LABYRINTH_ROWS-1) - i);
					freeGround.setPositionColumn(j);
					freeGrounds.add(freeGround); // the bug stays on a free spot by default at the begining the same as any moving object
				}
				else if (lineElements[j].compareTo("S") == 0) {
					AnimationStaticObject spot = new AnimationStaticObject((TextureAtlas) game.assetManager.get("ingame/spot/spot.pack"), Type.SPOT);
					spot.setPositionLine((LABYRINTH_ROWS-1) - i);
					spot.setPositionColumn(j);
					spots.add(spot);
					SpriteStaticObject freeGround = new SpriteStaticObject(game.assetManager.get("ingame/free.png", Texture.class), Type.FREE);
					freeGround.setPositionLine((LABYRINTH_ROWS-1) - i);
					freeGround.setPositionColumn(j);
					freeGrounds.add(freeGround); // the bug stays on a free spot by default at the begining the same as any moving object
				}
				else if (lineElements[j].compareTo("W") == 0) {
					SpriteStaticObject wall = new SpriteStaticObject(game.assetManager.get("ingame/wall.png", Texture.class), Type.WALL);
					wall.setPositionLine((LABYRINTH_ROWS-1) - i);
					wall.setPositionColumn(j);
					walls.add(wall);
				}
				else if (lineElements[j].compareTo("F") == 0) {
					SpriteStaticObject freeGround = new SpriteStaticObject(game.assetManager.get("ingame/free.png", Texture.class), Type.FREE);
					freeGround.setPositionLine((LABYRINTH_ROWS-1) - i);
					freeGround.setPositionColumn(j);
					freeGrounds.add(freeGround);
				}
			}
		}
		
	}
	
	public void render(float deltaTime) {
		update(deltaTime);
		
		game.batch.begin();
		
		for (SpriteStaticObject freeGround: freeGrounds) {
			freeGround.draw(game.batch);
		}
		
		for (SpriteStaticObject wall: walls) {
			wall.draw(game.batch);
		}
		
		for (AnimationStaticObject spot: spots) {
			spot.draw(game.batch, deltaTime);
		}
		
		for (SpriteMovingObject sarcophagus: sarcophaguses) {
			sarcophagus.draw(game.batch);
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
			
			for (SpriteStaticObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (SpriteMovingObject v: sarcophaguses) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return v;
				}
			}
		}
		else if (direction == MoveDirection.RIGHT) {
			int iDest = i;
			int jDest = j + 1;
			
			for (SpriteStaticObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (SpriteMovingObject v: sarcophaguses) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return v;
				}
			}
		}
		else if (direction == MoveDirection.UP) {
			int iDest = i + 1;
			int jDest = j;
			
			for (SpriteStaticObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (SpriteMovingObject v: sarcophaguses) {
				if (v.getPositionLine() == iDest && v.getPositionColumn() == jDest) {
					return v;
				}
			}
		}
		else if (direction == MoveDirection.DOWN) {
			int iDest = i - 1;
			int jDest = j;
			
			for (SpriteStaticObject w: walls) {
				if (w.getPositionLine() == iDest && w.getPositionColumn() == jDest) {
					return w;
				}
			}
			
			for (SpriteMovingObject v: sarcophaguses) {
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
		
		for (SpriteMovingObject v: sarcophaguses) {
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
