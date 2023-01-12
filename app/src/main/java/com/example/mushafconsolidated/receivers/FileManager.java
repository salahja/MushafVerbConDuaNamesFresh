package com.example.mushafconsolidated.receivers;

import android.content.Context;
import android.os.Environment;

public class FileManager {


    public static String createAyaAudioLinkLocation(Context context , int reader, int aya, int sura) {
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
       return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/audio/" + reader + "/" + suraID+ ayaID + AudioAppConstants.Extensions.MP3;
        //Audio file path
 /*       return Environment
                .getExternalStorageDirectory()
                .getAbsolutePath()
                + context.getString(R.string.app_folder_path)
                + "/Audio/" + reader + "/" + suraID
                + ayaID + AudioAppConstants.Extensions.MP3;*/
    }

    public static String createAyaSurahLinkLocation(Context context , int reader,  int sura) {
        //create file name from aya id and sura id
        int suraLength = String.valueOf(sura).trim().length();
        String suraID = sura + "";

        if (suraLength == 1)
            suraID = "00" + sura;
        else if (suraLength == 2)
            suraID = "0" + sura;


        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/audio/" + reader + "/" + sura+   AudioAppConstants.Extensions.MP3;
        //Audio file path
 /*       return Environment
                .getExternalStorageDirectory()
                .getAbsolutePath()
                + context.getString(R.string.app_folder_path)
                + "/Audio/" + reader + "/" + suraID
                + ayaID + AudioAppConstants.Extensions.MP3;*/
    }
}
