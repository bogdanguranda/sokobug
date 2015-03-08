package sokobug.domain.entities;

public abstract class MovingObject extends LabyrinthObject {
	public static final int MOVE_NONE = -1;
	public static final int MOVE_LEFT = 0;
	public static final int MOVE_RIGHT = 1;
	public static final int MOVE_UP = 2;
	public static final int MOVE_DOWN = 3;
	
	private float moveSpeed = 400.f; // 400 de pixeli pe secunda
	private int moveDirection = MOVE_NONE;
	private float distanceToMove = LabyrinthObject.OBJECT_SIZE;
	private float movedDistance = 0;
	
	public MovingObject(int TYPE_OBJECT) {
		super(TYPE_OBJECT);
	}
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
	
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	public boolean isMoving() {
		return moveDirection != MOVE_NONE;
	}
	
	public void move(int MOVE_DIRECTION) {
		this.moveDirection = MOVE_DIRECTION;
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
			positionX = positionX + moveX;
			positionY = positionY + moveY;
			
			float finalDistance = distanceToMove / 20.f;
			if (movedDistance + finalDistance >= distanceToMove) {
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

