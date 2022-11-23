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
public class TranslationListPrefrence extends BottomSheetDialogFragment  {
    public static final String TAG = "opton";
    private int chap_id,verse_id;
    private String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
            chap_id = getArguments().getInt("chap_id");
            verse_id = getArguments().getInt("verse_no");
        }
        Utils utils = new Utils(getActivity());
      //  setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Material3_BottomSheetDialog);
    }



    // TODO: Customize parameter argument names
    private static final String ARG_OPTIONS_DATA = "item_count";
    OnItemClickListener mItemClickListener;
    RadioGroup radioGroup;
    private FontQuranAdapter fontQuranAdapter;
    RelativeLayout frameLayout;
    // TODO: Customize parameters
    public static TranslationListPrefrence newInstance() {
        final TranslationListPrefrence fragment = new TranslationListPrefrence();

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

        ImageView ivCopy, ivBookmark, ivShare;
        TextView textView,textView2,textView3;
        RadioButton en_sahih,ur_junagarhi,en_jalalayn,ur_jalalayn,en_erberry;



        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            //  super(inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false));
            super(inflater.inflate(R.layout.translation_prference_dialog  , parent, false));
            en_sahih = itemView.findViewById(R.id.en_sahi);
            ur_junagarhi = itemView.findViewById(R.id.ur_junagarhi);
            en_jalalayn = itemView.findViewById(R.id.en_jalalayn);
            ur_jalalayn = itemView.findViewById(R.id.ur_jalalayn);
            en_erberry = itemView.findViewById(R.id.en_arberry);

           frameLayout= itemView.findViewById(R.id.bottomSheet);
           itemView.setOnClickListener(this);

            en_sahih.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                    editor.putString("selecttranslation", "en_sahih");
                    editor.apply();
                    dismiss();
                }
            });
            ur_junagarhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                    editor.putString("selecttranslation", "ur_junagarhi");
                    editor.apply();
                    dismiss();
                }
            });

            en_jalalayn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                    editor.putString("selecttranslation", "en_jalalayn");
                    editor.apply();
                    dismiss();
                }
            });

            ur_jalalayn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                    editor.putString("selecttranslation", "ur_jalalayn");
                    editor.apply();
                    dismiss();
                }
            });

            en_erberry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                    editor.putString("selecttranslation", "en_erberry");
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


            String theme = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("selecttranslation", "en_sahih");

       if(theme.equals("en_sahih")){
           holder.en_sahih.setChecked(true);
       } else if    (theme.equals("ur_jalalayn")) {

           holder.ur_junagarhi.setChecked(true);
    } else if    (theme.equals("ur_junagarhi")) {
            holder.en_jalalayn.setChecked(true);
       } else if    (theme.equals("ur_jalalayn")) {
           holder.ur_jalalayn.setChecked(true);

        } else if    (theme.equals("en_erberry")) {
            holder.en_erberry.setChecked(true);
        }




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