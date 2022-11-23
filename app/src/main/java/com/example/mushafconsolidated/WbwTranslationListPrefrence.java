package com.example.mushafconsolidated;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ThemeListPrefrence.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class WbwTranslationListPrefrence extends BottomSheetDialogFragment  {
    public static final String TAG = "opton";
    private int chap_id,verse_id;
    private String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  
    }



    // TODO: Customize parameter argument names
    private static final String ARG_OPTIONS_DATA = "item_count";
    OnItemClickListener mItemClickListener;
    RadioGroup radioGroup;
    private FontQuranAdapter fontQuranAdapter;
    RelativeLayout frameLayout;
    // TODO: Customize parameters
    public static WbwTranslationListPrefrence newInstance() {
        final WbwTranslationListPrefrence fragment = new WbwTranslationListPrefrence();
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
 
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.quran_list_dialog, container, false);
      
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<String> details = new ArrayList<>();

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

 
        RadioButton wbwen,wbwbangla,wbwindonesia,wbwurdu;



        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
       
            super(inflater.inflate(R.layout.wbw_prference_dialog  , parent, false));
            wbwen = itemView.findViewById(R.id.wbwen);
            wbwbangla = itemView.findViewById(R.id.wbwbangla);

            wbwurdu = itemView.findViewById(R.id.wbwurdu);
            wbwindonesia = itemView.findViewById(R.id.wbwindonesia);

           frameLayout= itemView.findViewById(R.id.bottomSheet);
           itemView.setOnClickListener(this);

            wbwen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
               
                    editor.putString("wbw", "en");
                    editor.apply();
                    dismiss();
                }
            });
            wbwbangla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
              
                    editor.putString("wbw", "bn");
                    editor.apply();
                    dismiss();
                }
            });

            wbwindonesia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                 
                    editor.putString("wbw", "in");
                    editor.apply();
                    dismiss();
                }
            });

            wbwurdu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                    editor.putString("wbw", "ur");
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


            String theme = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("wbw", "en");

       if(theme.equals("en")){
           holder.wbwen.setChecked(true);
       } else if    (theme.equals("bn")) {

           holder.wbwbangla.setChecked(true);
    } else if    (theme.equals("ur")) {
            holder.wbwurdu.setChecked(true);
       } else if    (theme.equals("in")) {
           holder.wbwindonesia.setChecked(true);

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