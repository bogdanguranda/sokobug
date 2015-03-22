package sokobug.domain.entities;

public abstract class LabyrinthObject {
	
	public static final int OBJECT_SIZE = 80;
	public enum Type {
		BUG, SARCOPHAGUS, WALL, FREE, SPOT
	}
	
	private Type type;
	private int positionLine;
	private int positionColumn;
	protected float positionX;
	protected float positionY;
	
	public LabyrinthObject(Type type) {
		this.type = type;
	}
	
	public Type getType() {
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
