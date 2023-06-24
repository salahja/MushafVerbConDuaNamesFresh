package com.example.mushafconsolidated.fragments;

import static com.example.Constant.SURAH_ID;
import static com.example.mushafconsolidated.receivers.AudioAppConstants.General.AYA_ID;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
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

import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
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
public class SelectedWordAyah extends BottomSheetDialogFragment  {
    public static final String TAG = "opton";
    private List<QuranEntity> quranEntities;
    private int ayah,surah;
    private String surahname,arabicword,wordmeaning;

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
    public static SelectedWordAyah newInstance(String[] data) {
        final SelectedWordAyah fragment = new SelectedWordAyah();

        final Bundle args = new Bundle();
        args.putStringArray(ARG_OPTIONS_DATA, data);
        fragment.setArguments(args);


      Bundle bundle = fragment.getArguments();
      if(null!=bundle) {
          bundle.getInt(SURAH_ID);
          bundle.getInt(AYA_ID);
      }
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
        Bundle bundle = this.getArguments();
        String[] stringArray = bundle.getStringArray(ARG_OPTIONS_DATA);
  surah = Integer.parseInt(stringArray[0]);
         ayah = Integer.parseInt(stringArray[1]);
          surahname = stringArray[2];
         arabicword = stringArray[3];
        wordmeaning = stringArray[4];
        Utils utils=new Utils(getActivity());
        quranEntities = Utils.getsurahayahVerses(surah, ayah);

        return inflater.inflate(R.layout.quran_list_dialog, container, false);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<String> details = new ArrayList<>();

        fontQuranAdapter = new FontQuranAdapter(quranEntities);
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

        RadioButton purple,black,dark_blue, green,brown;
        TextView verse,translation,tafsir,wordDetails,surah,ayadetail,arabicword,meaning;



        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            //  super(inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false));
            super(inflater.inflate(R.layout.selectwordverse, parent, false));
            verse = itemView.findViewById(R.id.verse);
            translation = itemView.findViewById(R.id.translation);

           frameLayout= itemView.findViewById(R.id.bottomSheet);
           tafsir=itemView.findViewById(R.id.tafsir);

            surah=itemView.findViewById(R.id.surah);
            ayadetail=itemView.findViewById(R.id.ayadetail);
            arabicword=itemView.findViewById(R.id.arabicword);
            meaning=itemView.findViewById(R.id.meaning);

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
        List<QuranEntity> quranEntities;
        public FontQuranAdapter(List<QuranEntity> quranEntities) {
            this.quranEntities=quranEntities;
        }


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
            QuranEntity entity = quranEntities.get(position);
            holder.verse.setText(entity.getQurantext());;
            holder.translation.setText(Html.fromHtml(entity.getTranslation()));
            holder.tafsir.setText(entity.getTafsir_kathir());


            holder.surah.setText(surahname);
            holder.arabicword.setText(arabicword);
            holder.meaning.setText(wordmeaning);
           // String word="";
            StringBuilder word=new StringBuilder();
            word.append(surah).append(":").append(ayah);
     //    word.append(String.valueOf(surah)).append(":").append(String.valueOf(ayah)).append(" ").append(arabicword).append(" ").append(wordmeaning).append( "").append("(").append(surahname).append(")");
            holder.ayadetail.setText(word);






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