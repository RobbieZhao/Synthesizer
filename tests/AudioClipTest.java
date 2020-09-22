import org.junit.Assert;
import org.junit.Test;

public class AudioClipTest {
    @Test
    public void constructorTest() {
        AudioClip audioClip = new AudioClip();
        Assert.assertTrue(audioClip.getData().length == AudioClip.getSampleRate() * 2);
    }

    @Test
    public void getsetSample() {
        AudioClip audioClip = new AudioClip();
        for (int i = -32768; i <= 32767; i++) {
            audioClip.setSample(0, i);
            Assert.assertTrue(audioClip.getSample(0) == i);
        }
    }
}
