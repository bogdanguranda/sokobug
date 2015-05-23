package sokobug.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LevelLoader {
	public static String readLevel(String lvlFile) {
		return deserializeLevel(lvlFile);
	}

	public static void serializeLevel(String txtFile) {
		FileHandle file = Gdx.files.internal("level/levels/" + txtFile);
		String newName = txtFile.substring(0, txtFile.length() - 3) + "lvl";
		FileHandle fileLvl = Gdx.files.local("../android/assets/level/levels/" + newName);
		fileLvl.writeString(stringEncode(file.readString()), false);
	}

	private static String deserializeLevel(String lvlFile) {
		FileHandle file = Gdx.files.internal("level/levels/" + lvlFile);
		String decodedLevel = stringDecode(file.readString());
		return decodedLevel;
	}

	private static String stringEncode(String plainText) {
		String codedText = "";
		int substitutes[] = {1, 2, 2, 3, 3, 4};
		for (int i = 0; i < plainText.length(); i++) {
			char c = plainText.charAt(i);
			codedText += String.valueOf(((char)(((int)c) + substitutes[i % substitutes.length])));
		}
		return codedText;
	}

	private static String stringDecode(String codedText) {
		String decodedText = "";
		int substitutes[] = {1, 2, 2, 3, 3, 4};
		for (int i = 0; i < codedText.length(); i++) {
			char c = codedText.charAt(i);
			decodedText += String.valueOf(((char)(((int)c) - substitutes[i % substitutes.length])));
		}
		return decodedText;
	}
}
