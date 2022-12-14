package mm.prayer.muslimmate.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mushafconsolidated.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import mm.prayer.muslimmate.ui.LocationInfo;
import mm.prayer.muslimmate.ui.NumbersLocal;
import mm.prayer.muslimmate.adapter.WeatherAdapter;
import mm.prayer.muslimmate.model.WeatherApp;
import mm.prayer.muslimmate.ui.WeatherIcon;
import mm.prayer.muslimmate.model.WeatherSave;


/**
 * Fragment to show weather
 */

public class WeatherFragment extends Fragment {
    private final String URL = "https://api.openweathermap.org/data/2.5/forecast?";
    //    private final String API_ID = "&appid=9aea9d6625da386486980f9c7cb32b73";
    private final String API_ID = "&appid=ac6f2688dbfdc24772be777529947e27";
    private final String METRIC="&lang=PT&units=metric";
    private RecyclerView weatherRecyclerView;
    private WeatherAdapter adapter;
    private List<WeatherApp> weatherList;
    private TextView today, todayDescription, dayA, humidity, windSpeed,
            dayB, dayC, dayD, dayA_temp, dayB_temp, dayC_temp, dayD_temp,
            locationName;
    private ImageView imageToday, dayA_Image, dayB_Image, dayC_Image, dayD_image, refresh;
    private ProgressBar seeking;
    private boolean firstEntry = true;
    private Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mm_fragment_weather, container, false);
        init(view);
        return view;
    }

    /**
     * Function to init weather fragment views
     *
     * @param view Fragment view
     */
    private void init(View view) {


        //init views in the weather view
        today = (TextView) view.findViewById(R.id.textView29);
        todayDescription = (TextView) view.findViewById(R.id.textView20);
        imageToday = (ImageView) view.findViewById(R.id.imageView6);
        humidity = (TextView) view.findViewById(R.id.humidity);
        windSpeed = (TextView) view.findViewById(R.id.windSpeed);
        seeking = (ProgressBar) view.findViewById(R.id.seeking);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        dayA = (TextView) view.findViewById(R.id.day1);
        dayB = (TextView) view.findViewById(R.id.day2);
        dayC = (TextView) view.findViewById(R.id.day3);
        dayD = (TextView) view.findViewById(R.id.day4);
        locationName = (TextView) view.findViewById(R.id.tv_city_name);
        dayA_temp = (TextView) view.findViewById(R.id.day1Temp);
        dayB_temp = (TextView) view.findViewById(R.id.day2temp);
        dayC_temp = (TextView) view.findViewById(R.id.day3temp);
        dayD_temp = (TextView) view.findViewById(R.id.day4temp);
        dayA_Image = (ImageView) view.findViewById(R.id.day1Imgae);
        dayB_Image = (ImageView) view.findViewById(R.id.day2Image);
        dayC_Image = (ImageView) view.findViewById(R.id.day3Image);
        dayD_image = (ImageView) view.findViewById(R.id.day4Image);

        //button refresh
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new WeatherUpdate(true).execute();
            }
        });

        //Recycler view init
        weatherList = new ArrayList<>();
        adapter = new WeatherAdapter(weatherList, getContext());
        weatherRecyclerView = (RecyclerView) view.findViewById(R.id.weather);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        weatherRecyclerView.setLayoutManager(layoutManager);
        weatherRecyclerView.setItemAnimator(new DefaultItemAnimator());
        weatherRecyclerView.setAdapter(adapter);
        LocationInfo locationInfo = ConfigPreferences.getLocationConfig(getContext());
        if (locationInfo != null) {
            locationName.setText(getString(R.string.near) + " " + (ConfigPreferences.getApplicationLanguage(getContext()).equals("en") ? locationInfo.city : locationInfo.city_ar));
        }

        //load offline saved weather data if exists
        WeatherSave weatherSave = ConfigPreferences.getTodayListWeather(getContext());
        if (weatherSave != null && weatherSave.weatherApps.size() != 0) {
            today.setText(NumbersLocal.convertNumberType(getContext(), weatherSave.weatherApps.get(0).tempMini + "??"));
            humidity.setText(NumbersLocal.convertNumberType(getContext(), weatherSave.weatherApps.get(0).humidity) + " %");
            windSpeed.setText(NumbersLocal.convertNumberType(getContext(), weatherSave.weatherApps.get(0).windSpeed + " " + getString(R.string.wind_meager)));
            todayDescription.setText(weatherSave.weatherApps.get(0).desc);
            imageToday.setImageResource(WeatherIcon.get_icon_id_white(weatherSave.weatherApps.get(0).image));
            weatherList.addAll(weatherSave.weatherApps);
            adapter.notifyDataSetChanged();
            WeatherSave weekWeather = ConfigPreferences.getWeekListWeather(getContext());
            if (weekWeather.weatherApps.size() > 0) {
                showDate(weekWeather.weatherApps, 0, dayA_temp, dayA_Image, dayA);
                showDate(weekWeather.weatherApps, 1, dayB_temp, dayB_Image, dayB);
                showDate(weekWeather.weatherApps, 2, dayC_temp, dayC_Image, dayC);
                showDate(weekWeather.weatherApps, 3, dayD_temp, dayD_image, dayD);
            }
        }


        //call weather api to get new weather
        new WeatherUpdate(false).execute();

    }


    /**
     * Async task to get weather from api
     */
    private class WeatherUpdate extends AsyncTask<Void, Void, List<WeatherApp>> {
        List<WeatherApp> weathers;

        private boolean showSnackbar;

        public WeatherUpdate(boolean showSnackbar) {
            this.showSnackbar = showSnackbar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            seeking.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
        }

        @Override
        protected List<WeatherApp> doInBackground(Void... voids) {
            try {
                //api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}
                //weather list
                weathers = new ArrayList<>();
                LocationInfo locationInfo = ConfigPreferences.getLocationConfig(getContext());
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(URL + "lat=" + locationInfo.latitude + "&lon=" + locationInfo.longitude + "&lang=" + Locale.getDefault().getLanguage() + API_ID+METRIC).build();

                Log.i("URL_WITHER", URL + "lat=" + locationInfo.latitude + "&lon=" + locationInfo.longitude + "&lang=" + Locale.getDefault().getLanguage() + API_ID);
                //receive json and parse
                Response response = client.newCall(request).execute();
                JSONArray ja = new JSONArray();


                String jsonData = response.body().string();
                if (jsonData != null) {
                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONArray Jarray = Jobject.getJSONArray("list");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        JSONObject main = object.getJSONObject("main");
                        JSONArray weather = object.getJSONArray("weather");
                        String desc = weather.getJSONObject(0).getString("description");
                        Log.i("URL_WITHER" , "desc : "+desc);
                        String icon = weather.getJSONObject(0).getString("icon");
                        String date = object.getString("dt_txt");
                        String temp = main.getString("temp");
                        String temp_min = main.getString("temp_min");
                        String temp_max = main.getString("temp_max");
                        String humidity = main.getString("humidity");
                        JSONObject wind = object.getJSONObject("wind");
                        String windSpeed = wind.getString("speed");

                        //convert weather degree and add to weather list

                        float celsius = ((Float.valueOf(temp) - 273.15f) * 9 / 5) + 32;
                        weathers.add(new WeatherApp
                                (date, temp + "",
                                        temp_min  + "",
                                       temp_max+ "",
                                        icon, desc, humidity, windSpeed));
                 /*       weathers.add(new WeatherApp
                                (date, Math.round(Float.valueOf(temp) - 272.15f) + "",
                                        Math.round(Float.valueOf(temp_min) - 272.15f) + "",
                                        Math.round(Float.valueOf(temp_max) - 272.15f) + "",
                                        icon, desc, humidity, windSpeed));*/
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return weathers;
        }

        @Override
        protected void onPostExecute(List<WeatherApp> weathers) {
            super.onPostExecute(weathers);
            try {
                seeking.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);

                if (showSnackbar) {
                    snackbar = Snackbar
                            .make(refresh, R.string.weather_updated, Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                    View snackView = snackbar.getView();
//                TextView snackViewText = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                  //  Button snackViewButton = (Button) snackView.findViewById(com.google.android.material.R.id.snackbar_action);
                 //   snackViewButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.watermelon));
                    snackbar.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            snackbar.dismiss();
                        }
                    } , 30000);
                }
                List<WeatherApp> todayList = new ArrayList<>();
                List<WeatherApp> allDays = new ArrayList<>();
                String previousDayName = "";
                int min = 0;
                int max = 0;
                String weatherIcon = "";
                for (WeatherApp weather : weathers) {
                    String[] splits = weather.dayName.split(" ");
                    if (NumbersLocal.convertNumberTypeToEnglish(getContext(), getDataNow()).equals(splits[0].trim())) {
                        //degrees of the current day
                        todayList.add(weather);
                    } else {
                        if (firstEntry) {
                            previousDayName = splits[0].trim();
                            firstEntry = false;
                        }
                        //degrees of the week
                        if (splits[0].trim().equals(previousDayName)) {
                            if (weather.dayName.contains("12:00:00")) {

                                max =     Math.round(Float.parseFloat(weather.tempMini));
                              //   Integer.parseInt(weather.tempMini);
                                weatherIcon = weather.image;
                            } else if (weather.dayName.contains("21:00:00")) {
                                min =     Math.round(Float.parseFloat(weather.tempMini));

                                
                            }
                            //check to add day
                            if (min != 0 && max != 0) {
                                weather.tempMax = max + "";
                                weather.tempMini = min + "";
                                allDays.add(new WeatherApp(previousDayName,
                                        min + "",
                                        max + "",
                                        weatherIcon));
                                max = min = 0;
                            }
                        } else {
                            previousDayName = splits[0].trim();
                            max = min = 0;
                        }
                    }
                }
                System.out.println("check");
                //update or add weather of the week
                if (todayList.size() > 0) {
                    weatherList.clear();
                    ConfigPreferences.setTodayListWeather(getActivity(), todayList);
                    weatherList.addAll(todayList);
                    adapter.notifyDataSetChanged();
                    WeatherSave weatherSave = ConfigPreferences.getTodayListWeather(getContext());

                    today.setText(NumbersLocal.convertNumberType(getContext(), weatherSave.weatherApps.get(0).tempMini + "??"));
                    humidity.setText(NumbersLocal.convertNumberType(getContext(), weatherSave.weatherApps.get(0).humidity) + " %");
                    windSpeed.setText(NumbersLocal.convertNumberType(getContext(), weatherSave.weatherApps.get(0).windSpeed + " " + getString(R.string.wind_meager)));
                    todayDescription.setText(weatherSave.weatherApps.get(0).desc);
                    imageToday.setImageResource(WeatherIcon.get_icon_id_white(weatherSave.weatherApps.get(0).image));
                }

                //show and save locally  weather of the week
                if (allDays.size() > 0) {
                    ConfigPreferences.setWeekListWeather(getActivity(), allDays);
                    showDate(allDays, 0, dayA_temp, dayA_Image, dayA);
                    showDate(allDays, 1, dayB_temp, dayB_Image, dayB);
                    showDate(allDays, 2, dayC_temp, dayC_Image, dayC);
                    showDate(allDays, 3, dayD_temp, dayD_image, dayD);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * Function to get date now
     *
     * @return Date in string
     */
    public String getDataNow() {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }


    /**
     * Function to show saved weather
     *
     * @param weathers Saved weathers
     * @param position Position
     * @param temp     Temp text view
     * @param image    Weather view
     * @param day      Day of week text view
     */
    public void showDate(List<WeatherApp> weathers, int position
            , TextView temp, ImageView image, TextView day) {
        try {
            WeatherApp weather = weathers.get(position);
            String[] time = weather.dayName.split(" ");
            String[] date = time[0].split("-");
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            d.setYear(Integer.parseInt(date[0]));
            d.setMonth(Integer.parseInt(date[1]) - 1);
            d.setDate(Integer.parseInt(date[2]) - 1);
            String dayOfTheWeek = sdf.format(d);
            day.setText(dayOfTheWeek);
            image.setImageResource(WeatherIcon.get_icon_id_white(weather.image));
            temp.setText(NumbersLocal.convertNumberType(getContext(), "??" + weather.tempMax + " | " + weather.tempMini + "??"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
