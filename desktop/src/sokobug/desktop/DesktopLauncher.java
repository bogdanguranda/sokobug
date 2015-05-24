package sokobug.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import sokobug.Sokobug;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 800;
		config.title = "Sokobug";
		config.addIcon("desktopIcons/icon-16x16.png", FileType.Internal);
		config.addIcon("desktopIcons/icon-32x32.png", FileType.Internal);
		config.addIcon("desktopIcons/icon-128x128.png", FileType.Internal);

		new LwjglApplication(new Sokobug(), config);
	}
}