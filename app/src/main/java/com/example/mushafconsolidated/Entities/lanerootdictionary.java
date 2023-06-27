package com.example.mushafconsolidated.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//primaryKeys ={"translation_id","verse_id"}

@Entity(tableName = "lanesrootdictionary")
public class lanerootdictionary {
    @NonNull
    private String rootarabic;
    @NonNull
    private String rootbuckwater;


    private String definition;
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    public lanerootdictionary(@NonNull String rootarabic, @NonNull String rootbuckwater, String definition, int id) {
        this.rootarabic = rootarabic;
        this.rootbuckwater = rootbuckwater;
        this.definition = definition;
        this.id = id;
    }


    @NonNull
    public String getRootarabic() {
        return rootarabic;
    }

    public void setRootarabic(@NonNull String rootarabic) {
        this.rootarabic = rootarabic;
    }

    @NonNull
    public String getRootbuckwater() {
        return rootbuckwater;
    }

    public void setRootbuckwater(@NonNull String rootbuckwater) {
        this.rootbuckwater = rootbuckwater;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}