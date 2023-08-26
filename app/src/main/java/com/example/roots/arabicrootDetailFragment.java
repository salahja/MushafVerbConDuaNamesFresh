package com.example.roots;

import static com.example.Constant.QURAN_VERB_ROOT;
import static com.example.Constant.WORDDETAILS;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.mushafconsolidated.R;
import com.example.roots.placeholder.PlaceholderContent;


import java.util.ArrayList;

/**
 * A fragment representing a single arabicroot detail screen.
 * This fragment is either contained in a {@link arabicrootListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class arabicrootDetailFragment extends Fragment implements AdapterView.OnItemClickListener{

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
            assert mItem != null;
            rootsArrayList = mItem.rootsArrayList;
        //    updateContent();
        }
        return true;
    };
    private RootDetailAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public arabicrootDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

         adapter = new RootDetailAdapter(rootsArrayList, getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager = new GridLayoutManager(getActivity(), 4);
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


      //  view.setOnDragListener(dragListener);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        com.example.mushafconsolidated.databinding.FragmentArabicrootDetailBinding binding = null;
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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
              String bmark = (String) adapter.getItem(position);
                //        ChaptersAnaEntity surah = (ChaptersAnaEntity) bookmarksShowAdapter.getItem(position);
                Bundle dataBundle = new Bundle();

                dataBundle.putString("root", bmark);
                Bundle bundle = new Bundle();
                assert getArguments() != null;
                if(getArguments().containsKey(WORDDETAILS)){
                     bundle.putString(WORDDETAILS,getArguments().getString(WORDDETAILS));
                 }
                Intent intent = new Intent(getActivity(), RootBreakupAct.class);

                bundle.putString(QURAN_VERB_ROOT, bmark);
                intent.putExtras(bundle);

                startActivity(intent);


            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.printf("check");

    }
}




