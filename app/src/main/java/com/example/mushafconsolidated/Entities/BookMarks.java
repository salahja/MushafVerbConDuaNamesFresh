package com.example.mushafconsolidated.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Entity(tableName = "bookmark")
public class BookMarks {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String header;
    @NonNull
    private String verseno;
    @NonNull
    private String chapterno;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @NonNull
    private String surahname;
    private String datetime;
    @Ignore
    private String count;

    public BookMarks(int id, String header, @NonNull String verseno, @NonNull String chapterno, @NonNull String surahname, String datetime) {
        this.id = id;
        this.header = header;
        this.verseno = verseno;
        this.chapterno = chapterno;
        this.surahname = surahname;
        this.datetime = datetime;
    }

    public BookMarks(int id, String header, @NonNull String verseno, @NonNull String chapterno, @NonNull String surahname, String datetime, String count) {
        this.id = id;
        this.header = header;
        this.verseno = verseno;
        this.chapterno = chapterno;
        this.surahname = surahname;
        this.datetime = datetime;
        this.count = count;
    }

    public BookMarks() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    public void setVerseno(@NonNull String verseno) {
        this.verseno = verseno;
    }

    @NonNull
    public String getChapterno() {
        return chapterno;
    }

    @NonNull
    public String getVerseno() {
        return verseno;
    }


    public void setChapterno(@NonNull String chapterno) {
        this.chapterno = chapterno;
    }

    @NonNull
    public String getSurahname() {
        return surahname;
    }

    public void setSurahname(@NonNull String surahname) {
        this.surahname = surahname;
    }

    @NonNull
    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}

