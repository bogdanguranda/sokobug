package sokobug.domain.entities;

import sokobug.domain.entities.MovingObject.MoveDirection;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Pad {
	private Sprite padSprite;
	
	public Pad(Sprite padSprite) {
		this.padSprite = padSprite;
	}
	
	public void draw(SpriteBatch batch) {
		padSprite.draw(batch);
	}
	
	public float getWidth() {
		return padSprite.getWidth();
	}
	
	public float getHeight() {
		return padSprite.getHeight();
	}
	
	public void setPosition(float positionX, float positionY) {
		padSprite.setPosition(positionX, positionY);
	}
	
	public MoveDirection arrowDirectionByPoint(float pointX, float pointY) {
		float widthOneThird = padSprite.getWidth() / 3.f;
		float heightOneThird = padSprite.getHeight() / 3.f;
		Rectangle upArrow = new Rectangle(padSprite.getX() + widthOneThird, padSprite.getY() + 2.f * heightOneThird,
				widthOneThird, heightOneThird);
		Rectangle rightArrow = new Rectangle(padSprite.getX() + widthOneThird * 2.f, padSprite.getY() + heightOneThird,
				widthOneThird, heightOneThird);
		Rectangle downArrow = new Rectangle(padSprite.getX() + widthOneThird, padSprite.getY(),
				widthOneThird, heightOneThird);
		Rectangle leftArrow = new Rectangle(padSprite.getX(), padSprite.getY() + heightOneThird,
				widthOneThird, heightOneThird);
		if (upArrow.contains(pointX, pointY)) {
			return MoveDirection.UP;
		}
		else if (rightArrow.contains(pointX, pointY)) {
			return MoveDirection.RIGHT;
		}
		else if (downArrow.contains(pointX, pointY)) {
			return MoveDirection.DOWN;
		}
		else if (leftArrow.contains(pointX, pointY)) {
			return MoveDirection.LEFT;
		}
		else {
			return MoveDirection.NONE;
		}
	}
}
