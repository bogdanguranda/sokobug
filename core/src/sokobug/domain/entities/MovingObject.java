package sokobug.domain.entities;

public abstract class MovingObject extends LabyrinthObject {
	
	public enum MoveDirection {
		NONE, LEFT, RIGHT, UP, DOWN;
		
		public int iAddition = 0;
		public int jAddition = 0;
		
		private MoveDirection() {
			if (this.name().compareTo("NONE") == 0) {
				iAddition = 0;
				jAddition = 0;
			}
			else if (this.name().compareTo("LEFT") == 0) {
				iAddition = 0;
				jAddition = -1;
			}
			else if (this.name().compareTo("RIGHT") == 0) {
				iAddition = 0;
				jAddition = +1;
			}
			else if (this.name().compareTo("UP") == 0) {
				iAddition = +1;
				jAddition = 0;
			}
			else if (this.name().compareTo("DOWN") == 0) {
				iAddition = -1;
				jAddition = 0;
			}
		}
	}
	private MoveDirection orientation = MoveDirection.UP;
	
	protected float positionX;
	protected float positionY;
	
	private float moveSpeed = 400.f; // 400 de pixeli pe secunda
	private MoveDirection moveDirection = MoveDirection.NONE;
	private float distanceToMove = LabyrinthObject.OBJECT_SIZE;
	private float movedDistance = 0;
	
	public MovingObject(Type type) {
		super(type);
	}
	
	public MoveDirection getOrientation() {
		return orientation;
	}
	
	public void setOrientation(MoveDirection orientation) {
		this.orientation = orientation;
	}
	
	@Override
	public void setPositionLine(int positionLine) {
		this.positionLine = positionLine;
		positionY = positionLine * OBJECT_SIZE;
	}
	
	@Override
	public void setPositionColumn(int positionColumn) {
		this.positionColumn = positionColumn;
		positionX = positionColumn * OBJECT_SIZE;
	}
	
	public float getPositionX() {
		return positionX;
	}

	public float getPositionY() {
		return positionY;
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
	
	@Override
	public void update(float deltaTime) {
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
				setPositionColumn(getPositionColumn() + moveDirection.jAddition);
				setPositionLine(getPositionLine() + moveDirection.iAddition);
				movedDistance = 0;
				moveDirection = MoveDirection.NONE;
			}
		}
		
	}
}

