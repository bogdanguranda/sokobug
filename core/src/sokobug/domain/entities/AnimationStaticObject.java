package sokobug.domain.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class AnimationStaticObject extends StaticObject {
	
	private enum AnimationStatus {
		ON, OFF
	}
	
	private Animation animation;
	private boolean animate = true;
	private float stateTime = 0.f;
	private Sprite backgroundFrames[];
	
	public AnimationStaticObject(TextureAtlas atlas, Sprite[] backgroundFrames, Type type) {
		super(type);
        animation = new Animation(1/24.f, atlas.getRegions());
        animation.setPlayMode(PlayMode.LOOP);
        this.backgroundFrames = backgroundFrames;
	}
	
	@Override
	public void update(float deltaTime) {
		stateTime += deltaTime;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if (animate) {
			backgroundFrames[AnimationStatus.ON.ordinal()].draw(batch);
			batch.draw(animation.getKeyFrame(stateTime), positionColumn * OBJECT_SIZE, positionLine * OBJECT_SIZE);
		}
		else {
			backgroundFrames[AnimationStatus.OFF.ordinal()].draw(batch);
		}
	}
	
	public boolean getAnimate() {
		return animate;
	}
	
	public void setAnimate(boolean animate) {
		this.animate = animate;
	}
	
	@Override
	public void setPositionLine(int positionLine) {
		super.setPositionLine(positionLine);
		for (Sprite background: backgroundFrames) {
			background.setY(positionLine * OBJECT_SIZE);
		}
	}
	
	@Override
	public void setPositionColumn(int positionColumn) {
		super.setPositionColumn(positionColumn);
		for (Sprite background: backgroundFrames) {
			background.setX(positionColumn * OBJECT_SIZE);
		}
	}
	
	public int getWidth() {
		return animation.getKeyFrame(0.f).getRegionWidth();
	}
	
	public int getHeight() {
		return animation.getKeyFrame(0.f).getRegionHeight();
	}

}
