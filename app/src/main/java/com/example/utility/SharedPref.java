package com.example.utility;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPref {
    public static SharedPreferences sharedPreferences;
    final Context sharedContext;

    public SharedPref(Context sharedContext) {
        //  this.sharedPreferences = sharedPreferences;
        this.sharedContext = sharedContext;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(sharedContext);
    }


    public static int SeekarabicFontsize() {
        // return Integer.(sharedPreferences.getInt("pref_font_seekbar_key", (Context.MODE_PRIVATE)));
        return sharedPreferences.getInt("pref_font_arabic_key", Context.MODE_PRIVATE);

    }


    public static String getLanguage() {
        return sharedPreferences.getString("lang", String.valueOf(Context.MODE_PRIVATE));

    }

    public static String getWbwLanguage() {
        return sharedPreferences.getString("wbw", String.valueOf(Context.MODE_PRIVATE));

    }


    public static String themePreferences() {
        return sharedPreferences.getString("themePref", String.valueOf(Context.MODE_PRIVATE));

    }



    public static String getTranslation() {
        return sharedPreferences.getString("selecttranslation", String.valueOf(Context.MODE_PRIVATE));

    }

    public static Boolean showTranslation() {
        return sharedPreferences.getBoolean("showTranslationKey", true);

    }

    public static Boolean showWordByword() {
        return sharedPreferences.getBoolean("wordByWord", false);

    }

    public static boolean showErab() {
        return sharedPreferences.getBoolean("showErabKey", true);

    }




}
