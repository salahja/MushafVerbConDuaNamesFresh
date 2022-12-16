package com.example.mushafconsolidated;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

public class q_SearchView extends LinearLayout {


    private  View open_search_button;
    private  View close_search_button;
    private   TextView search_input_text;
    private LinearLayout search_open_view;
   private ImageView searchImf;
   private EditText etSearch;
   MaterialButton btnSelectBookText;
   CheckBox cbPartialMatch;

    public q_SearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        LayoutInflater.from(context)
                .inflate(R.layout.q_search_view, this, true);
        searchImf =getRootView().findViewById(R.id.ibApply);
        etSearch=getRootView().findViewById(R.id.etSearch);
        btnSelectBookText=getRootView().findViewById(R.id.btnSelectBookText);
        cbPartialMatch=getRootView().findViewById(R.id.cbPartialMatch);
        close_search_button=getRootView().findViewById(R.id.llSearchBar);
        search_open_view=getRootView().findViewById(R.id.search_open_view);
    /*    open_search_button = getRootView().findViewById(R.id.open_search_button);
        close_search_button = getRootView().findViewById(R.id.close_search_button);
        search_input_text=getRootView().findViewById(R.id.search_input_text);
        search_open_view=getRootView().findViewById(R.id.search_open_view);*/
        searchImf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch();
            }
        });
        close_search_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSearch();
            }
        });


    }


    private void closeSearch() {


        int cx = search_open_view.getWidth() / 2;
        int cy = search_open_view.getHeight() / 2;

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(search_open_view
                , cx, cy, initialRadius, 0f);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                search_open_view.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
       /* Animator circularConceal = ViewAnimationUtils.createCircularReveal(
                search_open_view,
                (open_search_button.getRight() + open_search_button.getLeft()) / 2,
                (open_search_button.getTop() + open_search_button.getBottom()) / 2,
                0f, open_search_button.getWidth() );
        circularConceal.setDuration(300)  ;
        circularConceal.start();
        circularConceal.addListener(new Animator.AnimatorListener() {

         //   override fun onAnimationRepeat(animation: Animator?) = Unit
         //   override fun onAnimationCancel(animation: Animator?) = Unit
         //   override fun onAnimationStart(animation: Animator?) = Unit
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                search_open_view.setVisibility(INVISIBLE);
                search_input_text.setText("");

                circularConceal.removeAllListeners();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Animator Unit;
            //    Animator animator=Unit;

            }
        });*/


    }

    private void openSearch() {
        etSearch.setText("");

        searchImf.setVisibility(VISIBLE);
        etSearch.setVisibility(VISIBLE);
        btnSelectBookText.setVisibility(VISIBLE);
        cbPartialMatch.setVisibility(VISIBLE);
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                search_open_view,
                (open_search_button.getRight() + open_search_button.getLeft()) / 2,
                (open_search_button.getTop() + open_search_button.getBottom()) / 2,
                0f, open_search_button.getWidth() );
        circularReveal.setDuration(300)  ;
        circularReveal.start();
    }

}
