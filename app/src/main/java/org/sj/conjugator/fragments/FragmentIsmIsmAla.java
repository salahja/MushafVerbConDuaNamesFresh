package org.sj.conjugator.fragments;

import static com.example.Constant.MUJARRADVERBTAG;
import static com.example.Constant.QURAN_VERB_ROOT;
import static com.example.Constant.QURAN_VERB_WAZAN;
import static com.example.Constant.VERBMOOD;
import static com.example.Constant.VERBTYPE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.R;

import org.jetbrains.annotations.NotNull;

import org.sj.conjugator.adapter.IsmAlaSarfKabeerAdapter;
import org.sj.conjugator.utilities.GatherAll;

import java.util.ArrayList;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class FragmentIsmIsmAla extends Fragment {

    private static final String TAG = "PermissionDemo";


    boolean isAugmented, isUnAugmented;

    RecyclerView recyclerView;

    private String unaugmentedFormula;
    private String verbroot;

    private ArrayList<ArrayList> skabeer = new ArrayList<>();



    public FragmentIsmIsmAla newInstance() {
        FragmentIsmIsmAla f = new FragmentIsmIsmAla();
        Bundle dataBundle = getArguments();
        assert dataBundle != null;
        if (null != dataBundle) {
            dataBundle.getString(QURAN_VERB_ROOT);
            dataBundle.getString(QURAN_VERB_WAZAN);//verb formula depnding upon the verbtype mujjarad or mazeed
            dataBundle.getString(VERBMOOD);
            dataBundle.getString(VERBTYPE);

        }
        f.setArguments(dataBundle);
        return f;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(TAG, "ONCREATE OPTION MENU verse ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.sarfkabeerheader, container, false);
        FloatingTextButton callButton = view.findViewById(R.id.action_buttons);
        Bundle dataBundle = getArguments();
        if (dataBundle != null) {
            String callingfragment = dataBundle.getString(MUJARRADVERBTAG);
            if (callingfragment != null) {
                if (callingfragment.equals("tverblist")) {
                    callButton.setVisibility(View.VISIBLE);
                } else {
                    callButton.setVisibility(View.GONE);
                }
            } else {
                callButton.setVisibility(View.GONE);
            }
        }
   
        if (dataBundle.getString(VERBTYPE).equals("mujarrad")) {
            isUnAugmented = true;
            unaugmentedFormula = dataBundle.getString(QURAN_VERB_WAZAN);
        } else {
            String augmentedFormula = dataBundle.getString(QURAN_VERB_WAZAN);
            isAugmented = true;
        }
        verbroot = dataBundle.getString(QURAN_VERB_ROOT);
        recyclerView = view.findViewById(R.id.sarfrecview);
        skabeer = setUparrays(view);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();

                fm.popBackStack();

            }
        });
        return view;
    }

    @NotNull
    private ArrayList<ArrayList> setUparrays(View view) {
        if (isUnAugmented) {
            ninitThulathiAdapter();

        } else {

            initMazeedAdapterNew();
        }
        recyclerView = view.findViewById(R.id.sarfrecview);

        return skabeer;
    }

    private void initMazeedAdapterNew() {
    }

    private void ninitThulathiAdapter() {
        //   OldThulathi();
        ArrayList<ArrayList> mujarradListing = GatherAll.getInstance().getMujarradIsmAla(verbroot, unaugmentedFormula);
        if (!mujarradListing.isEmpty()) {
            IsmAlaSarfKabeerAdapter ska = new IsmAlaSarfKabeerAdapter(mujarradListing, getContext());

            recyclerView.setAdapter(ska);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

    }

}
