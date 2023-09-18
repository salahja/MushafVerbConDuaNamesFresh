package com.example.mushafconsolidated.Entities;

import android.text.SpannableString;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

public class QuranCorpusWbw {

    @Ignore
    private SpannableString spannableverse;
    @Embedded
    private   CorpusEntity corpus;
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    private   wbwentity wbw;

    public QuranCorpusWbw(CorpusEntity corpus, wbwentity wbw) {
        this.corpus = corpus;
        this.wbw = wbw;
    }

    public QuranCorpusWbw() {
    }

    public SpannableString getSpannableverse() {
        return spannableverse;
    }

    public void setSpannableverse(SpannableString spannableverse) {
        this.spannableverse = spannableverse;
    }

    public CorpusEntity getCorpus() {
        return corpus;
    }

    public void setCorpus(CorpusEntity corpus) {
        this.corpus = corpus;
    }

    public wbwentity getWbw() {
        return wbw;
    }

    public void setWbw(wbwentity wbw) {
        this.wbw = wbw;
    }
}
