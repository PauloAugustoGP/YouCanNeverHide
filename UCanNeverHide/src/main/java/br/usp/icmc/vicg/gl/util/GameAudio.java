package br.usp.icmc.vicg.gl.util;

// This example is from the book _Java AWT Reference_ by John Zukowski.
// Written by John Zukowski.  Copyright (c) 1997 O'Reilly & Associates.
// You may study, use, modify, and distribute this example for any purpose.
// This example is provided WITHOUT WARRANTY either expressed or
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameAudio
{
    private Clip propellerSound;
    
    public static Clip CreateSound( String filename )
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            
            return clip;
        }
        catch (LineUnavailableException | UnsupportedAudioFileException | IOException exc)
        {
            exc.printStackTrace(System.out);
        }
        
        return null;
    }
    
    public void loop( String filename )
    {
        this.propellerSound = CreateSound( filename );
        this.propellerSound.setLoopPoints(0, -1);

        this.propellerSound.loop(Clip.LOOP_CONTINUOUSLY);
        this.propellerSound.start();
    }
    
    public void play( String filename )
    {
        this.propellerSound = CreateSound( filename );
        this.propellerSound.setLoopPoints(0, -1);

        this.propellerSound.start();
    }
    
    public void stop()
    {
        this.propellerSound.stop();
    }
}