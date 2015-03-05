package sokobug.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LevelObject {
	public static final int TYPE_BUG = 0;
	public static final int TYPE_VASE = 1;
	public static final int TYPE_WALL = 2;
	
	private int positionLine;
	private int positionColumn;
	protected Sprite sprite;
	private int type;
	
	public LevelObject(Texture texture, int TYPE_OBJECT) {
		this.sprite = new Sprite(texture);
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
		sprite.setPosition(sprite.getX(), positionLine * sprite.getHeight());
	}
	
	public void setPositionColumn(int positionColumn) {
		this.positionColumn = positionColumn;
		sprite.setPosition(positionColumn * sprite.getWidth(), sprite.getY());
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Texture texture) {
		this.sprite.setTexture(texture);
	}
}
