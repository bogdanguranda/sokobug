package sokobug.domain.entities;

public abstract class LabyrinthObject {
	
	public enum Type {
		BUG, SARCOPHAGUS, WALL, FREE, SPOT
	}
	
	protected static final int OBJECT_SIZE = 80;
	
	protected Type type;
	protected int positionLine;
	protected int positionColumn;
	
	public LabyrinthObject(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getPositionLine() {
		return positionLine;
	}
	
	public void setPositionLine(int positionLine) {
		this.positionLine = positionLine;
	}

	public int getPositionColumn() {
		return positionColumn;
	}
	
	public void setPositionColumn(int positionColumn) {
		this.positionColumn = positionColumn;
	}
	
	public int getSize() {
		return OBJECT_SIZE;
	}
}
