package sokobug.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Layer extends LabyrinthObject {
	
	private List<LabyrinthObject> layerObjects = new ArrayList<LabyrinthObject>();

	public Layer() {
		super(Type.LAYER);
	}

	@Override
	public void draw(SpriteBatch batch) {
		for (LabyrinthObject layerObject: layerObjects) {
			layerObject.draw(batch);
		}
	}

	@Override
	public void update(float deltaTime) {
		for (LabyrinthObject layerObject: layerObjects) {
			layerObject.update(deltaTime);
		}
	}
	
	@Override
	public LabyrinthObject isCollidingWith(LabyrinthObject otherObject) {
		for (LabyrinthObject layerObject: layerObjects) {
			LabyrinthObject collider = layerObject.isCollidingWith(otherObject);
			if (collider != null) {
				return collider;
			}
		}
		return null;
	}
	
	public void addLabyrinthObject(LabyrinthObject layerObject) {
		layerObjects.add(layerObject);
	}
	
	public List<LabyrinthObject> getLayerObjects() {
		return layerObjects;
	}

}
