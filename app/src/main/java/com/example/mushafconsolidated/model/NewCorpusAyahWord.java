package com.example.mushafconsolidated.model;

import android.text.SpannableString;

import com.example.mushafconsolidated.Entities.wbwentity;

import java.util.ArrayList;

public class NewCorpusAyahWord {
    private wbwentity word;
    private ArrayList<wbwentity> words;
    private SpannableString spannableverse;

    public NewCorpusAyahWord(wbwentity word, SpannableString spannableverse) {
        this.word = word;
        this.spannableverse = spannableverse;
    }


    public ArrayList<wbwentity> getWords() {
        return words;
    }

    public void setWords(ArrayList<wbwentity> words) {
        this.words = words;
    }

    public NewCorpusAyahWord() {
    }

    public wbwentity getWord() {
        return word;
    }

    public void setWord(wbwentity word) {
        this.word = word;
    }

    public SpannableString getSpannableverse() {
        return spannableverse;
    }

    public void setSpannableverse(SpannableString spannableverse) {
        this.spannableverse = spannableverse;
    }
}
