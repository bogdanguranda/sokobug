package sokobug.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import sokobug.Sokobug;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width = 1920;
//		config.height = 1080;
		new LwjglApplication(new Sokobug(), config);
	}
}