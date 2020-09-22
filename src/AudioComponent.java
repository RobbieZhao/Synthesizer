public interface AudioComponent {
    AudioClip getClip();

    int getNumInputs();

    String getInputName(int index);

    void connectInput(AudioComponent input);

}