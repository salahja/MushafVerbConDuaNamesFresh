package com.example.mushafconsolidated.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.mushafconsolidated.Entities.QuranEntity;

import java.util.List;

@Dao
public interface QuranDao {
    @Query("SELECT * FROM qurans where surah=:surahid")
    List<QuranEntity> getQuranVersesBySurah(int surahid);

    @Query("SELECT * FROM qurans where surah=:surahid")
    List<QuranEntity> getTranslation(int surahid);

    @Query("SELECT * FROM qurans order by surah,ayah")
    List<QuranEntity> getAllQuran();

    @Query("SELECT * FROM qurans where juz=:part")
    List<QuranEntity> getQuranbyJuz(int part);



    @Query("SELECT * FROM qurans where surah=:surahid and ayah=:ayahid")
    List<QuranEntity> getsurahayahVerses(int surahid, int ayahid);

    @Query("select * from qurans where docid  between :start and :end")
    List<QuranEntity> getVersesByPart(int start, int end);



    @Query("select * from qurans where  surah =:sura and page = :pageno order by ayah")
    List<QuranEntity> getAyahsByPage(int sura,int pageno);
    @Query("select * from qurans where  surah =:juz and page = :pageno order by ayah")
    List<QuranEntity> getAyahsByPagejuz(int juz,int pageno);




    @Query("select * from qurans where surah = :surahid and ayah>=:from and ayah<=:toid order by ayah  ")
    List<QuranEntity> getQuranbySurahAyahrange(int surahid,int from,int toid);

  //getQuranbySurahAyahrange
    //select * from qurans where ayah>=50 and ayah<=78 and surah=9


    @Query("select page from qurans where surah = :pos order by ayah limit 1 ")
    int getSuraStartpage(int pos);


}
