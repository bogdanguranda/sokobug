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
	public float distanceToMove = sprite.getWidth();
	public float movedDistance = 0;
	
	public MovingObject(Texture texture, int TYPE_OBJECT) {
		super(texture, TYPE_OBJECT);
	}
	
	public boolean isMoving() {
		return moveDirection != MOVE_NONE;
	}
	
	public void setMove(int MOVE_DIRECTION) {
		moveDirection = MOVE_DIRECTION;
	}
	
	public void updateMove(float deltaTime) {
		if (isMoving()) {
			float moveX = 0, moveY = 0;
			
			if (moveDirection == MOVE_RIGHT) {
				moveX = (deltaTime * moveSpeed);
				movedDistance += moveX;
			}
			else if (moveDirection == MOVE_UP) {
				moveY = (deltaTime * moveSpeed);
				movedDistance += moveY;
			}
			else if (moveDirection == MOVE_LEFT) {
				moveX = -(deltaTime * moveSpeed);
				movedDistance -= moveX;
			}
			else if (moveDirection == MOVE_DOWN) {
				moveY = -(deltaTime * moveSpeed);
				movedDistance -= moveY;
			}
			
			sprite.setPosition(sprite.getX() + moveX, sprite.getY() + moveY);
			
			if (movedDistance >= distanceToMove) {
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
				movedDistance = 0;
				moveDirection = MOVE_NONE;
			}
		}
		
	}
}

