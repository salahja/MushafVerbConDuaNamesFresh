package com.example.mushafconsolidated.fragments;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Entities.BookMarks;
import com.example.mushafconsolidated.Entities.BookMarksPojo;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.utility.QuranGrammarApplication;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import sj.hisnul.adapter.BookmarCrateAdapter;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ThemeListPrefrence.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class BookMarkCreateFrag extends BottomSheetDialogFragment  {
    public static final String TAG = "opton";
    private    RecyclerView recyclerView;


    public BookMarkCreateFrag() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    // TODO: Customize parameter argument names
    private static final String ARG_OPTIONS_DATA = "item_count";
    OnItemClickListener mItemClickListener;
    RadioGroup radioGroup;
    private BookmarCrateAdapter bookmarCrateAdapter;
    RelativeLayout frameLayout;
    // TODO: Customize parameters
    public static BookMarkCreateFrag newInstance() {
        final BookMarkCreateFrag fragment = new BookMarkCreateFrag();
;
        return fragment;

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        

        return inflater.inflate(R.layout.quran_list_dialog, container, false);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<String> details = new ArrayList<>();
        Utils utils=new Utils(getActivity());
        ArrayList<BookMarks> bookMarks = utils.getBookMarks();
        List<BookMarksPojo> collectionC = utils.getCollectionC();
        BookmarCrateAdapter      bookmarCrateAdapter=    new BookmarCrateAdapter(bookMarks,collectionC);


        recyclerView.setAdapter(bookmarCrateAdapter);
        bookmarCrateAdapter.SetOnItemClickListener(new BookmarCrateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getActivity(), "create collections", Toast.LENGTH_SHORT).show();

            }
        });


    }






}