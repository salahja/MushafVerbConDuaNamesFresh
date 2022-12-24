package mm.prayer.muslimmate.Activity;

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


import com.example.mushafconsolidated.Activity.BaseActivity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import mm.prayer.muslimmate.fragments.ManualLocationActivityPrayer;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import mm.prayer.muslimmate.ui.DataConvertPopup;
import mm.prayer.muslimmate.ui.FusedLocationService;
import mm.prayer.muslimmate.ui.LocationInfo;
import mm.prayer.muslimmate.ui.LocationReader;
import mm.prayer.muslimmate.ui.MuslimMatePrayerTimes;
import mm.prayer.muslimmate.ui.QuiblaCalculator;
import mm.prayer.muslimmate.ui.Validations;

import mm.prayer.muslimmate.fragments.CalendarFragment;
import mm.prayer.muslimmate.fragments.IslamicEventsFragment;
import mm.prayer.muslimmate.fragments.PrayingFragment;
import mm.prayer.muslimmate.fragments.WeatherFragment;

public class MainTwoActivityPrayer extends BaseActivity implements com.google.android.gms.location.LocationListener {

    private static final int REQUEST_GPS_LOCATION = 113;

    private static final int[] TAB_TITLES = new int[]{R.string.prayertimings, R.string.calender,R.string.events,R.string.weather};
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
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainTwoActivityPrayer.this);
/*        String preferences = shared.getString("theme", "dark");
       // preferences="blue";
        switch (preferences) {
            case "white":
                switchTheme("purple");
                break;
            case "dark":
                switchTheme("dark");
                break;
            case "blue":
                switchTheme("blue");
                break;

            case "brown":
                switchTheme("brown");
                break;
        }*/
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

        Thread checkAndCopy = new Thread(new Runnable() {
            @Override
            public void run() {
                File mainFile = new File("/data/data/com.fekracomputers.muslimmate/muslim_organizer.sqlite.png");
            
                Intent data = new Intent(MainTwoActivityPrayer.this, DataActivity.class);
                startActivity(data);
                finish();
            }
        });
        locationInfo = ConfigPreferences.getLocationConfig(this);
        quiblaDegree = ConfigPreferences.getQuibla(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        FragmentManager fm = getSupportFragmentManager();
        ViewStateAdapter mSectionsPagerAdapter = new ViewStateAdapter(fm, getLifecycle());




        //  mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(TAB_TITLES[position])).attach();
        //  tabLayout.setupWithViewPager(mViewPager);

        //always set alarm for the prays in the application open
       /* if (ConfigPreferences.getPrayingNotification(this))
            Alarms.setNotificationAlarmMainPrayer(this);*/

        //clickable application title
        TextView applicationTitle = (TextView) findViewById(R.id.title);
        applicationTitle.setText(getString(R.string.main));
        applicationTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });


//        /*for testing prayer notification*/
//        sendBroadcast(new Intent(this, PrayerAlarm.class).putExtra("prayName" ,
//                "4"));

        //view pager to disable or enable landscape





        //check if user detect location before or not
        if (ConfigPreferences.getLocationConfig(this) == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_GPS_LOCATION);
            } else {
                //start to detect user loaction
                getLocation();
            }

        }

        //load azkar in the main activity



    }




    public void showDialog(){
        //start progress dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this,R.style.Theme_Material3_DayNight_Dialog_Alert);
        dialogBuilder.setCancelable(true);
        dialogBuilder.setMessage(getString(R.string.location_dialog_message));
        dialogBuilder.setTitle(android.R.string.dialog_alert_title);
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialogBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getLocation();
            }
        });

        dialog = dialogBuilder.show();



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mm_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

     /*   if (id == R.id.action_settings) {
            //start compass activity
            startActivity(new Intent(this, CompassActivity.class));
            return true;
        } else */
     if (id == R.id.action_convert_date) {
         //start date convert pop-up
          new DataConvertPopup(this);
     }else  if (id == R.id.action_settings) {
            //start compass activity
            startActivity(new Intent(this, CompassActivityPrayer.class));
            return true;


        } if (id == R.id.action_location) {
            //check permission for marshmallow
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_GPS_LOCATION);
            } else {
                //start getting location
                showDialog();
                return true;
            }

        } else if (id == R.id.settings) {

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

        } else if (id == R.id.worldpraye) {
            //start country prayer pop-up
           ManualLocationActivityPrayer fragment=new ManualLocationActivityPrayer();

            Intent sharingIntent = new Intent(MainTwoActivityPrayer.this, ManualLocationActivityPrayer.class);



            startActivity(sharingIntent);


          //  new CountryPrayerPopup(this , true , false);
        } else if (id == R.id.action_rate_app) {
            //market url of the application
            String url = "https://play.google.com/store/apps/details?id=com.fekracomputers.muslimmate";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } /*else if (id == R.id.action_about_app) {
            //start about activity
            startActivity(new Intent(this, AboutActivity.class));
        }*/ else if (id == R.id.action_share) {
            //share intent for the application
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "#" + getString(R.string.app_name) + "\n https://play.google.com/store/apps/details?id=com.fekracomputers.muslimmate");
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //check if user allow permission to app or not <= 21
            case REQUEST_GPS_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    finish();
                    Toast.makeText(this, "The application can't start without this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Validations.REQUEST_CODE && resultCode == 0) {
            getLocation();
        }else if (requestCode == 16){
//            sendBroadcast(new Intent().setAction("prayer.information.change"));
        }

    }

    /**
     * Function to get and save location in shared preference
     */
    public void getLocation() {
        if (Validations.gpsEnabledInLocation(this , true , true)) {
            //start progress dialog
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.detecting_location));
            progressDialog.show();
            gps = new FusedLocationService(this, this);
            System.out.println("check");
        }
    }

    Location currLocation = null;
    @Override
    public void onLocationChanged(Location location) {
        //get location from fused location api
        if (location != null && currLocation == null) {
            currLocation = location;
            gps.setFusedLatitude(location.getLatitude());
            gps.setFusedLongitude(location.getLongitude());
            if (gps.getFusedLatitude() != 0 && gps.getFusedLongitude() != 0) {

                Utils Utils=new Utils(MainTwoActivityPrayer.this);

             //   LocationInfo locationInfo = new Database().getLocationInfo((float) gps.getFusedLatitude(), (float) gps.getFusedLongitude());
              LocationInfo locationInfo =      Utils.getLocinfo(gps.getFusedLatitude(),gps.getFusedLongitude());
            //    LocationInfo locationInfo = new Database().getLocationInfo((float) gps.getFusedLatitude(), (float) gps.getFusedLongitude());
                Calendar calendar = Calendar.getInstance();
                LocationReader lr = new LocationReader(this);
                lr.read(gps.getFusedLatitude(),gps.getFusedLongitude());
                int dst = calendar.getTimeZone().getDSTSavings();
                locationInfo.dls = dst;
                Double hlat=17.37528;
                Double hlon=78.47444;

                switch (MuslimMatePrayerTimes.getDefaultMazhab(lr.getCountryCode())){
                    case PTC_MAZHAB_HANAFI:
                        locationInfo.mazhab = 1;
                        break;
                    case  PTC_MAZHAB_SHAFEI:
                        locationInfo.mazhab = 0;
                        break;
                }
                switch (MuslimMatePrayerTimes.getDefaultWay(lr.getCountryCode())){
                    case PTC_WAY_EGYPT:
                        locationInfo.way = 0;
                        break;
                    case  PTC_WAY_UMQURA:
                        locationInfo.way = 3;
                        break;

                    case  PTC_WAY_MWL:
                        locationInfo.way = 4;
                        break;

                    case  PTC_WAY_KARACHI:
                        locationInfo.way = 1;
                        break;

                    case  PTC_WAY_ISNA:
                        locationInfo.way = 2;
                        break;
                }
                ConfigPreferences.setLocationConfig(this, locationInfo);
                ConfigPreferences.setQuibla(this, (int) QuiblaCalculator.doCalculate((float) location.getLatitude(), (float) location.getLongitude()));
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("mazhab", locationInfo.mazhab + ""); // value to store
                editor.putString("calculations", locationInfo.way + "");
                editor.commit();
                Toast.makeText(getApplicationContext(), "Your Location is : " + locationInfo.city, Toast.LENGTH_LONG).show();
                progressDialog.cancel();
                gps.stopFusedLocation();
                Intent intent = getIntent();
                sendBroadcast(new Intent().setAction("prayer.information.change"));
                finish();
                startActivity(intent);
                ConfigPreferences.setPrayingNotification(this, true);
             //   Alarms.startCalculatePrayingBroadcast(this);

            }
        }else{
          //  new CountryPrayerPopup(this , true , true);
        }
    }

    /**
     * Adapter for application tabs and switch
     */



    /**
     * Async task to show azkar and count of every Zeker
     */


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
