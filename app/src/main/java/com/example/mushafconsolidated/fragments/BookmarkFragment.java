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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mushafconsolidated.Activity.BookMarkActivity;
import com.example.mushafconsolidated.Activity.QuranGrammarAct;
import com.example.mushafconsolidated.Adapters.BookmarksShowAdapter;
import com.example.mushafconsolidated.Entities.BookMarks;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.utility.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import database.entity.MujarradVerbs;
import mm.prayer.muslimmate.fragments.CalendarFragment;
import mm.prayer.muslimmate.fragments.IslamicEventsFragment;
import mm.prayer.muslimmate.fragments.PrayingFragment;
import mm.prayer.muslimmate.fragments.WeatherFragment;

/**
 * Bookmark fragment class
 */
public class BookmarkFragment extends Fragment  {
    private static final int[] TAB_TITLES = new int[]{ R.string.pins ,R.string.collection};
    CoordinatorLayout coordinatorLayout;
    RecyclerView.LayoutManager layoutManager;
    private BookmarksShowAdapter bookmarksShowAdapter;
    private RecyclerView mRecview;
    private ViewPager2 mViewPager;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mm_activity_main_location, container, false);
        Utils utils = new Utils(getActivity());
        final TabLayout tabLayout = view.findViewById(R.id.tabs);
        final ViewPager2 viewPager =view. findViewById(R.id.container);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ViewStateAdapter mSectionsPagerAdapter = new ViewStateAdapter(fm, getLifecycle());




        //  mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(TAB_TITLES[position])).attach();

        //clickable application title
        TextView applicationTitle = (TextView) view.findViewById(R.id.title);
        applicationTitle.setText(getString(R.string.main));
        applicationTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

OnItemClickListener listener = null;

        //  List<BookMarks> bookmarks = new DatabaseAccess().getBookmarks();
        bookmarksShowAdapter = new BookmarksShowAdapter(getActivity());

        this.layoutManager = new LinearLayoutManager(getActivity());
     //   mRecview.setLayoutManager(layoutManager);

       // bookmarksShowAdapter.setBookMarkArrayList(bookMarksNew);
      //  mRecview.setAdapter(bookmarksShowAdapter);

      //  enableSwipeToDeleteAndUndo();
        return view;
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                //    final int position = viewHolder.getAdapterPosition();
                //  final String item = mAdapter.getData().get(position);
                //   mAdapter.removeItem(position);
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
                        //     bookmarksShowAdapter.restoreItem(item, position);
                        mRecview.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.CYAN);
                snackbar.show();
                final long itemId = bookmarksShowAdapter.getItemId(position);
                final int bookmarkid = bookmarksShowAdapter.getBookmarid();
                bookmarksShowAdapter.getBookChapterno();
                //      bookmarksShowAdapter.getBookMarkArrayList(bookmarkid)
                //  Utils butils = new Utils(getActivity());
                //  butils.deleteBookmarks(bookmarid);
                Utils.deleteBookMarks(item);

            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mRecview);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bookmarksShowAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                BookMarks bmark = (BookMarks) bookmarksShowAdapter.getItem(position);
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

    private static class ViewStateAdapter extends FragmentStateAdapter {
        public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    PinsFragment fragv = new PinsFragment();

                    return fragv;
                case 1:
                    CollectionFrag cfrag = new CollectionFrag();
                    return cfrag;




                default:
                    PrayingFragment fragvv = new PrayingFragment();
                    return fragvv;
            }


        }

        @Override
        public int getItemCount() {

            return 2;

        }

    }
    private void loadFragments(Fragment fragment, String fragtag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.left_slide, android.R.anim.fade_out);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(fragtag);
        transaction.commit();

    }
}
