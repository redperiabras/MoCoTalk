package PROCESSES;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BeepEmitter {
	private AudioInputStream audioInputStream;
    private Clip clip;
    private Clip shortBeep;
    private Clip longBeep;
    
	public BeepEmitter() {
		try {
		       audioInputStream = AudioSystem.getAudioInputStream(BeepEmitter.class.getResource("/SOUND/beep.wav"));
		       clip = AudioSystem.getClip( );
		       clip.open(audioInputStream);
		}
		catch(Exception ex){
		        System.out.println("Error with playing sound.");
		        ex.printStackTrace( );
		}
	}
	public void playBeep(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}
	public void stopBeep() {
		clip.stop();
	}

	public void shortStart() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(BeepEmitter.class.getResource("/SOUND/shortBeep.wav"));
			shortBeep = AudioSystem.getClip( );
		    shortBeep.open(audioInputStream);
			shortBeep.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void shortStop() {
		shortBeep.stop();
	}
	
	public void longStart() {
		 
	    try {
			audioInputStream = AudioSystem.getAudioInputStream(BeepEmitter.class.getResource("/SOUND/longBeep.wav"));
		    longBeep = AudioSystem.getClip( );
		    longBeep.open(audioInputStream);
			longBeep.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void longStop() {
		longBeep.stop();
	}
	
}
