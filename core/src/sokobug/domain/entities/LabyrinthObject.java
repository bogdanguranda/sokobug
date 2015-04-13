package sokobug.domain.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class LabyrinthObject {
	
	public enum Type {
		BUG("B"), SARCOPHAGUS("P"), WALL("W"), FREE("F"), SPOT("S"), LAYER("Layer");
		
		private String associatedString;
		private boolean collidable;
		
		private Type(String associatedString) {
			this.associatedString = associatedString;
			if (associatedString.compareTo("B") == 0 || associatedString.compareTo("P") == 0 || associatedString.compareTo("W") == 0) {
				this.collidable = true;
			}
			else {
				this.collidable = false;
			}
		}
		
		public String getAssociatedString() {
			return this.associatedString;
		}
	}
	
	public static final int OBJECT_SIZE = 80;
	
	protected Type type;
	protected int positionLine;
	protected int positionColumn;
	
	public LabyrinthObject(Type type) {
		this.type = type;
	}
	
	public abstract void draw(SpriteBatch batch);
	
	public abstract void update(float deltaTime);
	
	public boolean isCollidable() {
		return type.collidable;
	}
	
	public LabyrinthObject isCollidingWith(LabyrinthObject otherObject) {
		if (this.equals(otherObject))
			return null;
		
		if (otherObject.isCollidable()) {
			if (otherObject.getPositionLine() == getPositionLine() && otherObject.getPositionColumn() == getPositionColumn()) {
				return this;
			}
		}
		return null;
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
