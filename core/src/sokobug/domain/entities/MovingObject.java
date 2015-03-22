package sokobug.domain.entities;

public abstract class MovingObject extends LabyrinthObject {
	public enum MoveDirection {
		NONE, LEFT, RIGHT, UP, DOWN
	}
	
	private float moveSpeed = 400.f; // 400 de pixeli pe secunda
	private MoveDirection moveDirection = MoveDirection.NONE;
	private float distanceToMove = LabyrinthObject.OBJECT_SIZE;
	private float movedDistance = 0;
	
	public MovingObject(Type type) {
		super(type);
	}
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
	
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	public boolean isMoving() {
		return moveDirection != MoveDirection.NONE;
	}
	
	public void move(MoveDirection direction) {
		this.moveDirection = direction;
	}
	
	public void updateMove(float deltaTime) {
		if (isMoving()) {
			float moveX = 0, moveY = 0;
			
			if (moveDirection == MoveDirection.RIGHT) {
				moveX = (deltaTime * moveSpeed);
				movedDistance += moveX;
			}
			else if (moveDirection == MoveDirection.UP) {
				moveY = (deltaTime * moveSpeed);
				movedDistance += moveY;
			}
			else if (moveDirection == MoveDirection.LEFT) {
				moveX = -(deltaTime * moveSpeed);
				movedDistance -= moveX;
			}
			else if (moveDirection == MoveDirection.DOWN) {
				moveY = -(deltaTime * moveSpeed);
				movedDistance -= moveY;
			}
			positionX = positionX + moveX;
			positionY = positionY + moveY;
			
			float finalDistance = distanceToMove / 20.f;
			if (movedDistance + finalDistance >= distanceToMove) {
				if (moveDirection == MoveDirection.LEFT) {
					setPositionColumn(getPositionColumn()-1);
				}
				else if (moveDirection == MoveDirection.RIGHT) {
					setPositionColumn(getPositionColumn()+1);
				}
				else if (moveDirection == MoveDirection.UP) {
					setPositionLine(getPositionLine()+1);
				}
				else if (moveDirection == MoveDirection.DOWN) {
					setPositionLine(getPositionLine()-1);
				}
				movedDistance = 0;
				moveDirection = MoveDirection.NONE;
			}
		}
		
	}
}

