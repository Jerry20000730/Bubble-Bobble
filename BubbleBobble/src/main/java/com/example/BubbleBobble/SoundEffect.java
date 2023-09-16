package com.example.BubbleBobble;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;

import java.net.URL;

/**
 * SoundEffect handles the game's SFX.
 * Classes that want to use SFX will call the static variables in this enum and
 * play them via the play() method.
 */
public enum SoundEffect {
	FRUIT("sfx/fruit.wav"),
	DEATH("sfx/death.wav"),
	SHOOT("sfx/shoot.wav"),
	POP("sfx/pop.wav"),
	BUBBLED("sfx/bubbled.wav"),
	JUMP("sfx/jump.wav"),
	EXPLODE("sfx/explode.wav"),
	BOMB("sfx/bomb.wav"),
	FREEZE("sfx/ice.wav"),
	MEDICINE("sfx/medicine.wav"),
	LAND("sfx/land.wav"),
	EMPTY("sfx/empty.wav"),
	WIN("sfx/win.wav"),
	OVER("sfx/over.wav");

	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}

	public static Volume volume = Volume.LOW;

	private AudioClip clip;

	/**
	 * set the sound effect
	 * @param soundFileName the name of the sound file in String
	 */
	SoundEffect(String soundFileName) {
		// sets the sound effect
		try {
			URL url = this.getClass().getClassLoader().getResource(soundFileName);
			assert url != null;
			clip = new AudioClip(url.toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * play the sound effect
	 */
	public void play() {
		if (volume != Volume.MUTE) {
			if (clip.isPlaying()) {
				clip.stop();
			}
			// lack of redirect to the first frame of clip, may need to use MediaPlayer
			clip.play();
		}
	}


	/**
	 * set whether or not the sound effect loops
	 */
	public void setToLoop() {
		clip.setCycleCount(AudioClip.INDEFINITE);
	}


	/**
	 * set volume to high
	 */
	public void setToLoud() {
		volume = Volume.HIGH;
	}
}
