package com.example.mushafconsolidated.Entities;

import java.util.List;

public class Page {
    private List<QuranMetaEntity> ayahItems;
    private int pageNum;
    private int rubHizb;
    private int juz;


    public int getJuz() {
        return juz;
    }

    public void setJuz(int juz) {
        this.juz = juz;
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
