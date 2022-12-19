package mm.prayer.muslimmate.Activity;

import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.mushafconsolidated.R;
import com.google.android.material.color.DynamicColors;


public  class PrayerBaseActivity extends AppCompatActivity {
    public static final String PURPLE_THEME = "purple";
    public static final String DARK_THEME = "dark";
    public static final String DARK_BLUE = "blue";
    public static final String DARK_GREEN = "green";
    public static final String BROWN_MODE = "brown";



    public String currenttheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        currenttheme = PreferenceManager.getDefaultSharedPreferences(this).getString("themepref", "dark");
        super.onCreate(savedInstanceState);
        DynamicColors.applyToActivitiesIfAvailable(PrayerBaseActivity.this.getApplication());
        switchTheme(currenttheme);
    }
//  protected  void setTheme(){

    //  switchTheme(currenttheme);
    // }
    protected void switchTheme(String currenttheme) {
        switch (currenttheme) {
            default:
            case PURPLE_THEME:
                setTheme(R.style.AppTheme_Dark);
                break;
            case DARK_THEME:
                setTheme(R.style.Theme_Black);
                break;
            case DARK_BLUE:
                setTheme(R.style.Theme_DarkBlue);
                break;
            case DARK_GREEN:
                setTheme(R.style.AppTheme_DarkGreen);
                break;
            case BROWN_MODE:
                setTheme(R.style.Theme_Browns);
                break;
        }
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("mmtheme", currenttheme).apply();
    }
}