package com.example.mushafconsolidated.Activity;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.mushafconsolidated.R;
import com.ib.qiblafinder.QiblaDegreeListener;
import com.ib.qiblafinder.view.QiblaCompassView;

public class QiblaActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QiblaCompassView qiblaCompassView = findViewById(R.id.qiblaCompassView);
      //  qiblaCompassView.setDegree(17.37528f);


      //     qiblaCompassView.degree = 17.37528f;  // Angle from your location to Qibla
//        Location location = qiblaCompassView.getLocation();
    //    qiblaCompassView.location =       // Get your latest location and provide to QiblaCompassView for accurate direction
       qiblaCompassView.setDegreeListener(new QiblaDegreeListener() {
           @Override
           public void onDegreeChange(float v) {

           }
       });

    }
}
