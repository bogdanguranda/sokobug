package sokobug.domain.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteStaticObject extends StaticObject {
	private Sprite sprite;

	public SpriteStaticObject(Texture texture, Type type) {
		super(type);
		sprite = new Sprite(texture);
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void setPositionLine(int positionLine) {
		super.setPositionLine(positionLine);
		sprite.setY(positionLine * OBJECT_SIZE);
	}

	@Override
	public void setPositionColumn(int positionColumn) {
		super.setPositionColumn(positionColumn);
		sprite.setX(positionColumn * OBJECT_SIZE);
	}

}
