package mm.prayer.muslimmate.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//primaryKeys ={"translation_id","verse_id"}

@Entity(tableName = "Countries")
public class Countries {
    @NonNull

    private String    Code           ;
    @NonNull private String    Continent_Code ;
    @NonNull private String     En_Name        ;
    @NonNull private String     iso3           ;
    @NonNull private String     Number         ;
    @NonNull private String    En_Full_Name   ;
    @NonNull private int    mazhab         ;
    @NonNull private int   way            ;
    @NonNull private int   dls            ;
    @NonNull private String  Ar_Name        ;
    @NonNull private int   islamic        ;

    @PrimaryKey
    @NonNull
    private  int id;

    public Countries() {
    }

    public Countries(@NonNull String code, @NonNull String continent_Code, @NonNull String en_Name, @NonNull String iso3, @NonNull String number, @NonNull String en_Full_Name, @NonNull int mazhab, int way, int dls, @NonNull String ar_Name, int islamic, int id) {
        Code = code;
        Continent_Code = continent_Code;
        En_Name = en_Name;
        this.iso3 = iso3;
        Number = number;
        En_Full_Name = en_Full_Name;
        this.mazhab = mazhab;
        this.way = way;
        this.dls = dls;
        Ar_Name = ar_Name;
        this.islamic = islamic;
        this.id = id;
    }

    @NonNull
    public String getCode() {
        return Code;
    }

    public void setCode(@NonNull String code) {
        Code = code;
    }

    @NonNull
    public String getContinent_Code() {
        return Continent_Code;
    }

    public void setContinent_Code(@NonNull String continent_Code) {
        Continent_Code = continent_Code;
    }

    @NonNull
    public String getEn_Name() {
        return En_Name;
    }

    public void setEn_Name(@NonNull String en_Name) {
        En_Name = en_Name;
    }

    @NonNull
    public String getIso3() {
        return iso3;
    }

    public void setIso3(@NonNull String iso3) {
        this.iso3 = iso3;
    }

    @NonNull
    public String getNumber() {
        return Number;
    }

    public void setNumber(@NonNull String number) {
        Number = number;
    }

    @NonNull
    public String getEn_Full_Name() {
        return En_Full_Name;
    }

    public void setEn_Full_Name(@NonNull String en_Full_Name) {
        En_Full_Name = en_Full_Name;
    }

    @NonNull
    public int getMazhab() {
        return mazhab;
    }

    public void setMazhab(@NonNull int mazhab) {
        this.mazhab = mazhab;
    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public int getDls() {
        return dls;
    }

    public void setDls(int dls) {
        this.dls = dls;
    }

    @NonNull
    public String getAr_Name() {
        return Ar_Name;
    }

    public void setAr_Name(@NonNull String ar_Name) {
        Ar_Name = ar_Name;
    }

    public int getIslamic() {
        return islamic;
    }

    public void setIslamic(int islamic) {
        this.islamic = islamic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}