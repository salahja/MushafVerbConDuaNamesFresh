package mm.prayer.muslimmate.model;

import com.example.mushafconsolidated.Entities.AudioPlayed;

import java.util.ArrayList;
import java.util.List;

public class AudioPositionSaved {
    public List<AudioPlayed> audiopsaved;

    public AudioPositionSaved(List<AudioPlayed> audiopsaved) {
        this.audiopsaved = audiopsaved;
    }

    public List<AudioPlayed> getAudiopsaved() {
        return audiopsaved;
    }

    public void setAudiopsaved(List<AudioPlayed> audiopsaved) {
        this.audiopsaved = audiopsaved;
    }
}