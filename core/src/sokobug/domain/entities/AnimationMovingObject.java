package sokobug.domain.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimationMovingObject extends MovingObject {
	private Animation animation;
	private float stateTime = 0.f;
	
	public AnimationMovingObject(TextureAtlas atlas, Type type) {
		super(type);
        animation = new Animation(1/24.f, atlas.getRegions());
        animation.setPlayMode(PlayMode.LOOP);
	}
	
	@Override
	public void updateMove(float deltaTime) {
		super.updateMove(deltaTime);
		if (isMoving()) {
			stateTime += deltaTime;
		}
		else {
			stateTime = 0.f;
		}
	}
	
	public void draw(SpriteBatch batch, float deltaTime) {
		batch.draw(animation.getKeyFrame(stateTime), positionX, positionY);
	}
	
	public int getWidth() {
		return animation.getKeyFrame(0.f).getRegionWidth();
	}
	
	public int getHeight() {
		return animation.getKeyFrame(0.f).getRegionHeight();
	}
}
