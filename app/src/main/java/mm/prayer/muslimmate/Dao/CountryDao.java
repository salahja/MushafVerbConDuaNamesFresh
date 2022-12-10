package mm.prayer.muslimmate.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import mm.prayer.muslimmate.entity.Countries;

@Dao
public interface CountryDao {


    @Query("SELECT * FROM Countries ")
    List<Countries> getAllCountries();

    @Query("SELECT * FROM Countries where iso3=:code")
    List<Countries> getCountryCode(String code);
    @Query("SELECT * FROM Countries where En_Name=:code")
    List<Countries> getCountryCodebycityname(String code);


}
