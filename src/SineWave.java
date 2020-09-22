public class SineWave implements AudioComponent{
    private AudioClip audioClip;
    private double frequency;

    private final String name = "SineWave";

    public SineWave(double frequency) {
        this.frequency = frequency;
        audioClip = new AudioClip();
        for (int i = 0; i < AudioClip.getSampleRate(); i++) {
            audioClip.setSample(i, (int) (32767 * Math.sin(2 * Math.PI * this.frequency * i / AudioClip.getSampleRate())));
        }
    }

    public void update(double frequency) {
        this.frequency = frequency;
        for (int i = 0; i < AudioClip.getSampleRate(); i++) {
            audioClip.setSample(i, (int) (32767 * Math.sin(2 * Math.PI * frequency * i / AudioClip.getSampleRate())));
        }
    }


    public AudioClip getClip() {
        return audioClip;
    }

    public int getNumInputs() {
        return 0;
    }

    public String getInputName(int index){
        return name;
    }

    public void connectInput(AudioComponent input) {
        input.connectInput(this);
    }
}
