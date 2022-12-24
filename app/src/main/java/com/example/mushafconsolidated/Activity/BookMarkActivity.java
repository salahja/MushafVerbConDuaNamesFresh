package com.example.mushafconsolidated.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import mm.prayer.muslimmate.Activity.CompassActivityPrayer;
import mm.prayer.muslimmate.Activity.PrayerBaseActivity;
import mm.prayer.muslimmate.Activity.SettingsActivity;
import mm.prayer.muslimmate.fragments.CalendarFragment;
import mm.prayer.muslimmate.fragments.IslamicEventsFragment;
import mm.prayer.muslimmate.fragments.ManualLocationActivityPrayer;
import mm.prayer.muslimmate.fragments.PrayingFragment;
import mm.prayer.muslimmate.fragments.WeatherFragment;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import mm.prayer.muslimmate.ui.DataConvertPopup;
import mm.prayer.muslimmate.ui.FusedLocationService;
import mm.prayer.muslimmate.ui.LocationInfo;
import mm.prayer.muslimmate.ui.LocationReader;
import mm.prayer.muslimmate.ui.MuslimMatePrayerTimes;
import mm.prayer.muslimmate.ui.QuiblaCalculator;
import mm.prayer.muslimmate.ui.Validations;

public class BookMarkActivity extends BaseActivity{

    private static final int REQUEST_GPS_LOCATION = 113;

    private static final int[] TAB_TITLES = new int[]{R.string.collection, R.string.pins };

    public static LocationInfo locationInfo;
    public static int quiblaDegree;
    // private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager2 mViewPager;
    private ProgressDialog detectLocation;
    FusedLocationService gps;
    ProgressDialog progressDialog;

    private Dialog dialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(BookMarkActivity.this);

        //load application language
        String languageToLoad = ConfigPreferences.getApplicationLanguage(this);
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        /*addHelper = new Adds();*/
        setContentView(R.layout.mm_activity_main_location);
        final TabLayout tabLayout = findViewById(R.id.tabs);
        final ViewPager2 viewPager = findViewById(R.id.container);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        FragmentManager fm = getSupportFragmentManager();
        ViewStateAdapter mSectionsPagerAdapter = new ViewStateAdapter(fm, getLifecycle());




        //  mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(TAB_TITLES[position])).attach();
 
        //clickable application title
        TextView applicationTitle = (TextView) findViewById(R.id.title);
        applicationTitle.setText(getString(R.string.main));
        applicationTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });






    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mm_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

     if (id == R.id.action_convert_date) {
         //start date convert pop-up
          new DataConvertPopup(this);
     }else  if (id == R.id.action_settings) {
            //start compass activity
            startActivity(new Intent(this, CompassActivityPrayer.class));
            return true;


        }  else if (id == R.id.settings) {

               startActivityForResult(new Intent(this, SettingsActivity.class) , 16);
        }


        /*else if (id == R.id.action_convert_date) {
            //start date convert pop-up
            new DataConvertPopup(this);
        }*/ else if (id == R.id.settings) {
            //settings activity
        //    startActivityForResult(new Intent(this, SettingsActivity.class) , 16);
        }  else if (id == R.id.mosques) {
            //check gps enable or not
          //  if (Validations.gpsEnabled(this)) {
            //    if (Validations.isNetworkAvailable(this)) {
            //    startActivity(new Intent(this, MosquesActivity.class));
            //    }
        //    }

        }   else if (id == R.id.action_share) {
            //share intent for the application
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "#" + getString(R.string.app_name) + "\n https://play.google.com/store/apps/details?id=com.fekracomputers.muslimmate");
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }

        return super.onOptionsItemSelected(item);
    }






    private class ViewStateAdapter extends FragmentStateAdapter {
        public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    PrayingFragment fragv = new PrayingFragment();

                    return fragv;
                case 1:
                    CalendarFragment cfrag = new CalendarFragment();
                  return cfrag;

                case 2:

                IslamicEventsFragment eventsFragment = new IslamicEventsFragment();
                return eventsFragment;
                case 3:
                WeatherFragment weatherFragment = new WeatherFragment();
                return weatherFragment;



                default:
                    PrayingFragment fragvv = new PrayingFragment();
                    return fragvv;
            }


        }

        @Override
        public int getItemCount() {

            return 4;

        }

    }

    private class DataActivity {
    }
}
