package sokobug.domain;

import com.badlogic.gdx.graphics.Texture;

public class MovingObject extends LevelObject{
	public static final int MOVE_NONE = -1;
	public static final int MOVE_LEFT = 0;
	public static final int MOVE_RIGHT = 1;
	public static final int MOVE_UP = 2;
	public static final int MOVE_DOWN = 3;
	
	private float moveSpeed = 400.f; // 400 de pixeli pe secunda
	public int moveDirection = MOVE_NONE;
	public float moveDistanceX = 0;
	public float moveDistanceY = 0;
	
	public MovingObject(Texture texture, int TYPE_OBJECT) {
		super(texture, TYPE_OBJECT);
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
	
	public void updateMove(float deltaTime) {
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
			
			if (epsilon < 10.0f) { //if its less than 2 pixels far from or beyond destination then move to destination and finish the move order
				if (moveDirection == MOVE_LEFT) {
					setPositionColumn(getPositionColumn()-1);
				}
				else if (moveDirection == MOVE_RIGHT) {
					setPositionColumn(getPositionColumn()+1);
				}
				else if (moveDirection == MOVE_UP) {
					setPositionLine(getPositionLine()+1);
				}
				else if (moveDirection == MOVE_DOWN) {
					setPositionLine(getPositionLine()-1);
				}
				moveDistanceX = 0;
				moveDistanceY = 0;
				moveDirection = MOVE_NONE;
			}
		}
		
	}
}

