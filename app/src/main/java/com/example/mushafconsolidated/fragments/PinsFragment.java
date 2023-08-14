package com.example.mushafconsolidated.fragments;

import static com.example.Constant.AYAHNUMBER;
import static com.example.Constant.AYAH_ID;
import static com.example.Constant.CHAPTER;
import static com.example.Constant.CHAPTERORPART;
import static com.example.Constant.MUFRADATFRAGTAG;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.example.Constant.SURAH_ID;
import static com.example.Constant.WBW;


import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.List;


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
                snackbar.setAction("UNDO", view -> mRecview.scrollToPosition(position));
                snackbar.setActionTextColor(Color.CYAN);
                snackbar.show();
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
     bookmarksShowAdapter.SetOnItemClickListener((v, position) -> {
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

     });

    }


}









