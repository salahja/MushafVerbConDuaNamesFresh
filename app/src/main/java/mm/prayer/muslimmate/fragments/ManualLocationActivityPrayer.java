package mm.prayer.muslimmate.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;


import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import mm.prayer.muslimmate.Activity.PrayerBaseActivity;
import mm.prayer.muslimmate.Activity.PrayShowActivityPrayer;
import mm.prayer.muslimmate.entity.Cities;
import mm.prayer.muslimmate.entity.Countries;

import mm.prayer.muslimmate.interfaces.DetectLocationManualListener;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import mm.prayer.muslimmate.ui.HGDate;

/**
 * Created by TuiyTuy on 12/14/2016.
 */

public class ManualLocationActivityPrayer extends PrayerBaseActivity {


    Spinner countrySp , citySp;
    MaterialButton okBtn , cancelBtn;
    private String[] cities;
    private List<Cities> cityList;
    private ProgressDialog progressDialog;
    DetectLocationManualListener listener;

    // --Commented out by Inspection START (10/12/22, 1:20 PM):
//    public void addListener(DetectLocationManualListener listener){
//        this.listener = listener;
//    }
// --Commented out by Inspection STOP (10/12/22, 1:20 PM)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   switchTheme("brown");
        setContentView(R.layout.mm_topache);
        LatLng latLng = new LatLng(0, 0);

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
        Utils Utils=new Utils(ManualLocationActivityPrayer.this);
        final List<Countries> countriesList = Utils.getAllCountries();
        List<String> countryNamesArray = new ArrayList<>();
        List<String> countryArabicNamesArray = new ArrayList<>();
        final List<String> countriesID = new ArrayList<>();
        for (Countries countryItem : countriesList) {
            countryNamesArray.add(countryItem.getEn_Name());
            countryArabicNamesArray.add(countryItem.getAr_Name());
            countriesID.add(countryItem.getCode());
        }

        //show arabic and english names of languages
        String[] countries;
        if (ConfigPreferences.getApplicationLanguage(ManualLocationActivityPrayer.this).equals("ar")) {
            countries = countryArabicNamesArray.toArray(new String[0]);
        } else {
            countries = countryNamesArray.toArray(new String[0]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ManualLocationActivityPrayer.this,
                R.layout.mm_spinner_view, countries);
        countrySp.setAdapter(adapter);

        //





        countrySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils Utils=new Utils(ManualLocationActivityPrayer.this);
                //extract countries names and short cuts

                String ccode=      countriesID.get(position);
                // List<Countries> countrycode=     Utils.GetCountryCitycode(ccode);
                cityList = Utils.GetCitycode(ccode);
                List<String> cityNames = new ArrayList<>();
                List<String> cityArabicNames = new ArrayList<>();
                for (Cities city : cityList) {
                    cityNames.add(city.getCity());
                    cityArabicNames.add(city.getAr_Name() == null ? city.getCity() : city.getAr_Name());
                }
                if (ConfigPreferences.getApplicationLanguage(ManualLocationActivityPrayer.this).equals("ar")) {
                    cities = cityArabicNames.toArray(new String[0]);
                } else {
                    cities = cityNames.toArray(new String[0]);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(ManualLocationActivityPrayer.this,
                        R.layout.mm_spinner_view, cities);
                citySp.setAdapter(adapter);
                addLATLNG();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        okBtn.setOnClickListener(view -> {


            final Double lat= cityList.get(citySp.getSelectedItemPosition()).getLatitude();
            final Double lon = cityList.get(citySp.getSelectedItemPosition()).getLongitude();
            showDialog();
            new Thread(() -> {
                HGDate hgDate = new HGDate();
                hgDate.toHigri();

                ConfigPreferences.setWorldPrayerCountry(ManualLocationActivityPrayer.this,  Utils.getLocinfo(lat,lon));
                ManualLocationActivityPrayer.this.startActivity(new Intent(ManualLocationActivityPrayer.this, PrayShowActivityPrayer.class).putExtra("date", hgDate.getDay() + "-" + hgDate.getMonth() + "-" + hgDate.getYear() + "- 0"));
                ManualLocationActivityPrayer.this.finish();
                if (progressDialog != null){
                    progressDialog.dismiss();
                }
            }).start();
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
