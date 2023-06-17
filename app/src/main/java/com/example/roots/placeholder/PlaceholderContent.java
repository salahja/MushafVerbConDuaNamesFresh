package com.example.roots.placeholder;

import com.example.mushafconsolidated.Entities.qurandictionary;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.utility.QuranGrammarApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();
    public static final ArrayList<String> root=new ArrayList<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 0; i <= 27; i++) {
            addItem(createPlaceholderItem(i));
        }
    }


    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }



    private static PlaceholderItem createPlaceholderItem(int position) {
        String[] array = QuranGrammarApplication.getContext().getResources().getStringArray(R.array.arabicletters);
        // return new PlaceholderItem(String.valueOf(position), "Item " + position, makeDetails(position));

        return new PlaceholderItem(String.valueOf(position), array[position], makeDetailso(position,array[position],new ArrayList<String>()), new ArrayList<>());
    }

    private static ArrayList<String> makeDetails(int position) {

 //    StringBuilder builder = new StringBuilder();
     //   builder.append("Details about Item: ").append(position);
        ArrayList<String> details=new ArrayList<>();
        for (int i = 0; i < position; i++) {

         details.add("details about item");
        }
      //  return builder.toString();

        return details;
    }

    private static ArrayList<String> makeDetailso(int position, String s, ArrayList<String> strings) {
        StringBuilder builder = new StringBuilder();
        ArrayList<qurandictionary> letter = Utils.getRootwordsbyLetter(s);

        for (qurandictionary qurandictionary : letter) {
            strings.add(qurandictionary.getRootarabic());
        //    builder.append(qurandictionary.getRootarabic()).append("\n");
        }


        //   ArrayList<qurandictionary> letter = Utils.getRootwordsbyLetter("غ");
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            //          builder.append("\nMore details information here.");
        }
        return strings;
    }
    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {

        public final String id;
        public final String content;
        public final ArrayList<String>  details;


        public     ArrayList<String> rootsArrayList=new ArrayList<>();

        public PlaceholderItem(String id, String content, ArrayList<String> details, ArrayList<String>rootsArrayList) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.rootsArrayList=rootsArrayList;
        }



        @Override
        public String toString() {
            return content;
        }
    }

    public class roots {
        public String rootword;
                private roots(String root){
              this.rootword=root;
                }
    }
}