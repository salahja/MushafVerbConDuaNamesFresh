package com.example.mushafconsolidated.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.Entities.QuranMetaEntity;

import java.util.List;

@Dao
public interface QuranMetaDao {

    @Query("select  * from quranmeta where `surahIndex` = :id order by ayahInSurahIndex asc")
    public List< QuranMetaEntity> getAllAyahOfSurahIndex(int id);

    @Query("select  * from quranmeta where `ayahIndex` between :start and :end order by ayahIndex asc ")
    public List< QuranMetaEntity> getAyahSInRange(int start, int end);

    @Query("select  * from quranmeta where `ayahIndex` = :id")
    public  QuranMetaEntity getAyahByIndex(int id);

    @Query("select  * from quranmeta where cleantext like  '%' || :txt || '%' ")
    public List< QuranMetaEntity> getAyahByAyahText(String txt);

  /*  @Query("select ayahIndex from quranmeta where audioPath is  null ")
    public List<Integer> getAyahNumberNotAudioDownloaded();*/

    @Query("select count(*) from quranmeta")
    public int getAyahCount();



    @Query("select  * from quranmeta where `surahIndex` = :index  and ayahInSurahIndex = :ayahIndex")
     QuranMetaEntity getAyahByInSurahIndex(int index, int ayahIndex);

/*    @Query("select max(ayahIndex) from quranmeta where audioPath is not null ")
    int getLastDownloadedAyahAudio();*/

  /*  @Query("select  cleantext ,ayahIndex, surahIndex, pageNum, juz, hizbQuarter, sajda, manzil, ayahInSurahIndex, text  from quranmeta ay  , surah su where ay.surahIndex = su.`index` and su.name = :suraName  ")
    List< QuranMetaEntity> getAllAyahOfSurahByName(String suraName);*/


    @Query("select * from quranmeta where pageNum = :i order by ayahIndex")
    List< QuranMetaEntity> getAyahsByPage(int i);

    @Query("select pageNum from quranmeta where surahIndex = :pos order by ayahInSurahIndex limit 1 ")
    int getSuraStartpage(int pos);
/*
    @Query("select count(*) from quranmeta where tafseer is not null ")
    int getTotalTafseerDownloaded();

    @Query("select count(*) from quranmeta where audioPath is not null ")
    int getTotalAudioDownloaded();*/

    @Query("select pageNum from quranmeta where juz = :pos order by pageNum asc limit 1 ")
    int getPageFromJuz(int pos);

    @Query("select pageNum from quranmeta where surahIndex = :surah and ayahInSurahIndex = :ayah order by pageNum asc limit 1 ")
    int getPageFromSurahAndAyah(int surah, int ayah);

/*    @Query("select  * from quranmeta where `surahIndex` = :l and tafseer is not null")
    List< QuranMetaEntity> getAllAyahOfSurahIndexForTafseer(long l);*/

    @Query("select pageNum from quranmeta group by hizbQuarter order by pageNum asc")
    List<Integer> getHizbQuaterStart();
//  String sql = "select soraid , ayaid from aya where page = " + page + " and ayaid  is not 0  order by soraid,ayaid ;";
    @Query("select * from quranmeta where pageNum=:page and ayahIndex is not 0 order by surahIndex,ayahIndex")
    List<QuranMetaEntity> getPageAyat(int page);

}
