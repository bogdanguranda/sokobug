package sokobug.domain.entities;

public abstract class LabyrinthObject {
	public static final int TYPE_BUG = 0;
	public static final int TYPE_VASE = 1;
	public static final int TYPE_WALL = 2;
	//public static final int TYPE_FREE = 3;
	//public static final int TYPE_SPOT = 4;
	
	public static final int OBJECT_SIZE = 80;
	private int positionLine;
	private int positionColumn;
	protected float positionX;
	protected float positionY;
	private int type;
	
	public LabyrinthObject(int TYPE_OBJECT) {
		this.type = TYPE_OBJECT;
	}
	
	public int getType() {
		return type;
	}
	
	public int getPositionLine() {
		return positionLine;
	}

	public int getPositionColumn() {
		return positionColumn;
	}
	
	public void setPositionLine(int positionLine) {
		this.positionLine = positionLine;
		positionY = positionLine * OBJECT_SIZE;
	}
	
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
	
	public int getSize() {
		return OBJECT_SIZE;
	}
}
