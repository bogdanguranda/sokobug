package sokobug.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationWrapper {
    Animation mAnimation;
    
    public AnimationWrapper(String atlasFilePath, int frameColumns, int frameRows) {
        Texture walkSheet = new Texture(Gdx.files.internal(atlasFilePath));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/frameColumns, walkSheet.getHeight()/frameRows);              // #10
        TextureRegion[] walkFrames = new TextureRegion[frameColumns * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameColumns; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        mAnimation = new Animation(1.0f/24.0f, walkFrames);
    }
}
