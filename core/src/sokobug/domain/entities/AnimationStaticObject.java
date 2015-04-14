package sokobug.domain.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class AnimationStaticObject extends StaticObject {
	
	private Animation animation;
	private float stateTime = 0.f;
	private int specificFrame = -1;
	
	public AnimationStaticObject(TextureAtlas atlas, Type type) {
		super(type);
        animation = new Animation(1/24.f, atlas.getRegions());
        animation.setPlayMode(PlayMode.LOOP);
	}
	
	@Override
	public void update(float deltaTime) {
		if (specificFrame == -1) {
			stateTime += deltaTime;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if (specificFrame == -1) {
			batch.draw(animation.getKeyFrame(stateTime), positionColumn * OBJECT_SIZE, positionLine * OBJECT_SIZE);
		}
		else {
			batch.draw(animation.getKeyFrames()[specificFrame], positionColumn * OBJECT_SIZE, positionLine * OBJECT_SIZE);
		}
	}
	
	public void setStaticFrame(int index) {
		specificFrame = index;
	}
	
	public int getWidth() {
		return animation.getKeyFrame(0.f).getRegionWidth();
	}
	
	public int getHeight() {
		return animation.getKeyFrame(0.f).getRegionHeight();
	}

}
