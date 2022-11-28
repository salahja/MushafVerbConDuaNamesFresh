package com.example.mushafconsolidated.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mushafconsolidated.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FabMenuActivity extends BaseActivity {

    FloatingActionButton mAddbookmarkfab, mAddTafsirfab, mAddSharefab;
    ExtendedFloatingActionButton mAddFab;
    TextView addAlarmActionText, addPersonActionText;    // to check whether sub FABs are visible or not
    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myexpandablefab);
        mAddFab = findViewById(R.id.mAddFab);
        mAddbookmarkfab = findViewById(R.id.bookmark);
        mAddTafsirfab = findViewById(R.id.tafsir);

        ;        // Now set all the FABs and all the action name
        // texts as GONE
        mAddbookmarkfab.setVisibility(View.GONE);
        mAddTafsirfab.setVisibility(View.GONE);
   //     mAddSharefab.setVisibility(View.GONE);



        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are
        // invisible
        isAllFabsVisible = false;        // Set the Extended floating action button to
        // shrinked state initially
     //   mAddFab.shrink();        // We will make all the FABs and action name texts
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs VISIBLE.
                            mAddbookmarkfab.show();
                            mAddTafsirfab.show();
                       //     mAddSharefab.show();
                            // parent FAB
                            mAddFab.extend();                            // make the boolean variable true as
                            // we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = true;
                        } else {                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs GONE.
                            mAddbookmarkfab.hide();
                            mAddTafsirfab.hide();
                                               // Set the FAB to shrink after user
                            // closes all the sub FABs
                            mAddFab.shrink();                            // make the boolean variable false
                            // as we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = false;
                        }
                    }
                });        // below is the sample action to handle add person
        // FAB. Here it shows simple Toast msg. The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        mAddTafsirfab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText
                                (FabMenuActivity
                                                .this, "Person Added",
                                        Toast.LENGTH_SHORT).show();
                    }
                });        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        mAddbookmarkfab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText

                                (FabMenuActivity
                                                .this, "Alarm Added",
                                        Toast.LENGTH_SHORT).show();
                    }

                });

    }
}