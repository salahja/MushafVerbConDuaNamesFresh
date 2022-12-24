package com.example.mushafconsolidated.fragments;

import static com.example.Constant.AYAHNUMBER;
import static com.example.Constant.AYAH_ID;
import static com.example.Constant.CHAPTER;
import static com.example.Constant.CHAPTERORPART;
import static com.example.Constant.MUFRADATFRAGTAG;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.example.Constant.SURAH_ID;
import static com.example.Constant.WBW;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Activity.QuranGrammarAct;
import com.example.mushafconsolidated.Adapters.BookmarksShowAdapter;
import com.example.mushafconsolidated.Adapters.CollectionShowAdapter;
import com.example.mushafconsolidated.Entities.BookMarks;
import com.example.mushafconsolidated.Entities.BookMarksPojo;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.utility.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;

import mm.itl.prayertime.Prayer;
import mm.itl.prayertime.StandardMethod;
import mm.prayer.muslimmate.ui.LocationInfo;
import mm.prayer.muslimmate.ui.LocationReader;
import mm.prayer.muslimmate.ui.MuslimMatePrayerTimes;


/**
 * Created by Dev. M. Hussein on 5/9/2017.
 */

public class CollectionFrag extends Fragment {
    CoordinatorLayout coordinatorLayout;
    RecyclerView.LayoutManager layoutManager;
    private CollectionShowAdapter collectionShowAdapter;
    private RecyclerView mRecview;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //    View rootView = inflater.inflate(R.layout.activity_collection, container, false);
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        Utils utils = new Utils(getActivity());
        List<BookMarksPojo> bookMarksNew = utils.getCollectionC();

        //  List<BookMarks> bookmarks = new DatabaseAccess().getBookmarks();
        collectionShowAdapter = new CollectionShowAdapter(getActivity());
        mRecview = view.findViewById(R.id.recyclerViewAdapterTranslation);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        this.layoutManager = new LinearLayoutManager(getActivity());
        mRecview.setLayoutManager(layoutManager);
        //    bookmarksShowAdapter.setBookMarkArrayList((ArrayList<String>) bookmarstringarray);
        collectionShowAdapter.setBookMarkArrayList(bookMarksNew);
        mRecview.setAdapter(collectionShowAdapter);
        //    mRecview.setLayoutManager(new LinearLayoutManager(getActivity()));
    enableSwipeToDeleteAndUndo();
        return view;
      //  return rootView;
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                //    final int position = viewHolder.getAdapterPosition();
                //  final String item = mAdapter.getData().get(position);
                //   mAdapter.removeItem(position);
                final int position = viewHolder.getAdapterPosition();
                final BookMarksPojo item = collectionShowAdapter.getBookMarkArrayList().get(position);
                //   final int code = item.hashCode();
                collectionShowAdapter.getItemId(position);
                collectionShowAdapter.removeItem(position);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //     bookmarksShowAdapter.restoreItem(item, position);
                        mRecview.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.CYAN);
                snackbar.show();
                final long itemId = collectionShowAdapter.getItemId(position);
                final int bookmarkid = collectionShowAdapter.getBookmarid();
                collectionShowAdapter.getBookChapterno();
                //      bookmarksShowAdapter.getBookMarkArrayList(bookmarkid)
                //  Utils butils = new Utils(getActivity());
                //  butils.deleteBookmarks(bookmarid);
            //    Utils.deleteBookMarks(item);

            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mRecview);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        collectionShowAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                BookMarksPojo bmark = (BookMarksPojo) collectionShowAdapter.getItem(position);
                //        ChaptersAnaEntity surah = (ChaptersAnaEntity) bookmarksShowAdapter.getItem(position);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt(SURAH_ID, Integer.parseInt(bmark.getChapterno()));
                dataBundle.putInt(AYAHNUMBER, Integer.parseInt(bmark.getVerseno()));
                dataBundle.putString(SURAH_ARABIC_NAME, bmark.getSurahname());
//                dataBundle.putInt(VERSESCOUNT,bmark.getVersescount());
                //VersesFragment frag = new VersesFragment();
                //   frag.setArguments(dataBundle);
                String header = bmark.getHeader();
                Fragment fragment;
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









