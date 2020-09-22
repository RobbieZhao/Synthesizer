
//import org.junit.rules.Stopwatch;
import org.junit.rules.Stopwatch;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class AudioClip {
    private static final double duration = 1.0;
    private static final int sampleRate = 44100;
    private byte byteArray[];

    public AudioClip() {
        byteArray = new byte[2 * sampleRate];
    }

    public static int getSampleRate() {
        return sampleRate;
    }

    public int getSample (int index) {
        return (byteArray[2 * index + 1] << 8) | (byteArray[2 * index] & 0xff);
    }

    public void setSample(int index, int value) {
        byteArray[2 * index + 1] = (byte) (value >> 8);
        byteArray[2 * index] = (byte) (value);
    }

    public byte[] getData() {
        byte[] new_byteArray = new byte[byteArray.length];
        System.arraycopy(byteArray, 0, new_byteArray, 0, new_byteArray.length);
        return new_byteArray;
    }

    public static void main(String[] args) throws LineUnavailableException {

        SinewaveWidget sinewaveWidget = new SinewaveWidget();

        //get properties from the system about samples rates, etc
        Clip c = AudioSystem.getClip(); //terrible name

//This is the format that we're following, 44.1KHz mono audio, 16 bits per sample
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

//        Stopwatch sw = new Stopwatch();

        AudioComponent gen = new SineWave(220);
        Filter volume = new Filter(0.8, gen);

        AudioComponent gen2 = new SineWave(300);

        AudioComponent[] arr = {gen, gen2};
        Mixer mix = new Mixer();
        Filter volume_mix = new Filter(0.5, mix);

//        System.out.println(sw.elapsedTime());

        AudioClip clip = volume_mix.getClip();

//        System.out.println(sw.elapsedTime());



        c.open(format16, clip.getData(), 0, clip.getData().length); //reads data from my byte array to play it

        System.out.println("about to play");
        c.start(); //plays it
        c.loop(2); //plays it 2 more times if desired, so 3 seconds total

//makes sure the program doesn't quit before the sound plays
        while(c.getFramePosition() < AudioClip.getSampleRate() || c.isActive() || c.isRunning()){}

        System.out.println("done");
        c.close();


    }
}
