package com.example.mushafconsolidated.fragments;

import static com.example.Constant.SURAHNAME;
import static com.example.Constant.VERSESCOUNT;
import static com.example.Constant.halspanLight;
import static com.example.mushafconsolidated.settings.Constants.SURAH_INDEX;

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

import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.mushafconsolidated.settings.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ThemeListPrefrence.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class audioplaysetting extends BottomSheetDialogFragment  {
    public static final String TAG = "opton";
    private int chap_id,verse_id,versescount;
    private String qariname,surahname;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qariname = getArguments().getString("qari");
            surahname = getArguments().getString(SURAHNAME);
            chap_id = getArguments().getInt(SURAH_INDEX);

            versescount = getArguments().getInt(VERSESCOUNT);





        }
        Utils utils = new Utils(getActivity());
      //  setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Material3_BottomSheetDialog);
    }



    // TODO: Customize parameter argument names
    private static final String ARG_OPTIONS_DATA = "item_count";
    OnItemClickListener mItemClickListener;
    RadioGroup radioGroup;
    private AudionPlayAdapter audionPlayAdapter;
    RelativeLayout frameLayout;
    // TODO: Customize parameters
    public static audioplaysetting newInstance() {
        final audioplaysetting fragment = new audioplaysetting();

        return fragment;

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
    /*    ContextThemeWrapper contextThemeWrapper= new ContextThemeWrapper(getActivity(),R.style.Theme_DarkBlue);


   return      inflater.cloneInContext(contextThemeWrapper).inflate(R.layout.quran_list_dialog, container, false);*/
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.quran_list_dialog, container, false);
        //  return inflater.inflate(R.layout.selecttranslationselection, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<String> details = new ArrayList<>();

        audionPlayAdapter = new AudionPlayAdapter();
        recyclerView.setAdapter(audionPlayAdapter);
        audionPlayAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            }
        });

    }

    private class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView ivCopy, ivBookmark, ivShare;
        TextView textView,textView2,textView3;
        TextView startrange,endrange,startname,endname,qarilabel,qariname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
        }

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {

            // TODO: Customize the item layout
            //  super(inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false));
            super(inflater.inflate(R.layout.audio_setting  , parent, false));

            startrange = itemView.findViewById(R.id.start_range);
            endrange = itemView.findViewById(R.id.endrange);
            startname = itemView.findViewById(R.id.startname);
            endname = itemView.findViewById(R.id.endname);
            qarilabel = itemView.findViewById(R.id.qarilable);
            qariname = itemView.findViewById(R.id.qariname);
            frameLayout= itemView.findViewById(R.id.bottomSheet);
            itemView.setOnClickListener(this);
            endname.setTag("end");
            endname.setOnClickListener(this);;





        }







        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    private class AudionPlayAdapter extends RecyclerView.Adapter<ViewHolder> {

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


            String theme = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("selecttranslation", "en_sahih");

             holder.startrange.setText("Start Range");
             holder.endrange.setText("End Range");
             StringBuilder st=new StringBuilder();
            StringBuilder stt=new StringBuilder();
             st.append(surahname).append("-").append(chap_id).append(":").append("1");
            stt.append(surahname).append("-").append(chap_id).append(":").append(versescount);
            holder.startname.setText(st.toString());
            holder.endname.setText(stt.toString());
            holder.qarilabel.setText("qari");
            holder.qariname.setText(qariname);


      /*      holder.ivShare.setOnClickListener(convertView -> {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        holder.duaheader.getText() + "\n\n" +
                                holder.tvDuaArabic.getText() + "\n\n" +
                                holder.tvDuaTranslation.getText() + "\n\n" +
                                holder.tvDuaReference.getText() + "\n\n" +
                                convertView.getResources().getString(R.string.action_share_credit)
                );
                intent.setType("text/plain");
                convertView.getContext().startActivity(
                        Intent.createChooser(
                                intent,
                                convertView.getResources().getString(R.string.action_share_title)
                        )
                );
            });
*/



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