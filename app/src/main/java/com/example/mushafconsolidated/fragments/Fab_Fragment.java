package com.example.mushafconsolidated.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mushafconsolidated.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nambimobile.widgets.efab.ExpandableFabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fab_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fab_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FloatingActionButton fab1, fab2, fab3, fab;
    private boolean isFABOpen = false;

    public Fab_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Fab_Fragment newInstance() {
        Fab_Fragment fragment = new Fab_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View lay=    inflater.inflate(R.layout.expandablefab, container, false);
        View lay = inflater.inflate(R.layout.second_myexpandablefab, container, false);
  /*      ExpandableFabLayout ex = container.findViewById(R.id.expandable_fab);
        lay.setVerticalScrollbarPosition(1);
        lay.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        lay.setForegroundGravity(View.FOCUS_RIGHT);*/
        fab = (FloatingActionButton) lay.findViewById(R.id.expandfabfrag);
        fab1 = (FloatingActionButton) lay.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) lay.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) lay.findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        return lay;


    }

    private void showFABMenu() {
        isFABOpen = true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.printf("check");
            }
        });
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
    }
}