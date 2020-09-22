public class SquareWave implements AudioComponent{
    private AudioClip audioClip;
    private double frequency;

    private final String name = "SquareWave";

    public SquareWave(double frequency) {
        this.frequency = frequency;
        audioClip = new AudioClip();
        int sampleRate = AudioClip.getSampleRate();
        for (int i = 0; i < sampleRate; i++) {
            int sampleValue;
            if(( frequency * i / sampleRate) % 1 > 0.5) {
                sampleValue = 32767;
            } else {
                sampleValue = -32767;
            }
            audioClip.setSample(i, sampleValue);
        }
    }

    public void update(double frequency) {
        this.frequency = frequency;
        int sampleRate = AudioClip.getSampleRate();
        for (int i = 0; i < sampleRate; i++) {
            int sampleValue;
            if(( this.frequency * i / sampleRate) % 1 > 0.5) {
                sampleValue = 32767;
            } else {
                sampleValue = -32767;
            }
            audioClip.setSample(i, sampleValue);
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

