package sokobug.domain.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class AnimationStaticObject extends StaticObject {
	
	private Animation animation;
	private float stateTime = 0.f;
	
	public AnimationStaticObject(TextureAtlas atlas, Type type) {
		super(type);
        animation = new Animation(1/24.f, atlas.getRegions());
        animation.setPlayMode(PlayMode.LOOP);
	}
	
	public void draw(SpriteBatch batch, float deltaTime) {
		stateTime += deltaTime;
		batch.draw(animation.getKeyFrame(stateTime), positionColumn * OBJECT_SIZE, positionLine * OBJECT_SIZE);
	}
	
	public int getWidth() {
		return animation.getKeyFrame(0.f).getRegionWidth();
	}
	
	public int getHeight() {
		return animation.getKeyFrame(0.f).getRegionHeight();
	}

}
