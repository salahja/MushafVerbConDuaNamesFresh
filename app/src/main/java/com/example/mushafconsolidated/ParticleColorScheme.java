package com.example.mushafconsolidated;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.utility.CorpusUtilityorig;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     FontQuranListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ParticleColorScheme extends BottomSheetDialogFragment {
    // TODO: Customize parameter argument names
    private static final String ARG_OPTIONS_DATA = "item_count";
    OnItemClickListener mItemClickListener;
    TextView textView;
    private ColorSchemeAdapter colorSchemeAdapter;

    // TODO: Customize parameters
    public static ParticleColorScheme newInstance() {
        final ParticleColorScheme fragment = new ParticleColorScheme();
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
        return inflater.inflate(R.layout.colorschemelayout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //  recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        // parentRecyclerView = view.findViewById(R.id.juzRecyclerView);
        RecyclerView recyclerView = view.findViewById(R.id.colorrecview);
        recyclerView.setLayoutManager(mLayoutManager);
        Map<String, ForegroundColorSpan> spanhash;
        spanhash = CorpusUtilityorig.getStringForegroundColorSpanMap();
        textView = view.findViewById(R.id.Colortv);
        ArrayList<String> particle = new ArrayList();
        particle.add("PN = \"Proper Noun(?????? ??????)\",");
        particle.add("ADJ = \"Adjective(??????)");
        particle.add("V =Verb(??????)");
        particle.add("N =Noun");
        particle.add(" PRON = Pronouns(????????)");
        particle.add("DEM = Demonstrative Pronoun(?????? ??????????)");
        particle.add(" REL =  Relative Pronoun(?????? ??????????)");
        particle.add("T =  Time Adverb(?????? ????????)");
        particle.add("  LOC =  Location Adverb(?????? ????????)");
        particle.add("DET  determiner()");
        particle.add("EMPH  Emphatic l??m prefix(?????? ??????????????) ");
        particle.add("IMPV  Imperative l??mprefix(?????? ??????????)");
        particle.add("PRP  Purpose l??mprefix(?????? ??????????????)");
        particle.add("CONJ  Coordinating conjunction(?????? ??????)");
        particle.add("SUB  	Subordinating conjunction(?????? ??????????)");
        particle.add("ACC  	Accusative particle(?????? ??????)");
        particle.add("AMD  	Amendment particle(?????? ??????????????)	");
        particle.add("ANS  	Answer particle	(?????? ????????)");
        particle.add("AVR  	Aversion particle	(?????? ??????)");
        particle.add("CAUS  Particle of cause	(?????? ??????????)");
        particle.add("CERT  Particle of certainty	(?????? ??????????)");
        particle.add("CIRC  Circumstantial particle	(?????? ??????)");
        particle.add("COM  	Comitative particle	(?????? ????????????)");
        particle.add("COND  Conditional particle(?????? ??????)");
        particle.add("EQ  	Equalization particle(?????? ??????????)");
        particle.add("EXH  	Exhortation particle(?????? ??????????)");
        particle.add("EXL  	Explanation particle(?????? ??????????)");
        particle.add("EXP  	Exceptive particle	(???????? ??????????????)");
        particle.add("FUT  	Future particle	(?????? ??????????????)");
        particle.add("INC  	Inceptive particle	(?????? ????????????)");
        particle.add("INT  	Particle of interpretation(?????? ??????????)");
        particle.add("INTG  Interogative particle	(?????? ??????????????)");
        particle.add("NEG  	Negative particle(?????? ??????)");
        particle.add("PREV  Preventive particle	(?????? ??????)");
        particle.add("PRO  	Prohibition particle(?????? ??????)");
        particle.add("REM  	Resumption particle	(?????? ??????????????????)");
        particle.add("RES  	Restriction particle(???????? ??????)");
        particle.add("RET  	Retraction particle	(?????? ??????????)");
        particle.add("RSLT  Result particle(?????? ???????? ???? ???????? ??????????)");
        particle.add("SUP  	Supplemental particle	(?????? ????????)");
        particle.add("SUR  	Surprise particle	(?????? ??????????)");
        particle.add("VOC  	Vocative particle	(?????? ????????)");
        particle.add("INL  	Quranic initials(	(???????? ??????????	");
        ArrayList<String> details = new ArrayList<>();
        String sample = "???????????? ?????????????? ???????????????????????? ????????????????????";
        colorSchemeAdapter = new ColorSchemeAdapter(spanhash, particle);
        recyclerView.setAdapter(colorSchemeAdapter);
        colorSchemeAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //      int checkedRadioButtonId = textView.getCheckedRadioButtonId();
                //Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        final TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            //  super(inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false));
            super(inflater.inflate(R.layout.coloradapter, parent, false));
            text = itemView.findViewById(R.id.Colortv);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    private class ColorSchemeAdapter extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<String> particle;
        private String mItemCount;
        private Map<String, ForegroundColorSpan> spanhash;
        private OnItemClickListener mItemClickListener;

        ColorSchemeAdapter(String itemCount) {
            mItemCount = itemCount;
        }

        public ColorSchemeAdapter(Map<String, ForegroundColorSpan> spanhash, ArrayList<String> particle) {
            this.spanhash = spanhash;
            this.particle = particle;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String sample = "???????????? ?????????????? ???????????????????????? ????????????????????";
            Typeface mequran = Typeface.createFromAsset(getContext().getAssets(), "me_quran.ttf");
            Typeface qalam = Typeface.createFromAsset(getContext().getAssets(), "AlQalam.ttf");
            Typeface amiri = Typeface.createFromAsset(getContext().getAssets(), "Pdms.ttf");
            String s = particle.get(position);
            String[] split = s.split("\\s");
            ForegroundColorSpan foregroundColorSpan = spanhash.get(split[0]);
            SpannableString sp = new SpannableString(s);
            sp.setSpan(foregroundColorSpan, 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.text.setText(sp);
            holder.text.setTextSize(20);

        }

        @Override
        public int getItemCount() {
            return particle.size();
        }

        public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;
        }
    }

}