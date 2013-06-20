package project1.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.*;

public class MidiPlayer implements Runnable {
	private static Sequencer player;
	
	public MidiPlayer(String midiName) {
		try {
			Sequence song = MidiSystem.getSequence(new File("resources/sound/" + midiName));
			player = MidiSystem.getSequencer();
			player.open();
			player.setSequence(song);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if(player != null) {
			player.start();
		}
		else {
			System.out.println("Midi player don't exist!");
		}
	}
	
	public void play(int countLoop) {
		if(player != null) {
			player.start();
			player.setLoopCount(countLoop);
		}
		else {
			System.out.println("Midi player don't exist!");
		}
	}
	
	public void pause() {
		if(player.isRunning()) {
			player.stop();
		}
	}
	
	public void changeSpeed(double rate) {
		player.setTempoFactor((float) rate);
	}
	
	public void stop() {
		if(player.isRunning()) {
			player.stop();
			player.close();
		}
	}

	@Override
	public void run() {
		play(Sequencer.LOOP_CONTINUOUSLY);
	}
}
