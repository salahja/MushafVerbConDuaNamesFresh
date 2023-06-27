package com.example.mushafconsolidated.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.mushafconsolidated.Entities.lanelexicon;

import com.example.mushafconsolidated.Entities.lanerootdictionary;


import java.util.List;

@Dao
public interface LaneRootDao {
    @Query("SELECT * FROM lanesrootdictionary where rootarabic=:root")
    List<lanerootdictionary> getLanesRootDefinition(String root);

}




