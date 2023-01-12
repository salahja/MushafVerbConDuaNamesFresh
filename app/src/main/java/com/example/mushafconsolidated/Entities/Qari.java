package com.example.mushafconsolidated.Entities;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "audio")
public class Qari {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name ;
    @NonNull

    public String         name_english ;
    @NonNull

    public int           audiotype ;
    @NonNull
    public String  url ;

    public Qari(int id, @NonNull String name, @NonNull String name_english, int audiotype, @NonNull String url) {
        this.id = id;
        this.name = name;
        this.name_english = name_english;
        this.audiotype = audiotype;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName_english() {
        return name_english;
    }

    public void setName_english(@NonNull String name_english) {
        this.name_english = name_english;
    }


    public int getAudiotype() {
        return audiotype;
    }

    public void setAudiotype(int audiotype) {
        this.audiotype = audiotype;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }
}
