package com.example.mushafconsolidated.Entities;

import android.text.SpannableString;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.example.mushafconsolidated.model.WordSpan;

import java.util.ArrayList;

public class RootWordDetails {
    @Ignore
    SpannableString verses;



    private String arabic;
    private String lemma;

    private String tagone;
    private String tagtwo;
    private String tagthree;
    private String tagfour;
    private String tagfive;
    private String tag;
    @NonNull
    private int wordcount;
    @NonNull
    private int surah;
    @NonNull
    private int ayah;
    private String rootarabic;

    private String en;

    private String abjadname;
    private String namearabic;
    private String nameenglish;

    public RootWordDetails() {
    }

    public String getArabic() {
        return arabic;
    }

    public void setArabic(String arabic) {
        this.arabic = arabic;
    }

    public RootWordDetails(String arabic, String lemma, String tagone, String tagtwo, String tagthree, String tagfour, String tagfive, String tag, int wordcount, int surah, int ayah, String rootarabic, String en, String abjadname, String namearabic, String nameenglish) {
        this.arabic = arabic;
        this.lemma = lemma;
        this.tagone = tagone;
        this.tagtwo = tagtwo;
        this.tagthree = tagthree;
        this.tagfour = tagfour;
        this.tagfive = tagfive;
        this.tag = tag;
        this.wordcount = wordcount;
        this.surah = surah;
        this.ayah = ayah;
        this.rootarabic = rootarabic;
        this.en = en;
        this.abjadname = abjadname;
        this.namearabic = namearabic;
        this.nameenglish = nameenglish;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
}

/*
SELECT CorpusExpand.rootaraone || rootaratwo || rootarathree || rootarafour || rootarafive,
       CorpusExpand.surah,
       CorpusExpand.ayah,
       CorpusExpand.wordno,
       CorpusExpand.wordcount,
       CorpusExpand.form_one || form_two || form_three || form_four || form_five,
       CorpusExpand.araone,
       CorpusExpand.aratwo,
       CorpusExpand.arathree,
       CorpusExpand.arafour,
       CorpusExpand.arafive,
       CorpusExpand.tagone,
       CorpusExpand.tagtwo,
       CorpusExpand.tagthree,
       CorpusExpand.tagfour,
       CorpusExpand.tagfive,
       nouncorpus.tag,
       nouncorpus.propone,
       nouncorpus.proptwo,
       nouncorpus.form,
       nouncorpus.gendernumber,
       nouncorpus.type,
       nouncorpus.cases,
       wbw.en
      FROM corpusexpand,nouncorpus,
       wbw
 WHERE corpusexpand.tagone="ADJ" and
       CorpusExpand.rootaraone || rootaratwo || rootarathree || rootarafour || rootarafive LIKE "عبد" AND
       corpusexpand.surah = wbw.surah AND  corpusexpand.surah = nouncorpus.surah AND
       corpusexpand.ayah = wbw.ayah AND      corpusexpand.ayah = nouncorpus.ayah AND
       corpusexpand.wordno = wbw.wordno   AND  corpusexpand.wordno = nouncorpus.wordno order by corpusexpand.surah,
       corpusexpand.ayah

 */