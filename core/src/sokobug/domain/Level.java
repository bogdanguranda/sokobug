package sokobug.domain;

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
import sokobug.domain.entities.Layer;
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
	private Layer[] labyrinthObjects = new Layer[2];
	
	private boolean finishedLevel = false;
	
	public enum LayerType {
		BACKGROUND, FOREGROUND
	}
	
	public Level(Sokobug game) {
		this.game = game;
	}

	public Vector2 getSize() {
		return new Vector2(LABYRINTH_COLUMNS * LabyrinthObject.OBJECT_SIZE, LABYRINTH_ROWS * LabyrinthObject.OBJECT_SIZE);
	}
	
	public void load(int levelNumber) {
		this.levelNumber = levelNumber;
		this.finishedLevel = false;
		
		Layer backgroundLayer = new Layer();
		Layer foregroundLayer = new Layer();
		
		FileHandle file = Gdx.files.internal("level/levels/level" + String.valueOf(levelNumber) + ".txt");
		String text = file.readString();
		String[] lines = text.split("\\r?\\n");
		for (int i = 0; i < LABYRINTH_ROWS; i++) {
			String[] lineElements = lines[i].split("\\s");
			for (int j = 0; j < LABYRINTH_COLUMNS; j++) {
				if ((lineElements[j].compareTo("B") == 0) || (lineElements[j].compareTo("b") == 0)) {
					bug  = new AnimationMovingObject((TextureAtlas) game.assetManager.get("level/animations/bug/bug.pack"), Type.BUG);
					bug.setPositionLine((LABYRINTH_ROWS-1) - i);
					bug.setPositionColumn(j);
					if (lineElements[j].compareTo("B") == 0) {
						SpriteStaticObject freeGround = new SpriteStaticObject(game.assetManager.get("level/tiles/free.png", Texture.class), Type.FREE);
						freeGround.setPositionLine((LABYRINTH_ROWS-1) - i);
						freeGround.setPositionColumn(j);
						backgroundLayer.addLabyrinthObject(freeGround);
					}
					else {
						AnimationStaticObject spot = new AnimationStaticObject((TextureAtlas) game.assetManager.get("level/animations/spot/spot.pack"), Type.SPOT);
						spot.setPositionLine((LABYRINTH_ROWS-1) - i);
						spot.setPositionColumn(j);
						spot.setStaticFrame(1);
						backgroundLayer.addLabyrinthObject(spot);
					}
				}
				else if ((lineElements[j].compareTo("P") == 0) || (lineElements[j].compareTo("p") == 0)) {
					SpriteMovingObject v = new SpriteMovingObject(game.assetManager.get("level/tiles/sarcophagus.png", Texture.class), Type.SARCOPHAGUS);
					v.setPositionLine((LABYRINTH_ROWS-1) - i);
					v.setPositionColumn(j);
					foregroundLayer.addLabyrinthObject(v);
					if (lineElements[j].compareTo("P") == 0) {
						SpriteStaticObject freeGround = new SpriteStaticObject(game.assetManager.get("level/tiles/free.png", Texture.class), Type.FREE);
						freeGround.setPositionLine((LABYRINTH_ROWS-1) - i);
						freeGround.setPositionColumn(j);
						backgroundLayer.addLabyrinthObject(freeGround);
					}
					else {
						AnimationStaticObject spot = new AnimationStaticObject((TextureAtlas) game.assetManager.get("level/animations/spot/spot.pack"), Type.SPOT);
						spot.setPositionLine((LABYRINTH_ROWS-1) - i);
						spot.setPositionColumn(j);
						spot.setStaticFrame(1);
						backgroundLayer.addLabyrinthObject(spot);
					}
				}
				else if (lineElements[j].compareTo("S") == 0) {
					AnimationStaticObject spot = new AnimationStaticObject((TextureAtlas) game.assetManager.get("level/animations/spot/spot.pack"), Type.SPOT);
					spot.setPositionLine((LABYRINTH_ROWS-1) - i);
					spot.setPositionColumn(j);
					spot.setStaticFrame(1);
					backgroundLayer.addLabyrinthObject(spot);
				}
				else if (lineElements[j].compareTo("W") == 0) {
					SpriteStaticObject wall = new SpriteStaticObject(game.assetManager.get("level/tiles/wall.png", Texture.class), Type.WALL);
					wall.setPositionLine((LABYRINTH_ROWS-1) - i);
					wall.setPositionColumn(j);
					foregroundLayer.addLabyrinthObject(wall);
				}
				else if (lineElements[j].compareTo("F") == 0) {
					SpriteStaticObject freeGround = new SpriteStaticObject(game.assetManager.get("level/tiles/free.png", Texture.class), Type.FREE);
					freeGround.setPositionLine((LABYRINTH_ROWS-1) - i);
					freeGround.setPositionColumn(j);
					backgroundLayer.addLabyrinthObject(freeGround);
				}
			}
		}
		
		this.labyrinthObjects[0] = backgroundLayer;
		this.labyrinthObjects[1] = foregroundLayer;
	}
	
	public void update(float deltaTime) {
		bug.update(deltaTime);
		
		for (LabyrinthObject labyrinthObject: labyrinthObjects) {
			labyrinthObject.update(deltaTime);
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
	
	public void render(float deltaTime) {		
		if (!finishedLevel) {
			update(deltaTime);
			
			if (isVictory()) {
				finishedLevel = true;
				game.setScreen(game.victoryScreen);
			}
		}
		
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
		game.batch.begin();
		for (LabyrinthObject labyrinthObject: labyrinthObjects) {
			labyrinthObject.draw(game.batch);
		}
		bug.draw(game.batch);
		game.batch.end();
	}
	
	public LabyrinthObject isCollidingWith(MovingObject lvlObj, MoveDirection direction) {
		int iCurrent = lvlObj.getPositionLine();
		int jCurrent = lvlObj.getPositionColumn();
		
		lvlObj.setPositionColumn(jCurrent + direction.jAddition);
		lvlObj.setPositionLine(iCurrent + direction.iAddition);
		
		LabyrinthObject collider = labyrinthObjects[LayerType.FOREGROUND.ordinal()].isCollidingWith(lvlObj);
		
		lvlObj.setPositionColumn(jCurrent);
		lvlObj.setPositionLine(iCurrent);
		
		return collider;
	}
	
	public boolean isVictory() {
		int numberOfSpots = 0;
		int sarcophagusesOnSpot = 0;
		
		for (LabyrinthObject backgroundObject: labyrinthObjects[LayerType.BACKGROUND.ordinal()].getLayerObjects()) {
			if (backgroundObject.getType() == Type.SPOT) {
				numberOfSpots++;
				for (LabyrinthObject foregroundObject: labyrinthObjects[LayerType.FOREGROUND.ordinal()].getLayerObjects()) {
					if (foregroundObject.getType() == Type.SARCOPHAGUS) {
						if (backgroundObject.getPositionLine() == foregroundObject.getPositionLine() && backgroundObject.getPositionColumn() == foregroundObject.getPositionColumn()) {
							sarcophagusesOnSpot++;
						}
					}
				}	
			}
		}
		return sarcophagusesOnSpot == numberOfSpots;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (!bug.isMoving()) {
			if (keycode == Input.Keys.LEFT) {
				MoveDirection direction = MoveDirection.LEFT;
				if (bug.getOrientation() != direction) {
					bug.setOrientation(direction);
				}
				else {
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
				}
				return true;
			}
			else if (keycode == Input.Keys.RIGHT) {
				MoveDirection direction = MoveDirection.RIGHT;
				if (bug.getOrientation() != direction) {
					bug.setOrientation(direction);
				}
				else {
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
				}
				return true;
			}
			else if (keycode == Input.Keys.UP) {
				MoveDirection direction = MoveDirection.UP;
				if (bug.getOrientation() != direction) {
					bug.setOrientation(direction);
				}
				else {
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
				}
				return true;
			}
			else if (keycode == Input.Keys.DOWN) {
				MoveDirection direction = MoveDirection.DOWN;
				if (bug.getOrientation() != direction) {
					bug.setOrientation(direction);
				}
				else {
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
				if (bug.getOrientation() != direction) {
					bug.setOrientation(direction);
				}
				else {
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
				}
				return true;
			}
			else if (mouseX > bug.getPositionX() + bug.getWidth() 
					&& mouseY < bug.getPositionY() + bug.getHeight()
					&& mouseY > bug.getPositionY()) {
				MoveDirection direction = MoveDirection.RIGHT;
				if (bug.getOrientation() != direction) {
					bug.setOrientation(direction);
				}
				else {
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
				}
				return true;
			}
			else if (mouseY > bug.getPositionY() + bug.getHeight() 
					&& mouseX < bug.getPositionX() + bug.getWidth()
					&& mouseX > bug.getPositionX()) {
				MoveDirection direction = MoveDirection.UP;
				if (bug.getOrientation() != direction) {
					bug.setOrientation(direction);
				}
				else {
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
				}
				return true;
			}
			else if (mouseY < bug.getPositionY()
					&& mouseX < bug.getPositionX() + bug.getWidth()
					&& mouseX > bug.getPositionX()) {
				MoveDirection direction = MoveDirection.DOWN;
				if (bug.getOrientation() != direction) {
					bug.setOrientation(direction);
				}
				else {
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
