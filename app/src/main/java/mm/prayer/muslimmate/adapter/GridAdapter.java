package mm.prayer.muslimmate.adapter;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.mushafconsolidated.R;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mushafconsolidated.R;

import mm.prayer.muslimmate.ui.ApplicationContext;
import mm.prayer.muslimmate.interfaces.OnItemClickListener;
import mm.prayer.muslimmate.entity.Cities;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> implements Filterable {
    private CharSequence mSearchText = "";
    private List<Cities> names;
    private List<Cities> namesfilter;

    int rootcolor, weaknesscolor, wazancolor;
    private final Context context;
    int bookmarkpostion;
    OnItemClickListener mItemClickListener;
    //    private final Integer arabicTextColor;
    Context mycontext;

    private boolean mazeedregular;
    private int bookChapterno;
    private int bookVerseno;
    private Integer ayahNumber;
    private String urdu_font_selection;
    private int quran_arabic_font;


    private int urdu_font_size;
    private String arabic_font_selection;


    public GridAdapter(Context context, ArrayList<Cities> names) {
        this.context = context;
        this.names = names;
        this.namesfilter = names;


    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarfkabeercolumn, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mm_city_list, parent, false);
        //    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thulathisarfsagheer, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;


    }


    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        //  final List sarf = sarfSagheer.get(position);
//        final String[] array = (String[]) sarfSagheer.get(position).toArray.get();

        SharedPreferences sharedPreferences = getDefaultSharedPreferences(ApplicationContext.getContext());


        SharedPreferences prefs = getDefaultSharedPreferences(ApplicationContext.getContext());
        String fontCategory = sharedPreferences.getString("arabic_font_category", "cal.otf");
        //     Typeface prefArabicFontTypeface = Typeface.createFromAsset(ApplicationContext.getContext().getAssets(), fontCategory);
        String fonts = prefs.getString("Arabic_Font_Size", "25");
        //    int arabicfontSize = sharedPreferences.getInt("pref_font_arabic_key", 18);
        int translationfontsize = sharedPreferences.getInt("pref_font_englsh_key", 18);
        //  String arabic_font_category = prefs.getString("arabic_font_category", "kitab.tff");
        String isNightmode = sharedPreferences.getString("themepref", "dark");
        Cities item = names.get(position);
        //  Typeface mequran= Typeface.createFromAsset(ApplicationContext.getContext().getAssets(), "Taha.ttf");
        // final Integer arabicFontsize = Integer.valueOf(fonts);


        names.get(position);

        holder.name.setText(item.getCountry());
        ;
        holder.meaning.setText(item.getCity());
        //  holder.name.setTypeface(prefArabicFontTypeface);
        // holder.name.setTextSize(arabicfontSize);
        //   holder.meaning.setTypeface(mequran);
        holder.meaning.setTextSize(20);
        holder.meaning.setTextSize(20);


// holder.ivSurahIcon.setImageDrawable(drawable);
        //    holder.rulenumber.setTextSize(arabicFontsize);

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
                    List<Cities> filteredList = new ArrayList<>();
                    for (Cities details : names) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (details.getCity().toLowerCase().contains(charString.toLowerCase()))   {
                            filteredList.add(details);
                        }
                    }
                    namesfilter = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = namesfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                namesfilter = (ArrayList<Cities>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener // current clickListerner
    {


        public TextView name, meaning;


        public ViewHolder(View view) {
            super(view);


            name = view.findViewById(R.id.sp_country);
            meaning = view.findViewById(R.id.tv_city_name);
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

