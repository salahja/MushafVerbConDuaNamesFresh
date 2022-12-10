package mm.prayer.muslimmate.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import mm.prayer.muslimmate.entity.Cities;

@Dao
public interface CitiesDAO {


    @Query("SELECT * FROM CITYD ")
    List<Cities> getCities();




    @Query("SELECT * FROM cityd where country=:code")
    List<Cities> getCityCode(String code);
}
