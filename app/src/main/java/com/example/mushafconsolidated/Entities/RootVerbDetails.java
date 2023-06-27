package com.example.mushafconsolidated.Entities;

import android.text.SpannableString;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

public class RootVerbDetails {
    @Ignore
    SpannableString verses;


    private String arabic;
    private String lemma;
    private String araone;
    private String aratwo;
    private String arathree;
    private String arafour;
    private String arafive;
    private String tagone;
    private String tagtwo;
    private String tagthree;
    private String tagfour;
    private String tagfive;
    private String form;
    private String thulathibab;
    private String gendernumber;
    private String tense;
    private String voice;
    private String mood_kananumbers;
    private String lemma_a;
    @NonNull
    private int wordcount;
    @NonNull
    private int surah;
    @NonNull
    private int ayah;
    private String rootarabic;
    private int wordno;

    private String en;

    private String abjadname;
    private String namearabic;
    private String nameenglish;

    public RootVerbDetails() {
    }

    public RootVerbDetails(String arabic, String lemma, String araone, String aratwo, String arathree, String arafour, String arafive, String tagone, String tagtwo, String tagthree, String tagfour, String tagfive, String form, String thulathibab, String gendernumber, String tense, String voice, String mood_kananumbers, String lemma_a, int wordcount, int surah, int ayah, String rootarabic, int wordno, String en, String abjadname, String namearabic, String nameenglish) {
        this.arabic = arabic;
        this.lemma = lemma;
        this.araone = araone;
        this.aratwo = aratwo;
        this.arathree = arathree;
        this.arafour = arafour;
        this.arafive = arafive;
        this.tagone = tagone;
        this.tagtwo = tagtwo;
        this.tagthree = tagthree;
        this.tagfour = tagfour;
        this.tagfive = tagfive;
        this.form = form;
        this.thulathibab = thulathibab;
        this.gendernumber = gendernumber;
        this.tense = tense;
        this.voice = voice;
        this.mood_kananumbers = mood_kananumbers;
        this.lemma_a = lemma_a;
        this.wordcount = wordcount;
        this.surah = surah;
        this.ayah = ayah;
        this.rootarabic = rootarabic;
        this.wordno = wordno;
        this.en = en;
        this.abjadname = abjadname;
        this.namearabic = namearabic;
        this.nameenglish = nameenglish;
    }

    public String getMood_kananumbers() {
        return mood_kananumbers;
    }

    public void setMood_kananumbers(String mood_kananumbers) {
        this.mood_kananumbers = mood_kananumbers;
    }

    public int getWordno() {
        return wordno;
    }

    public void setWordno(int wordno) {
        this.wordno = wordno;
    }

    public String getArabic() {
        return arabic;
    }

    public void setArabic(String arabic) {
        this.arabic = arabic;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getTagone() {
        return tagone;
    }

    public void setTagone(String tagone) {
        this.tagone = tagone;
    }

    public String getTagtwo() {
        return tagtwo;
    }

    public void setTagtwo(String tagtwo) {
        this.tagtwo = tagtwo;
    }

    public String getTagthree() {
        return tagthree;
    }

    public void setTagthree(String tagthree) {
        this.tagthree = tagthree;
    }

    public String getTagfour() {
        return tagfour;
    }

    public void setTagfour(String tagfour) {
        this.tagfour = tagfour;
    }

    public String getTagfive() {
        return tagfive;
    }

    public void setTagfive(String tagfive) {
        this.tagfive = tagfive;
    }


    public int getWordcount() {
        return wordcount;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
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

    public String getRootarabic() {
        return rootarabic;
    }

    public void setRootarabic(String rootarabic) {
        this.rootarabic = rootarabic;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getAbjadname() {
        return abjadname;
    }

    public void setAbjadname(String abjadname) {
        this.abjadname = abjadname;
    }

    public String getNamearabic() {
        return namearabic;
    }

    public void setNamearabic(String namearabic) {
        this.namearabic = namearabic;
    }

    public String getNameenglish() {
        return nameenglish;
    }

    public void setNameenglish(String nameenglish) {
        this.nameenglish = nameenglish;
    }

    public String getAraone() {
        return araone;
    }

    public void setAraone(String araone) {
        this.araone = araone;
    }

    public String getAratwo() {
        return aratwo;
    }

    public void setAratwo(String aratwo) {
        this.aratwo = aratwo;
    }

    public String getArathree() {
        return arathree;
    }

    public void setArathree(String arathree) {
        this.arathree = arathree;
    }

    public String getArafour() {
        return arafour;
    }

    public void setArafour(String arafour) {
        this.arafour = arafour;
    }

    public String getArafive() {
        return arafive;
    }

    public void setArafive(String arafive) {
        this.arafive = arafive;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getThulathibab() {
        return thulathibab;
    }

    public void setThulathibab(String thulathibab) {
        this.thulathibab = thulathibab;
    }

    public String getGendernumber() {
        return gendernumber;
    }

    public void setGendernumber(String gendernumber) {
        this.gendernumber = gendernumber;
    }

    public String getTense() {
        return tense;
    }

    public void setTense(String tense) {
        this.tense = tense;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }



    public String getLemma_a() {
        return lemma_a;
    }

    public void setLemma_a(String lemma_a) {
        this.lemma_a = lemma_a;
    }
}

