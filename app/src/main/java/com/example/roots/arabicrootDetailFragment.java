package com.example.roots;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mushafconsolidated.Utils;


import com.example.mushafconsolidated.databinding.FragmentArabicrootDetailBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.mushafconsolidated.R;
import com.example.roots.placeholder.PlaceholderContent;


import java.util.ArrayList;
import java.util.List;

import sj.hisnul.adapter.CatTwoAdapter;
import sj.hisnul.adapter.RootDetailAdapter;
import sj.hisnul.entity.hcategory;

/**
 * A fragment representing a single arabicroot detail screen.
 * This fragment is either contained in a {@link arabicrootListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class arabicrootDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    private PlaceholderContent.PlaceholderItem mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTextView;

    private ArrayList<String> rootsArrayList=new ArrayList<>();
    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = PlaceholderContent.ITEM_MAP.get(clipDataItem.getText().toString());
           rootsArrayList = mItem.rootsArrayList;
        //    updateContent();
        }
        return true;
    };
    private FragmentArabicrootDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public arabicrootDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PlaceholderContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            if(mItem==null){
                rootsArrayList.add("");
            }else{
                rootsArrayList=mItem.details;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arabicroot_details_list, container, false);
    RecyclerView    recyclerView = view.findViewById(R.id.arabicroot_detaillist_rec);
        Utils utils = new Utils(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ArrayList<hcategory> duagrouptwo = Utils.getHcategory();
        RootDetailAdapter adapter = new RootDetailAdapter(rootsArrayList, getContext());
     ;   layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager = new GridLayoutManager(getActivity(), 5);
        ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                  return position == 0 ? 3 : 1;
            }
        });

        recyclerView.setHasFixedSize(true);
        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);




        //   mTextView = binding.arabicrootDetail;

        // Show the placeholder content as text in a TextView & in the toolbar if available.

        view.setOnDragListener(dragListener);


    //    mToolbarLayout = view.findViewById(R.id.toolbar_layout);
//         mTextView = binding.arabicrootDetail;

        // Show the placeholder content as text in a TextView & in the toolbar if available.
    //    updateContent();
      //  view.setOnDragListener(dragListener);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if (mItem != null) {
         mTextView.setText(mItem.content);
        //    mTextView.setText(mItem.rootsArrayList);
            if (mToolbarLayout != null) {
                mToolbarLayout.setTitle(mItem.content);
            }
        }
    }







}

/*

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
 //       ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);
        RecyclerView recyclerView =   view.findViewById(R.id.arabicroot_detaillist_rec);
     //   TextView arabicrootDetail = binding.arabicrootDetailContainer;
        //  recyclerView.setAdapter(sarfsagheerAdapter);
        recyclerView.setHasFixedSize(true);
    

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RootDetailAdapter adapter = new RootDetailAdapter(rootsArrayList, getContext());
      ;
        layoutManager = new GridLayoutManager(getActivity(), 4);


        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);





        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
    //    View itemDetailFragmentContainer = view.findViewById(R.id.arabicroot_detaillist_rec);



     //   setupRecyclerView(recyclerView, itemDetailFragmentContainer);


        binding = FragmentTestDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        mTextView = binding.arabicrootDetail;

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        rootView.setOnDragListener(dragListener);
        return rootView;
        
        
        
    }

 */