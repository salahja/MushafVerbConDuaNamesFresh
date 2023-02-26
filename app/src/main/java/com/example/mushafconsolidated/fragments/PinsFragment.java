package com.example.mushafconsolidated.fragments;

import static com.example.Constant.AYAHNUMBER;
import static com.example.Constant.AYAH_ID;
import static com.example.Constant.CHAPTER;
import static com.example.Constant.CHAPTERORPART;
import static com.example.Constant.MUFRADATFRAGTAG;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.example.Constant.SURAH_ID;
import static com.example.Constant.WBW;
import static mm.itl.prayertime.StandardMethod.EGYPT_SURVEY;
import static mm.itl.prayertime.StandardMethod.KARACHI_HANAF;
import static mm.itl.prayertime.StandardMethod.KARACHI_SHAF;
import static mm.itl.prayertime.StandardMethod.MOROCCO_AWQAF;
import static mm.itl.prayertime.StandardMethod.MUSLIM_LEAGUE;
import static mm.itl.prayertime.StandardMethod.NORTH_AMERICA;
import static mm.itl.prayertime.StandardMethod.UMM_ALQURRA;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Activity.QuranGrammarAct;
import com.example.mushafconsolidated.Adapters.BookmarksShowAdapter;
import com.example.mushafconsolidated.Entities.BookMarks;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.utility.QuranGrammarApplication;
import com.example.utility.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import mm.itl.prayertime.Prayer;
import mm.itl.prayertime.PrayerTime;
import mm.itl.prayertime.StandardMethod;
import mm.itl.prayertime.TimeType;
import mm.prayer.muslimmate.model.Dates;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import mm.prayer.muslimmate.ui.HGDate;
import mm.prayer.muslimmate.ui.LocationInfo;
import mm.prayer.muslimmate.ui.LocationReader;
import mm.prayer.muslimmate.ui.MuslimMatePrayerTimes;
import mm.prayer.muslimmate.ui.NumbersLocal;


/**
 * Created by Dev. M. Hussein on 5/9/2017.
 */

public class PinsFragment extends Fragment  {
    CoordinatorLayout coordinatorLayout;
    RecyclerView.LayoutManager layoutManager;
    private BookmarksShowAdapter bookmarksShowAdapter;
    private RecyclerView mRecview;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //    View rootView = inflater.inflate(R.layout.activity_collection, container, false);
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        Utils utils = new Utils(getActivity());
        List<BookMarks> bookMarksNew = Utils.getAllBookmarks("pins");
        List<BookMarks> bookMarksNews = Utils.getBookMarksNew();

        bookmarksShowAdapter = new BookmarksShowAdapter(getActivity());
        mRecview = view.findViewById(R.id.recyclerViewAdapterTranslation);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayoutbookmark);
        this.layoutManager = new LinearLayoutManager(QuranGrammarApplication.appContext);
        mRecview.setLayoutManager(layoutManager);

        bookmarksShowAdapter.setBookMarkArrayList(bookMarksNew);
        mRecview.setAdapter(bookmarksShowAdapter);

    enableSwipeToDeleteAndUndo();
        return view;

    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final BookMarks item = bookmarksShowAdapter.getBookMarkArrayList().get(position);
                //   final int code = item.hashCode();
                bookmarksShowAdapter.getItemId(position);
                bookmarksShowAdapter.removeItem(position);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mRecview.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.CYAN);
                snackbar.show();
                final long itemId = bookmarksShowAdapter.getItemId(position);
                final int bookmarkid = bookmarksShowAdapter.getBookmarid();
                bookmarksShowAdapter.getBookChapterno();
          //    Utils.deleteBookMarks(item);
            Utils.deleteBookmark(item);

            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mRecview);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     bookmarksShowAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                BookMarks bmark = (BookMarks) bookmarksShowAdapter.getItem(position);

                Bundle dataBundle = new Bundle();
                dataBundle.putInt(SURAH_ID, Integer.parseInt(bmark.getChapterno()));
                dataBundle.putInt(AYAHNUMBER, Integer.parseInt(bmark.getVerseno()));
                dataBundle.putString(SURAH_ARABIC_NAME, bmark.getSurahname());


                Intent readingintent = new Intent(getActivity(), QuranGrammarAct.class);
                readingintent.putExtra(MUFRADATFRAGTAG, false);
                readingintent.putExtra(CHAPTER, Integer.parseInt(bmark.getChapterno()));
                readingintent.putExtra(AYAH_ID, Integer.parseInt(bmark.getVerseno()));
                readingintent.putExtra(CHAPTERORPART, true);
                readingintent.putExtra(SURAH_ARABIC_NAME, bmark.getSurahname());
                readingintent.putExtra(WBW, true);
                startActivity(readingintent);

            }
        });

    }


}









