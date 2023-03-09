package com.example.mushafconsolidated.model;

public class Juz {

  private   String nameendlish;
    private   String namearabic;
    private   int juz;
    private   int pages;
    private   int ayah;
    private int chapterid;
    private  String qurantext;

    public String getNameendlish() {
        return nameendlish;
    }

    public void setNameendlish(String nameendlish) {
        this.nameendlish = nameendlish;
    }

    public String getNamearabic() {
        return namearabic;
    }

    public void setNamearabic(String namearabic) {
        this.namearabic = namearabic;
    }

    public int getJuz() {
        return juz;
    }

    public void setJuz(int juz) {
        this.juz = juz;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getAyah() {
        return ayah;
    }

    public void setAyah(int ayah) {
        this.ayah = ayah;
    }

    public String getQurantext() {
        return qurantext;
    }

    public void setQurantext(String qurantext) {
        this.qurantext = qurantext;
    }

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public Juz(String nameendlish, String namearabic, int juz, int pages, int ayah, int chapterid, String qurantext) {
        this.nameendlish = nameendlish;
        this.namearabic = namearabic;
        this.juz = juz;
        this.pages = pages;
        this.ayah = ayah;
        this.chapterid = chapterid;
        this.qurantext = qurantext;
    }
}
