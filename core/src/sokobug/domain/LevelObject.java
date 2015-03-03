package sokobug.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LevelObject {
	public int positionLine;
	public int positionColumn;
	public Sprite sprite;
	
	public LevelObject(Texture texture) {
		this.sprite = new Sprite(texture);
	}

}
