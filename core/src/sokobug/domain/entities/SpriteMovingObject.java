package sokobug.domain.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteMovingObject extends MovingObject {
	private Sprite sprite;
	
	public SpriteMovingObject(Texture texture, Type type) {
		super(type);
		sprite = new Sprite(texture);
	}
	
	@Override
	public void updateMove(float deltaTime) {
		super.updateMove(deltaTime);
		sprite.setPosition(positionX, positionY);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	@Override
	public void setPositionLine(int positionLine) {
		super.setPositionLine(positionLine);
		sprite.setY(positionY);
	}
	
	@Override
	public void setPositionColumn(int positionColumn) {
		super.setPositionColumn(positionColumn);
		sprite.setX(positionX);
	}
	
}
