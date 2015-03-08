package sokobug.domain.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteObject extends MovingObject {
	private Sprite sprite;
	
	public SpriteObject(Texture texture, int TYPE_OBJECT) {
		super(TYPE_OBJECT);
		sprite = new Sprite(texture);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.setPosition(positionX, positionY);
		sprite.draw(batch);
	}
	
}
