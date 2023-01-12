package com.example.mushafconsolidated.Entities;

import android.text.SpannableStringBuilder;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "quranmeta")
public class QuranMetaEntity {


   @NonNull
    private int   ayahInSurahIndex 	;
   @NonNull
    private int        ayahIndex 	;
   @NonNull
    private int     hizbQuarter 	;
   @NonNull
    private int     juz 	;
   @NonNull
    private int      manzil 	;
   @NonNull
    private int      pageNum 	;
   @NonNull
    private String     sajda 	;
   @NonNull
    private int     surahIndex 	;
   @NonNull
    private String      text 	;
   @NonNull
    private String     cleantext 	;
    @PrimaryKey
   @NonNull
    private int       id;

    public QuranMetaEntity(int ayahInSurahIndex, int ayahIndex, int hizbQuarter, int juz, int manzil, int pageNum, @NonNull String sajda, int surahIndex, @NonNull String text, @NonNull String cleantext, int id) {
        this.ayahInSurahIndex = ayahInSurahIndex;
        this.ayahIndex = ayahIndex;
        this.hizbQuarter = hizbQuarter;
        this.juz = juz;
        this.manzil = manzil;
        this.pageNum = pageNum;
        this.sajda = sajda;
        this.surahIndex = surahIndex;
        this.text = text;
        this.cleantext = cleantext;
        this.id = id;
    }

    public int getAyahInSurahIndex() {
        return ayahInSurahIndex;
    }

    public void setAyahInSurahIndex(int ayahInSurahIndex) {
        this.ayahInSurahIndex = ayahInSurahIndex;
    }

    public int getAyahIndex() {
        return ayahIndex;
    }

    public void setAyahIndex(int ayahIndex) {
        this.ayahIndex = ayahIndex;
    }

    public int getHizbQuarter() {
        return hizbQuarter;
    }

    public void setHizbQuarter(int hizbQuarter) {
        this.hizbQuarter = hizbQuarter;
    }

    public int getJuz() {
        return juz;
    }

    public void setJuz(int juz) {
        this.juz = juz;
    }

    public int getManzil() {
        return manzil;
    }

    public void setManzil(int manzil) {
        this.manzil = manzil;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @NonNull
    public String getSajda() {
        return sajda;
    }

    public void setSajda(@NonNull String sajda) {
        this.sajda = sajda;
    }

    public int getSurahIndex() {
        return surahIndex;
    }

    public void setSurahIndex(int surahIndex) {
        this.surahIndex = surahIndex;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getCleantext() {
        return cleantext;
    }

    public void setCleantext(@NonNull String cleantext) {
        this.cleantext = cleantext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}