package sokobug.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LevelObject {
	private int positionLine;
	private int positionColumn;
	private Sprite sprite;
	
	public LevelObject(Texture texture) {
		this.sprite = new Sprite(texture);
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
}

