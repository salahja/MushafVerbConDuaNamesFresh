package com.example.mushafconsolidated.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ThemeListPrefrence.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class VerbconjuationBottom extends BottomSheetDialogFragment  {
    public static final String TAG = "opton";
    private static List localityList;





    // TODO: Customize parameter argument names
    private static final String ARG_OPTIONS_DATA = "item_count";
    OnItemClickListener mItemClickListener;
    RadioGroup radioGroup;
    private FontQuranAdapter fontQuranAdapter;
    RelativeLayout frameLayout;
    List<String>  mLocalityList=new ArrayList<>();
    // TODO: Customize parameters
    public static VerbconjuationBottom newInstance(ArrayList list) {
     ;
        final VerbconjuationBottom fragment = new VerbconjuationBottom();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list",  list);
        fragment.setArguments(bundle);

        return fragment;

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.quran_list_dialog, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalityList = getArguments().getStringArrayList("list");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<String> details = new ArrayList<>();
        mLocalityList = getArguments().getStringArrayList("list");
        fontQuranAdapter = new FontQuranAdapter();
        recyclerView.setAdapter(fontQuranAdapter);
        fontQuranAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            }
        });

    }

    private class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView hua,huma,hum,hia,humaf,hunna,anta,antumam,antum,anti,antumaf,antunna,ana,nahnu;



        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            //  super(inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false));
            super(inflater.inflate(R.layout.conjugationbottom, parent, false));
            hua = itemView.findViewById(R.id.hua);
            huma = itemView.findViewById(R.id.huma);
            hum = itemView.findViewById(R.id.hum);

            hia = itemView.findViewById(R.id.hia);
            humaf = itemView.findViewById(R.id.humaf);
            hunna = itemView.findViewById(R.id.hunna);

            anta = itemView.findViewById(R.id.anta);
            antumam = itemView.findViewById(R.id.antuma);
            antum = itemView.findViewById(R.id.antum);

            anti = itemView.findViewById(R.id.anti);
            antumaf = itemView.findViewById(R.id.antumaf);
            antunna = itemView.findViewById(R.id.antunna);
            ana = itemView.findViewById(R.id.ana);
            nahnu = itemView.findViewById(R.id.nahnu);


           frameLayout= itemView.findViewById(R.id.bottomSheet);
           itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    private class FontQuranAdapter extends RecyclerView.Adapter<ViewHolder> {

        private OnItemClickListener mItemClickListener;



        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            SharedPreferences sharedPreferences =
                    androidx.preference.PreferenceManager.getDefaultSharedPreferences(getContext());


            String theme = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("themepref", "dark");
            holder.hua.setText(mLocalityList.get(0));
            holder.huma.setText(mLocalityList.get(1));
            holder.hum.setText(mLocalityList.get(2));
             holder.hia.setText(mLocalityList.get(3));
             holder.humaf.setText(mLocalityList.get(4));
           holder.hunna.setText(mLocalityList.get(5));

            holder.anta.setText(mLocalityList.get(6));
            holder.antumam.setText(mLocalityList.get(7));
            holder.antum.setText(mLocalityList.get(8));

            holder.anti.setText(mLocalityList.get(9));
            holder.antumaf.setText(mLocalityList.get(7));
            holder.antunna.setText(mLocalityList.get(10));

            holder.ana.setText(mLocalityList.get(11));
            holder.nahnu.setText(mLocalityList.get(12));

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