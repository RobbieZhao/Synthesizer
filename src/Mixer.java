import java.util.ArrayList;

public class Mixer implements AudioComponent{
    private ArrayList<AudioComponent> audioComponents;

    public Mixer() {
        audioComponents = new ArrayList<>();
    }

    public AudioClip getClip() {
        AudioClip newClip = new AudioClip();

        int sampleRate = AudioClip.getSampleRate();

        int[] arraySum = new int[sampleRate];

        for (int i = 0; i < audioComponents.size(); i++) {
            AudioClip tmpaudioClip = audioComponents.get(i).getClip();
            for (int j = 0; j < sampleRate; j++) {
                arraySum[j] += tmpaudioClip.getSample(j);
            }
        }

        for (int i = 0; i < sampleRate; i++) {
            newClip.setSample(i, clamping(arraySum[i]));
        }
        return newClip;
    }

    public static int clamping(int x) {
        if (x > 32767) {
            return 32767;
        } else if (x < -32768){
            return -32768;
        } else {
            return x;
        }
    }

    public int getNumInputs() {
        return audioComponents.size();
    }

    public String getInputName(int index) {
        return audioComponents.get(index).getInputName(0);
    }

    public void connectInput(AudioComponent input) {
        audioComponents.add(input);
    }
}
