package org.sj.conjugator.fragments;

import static com.example.Constant.QURAN_VERB_ROOT;
import static com.example.Constant.QURAN_VERB_WAZAN;
import static com.example.Constant.VERBMOOD;
import static com.example.Constant.VERBTYPE;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.VerbFormsDialogFrag;
import com.example.mushafconsolidated.fragments.VerbconjuationBottom;
import com.example.mushafconsolidated.fragments.WordAnalysisBottomSheet;
import com.tooltip.Tooltip;


import org.jetbrains.annotations.NotNull;
import org.sj.conjugator.adapter.SarfMujarradSarfSagheerListingAdapter;
import org.sj.conjugator.interfaces.OnItemClickListener;
import org.sj.conjugator.utilities.GatherAll;

import java.util.ArrayList;
import java.util.List;


public class MazeedTabSagheerFragmentVerb extends Fragment {
    private static final int WRITE_REQUEST_CODE = 101;
    private static final String TAG = "PermissionDemo";
    private final Context context;
    // --Commented out by Inspection (31/1/21 5:51 AM):ArrayList<String> sarfkabeer = new ArrayList<>();
    RecyclerView recyclerView;
    boolean isAugmented, isUnAugmented;
    private String verbweakness;
    private SarfMujarradSarfSagheerListingAdapter sarfsagheerAdapter;
    // ArrayList sarfSagheerThulathiArray = new ArrayList();
    private ArrayList sarfSagheerThulathiArray;
    private String augmentedFormula;
    private String unaugmentedFormula;
    private String verbroot, verbmood;
    private ArrayList<ArrayList> skabeer = new ArrayList<>();

    ArrayList<SarfSagheer> ssagheer;
    private Bundle dataBundle;

    public MazeedTabSagheerFragmentVerb(Context context) {
        this.context = context;

    }

    public MazeedTabSagheerFragmentVerb newInstance() {
        MazeedTabSagheerFragmentVerb f = new MazeedTabSagheerFragmentVerb(context);
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
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.sarfkabeerheader, container, false);
         dataBundle = getArguments();
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
        ArrayList sarfSagheerMazeedArray = new ArrayList();
        ArrayList<ArrayList> listing = GatherAll.getInstance().getMazeedListing(verbmood, verbroot, augmentedFormula);
        SarfSagheer ss = new SarfSagheer();
        ss.setWeakness(listing.get(0).get(0).toString());
        ss.setWazanname(listing.get(0).get(1).toString());
        ss.setVerbroot(listing.get(0).get(2).toString());
        ss.setMadhi(listing.get(0).get(3).toString());
        ss.setMadhimajhool(listing.get(0).get(4).toString());
        ss.setMudharay(listing.get(0).get(5).toString());
        ss.setMudharaymajhool(listing.get(0).get(6).toString());
        ss.setAmrone(listing.get(0).get(7).toString());
        ss.setNahiamrone(listing.get(0).get(8).toString());
        ss.setIsmfael(listing.get(0).get(9).toString());
        ss.setIsmmafool(listing.get(0).get(10).toString());
        ss.setIsmalaone(listing.get(0).get(11).toString());
        ss.setIsmalatwo(listing.get(0).get(12).toString());
        ss.setIsmalathree(listing.get(0).get(13).toString());
        ss.setZarfone(listing.get(0).get(14).toString());
        ss.setZarftwo(listing.get(0).get(15).toString());
        ss.setZarfthree(listing.get(0).get(16).toString());
        ss.setVerbtype(listing.get(0).get(17).toString());
        ss.setWazan(listing.get(0).get(18).toString());
        ssagheer = new ArrayList<>();
        ssagheer.add(ss);
        sarfsagheerAdapter = new SarfMujarradSarfSagheerListingAdapter(ssagheer, getActivity());
        recyclerView.setAdapter(sarfsagheerAdapter);

    }

    private void ninitThulathiAdapter() {
        int babno = 0;
        //  OldSarfSagheer(babno);
        ArrayList<ArrayList> listing = GatherAll.getInstance().getMujarradListing(verbmood, verbroot, unaugmentedFormula);
        //InsertSarfSagheerThulathi(ANAQISYAYI);
        SarfSagheer ss = new SarfSagheer();
        ss.setWeakness(listing.get(0).get(0).toString());
        ss.setWazanname(listing.get(0).get(1).toString());
        ss.setVerbroot(listing.get(0).get(2).toString());
        ss.setMadhi(listing.get(0).get(3).toString());
        ss.setMadhimajhool(listing.get(0).get(4).toString());
        ss.setMudharay(listing.get(0).get(5).toString());
        ss.setMudharaymajhool(listing.get(0).get(6).toString());
        ss.setAmrone(listing.get(0).get(7).toString());
        ss.setNahiamrone(listing.get(0).get(8).toString());
        ss.setIsmfael(listing.get(0).get(9).toString());
        ss.setIsmmafool(listing.get(0).get(10).toString());
        ss.setIsmalaone(listing.get(0).get(11).toString());
        ss.setIsmalatwo(listing.get(0).get(12).toString());
        ss.setIsmalathree(listing.get(0).get(13).toString());
        ss.setZarfone(listing.get(0).get(14).toString());
        ss.setZarftwo(listing.get(0).get(15).toString());
        ss.setZarfthree(listing.get(0).get(16).toString());
        ss.setVerbtype(listing.get(0).get(17).toString());
        ss.setWazan(listing.get(0).get(18).toString());
        ArrayList<SarfSagheer> ssagheer = new ArrayList<>();
        ssagheer.add(ss);
        sarfsagheerAdapter = new SarfMujarradSarfSagheerListingAdapter(ssagheer, getActivity());
        recyclerView.setAdapter(sarfsagheerAdapter);

    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.sarfrecview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        sarfsagheerAdapter.SetOnItemClickListener(new OnItemClickListener() {
            private static final String WORDNUMBER = "wordnumber";

            @Override
            public void onItemClick(View v, int position) {
                SarfSagheer wordEntity = ssagheer.get(position);
                if(v.getTag()!=null) {
                    String form = "Form ";
                    if (v.getTag().equals("form")) {
                        if(wordEntity.getWazan().equals("2")){
                            form=form.concat("II");

                        }    else if(wordEntity.getWazan().equals("3")) {
                            form = form.concat("III");
                        }else if(wordEntity.getWazan().equals("1")) {
                            form = form.concat("IV");


                        }else if(wordEntity.getWazan().equals("7")){
                            form=form.concat("V");
                        }else if(wordEntity.getWazan().equals("8")){
                            form=form.concat("VI");
                        }else if(wordEntity.getWazan().equals("4")){
                            form=form.concat("VII");
                        }else if(wordEntity.getWazan().equals("5")){
                            form=form.concat("VIII");
                        }




                        else if(wordEntity.getWazan().equals("9")){
                            form=form.concat("X");
                        }





                        VerbFormsDialogFrag item = new VerbFormsDialogFrag();
                            //    item.setdata(rootWordMeanings,wbwRootwords,grammarRootsCombined);
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                            String[] data = {form};
                            FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);
                            transactions.show(item);
                            VerbFormsDialogFrag.newInstance(data).show(getActivity().getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);









                    }
                }
                //      GrammarWordEntity wordEntity = (GrammarWordEntity) sarfsagheerAdapter.getItem(position);
                Bundle dataBundles = new Bundle();
                String wazan = wordEntity.getWazan();
                String weakness=                     wordEntity.getWeakness();
                String root=                wordEntity.getVerbroot();

                if (isAugmented) {
                  /*  isUnAugmented = true;
                    augmentedFormula = dataBundle.getString(QURAN_VERB_WAZAN);
                    dataBundle.putString(VERBTYPE, "mujarrad");
                    int color = context.getResources().getColor(R.color.background_color_light_brown);
                    final ArrayList<ArrayList> indictive = GatherAll.getInstance().getMazeedListing(verbmood, root, augmentedFormula);
                    VerbconjuationBottom frag=new VerbconjuationBottom();
                    Bundle bundle=new Bundle();
                    ArrayList list = indictive.get(1);
                ;;
                    bundle.putParcelableArrayList("list",list);
                    frag.setArguments(bundle);

                    frag.newInstance(list).show(((AppCompatActivity) context).getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);
*/





            } else {

                    /*unaugmentedFormula = dataBundle.getString(QURAN_VERB_WAZAN);
                    dataBundle.putString(VERBTYPE, "mazeed");
                    isAugmented = true;
                    final ArrayList<ArrayList> lists = GatherAll.getInstance().getMujarradListing(verbmood, root, unaugmentedFormula);
                    VerbconjuationBottom frag=new VerbconjuationBottom();
                    Bundle bundle=new Bundle();
                    ArrayList list = lists.get(1);
                    ;;
                    bundle.putParcelableArrayList("list",list);
                    frag.setArguments(bundle);

                    frag.newInstance(list).show(((AppCompatActivity) context).getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);*/
                }


         /*       int word_no = wordEntity.getWord_no();
                Bundle dataBundle = new Bundle();
                dataBundle.putString(SURAH_ID, surah_id);
                dataBundle.putInt(AYAHNUMBER, ayah_number);
                dataBundle.putInt(WORDNUMBER, word_no);
                dataBundle.putString(SURAH_ARABIC_NAME, suraharabicname);
                RootDialog rootDialog = new RootDialog();
                FragmentManager fragmentManager = getFragmentManager();
                rootDialog.setArguments(dataBundle);
                assert fragmentManager != null;
                fragmentManager.beginTransaction().add(R.id.fragmentParentViewGroup, rootDialog).addToBackStack(ROOTDIALOGFRAG).commit();
*/

            }
        });



    }

}
