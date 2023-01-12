package com.example.mushafconsolidated.DAO;

import androidx.room.Dao;
import androidx.room.Query;


import com.example.mushafconsolidated.Entities.Qari;

import java.util.List;




@Dao
public interface QariDao {
    @Query("SELECT * FROM audio ORDER BY id")
    List<Qari> getQaris();

    @Query("SELECT * FROM audio where name=:id")
    List<Qari> getSelectedQari(String id);

}




