package sokobug.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationWrapper {
    public Animation mAnimation;
    
    public AnimationWrapper(Texture framesAtlas, int frameColumns, int frameRows) {
        TextureRegion[][] tmp = TextureRegion.split(framesAtlas, framesAtlas.getWidth()/frameColumns, framesAtlas.getHeight()/frameRows);              // #10
        TextureRegion[] frames = new TextureRegion[frameColumns * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameColumns; j++) {
            	frames[index++] = tmp[i][j];
            }
        }
        mAnimation = new Animation(1.0f/24.0f, frames);
    }
}
