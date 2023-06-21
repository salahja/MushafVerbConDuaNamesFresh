package com.example.roots;

import static com.example.Constant.QURAN_VERB_ROOT;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.roots.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class RootBreakFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String root;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RootBreakFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RootBreakFragment newInstance(String rootword) {
        RootBreakFragment fragment = new RootBreakFragment();
        Bundle args = new Bundle();
        args.putString(QURAN_VERB_ROOT,rootword);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            root = getArguments().getString(QURAN_VERB_ROOT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_breakup, container, false);
        Utils utils=new Utils();
        utils.getRootDictionary(root);

        RecyclerView recyclerView =          view.findViewById(R.id.rootbreakup);
        // Set the adapter

        Context context = view.getContext();


        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
     //   recyclerView.setAdapter(new MyRootBreakRecyclerViewAdapter( utils.getQuranDictionarybyroot(root)));








        return view;
    }
}