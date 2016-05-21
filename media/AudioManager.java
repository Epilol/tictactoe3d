package isg.project.core.media;

import static playn.core.PlayN.*;
import java.util.HashMap;
import java.util.Map;
import playn.core.Sound;

public class AudioManager 
{
	private static AudioManager instance;
	
	private Map<SoundType, Sound> sounds;
	private static final float MUSIC_VOLUME = 0.5f;
	private static final float SOUND_VOLUME = 0.5f;
	
	public enum SoundType
	{
		MAIN_THEME,
		BUTTON_CLICK,
		BALL_CLICK;
	}
	
	private AudioManager()
	{
		sounds = new HashMap<SoundType, Sound>();
	}
	
	public static AudioManager getInstance()
	{
		if (instance == null)
			instance = new AudioManager();
		
		return instance;
	}
	
	public void addMusic(SoundType type, String musicPath)
	{
		Sound music = assets().getMusic(musicPath);
		music.setVolume(MUSIC_VOLUME);
		music.setLooping(true);
		music.prepare();
		sounds.put(type, music);
	}
	
	public void addSound(SoundType type, String soundPath)
	{
		Sound sound = assets().getSound(soundPath);
		sound.setVolume(SOUND_VOLUME);
		sound.setLooping(false);
		sound.prepare();
		sounds.put(type, sound);
	}

	public float getSoundVolume(SoundType sound)
	{
		return sounds.get(sound).volume();
	}
	
	public void setSoundVolume(SoundType sound, float volume)
	{
		sounds.get(sound).setVolume(volume);
	}

	public void play(SoundType type)
	{
		sounds.get(type).play();
	}

	public void play(boolean play, SoundType type)
	{
		if (play && !sounds.get(type).isPlaying()) 
			sounds.get(type).play();
		
		else if (!play) 
			sounds.get(type).stop();
	}
	
	public boolean isPlaying(SoundType sound)
	{
		return sounds.get(sound).volume() > 0.0f;
	}
}
