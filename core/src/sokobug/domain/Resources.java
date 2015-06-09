package sokobug.domain;

public enum Resources {
	BACKGROUNDS_TITLE("release/graphics/backgrounds/title.png"),
	BACKGROUNDS_POTATOES("release/graphics/backgrounds/potatoes.png"),
	BACKGROUNDS_MENU("release/graphics/backgrounds/menu.png"),
	
	DESKTOPICONS_16X16("release/graphics/desktopIcons/icon-16x16.png"),
	DESKTOPICONS_32X32("release/graphics/desktopIcons/icon-32x32.png"),
	DESKTOPICONS_128X128("release/graphics/desktopIcons/icon-128x128.png"),
	
	FONTS_JAPONESA24("release/graphics/fonts/Japonesa24.fnt"),
	FONTS_JAPONESA32("release/graphics/fonts/Japonesa32.fnt"),
	FONTS_JAPONESA60("release/graphics/fonts/Japonesa60.fnt"),
	FONTS_JAPONESA120("release/graphics/fonts/Japonesa120.fnt"),

	UI_BUTTONS_PACK("release/graphics/ui/buttons/buttons.pack"),
	UI_BUTTONS_JSON("release/graphics/ui/buttons/buttons.json"),

	ANIMATIONS_PACK_BUG("release/graphics/level/animations/bug/bug.pack"),
	ANIMATIONS_PACK_SPOTGLOW("release/graphics/level/animations/spot/spotGlow.pack"),
	ANIMATIONS_PACK_VICTORYGLOW("release/graphics/level/animations/victory/victoryGlow.pack"),
	
	TILES_SARCOPHAGUS("release/graphics/level/tiles/sarcophagus.png"),
	TILES_WALL("release/graphics/level/tiles/wall.png"),
	TILES_FREE("release/graphics/level/tiles/free.png"),
	TILES_SPOTON("release/graphics/level/tiles/spotOn.png"),
	TILES_SPOTOFF("release/graphics/level/tiles/spotOff.png"),

	METAITEMS_TOPBAR("release/graphics/level/metaItems/topBar.png"),
	METAITEMS_PAD("release/graphics/level/metaItems/pad.png"),
	METAITEMS_VICTORYWING("release/graphics/level/metaItems/victoryWing.png"),
	
	LABELS_FINISH("release/graphics/level/labels/finish.png"),

	SOUND_BUGMOVE("release/sound/effects/bugMove.ogg"),
	SOUND_BUTTONCLICK("release/sound/effects/buttonClick.ogg"),
	SOUND_SARCOPHAGUSMOVE("release/sound/effects/sarcophagusMove.ogg"),
	SOUND_VICTORY("release/sound/effects/victory.ogg"),
	
	MUSIC_NEWGROUNDS_EGYPT("release/sound/music/newgrounds_egypt.ogg"),
	MUSIC_LONELY_DESERT("release/sound/music/lonely_desert.ogg"),
	MUSIC_EGYPTIAN_NIGHTS("release/sound/music/egyptian_nights.ogg"),
	MUSIC_NEWGROUNDS_ARABIA("release/sound/music/newgrounds_arabia.ogg"),
	MUSIC_PYRAMID("release/sound/music/pyramid.ogg"),
	
	LEVELS_DATA("release/data/levels/");
	
	private String path;
	
	private Resources(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
}
