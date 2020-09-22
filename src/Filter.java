public class Filter implements AudioComponent{
    private AudioComponent audioComponent;
    private double scale;


    public Filter(double scale, AudioComponent input) {
        this.scale = scale;
        this.audioComponent = input;
    }

    public Filter() {}

    public void setScale(double scale) {
        this.scale = scale;
    }

    public AudioClip getClip() {
        AudioClip newClip = new AudioClip();
        for (int i = 0; i < AudioClip.getSampleRate(); i++) {
            newClip.setSample(i, clamping((int)(audioComponent.getClip().getSample(i) * scale)));
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
        return 1;
    }

    public String getInputName(int index){
        return audioComponent.getInputName(index);
    }

    public void connectInput(AudioComponent input) {
        audioComponent = input;
    }

}
