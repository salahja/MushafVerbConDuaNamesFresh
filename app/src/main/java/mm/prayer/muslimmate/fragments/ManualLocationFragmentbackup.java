package mm.prayer.muslimmate.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;


import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.List;

import mm.prayer.muslimmate.Activity.PrayShowActivityPrayer;
import mm.prayer.muslimmate.entity.Cities;
import mm.prayer.muslimmate.entity.Countries;
 
import mm.prayer.muslimmate.interfaces.DetectLocationManualListener;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import mm.prayer.muslimmate.ui.HGDate;


/**
 * Created by TuiyTuy on 12/14/2016.
 */

public class ManualLocationFragmentbackup extends Fragment implements SearchView.OnQueryTextListener{
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    View v;
    Context context;
    Spinner countrySp , citySp;
    Button okBtn , cancelBtn;
    private String[] countries, cities;
    private List<Cities> cityList;
    private ProgressDialog progressDialog;
    DetectLocationManualListener listener;

    private LatLng latLng;
    private AutoCompleteTextView actv;

    public void addListener(DetectLocationManualListener listener){
        this.listener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mm_fragment_select_location_manual, container , false);
        context = getActivity();
        latLng = new LatLng(0 , 0);

        setupViews();

        return v;
    }

    private void setupViews() {

        okBtn = (Button) v.findViewById(R.id.btn_current_ok);
        countrySp =   v.findViewById(R.id.sp_country);
        citySp =   v.findViewById(R.id.sp_city);
//     actv = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView);


        addItemToSpinner();



    }


    @SuppressLint("ResourceType")
    public void addItemToSpinner(){
        //extract countries names and short cuts
        Utils Utils=new Utils(getActivity());
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
        if (ConfigPreferences.getApplicationLanguage(context).equals("ar")) {
            countries = countryArabicNamesArray.toArray(new String[countryArabicNamesArray.size()]);
        } else {
            countries = countryNamesArray.toArray(new String[countryNamesArray.size()]);
        }
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());










        //
        //  String theme = sharedPreferences.getString("theme", 1);
        String themePref = sharedPreferences.getString("themepref", "white");
        ArrayAdapter<String> adapter;
        if(themePref.equals("blue")||themePref.equals("black")){
            //spinner adapter
            adapter = new ArrayAdapter<String>(context,
                    R.layout.mm_spinner_view_dark, countries);


        }else{
            //spinner adapter
            adapter = new ArrayAdapter<String>(context,
                    R.layout.mm_spinner_view_light, countries);
            adapter.setDropDownViewResource(R.layout.mm_spinner_view_light);
        }


        countrySp.setAdapter(adapter);

        //on change new item from spinner
        countrySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String code = countriesID.get(position);
                Utils Utils=new Utils(context);
                //extract countries names and short cuts
                final List<Countries> countriesList=   Utils.getAllCountries();
                cityList = Utils.GetCitycode(code);
                List<String> cityNames = new ArrayList<String>();
                List<String> cityArabicNames = new ArrayList<String>();
                for (Cities city : cityList) {
                    cityNames.add(city.getCity());
                    cityArabicNames.add(city.getAr_Name() == null ? city.getCity() : city.getAr_Name());
                }
                if (ConfigPreferences.getApplicationLanguage(context).equals("ar")) {
                    cities = cityArabicNames.toArray(new String[cityArabicNames.size()]);
                } else {
                    cities = cityNames.toArray(new String[cityNames.size()]);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        R.layout.mm_spinner_view_dark, cities);
                citySp.setAdapter(adapter);
                addLATLNG();
            }



            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        citySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addLATLNG();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                        Utils utility=new Utils(getActivity());

                        ConfigPreferences.setWorldPrayerCountry(context,  Utils.getLocinfo(lat,lon));
                        context.startActivity(new Intent(context, PrayShowActivityPrayer.class).putExtra("date", hgDate.getDay() + "-" + hgDate.getMonth() + "-" + hgDate.getYear() + "- 0"));
                        ((Activity)context).finish();
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
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
    }
   



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

/*    @Override
    public boolean onQueryTextSubmit(String query) {
        gad.getFilter().filter(query);
        //  Utils.LogDebug("Submitted: "+query);
        return true;
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //    Utils.LogDebug("Changed: "+newText);
        return false;
    }
}
