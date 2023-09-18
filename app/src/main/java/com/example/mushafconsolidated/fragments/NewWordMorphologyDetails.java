package com.example.mushafconsolidated.fragments;

import static com.example.utility.CorpusConstants.verbfeaturesenglisharabic.ACT;
import static com.example.utility.CorpusConstants.verbfeaturesenglisharabic.IMPF;
import static com.example.utility.CorpusConstants.verbfeaturesenglisharabic.IMPV;
import static com.example.utility.CorpusConstants.verbfeaturesenglisharabic.JUS;
import static com.example.utility.CorpusConstants.verbfeaturesenglisharabic.PASS;
import static com.example.utility.CorpusConstants.verbfeaturesenglisharabic.PERF;
import static com.example.utility.CorpusConstants.verbfeaturesenglisharabic.SUBJ;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;

import androidx.annotation.NonNull;

import com.example.mushafconsolidated.Entities.NounCorpus;
import com.example.mushafconsolidated.Entities.QuranCorpusWbw;
import com.example.mushafconsolidated.Entities.VerbCorpus;
import com.example.mushafconsolidated.model.CorpusWbwWord;
import com.example.utility.CorpusConstants;
import com.example.utility.CorpusUtilityorig;

import java.util.ArrayList;
import java.util.HashMap;

public class NewWordMorphologyDetails extends QuranMorphologyDetails {
    private ArrayList<VerbCorpus> verbCorpusRootWord;
    private ArrayList<NounCorpus> corpusNounWord;
    private QuranCorpusWbw word;

    public NewWordMorphologyDetails(QuranCorpusWbw word) {
        this.word = word;

    }

    public NewWordMorphologyDetails(QuranCorpusWbw word, ArrayList<NounCorpus> corpusNounWord, ArrayList<VerbCorpus> verbCorpusRootWord) {
        this.word = word;
        this.corpusNounWord = corpusNounWord;
        this.verbCorpusRootWord = verbCorpusRootWord;
    }

    public NewWordMorphologyDetails(QuranCorpusWbw word, ArrayList<NounCorpus> corpusNounWord) {
        this.word = word;
        this.corpusNounWord = corpusNounWord;

    }

// --Commented out by Inspection START (13/11/22, 3:02 PM):
//    public WordMorphologyDetails() {
//    }
// --Commented out by Inspection STOP (13/11/22, 3:02 PM)

    public SpannableString getWorkBreakDown() {
        //   SpannableStringBuilder tagspannable = null;
        SpannableString tagspannable = null;
        int wordcount = word.getCorpus().getWordcount();
        if (wordcount == 1) {
            String tagone = word.getCorpus().getTagone();
            String expandTagsone = expandTags(tagone);
            if (tagone.equals("N")) {
                expandTagsone = getNounDetails(word.getCorpus().getDetailsone());
            }
            if (tagone.equals("V")) {
                expandTagsone = getVerbDetails(expandTagsone, word.getCorpus().getDetailsone());
            }
            tagspannable = new SpannableString(expandTagsone);
            //   tagspannable.setSpan(new ForegroundColorSpan(CYAN), 0, expandTagsone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tagspannable = CorpusUtilityorig.NewSetWordSpanTag(word.getCorpus().getTagone(), word.getCorpus().getTagtwo(), word.getCorpus().getTagthree(),
                    word.getCorpus().getTagfour(), word.getCorpus().getTagfive(), " ", " ", "", "", expandTagsone);
        } else if (wordcount == 2) {
            String tagtwo = word.getCorpus().getTagtwo();
            String tagone = word.getCorpus().getTagone();
            String expandTagsone = expandTags(tagone);
            boolean tagnounone = tagone.equals("N") || tagone.equals("ADJ") || tagone.equals("PN");
            boolean tagnountwo = tagtwo.equals("N") || tagtwo.equals("ADJ") || tagtwo.equals("PN");
            String expandTagstwo = expandTags(tagtwo);
            if (tagnounone) {
                expandTagsone = getNounDetails(word.getCorpus().getDetailsone());
            } else if (tagnountwo) {
                expandTagstwo = getNounDetails(word.getCorpus().getDetailstwo());
            }
            if (tagone.equals("V")) {
                expandTagsone = getVerbDetails(expandTagsone, word.getCorpus().getDetailsone());
            } else if (tagtwo.equals("V")) {
                expandTagstwo = getVerbDetails(expandTagstwo, word.getCorpus().getDetailstwo());
            }
            tagspannable = CorpusUtilityorig.NewSetWordSpanTag(word.getCorpus().getTagone(), word.getCorpus().getTagtwo(), word.getCorpus().getTagthree(),
                    word.getCorpus().getTagfour(), word.getCorpus().getTagfive(), " ", " ", "", expandTagstwo, expandTagsone);

        } else if (wordcount == 3) {
            StringBuilder sb = new StringBuilder();
            String expandTagsone = expandTags(word.getCorpus().getTagone());
            String expandTagstwo = expandTags(word.getCorpus().getTagtwo());
            String expandTagsthree = expandTags(word.getCorpus().getTagthree());
            String tagtwo = word.getCorpus().getTagtwo();
            String tagone = word.getCorpus().getTagone();
            String tagthree = word.getCorpus().getTagthree();
            boolean tagnounone = tagone.equals("N") || tagone.equals("ADJ") || tagone.equals("PN");
            boolean tagnountwo = tagtwo.equals("N") || tagtwo.equals("ADJ") || tagtwo.equals("PN");
            boolean tagnounthree = tagthree.equals("N") || tagthree.equals("ADJ") || tagthree.equals("PN");
            if (tagnounone) {
                expandTagsone = getNounDetails(word.getCorpus().getDetailsone());

            } else if (tagnountwo) {
                expandTagstwo = getNounDetails(word.getCorpus().getDetailstwo());

            } else if (tagnounthree) {
                expandTagsthree = getNounDetails(word.getCorpus().getDetailsthree());

            }
            if (word.getCorpus().getTagone().equals("V")) {
                expandTagsone = getVerbDetails(expandTagsone, word.getCorpus().getDetailsone());

            } else if (word.getCorpus().getTagtwo().equals("V")) {
                expandTagstwo = getVerbDetails(expandTagstwo, word.getCorpus().getDetailstwo());

            }
            if (word.getCorpus().getTagthree().equals("V")) {
                expandTagsthree = getVerbDetails(expandTagsthree, word.getCorpus().getDetailsthree());

            }
            expandTagsone.concat(" ");
            expandTagstwo.concat(" ");
            expandTagsthree.concat(" ");
            sb.append(word.getCorpus().getTagthree());
            sb.append("|");
            sb.append(word.getCorpus().getTagtwo());
            sb.append("|");
            sb.append(word.getCorpus().getTagone());
            tagspannable = CorpusUtilityorig.NewSetWordSpanTag(word.getCorpus().getTagone(), word.getCorpus().getTagtwo(), word.getCorpus().getTagthree(),
                    word.getCorpus().getTagfour(), word.getCorpus().getTagfive(), " ", " ", expandTagsthree, expandTagstwo, expandTagsone);

        } else if (wordcount == 4) {
            StringBuilder sb = new StringBuilder();
            String expandTagsone = expandTags(word.getCorpus().getTagone());
            String expandTagstwo = expandTags(word.getCorpus().getTagtwo());
            String expandTagsthree = expandTags(word.getCorpus().getTagthree());
            String expandTagsfour = expandTags(word.getCorpus().getTagfour());
            String tagtwo = word.getCorpus().getTagtwo();
            String tagone = word.getCorpus().getTagone();
            String tagthree = word.getCorpus().getTagthree();
            String tagfour = word.getCorpus().getTagfour();
            boolean tagnounone = tagone.equals("N") || tagone.equals("ADJ") || tagone.equals("PN");
            boolean tagnountwo = tagtwo.equals("N") || tagtwo.equals("ADJ") || tagtwo.equals("PN");
            boolean tagnounthree = tagthree.equals("N") || tagthree.equals("ADJ") || tagthree.equals("PN");
            boolean tagnounfour = tagfour.equals("N") || tagfour.equals("ADJ") || tagfour.equals("PN");
            if (tagnounone) {
                expandTagsone = getNounDetails(word.getCorpus().getDetailsone());
                expandTagsone.concat(" ");
            } else if (tagnountwo) {
                expandTagstwo = getNounDetails(word.getCorpus().getDetailstwo());
                expandTagstwo.concat(" ");
            } else if (tagnounthree) {
                expandTagsthree = getNounDetails(word.getCorpus().getDetailsthree());
                expandTagsthree.concat(" ");
            } else if (tagnounfour) {
                expandTagsfour = getNounDetails(word.getCorpus().getDetailsfour());
            }
            if (word.getCorpus().getTagone().equals("V")) {
                expandTagsone = getVerbDetails(expandTagsone, word.getCorpus().getDetailsone());

            } else if (word.getCorpus().getTagtwo().equals("V")) {
                expandTagstwo = getVerbDetails(expandTagstwo, word.getCorpus().getDetailstwo());

            } else if (word.getCorpus().getTagthree().equals("V")) {
                expandTagsthree = getVerbDetails(expandTagsthree, word.getCorpus().getDetailsthree());

            } else if (word.getCorpus().getTagfour().equals("V")) {
                expandTagsfour = getVerbDetails(expandTagsfour, word.getCorpus().getDetailsfour());

            }
            sb.append(word.getCorpus().getTagfour());
            sb.append("|");
            sb.append(word.getCorpus().getTagthree());
            sb.append("|");
            sb.append(word.getCorpus().getTagtwo());
            sb.append("|");
            sb.append(word.getCorpus().getTagone());
            tagspannable = CorpusUtilityorig.NewSetWordSpanTag(word.getCorpus().getTagone(), word.getCorpus().getTagtwo(), word.getCorpus().getTagthree(),
                    word.getCorpus().getTagfour(), word.getCorpus().getTagfive(), " ", expandTagsfour, expandTagsthree, expandTagstwo, expandTagsone);

        } else if (wordcount == 5) {
            StringBuilder sb = new StringBuilder();
            String expandTagsone = expandTags(word.getCorpus().getTagone());
            String expandTagstwo = expandTags(word.getCorpus().getTagtwo());
            String expandTagsthree = expandTags(word.getCorpus().getTagthree());
            String expandTagsfour = expandTags(word.getCorpus().getTagfour());
            String expandTagsfive = expandTags(word.getCorpus().getTagfive());
            if (word.getCorpus().getTagone().equals("V")) {
                expandTagsone = getVerbDetails(expandTagsone, word.getCorpus().getDetailsone());

            } else if (word.getCorpus().getTagtwo().equals("V")) {
                expandTagstwo = getVerbDetails(expandTagstwo, word.getCorpus().getDetailstwo());

            } else if (word.getCorpus().getTagthree().equals("V")) {
                expandTagsthree = getVerbDetails(expandTagsthree, word.getCorpus().getDetailsthree());

            } else if (word.getCorpus().getTagfour().equals("V")) {
                expandTagsfour = getVerbDetails(expandTagsfour, word.getCorpus().getDetailsfour());

            } else if (word.getCorpus().getTagfive().equals("V")) {
                expandTagsfive = getVerbDetails(expandTagsfive, word.getCorpus().getDetailsfour());

            }
            sb.append(word.getCorpus().getTagfour());
            sb.append("|");
            sb.append(word.getCorpus().getTagthree());
            sb.append("|");
            sb.append(word.getCorpus().getTagtwo());
            sb.append("|");
            sb.append(word.getCorpus().getTagone());
            tagspannable = CorpusUtilityorig.NewSetWordSpanTag(word.getCorpus().getTagone(), word.getCorpus().getTagtwo(), word.getCorpus().getTagthree(),
                    word.getCorpus().getTagfour(), word.getCorpus().getTagfive(), expandTagsone, expandTagstwo, expandTagsthree, expandTagsfour, expandTagsfive);
            sb.append("|");
            sb.append(word.getCorpus().getTagfour());
            sb.append("|");
            sb.append(word.getCorpus().getTagthree());
            sb.append("|");
            sb.append(word.getCorpus().getTagtwo());
            sb.append("|");
            sb.append(word.getCorpus().getTagone());

        }
        return tagspannable;
    }

    private String getNounDetails(String wordetails) {
        String expandTagsone = "";
        String[] split = wordetails.split("\\|");
        HashMap<String, SpannableStringBuilder> wordbdetail = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        getNdetails(corpusNounWord, wordbdetail, sb);
        StringBuilder genderNumberdetails1 = getGenderNumberdetails(corpusNounWord.get(0).getGendernumber());
        if (wordbdetail.get("noun") != null) {
            expandTagsone = expandTagsone.concat(String.valueOf(wordbdetail.get("noun")));
            expandTagsone = expandTagsone.concat("Root:-").concat(corpusNounWord.get(0).getRoot_a());
        }
        //   expandTagsone = expandTagsone.concat(genderNumberdetails1.toString());
        return expandTagsone;
    }

    @NonNull
    private String getVerbDetails(String expandTagsone, String wordetails) {
        String[] split = wordetails.split("\\|");
        String s = split[split.length - 1];
        if (!s.contains("MOOD")) {
            StringBuilder genderNumberdetails = getGenderNumberdetails(s);
            expandTagsone = expandTagsone.concat(genderNumberdetails.toString());
            String verbTense = expandVerbTense(word.getCorpus().getDetailsone());
            expandTagsone = expandTagsone.concat("(").concat(verbTense).concat(")");
            expandTagsone = expandTagsone.concat(CorpusConstants.verbfeaturesenglisharabic.IND);

        } else {
            String ss = split[split.length - 2];
            StringBuilder genderNumberdetails = getGenderNumberdetails(ss);
            expandTagsone = expandTagsone.concat(genderNumberdetails.toString());
            String verbTense = expandVerbTense(word.getCorpus().getDetailsone());
            expandTagsone = expandTagsone.concat("(").concat(verbTense).concat(")");

        }
        if (!s.contains("PASS")) {
            expandTagsone = expandTagsone.concat(ACT);

        } else {
            expandTagsone = expandTagsone.concat(PASS);
        }
        //   expandTagsone.concat(verbCorpusRootWord.get(0).getRoot_a());
        expandTagsone = expandTagsone.concat("Root:-").concat(verbCorpusRootWord.get(0).getRoot_a());
        return expandTagsone;
    }

    private String expandVerbTense(String verbdetails) {
        String tense;
        if (verbdetails.contains("IMPF")) {
            tense = IMPF;
        } else if (verbdetails.contains("IMPV")) {
            tense = IMPV;
        } else if (verbdetails.contains("PERF")) {
            tense = PERF;

        } else {
            tense = "";
        }
        if (verbdetails.contains("SUBJ")) {
            tense = tense.concat(SUBJ);
        } else if (verbdetails.contains("JUS")) {
            tense = tense.concat(JUS);
        }
        return tense;
    }
}
