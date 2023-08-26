package com.example.mushafconsolidated.Activity;

import android.os.Bundle;
import android.text.SpannableString;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class KeyboardSearchResult extends WordOccuranceAct {
    final LinkedHashMap<String, List<SpannableString>> expandlexicon = new LinkedHashMap<>();

    public KeyboardSearchResult() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executeDictionary();
    }

    void executeDictionary() {
        //  ArrayList<lanelexicon> lanesDifinition = utils.getLanesDifinition(root);
        //  ArrayList<SpannableString> lanesdifinition ;
        List<SpannableString> list = new ArrayList<>();
        StringBuilder lanessb = new StringBuilder();
        //  for (lanelexicon lanes : lanesDifinition) {
        //  <p style="margin-left:200px; margin-right:50px;">
        //    list.add("<p style=\"margin-left:200px; margin-right:50px;\">");
        //  list.add("<p style=\"margin-left:200px; margin-right:50px;\">");
        //  list.add(lanes.getDefinition() );
        list.add(SpannableString.valueOf(""));
        //   }
        expandlexicon.put("lanes Lexicon", list);
        expandlexicon.put("Hans", list);
        List<String> expandLexconTitle;
        expandLexconTitle = new ArrayList<>(expandlexicon.keySet());
        expandNounTitles = new ArrayList<>(expandNounVerses.keySet());
        expandVerbTitles = new ArrayList<>(expandVerbVerses.keySet());
        expandNounVerses.putAll(expandlexicon);
        expandNounVerses.putAll(expandVerbVerses);
        expandNounTitles.addAll((expandLexconTitle));
        expandNounTitles.addAll(expandVerbTitles);

    }

}




