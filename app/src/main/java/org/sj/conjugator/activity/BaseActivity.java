package org.sj.conjugator.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.mushafconsolidated.R;
import com.google.android.material.color.DynamicColors;

public  class BaseActivity extends AppCompatActivity {
    public static final String LIGHT_THEME = "light";
    public static final String DARK_THEME = "dark";
    public static final String DARK_BLUE = "blue";
    public static final String DARK_GREEN = "green";
    public static final String BROWN_MODE = "brown";

    public String currenttheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        currenttheme = PreferenceManager.getDefaultSharedPreferences(this).getString("themepref", "dark");
        super.onCreate(savedInstanceState);
        DynamicColors.applyToActivitiesIfAvailable(this.getApplication());
        switchTheme(currenttheme);
    }
//  protected  void setTheme(){

    //  switchTheme(currenttheme);
    // }
    protected void switchTheme(String currenttheme) {
        switch (currenttheme) {

            case LIGHT_THEME:
                setTheme(R.style.AppTheme);
                break;
            case DARK_THEME:
                setTheme(R.style.Theme_Black);
                break;
            case DARK_BLUE:
                setTheme(R.style.AppTheme_materialdarkblue);

                break;
            case DARK_GREEN:
                setTheme(R.style.AppTheme_DarkGreen);
                break;
            case BROWN_MODE:
                setTheme(R.style.Theme_Browns);
                break;
            default:
                setTheme(R.style.Theme_Black);
                break;
        }
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("theme", currenttheme).apply();
    }
}