package com.example.mushafconsolidated.Entities;

import java.util.List;

public class Page {
   private List<QuranMetaEntity> ayahItems;
    private List<QuranEntity> ayahItemsquran;
    private int pageNum;
    private int rubHizb;
    private int juz;

    public List<QuranEntity> getAyahItemsquran() {
        return ayahItemsquran;
    }

    public int getJuz() {
        return juz;
    }

    public void setJuz(int juz) {
        this.juz = juz;
    }

    public void setAyahItemsquran(List<QuranEntity> ayahItemsquran) {
        this.ayahItemsquran = ayahItemsquran;
    }

    public List<QuranMetaEntity> getAyahItems() {
        return ayahItems;
    }

    public void setAyahItems(List<QuranMetaEntity> ayahItems) {
        this.ayahItems = ayahItems;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getRubHizb() {
        return rubHizb;
    }

    public void setRubHizb(int rubHizb) {
        this.rubHizb = rubHizb;
    }
}
