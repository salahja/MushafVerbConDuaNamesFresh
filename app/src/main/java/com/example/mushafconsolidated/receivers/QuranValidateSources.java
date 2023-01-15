package com.example.mushafconsolidated.receivers;

import android.content.Context;
import android.os.Environment;

import com.example.mushafconsolidated.R;

import java.io.File;

public class QuranValidateSources {


    /**
     * Function to validate aya found or not
     *
     * @param context Application context
     * @param reader  Reader id
  b
     * @param sura    Sura id
     * @return Flag of found or not
     */
    public static boolean validateAyaAudio(Context context, int reader,int aya,  int sura) {

        //create file name from aya id and sura id
        int suraLength = String.valueOf(sura).trim().length();
        String suraID = sura + "";
        int ayaLength = String.valueOf(aya).trim().length();
        String ayaID = aya + "";

        if (suraLength == 1)
            suraID = "00" + sura;
        else if (suraLength == 2)
            suraID = "0" + sura;

        if (ayaLength == 1)
            ayaID = "00" + aya;
        else if (ayaLength == 2)
            ayaID = "0" + aya;

        //Audio file path
        ///storage/emulated/0/Mushafapplication/Audio/1/8.mp3
        String app_folder_path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()+ "/audio/"+reader+"/"
                +suraID
                + ayaID + AudioAppConstants.Extensions.MP3;
        String filePath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath()
                + context.getString(R.string.app_folder_path)
                + "/Audio/" + reader+"/"+suraID
                  + AudioAppConstants.Extensions.MP3;

        //check file found or not
        File file = new File(app_folder_path);
        if (!file.exists())
            return false;

        return true;
    }

    public static boolean validateSurahAudio(Context context, int reader,   int sura) {

        //create file name from aya id and sura id
        int suraLength = String.valueOf(sura).trim().length();
        String suraID = sura + "";


        if (suraLength == 1)
            suraID = "00" + sura;
        else if (suraLength == 2)
            suraID = "0" + sura;



        //Audio file path
        ///storage/emulated/0/Mushafapplication/Audio/1/8.mp3
        String app_folder_path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()+ "/audio/"+reader+"/"
                +sura
                +   AudioAppConstants.Extensions.MP3;
        String filePath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath()
                + context.getString(R.string.app_folder_path)
                + "/Audio/" + reader+"/"+suraID
                + AudioAppConstants.Extensions.MP3;

        //check file found or not
        File file = new File(app_folder_path);
        if (!file.exists())
            return false;

        return true;
    }
}