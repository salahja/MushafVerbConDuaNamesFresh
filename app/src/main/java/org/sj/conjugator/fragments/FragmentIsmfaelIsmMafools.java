package org.sj.conjugator.fragments;

import static com.example.Constant.MUJARRADVERBTAG;
import static com.example.Constant.QURAN_VERB_ROOT;
import static com.example.Constant.QURAN_VERB_WAZAN;
import static com.example.Constant.VERBMOOD;
import static com.example.Constant.VERBTYPE;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import org.sj.conjugator.adapter.IsmFaelIsmMafoolSarfKabeerAdapter;
import org.sj.conjugator.adapter.MazeedFihiSagheerListingadapter;
import org.sj.conjugator.utilities.GatherAll;
import org.sj.nounConjugation.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator;
import org.sj.nounConjugation.trilateral.unaugmented.UnaugmentedTrilateralPassiveParticipleConjugator;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.passiveparticiple.PassiveParticipleModifier;
import org.sj.verbConjugation.trilateral.unaugmented.UnaugmentedTrilateralRoot;
import org.sj.verbConjugation.util.KovRulesManager;
import org.sj.verbConjugation.util.SarfDictionary;
import org.sj.verbConjugation.util.TrilateralKovRule;

import java.util.ArrayList;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class FragmentIsmfaelIsmMafools extends Fragment {

    private static final String TAG = "PermissionDemo";

    RecyclerView recyclerView;

    boolean regularverb;
    boolean isAugmented, isUnAugmented;

    private ArrayList<ArrayList> skabeer = new ArrayList<>();

    private String augmentedFormula;
    private String unaugmentedFormula;
    private String verbroot;
    private String verbmood;

    public FragmentIsmfaelIsmMafools newInstance() {
        FragmentIsmfaelIsmMafools f = new FragmentIsmfaelIsmMafools();
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

    public void setRegularverb(boolean regularverb) {
        this.regularverb = regularverb;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
 ;
                fm.popBackStack();

            }
        });

        assert dataBundle != null;
        if (dataBundle.getString(VERBTYPE).equals("mujarrad")) {
            isUnAugmented = true;
            unaugmentedFormula = dataBundle.getString(QURAN_VERB_WAZAN);
        } else {
            augmentedFormula = dataBundle.getString(QURAN_VERB_WAZAN);
            isAugmented = true;
        }
        verbroot = dataBundle.getString(QURAN_VERB_ROOT);
        verbmood = dataBundle.getString(VERBMOOD);
        recyclerView = view.findViewById(R.id.sarfrecview);
        skabeer = setUparrays(view);

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

        ArrayList<ArrayList> arrayLists = GatherAll.getInstance().buildAugmenteParticiples(verbroot, augmentedFormula);
        if (!arrayLists.isEmpty()) {
            IsmFaelIsmMafoolSarfKabeerAdapter ska = new IsmFaelIsmMafoolSarfKabeerAdapter(arrayLists, getContext(), false);
            recyclerView.setAdapter(ska);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

    }

    private void ninitThulathiAdapter() {

        ArrayList<ArrayList> mujarradListing = GatherAll.getInstance().getMujarradParticiple(verbroot, unaugmentedFormula);
        if (!mujarradListing.isEmpty()) {
            boolean newsarf = true;
            IsmFaelIsmMafoolSarfKabeerAdapter ska = new IsmFaelIsmMafoolSarfKabeerAdapter(mujarradListing, getContext(), newsarf);

            recyclerView.setAdapter(ska);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.sarfrecview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ImageView ref;


    }

}
