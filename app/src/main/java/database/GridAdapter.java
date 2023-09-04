package database;


import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.R;
import com.example.utility.QuranGrammarApplication;

import org.jetbrains.annotations.NotNull;
import org.sj.conjugator.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import database.entity.AllahNames;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>  implements Filterable {
    // --Commented out by Inspection (28/08/23, 6:54 pm):private final CharSequence mSearchText = "";
    private final List<AllahNames> names;
    private  List<AllahNames> namesfilter;
    ArrayList<ImageItem> data;
    // --Commented out by Inspection (28/08/23, 6:54 pm):int rootcolor, weaknesscolor, wazancolor;
        // --Commented out by Inspection (28/08/23, 6:54 pm):private final Context context;
        // --Commented out by Inspection (28/08/23, 6:53 pm):int bookmarkpostion;
        OnItemClickListener mItemClickListener;
// --Commented out by Inspection START (28/08/23, 6:54 pm):
//        //    private final Integer arabicTextColor;
      Context context;
// --Commented out by Inspection STOP (28/08/23, 6:54 pm)

        // --Commented out by Inspection (28/08/23, 6:54 pm):private boolean mazeedregular;
        // --Commented out by Inspection (28/08/23, 6:53 pm):private int bookChapterno;
        // --Commented out by Inspection (28/08/23, 6:54 pm):private int bookVerseno;
        // --Commented out by Inspection (28/08/23, 6:53 pm):private Integer ayahNumber;
        // --Commented out by Inspection (28/08/23, 6:54 pm):private String urdu_font_selection;
        // --Commented out by Inspection (28/08/23, 6:54 pm):private int quran_arabic_font;


        // --Commented out by Inspection (28/08/23, 6:54 pm):private int urdu_font_size;
        // --Commented out by Inspection (28/08/23, 6:53 pm):private String arabic_font_selection;





    public GridAdapter(Context context, ArrayList<AllahNames> names, ArrayList<ImageItem> data) {
            this.context=context;
            this.names=names;
            this.namesfilter=names;
            this.data=data;

    }


    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            //      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarfkabeercolumn, parent, false);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nameimages, parent, false);
            //    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thulathisarfsagheer, parent, false);

        return new ViewHolder(view);
            
            
        }


        public void onBindViewHolder(@NotNull ViewHolder holder, int position) {

            SharedPreferences sharedPreferences =getDefaultSharedPreferences(QuranGrammarApplication.getContext());



            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
            String fontCategory = sharedPreferences.getString("arabic_font_category", "me_quran.ttf");
            Typeface prefArabicFontTypeface = Typeface.createFromAsset(QuranGrammarApplication.getContext().getAssets(), fontCategory);
            String fonts=      prefs.getString("Arabic_Font_Size", "25");
            int arabicfontSize = sharedPreferences.getInt("pref_font_arabic_key", 18);
            int translationfontsize = sharedPreferences.getInt("pref_font_englsh_key", 18);
            //  String arabic_font_category = prefs.getString("arabic_font_category", "kitab.tff");
            String isNightmode = sharedPreferences.getString("themepref", "dark");
            AllahNames item = names.get(position);
            Typeface mequran= Typeface.createFromAsset(QuranGrammarApplication.getContext().getAssets(), "Taha.ttf");
            final Integer arabicFontsize = Integer.valueOf(fonts);

            ImageItem imageItem = data.get(position);
            holder.imageView.setImageBitmap(imageItem.getImage());
            names.get(position);

            holder.name.setText(item.getTrans());
        ;   holder.meaning.setText(item.getMeaning());
         //  holder.name.setTypeface(prefArabicFontTypeface);
          // holder.name.setTextSize(arabicfontSize);
            holder.meaning.setTypeface(mequran);
            holder.meaning.setTextSize(translationfontsize);


        }



        public Object getItem(int position) {

            return namesfilter.get(position);
        }

        @Override
        public int getItemCount() {
            return namesfilter.size();

        }

        public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;
        }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    namesfilter = names;
                    ;
                } else {
                    List<AllahNames> filteredList = new ArrayList<>();
                    for (AllahNames details : names) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (details.getMeaning().toLowerCase().contains(charString.toLowerCase()))   {
                            filteredList.add(details);
                        }
                    }
                    namesfilter = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = namesfilter;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                namesfilter = (ArrayList<AllahNames>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder
              implements View.OnClickListener // current clickListerner
        {

            public ImageView imageView;
            public TextView name,meaning;


            public ViewHolder(View view) {
                super(view);

                imageView=view.findViewById(R.id.imageView);
                name=view.findViewById(R.id.Names);
                meaning=view.findViewById(R.id.meaning);
                view.setOnClickListener(this);




            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getLayoutPosition());
                }
            }

        }


    }

