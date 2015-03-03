package sokobug.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LevelObject {
	public static final int MOVE_NONE = -1;
	public static final int MOVE_LEFT = 0;
	public static final int MOVE_RIGHT = 1;
	public static final int MOVE_UP = 2;
	public static final int MOVE_DOWN = 3;
	
	private int positionLine;
	private int positionColumn;
	private Sprite sprite;
	
	private float moveSpeed = 160.f; // 80 de pixeli pe secunda
	public int moveDirection = MOVE_NONE;
	public float moveDistanceX = 0;
	public float moveDistanceY = 0;
	
	public LevelObject(Texture texture) {
		this.sprite = new Sprite(texture);
	}
	
	public int getPositionLine() {
		return positionLine;
	}

	public int getPositionColumn() {
		return positionColumn;
	}
	
	public void setPositionLine(int positionLine) {
		this.positionLine = positionLine;
		sprite.setPosition(sprite.getX(), positionLine * sprite.getHeight());
	}
	
	public void setPositionColumn(int positionColumn) {
		this.positionColumn = positionColumn;
		sprite.setPosition(positionColumn * sprite.getWidth(), sprite.getY());
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isMoving() {
		return moveDirection != MOVE_NONE;
	}
	
	public void setMove(int MOVE_DIRECTION) {
		moveDirection = MOVE_DIRECTION;
		if (moveDirection == MOVE_LEFT) {
			moveDistanceX = -sprite.getWidth();
			moveDistanceY = 0;
		}
		else if (moveDirection == MOVE_RIGHT) {
			moveDistanceX = sprite.getWidth();
			moveDistanceY = 0;
		}
		else if (moveDirection == MOVE_UP) {
			moveDistanceX = 0;
			moveDistanceY = sprite.getHeight();
		}
		else if (moveDirection == MOVE_DOWN) {
			moveDistanceX = 0;
			moveDistanceY = -sprite.getHeight();
		}
	}
	
	public void update(float deltaTime) {
		if (isMoving()) {
			float moveX = 0, moveY = 0;
			
			if(moveDistanceX != 0) {
				if (moveDistanceX < 0)
					moveX = -(deltaTime * moveSpeed);
				else
					moveX = (deltaTime * moveSpeed);
			}

			if(moveDistanceY != 0) {
				if (moveDistanceY < 0)
					moveY = -(deltaTime * moveSpeed);
				else
					moveY = (deltaTime * moveSpeed);
			}
			
			sprite.setPosition(sprite.getX() + moveX, sprite.getY() + moveY);
			
			moveDistanceX -= moveX;
			moveDistanceY -= moveY;
			
			float epsilon = moveDistanceX + moveDistanceY;
			if (epsilon < 0) // modul
				epsilon = -epsilon;
			
			if (epsilon < 0.1f) {
				if (moveDirection == MOVE_LEFT) {
					setPositionColumn(positionColumn-1);
				}
				else if (moveDirection == MOVE_RIGHT) {
					setPositionColumn(positionColumn+1);
				}
				else if (moveDirection == MOVE_UP) {
					setPositionLine(positionLine+1);
				}
				else if (moveDirection == MOVE_DOWN) {
					setPositionLine(positionLine-1);
				}
				moveDistanceX = 0;
				moveDistanceY = 0;
				moveDirection = MOVE_NONE;
			}
		}
		
	}
}

