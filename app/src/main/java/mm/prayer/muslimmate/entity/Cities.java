package mm.prayer.muslimmate.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//primaryKeys ={"translation_id","verse_id"}

@Entity(tableName = "cityd")
public class Cities {
     @NonNull private String country;
     @NonNull private String city;
     @NonNull private Double latitude;
     @NonNull private Double longitude;
     @NonNull private Double time_zone;
    @PrimaryKey(autoGenerate = true)

     @NonNull private int locId;

      private String Ar_Name;

    public Cities() {
    }

    public Cities(@NonNull String country, @NonNull String city, @NonNull Double latitude, @NonNull Double longitude, @NonNull Double time_zone, int locId, String ar_Name) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time_zone = time_zone;
        this.locId = locId;
        Ar_Name = ar_Name;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    @NonNull
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull Double latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull Double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public Double getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(@NonNull Double time_zone) {
        this.time_zone = time_zone;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getAr_Name() {
        return Ar_Name;
    }

    public void setAr_Name(String ar_Name) {
        Ar_Name = ar_Name;
    }
}