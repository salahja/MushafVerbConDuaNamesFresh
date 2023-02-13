package com.example.mushafconsolidated.Entities;

public class AudioPlayed {


    private  int surah;
    private  int ayah;
    private int trackposition;

    public AudioPlayed() {

    }

    public int getSurah() {
        return surah;
    }

    public void setSurah(int surah) {
        this.surah = surah;
    }

    public int getAyah() {
        return ayah;
    }

    public void setAyah(int ayah) {
        this.ayah = ayah;
    }

    public int getTrackposition() {
        return trackposition;
    }

    public void setTrackposition(int trackposition) {
        this.trackposition = trackposition;
    }

    public AudioPlayed(int surah, int ayah, int trackposition) {
        this.surah = surah;
        this.ayah = ayah;
        this.trackposition = trackposition;
    }
}
