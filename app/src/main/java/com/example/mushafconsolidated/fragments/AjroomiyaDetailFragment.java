package com.example.mushafconsolidated.fragments;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.example.mushafconsolidated.Activity.placeholder.AjroomiyaRulecontents;
import com.example.mushafconsolidated.Entities.GrammarRules;
import com.example.mushafconsolidated.R;


import com.example.mushafconsolidated.databinding.FragmentGrammarruleDetailBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * A fragment representing a single GrammarRule detail screen.
 * This fragment is either contained in a {@link AjroomiyaListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class AjroomiyaDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    /**
     * The placeholder content this fragment is presenting.
     */
    private GrammarRules mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private WebView mTextView;
    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = AjroomiyaRulecontents.ITEM_MAP.get(clipDataItem.getText().toString());
            updateContent();
        }
        return true;
    };
    private FragmentGrammarruleDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AjroomiyaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = AjroomiyaRulecontents.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grammarrule_detail, container, false);
     //   binding = FragmentGrammarruleDetailBinding.inflate(inflater, container, false);
      //  View rootView = binding.getRoot();
        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);

        mTextView = rootView.findViewById(R.id.grammarrule_detail);
        WebSettings webSettings = mTextView.getSettings();
        webSettings.setJavaScriptEnabled(true);
      //  mTextView.setInitialScale(1);
     //   mTextView.getSettings().setLoadWithOverviewMode(true);
    //    mTextView.getSettings().setUseWideViewPort(true);

  //      mTextView.getSettings().setBuiltInZoomControls(true);
        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        rootView.setOnDragListener(dragListener);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if (mItem != null) {

            mTextView.loadDataWithBaseURL(null, mItem.getDetailsrules(), "text/html", "utf-8", null);
            //   mTextView.setText(mItem.getDetailsrules());
            if (mToolbarLayout != null) {
                mToolbarLayout.setTitle(mItem.getWorddetails());
            }
        }
    }
}