package sokobug.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import sokobug.Sokobug;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1366;
		config.height = 768;
		//config.fullscreen = true; //pentru debug e mai lejer in window mod
		new LwjglApplication(new Sokobug(), config);
	}
}