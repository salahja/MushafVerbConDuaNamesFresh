package mm.prayer.muslimmate.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;


import com.example.mushafconsolidated.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.leo.searchablespinner.SearchableSpinner;
import com.leo.searchablespinner.interfaces.OnItemSelectListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mm.prayer.muslimmate.Activity.PrayerBaseActivity;
import mm.prayer.muslimmate.Activity.PrayShowActivityPrayer;
import mm.prayer.muslimmate.entity.Cities;
import mm.prayer.muslimmate.entity.Countries;
import mm.prayer.muslimmate.entity.DbUtility;
import mm.prayer.muslimmate.interfaces.DetectLocationManualListener;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import mm.prayer.muslimmate.ui.HGDate;

/**
 * Created by TuiyTuy on 12/14/2016.
 */

public class ManualLocationActivityPrayer extends PrayerBaseActivity {
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    View v;



    Context context;
    private Boolean iscountryistextinputlayout = false;
    private Boolean iscitytextinputlayut = false;
    Spinner countrySp , citySp;
    MaterialButton okBtn , cancelBtn;
    private String[] countries, cities;
    private List<Cities> cityList;
    private ProgressDialog progressDialog;
    DetectLocationManualListener listener;

    private LatLng latLng;
    private AutoCompleteTextView actv;
    private   TextInputLayout countryinputspinner;
    private TextInputLayout cityinputspinner;
    private Double lon,lat;

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void addListener(DetectLocationManualListener listener){
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   switchTheme("brown");
        setContentView(R.layout.mm_topache);
        latLng = new LatLng(0 , 0);

        setupViews();
    }

    private void setupViews() {

        cancelBtn =findViewById(R.id.btn_current_cancel);
        okBtn =  findViewById(R.id.btn_current_ok);
        countrySp =   findViewById(R.id.sp_country);
        citySp =   findViewById(R.id.sp_city);


        addItemToSpinner();



    }


    @SuppressLint("ResourceType")
    public void addItemToSpinner(){
        //extract countries names and short cuts
        DbUtility dbUtility=new DbUtility(ManualLocationActivityPrayer.this);
        final List<Countries> countriesList = dbUtility.getAllCountries();
        List<String> countryNamesArray = new ArrayList<>();
        List<String> countryArabicNamesArray = new ArrayList<>();
        final List<String> countriesID = new ArrayList<>();
        for (Countries countryItem : countriesList) {
            countryNamesArray.add(countryItem.getEn_Name());
            countryArabicNamesArray.add(countryItem.getAr_Name());
            countriesID.add(countryItem.getCode());
        }

        //show arabic and english names of languages
        if (ConfigPreferences.getApplicationLanguage(ManualLocationActivityPrayer.this).equals("ar")) {
            countries = countryArabicNamesArray.toArray(new String[countryArabicNamesArray.size()]);
        } else {
            countries = countryNamesArray.toArray(new String[countryNamesArray.size()]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ManualLocationActivityPrayer.this,
                R.layout.mm_spinner_view, countries);
        countrySp.setAdapter(adapter);
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(ManualLocationActivityPrayer.this);

        //





        countrySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DbUtility dbUtility=new DbUtility(ManualLocationActivityPrayer.this);
                //extract countries names and short cuts
                countriesList.get(position);
                final List<Countries> countriesList=   dbUtility.getAllCountries();
                String ccode=      countriesID.get(position);
                // List<Countries> countrycode=     dbUtility.GetCountryCitycode(ccode);
                cityList = dbUtility.GetCitycode(ccode);
                List<String> cityNames = new ArrayList<String>();
                List<String> cityArabicNames = new ArrayList<String>();
                for (Cities city : cityList) {
                    cityNames.add(city.getCity());
                    cityArabicNames.add(city.getAr_Name() == null ? city.getCity() : city.getAr_Name());
                }
                if (ConfigPreferences.getApplicationLanguage(ManualLocationActivityPrayer.this).equals("ar")) {
                    cities = cityArabicNames.toArray(new String[cityArabicNames.size()]);
                } else {
                    cities = cityNames.toArray(new String[cityNames.size()]);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ManualLocationActivityPrayer.this,
                        R.layout.mm_spinner_view, cities);
                citySp.setAdapter(adapter);
                addLATLNG();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Double lat= cityList.get(citySp.getSelectedItemPosition()).getLatitude();
                final Double lon = cityList.get(citySp.getSelectedItemPosition()).getLongitude();
                showDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HGDate hgDate = new HGDate();
                        hgDate.toHigri();
                        DbUtility utility=new DbUtility(ManualLocationActivityPrayer.this);

                        ConfigPreferences.setWorldPrayerCountry(ManualLocationActivityPrayer.this,  DbUtility.getLocinfo(getLat(),getLon()));
                        ManualLocationActivityPrayer.this.startActivity(new Intent(ManualLocationActivityPrayer.this, PrayShowActivityPrayer.class).putExtra("date", hgDate.getDay() + "-" + hgDate.getMonth() + "-" + hgDate.getYear() + "- 0"));
                        ((Activity) ManualLocationActivityPrayer.this).finish();
                        if (progressDialog != null){
                            progressDialog.dismiss();
                        }
                    }
                }).start();
            }
        });











    }

    private void addLATLNG() {
        double lat= cityList.get(citySp.getSelectedItemPosition()).getLatitude();
        double lon = cityList.get(citySp.getSelectedItemPosition()).getLongitude();
        if (listener != null) {
            listener.onDetectLocationManualListener(new LatLng(lat, lon));
        }
    }

    public void showDialog(){
        //start progress dialog
        progressDialog = new ProgressDialog(ManualLocationActivityPrayer.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
    }








}
