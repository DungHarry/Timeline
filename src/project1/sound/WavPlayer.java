package project1.sound;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.sound.midi.Sequencer;
import javax.sound.sampled.*;
import javax.swing.JFrame;

public class WavPlayer implements Runnable {
	//Report some variables for this class
	
	private static Clip clip;
	private FloatControl volume;
	//Constructor to load wav sound file
	
	public WavPlayer(String wavName) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("resources/sound/" + wavName));
			//AudioFormat format = audioIn.getFormat();
			//DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			
			if(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Method play() without variables to play wav file once
	
	public void play() {	
		if(clip != null) {
			clip.start();
		}
		else {
			System.out.println("Clip don't exist!");
		}
	}
	//Method play(int countLoop) to play wav file with countLoop times
	
	public void play(int countLoop) {
		if(clip != null) {
			clip.start();
		    clip.loop(countLoop);
		}
		else {
			System.out.println("Clip don't exist!");
		}
	}
	//Method paused() to pause and resume wav sound
	
	public void pause() {
		if(clip.isRunning()) {
			clip.stop();
		}
	}
	//Method stop() to stop wav sound
	
	public void stop() {
		if(clip.isRunning()) {
			clip.stop();
			clip.close();
		}
	}
	
	public void setVolume(Float value) {
		if(clip.isOpen()) {
			try{
				volume.setValue(value);
			}catch(Exception ex){
				
			}
			
		}
	}
	

	
	public static Clip getClip() {
		return clip;
	}

	@Override
	public void run() {
		this.play(Sequencer.LOOP_CONTINUOUSLY);
	}
}