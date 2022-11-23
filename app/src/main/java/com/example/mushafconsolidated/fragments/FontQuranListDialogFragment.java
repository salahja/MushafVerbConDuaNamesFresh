package com.example.mushafconsolidated.fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class FontQuranListDialogFragment extends BottomSheetDialogFragment {


    OnItemClickListener mItemClickListener;
    private FontQuranAdapter fontQuranAdapter;
    RadioGroup radioGroup;

    // TODO: Customize parameters
    public static FontQuranListDialogFragment newInstance( ) {
        final FontQuranListDialogFragment fragment = new FontQuranListDialogFragment();

        return fragment;

    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {





        return inflater.inflate(R.layout.quran_list_dialog, container, false);
        //   return inflater.inflate(R.layout.Arabic_Font_Selectionselection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        radioGroup=  view.findViewById(R.id.rdgroup);


        ArrayList<String> details=new ArrayList<>();
        String sample="بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ";

        fontQuranAdapter =new FontQuranAdapter(sample);
        recyclerView.setAdapter(fontQuranAdapter);
        fontQuranAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                //Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        });





    }

    private class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        final TextView text,qalamtext,mequrantext,quranictext;
        RadioGroup radioGroup;
        RadioButton rdqalam,rdmequran,rdamiri;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            //  super(inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false));
            super(inflater.inflate(R.layout.quranfonts_bottomsheet, parent, false));
            SharedPreferences sharedPreferences =
                    androidx.preference.PreferenceManager.getDefaultSharedPreferences(getContext());



            rdqalam=itemView.findViewById(R.id.rdqalam);
            rdmequran=itemView.findViewById(R.id.rdmequran);
            rdamiri=itemView.findViewById(R.id.rdquranic);
            radioGroup=itemView.findViewById(R.id.rdgroup);
            text = itemView.findViewById(R.id.text);
            qalamtext=itemView.findViewById(R.id.qalamtext);
            mequrantext=itemView.findViewById(R.id.mequrantext);
            quranictext=itemView.findViewById(R.id.quranictext);
            itemView.setOnClickListener(this);
            rdqalam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                    editor.putString("Arabic_Font_Selection", "AlQalam.ttf");
                    editor.apply();
                    dismiss();
                }
            });
            rdmequran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                    editor.putString("Arabic_Font_Selection", "me_quran.ttf");
                    editor.apply();
                    dismiss();
                }
            });

            rdamiri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                    editor.putString("Arabic_Font_Selection", "quranicfontregular.ttf");
                    editor.apply();
                    dismiss();
                }
            });


        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    private class FontQuranAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final String mItemCount;
        private OnItemClickListener mItemClickListener;

        FontQuranAdapter(String itemCount) {
            mItemCount = itemCount;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String sample="بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ";
            Typeface mequran = Typeface.createFromAsset(getContext().getAssets(), "me_quran.ttf");
            Typeface qalam = Typeface.createFromAsset(getContext().getAssets(), "AlQalam.ttf");
            Typeface quran = Typeface.createFromAsset(getContext().getAssets(), "quranicfontregular.ttf");
            holder.text.setText(sample);
            holder.qalamtext.setText(sample);
            holder.mequrantext.setText(sample);

            holder.qalamtext.setTypeface(qalam);
            holder.mequrantext.setTypeface(mequran);
            holder.quranictext.setText(sample);
            holder.quranictext.setTypeface(quran);

            String theme = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("Arabic_Font_Selection", "me_quran.ttf");
            if(theme.equals("me_quran.ttf")){
                holder.rdmequran.setChecked(true);
            } else if    (theme.equals("AlQalam.ttf")) {

                holder.rdqalam.setChecked(true);
            } else if    (theme.equals("quranicfontregular.ttf")) {
                holder.rdamiri.setChecked(true);
            }

        }

        @Override
        public int getItemCount() {
            return 1;
        }

        public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;
        }
    }

}