package com.example.mushafconsolidated.receivers;


import android.content.Context;
import android.util.Log;


import com.example.mushafconsolidated.Entities.QuranMetaEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper functions for the audio class
 */
public class AudioHelper {

    /**
     * Function to create stream link
     *
     * @param aya Aya info object
     * @return streaming link
     */
    public static synchronized String createStreamLink(QuranMetaEntity aya , String streamURL) {
        int suraNumber = String.valueOf(aya.getSurahIndex()).length();
        String suraID = aya.getSurahIndex() + "";

        if (suraNumber == 1)
            suraID = "00" + aya.getSurahIndex();
        else if (suraNumber == 2)
            suraID = "0" + aya.getSurahIndex();

        int ayaNumber = String.valueOf(aya.getAyahIndex()).length();
        String ayaID = aya.getAyahIndex() + "";

        if (ayaNumber == 1)
            ayaID = "00" + aya.getAyahIndex();
        else if (ayaNumber == 2)
            ayaID = "0" + aya.getAyahIndex();
//http://mirrors.quranicaudio.com/everyayah/Muhammad_Ayyoub_128kbps/074 048.mp3 surah74 verse 48
        String link = streamURL + suraID + ayaID + AudioAppConstants.Extensions.MP3;

        Log.e(AudioHelper.class.getSimpleName(), "streamURL : " + link);
        return link;
    }


    /**
     * Function to create download link
     */
    public List<String> createDownloadLinks(Context context ,  List<QuranMetaEntity> ayaList , String downloadLink , int readerID) {

        List<String> downloadLinks = new ArrayList<>();
      //  ayaList.add(0 , new Aya(1,1,1));
        //loop for all page ayat
        for (QuranMetaEntity ayaItem : ayaList) {
            //validate if aya download or not
            if (!QuranValidateSources.validateAyaAudio(context, readerID, ayaItem.getAyahIndex(), ayaItem.getSurahIndex())) {

                //create aya link
                int suraLength = String.valueOf(ayaItem.getSurahIndex()).trim().length();
                String suraID = ayaItem.getSurahIndex() + "";
                int ayaLength = String.valueOf(ayaItem.getAyahIndex()).trim().length();
                String ayaID = ayaItem.getAyahIndex() + "";
                if (suraLength == 1)
                    suraID = "00" + ayaItem.getSurahIndex();
                else if (suraLength == 2)
                    suraID = "0" + ayaItem.getSurahIndex();

                if (ayaLength == 1)
                    ayaID = "00" + ayaItem.getAyahIndex();
                else if (ayaLength == 2)
                    ayaID = "0" + ayaItem.getAyahIndex();

                //add aya link to list
                downloadLinks.add(downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3);
                Log.d("DownloadLinks", downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3);
            }
        }
        ayaList.remove(0);
        return downloadLinks;
    }

}
