package sokobug.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LevelLoader {
	public static String readLevel(int level, int chapter) {
		return deserializeLevel(level, chapter);
	}

	private static String deserializeLevel(int level, int chapter) {
		FileHandle file = Gdx.files.internal(Resources.LEVELS_DATA.getPath() + "chapter" + chapter + "/level" + level + ".lvl");
		String decodedLevel = stringDecode(file.readString());
		return decodedLevel;
	}

	public static void serializeLevel(int level, int chapter) {
		FileHandle file = Gdx.files.internal("development/data/levels/" + "chapter" + chapter + "/level" + level + ".txt");
		String newName = "level" + level + ".lvl";
		FileHandle fileLvl = Gdx.files.local("../android/assets/" + Resources.LEVELS_DATA.getPath() + "chapter" + chapter + "/" + newName);
		fileLvl.writeString(stringEncode(file.readString()), false);
	}

	private static String stringDecode(String codedText) {
		String decodedText = "";
		int substitutes[] = { 1, 2, 2, 3, 3, 4 };
		for (int i = 0; i < codedText.length(); i++) {
			char c = codedText.charAt(i);
			decodedText += String.valueOf(((char) (((int) c) - substitutes[i % substitutes.length])));
		}
		return decodedText;
	}
	
	private static String stringEncode(String plainText) {
		String codedText = "";
		int substitutes[] = { 1, 2, 2, 3, 3, 4 };
		for (int i = 0; i < plainText.length(); i++) {
			char c = plainText.charAt(i);
			codedText += String.valueOf(((char) (((int) c) + substitutes[i % substitutes.length])));
		}
		return codedText;
	}
}
