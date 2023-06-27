package com.example.mushafconsolidated.Entities;

import static com.example.Constant.VERBMOOD;

import com.example.mushafconsolidated.R;

public class VerbWazan {
    public final static String BNASARA = "نصر"; //   A-U // NASRA-YANSURU
    public final static String BZARABA = "ضرب";  //   A-I // ZARABA-YASZRIBU
    public final static String BFATAH = "فتح";  //  A-A // FATHA-YAFTAHU
    public final static String BSAMIA = "سمح";   //  I-A  //SAMIA-YASMAHU
    public final static String BKARUMU = "كرم";   //   U-U  //KARUMA-YAKRUMU
    public final static String BHASIBA = "حسب";    //  I-I  //HASIBA-YAHSIU
    private String root;
    private static String wazan;
    private String arabicword;

    public VerbWazan(String root, String wazan) {
        this.root = root;
        this.wazan = wazan;
    }
    public VerbWazan() {
    }

    public String getArabicword() {
        return arabicword;
    }

    public void setArabicword(String arabicword) {
        this.arabicword = arabicword;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    /*
        public String getWazan() {
            if(wazan.equals("N")){
                wazan="1";
            }else if(wazan.equals("Z")){

                wazan= Constants.BZARABA;
            }else if(wazan.equals("F")){
                wazan=BFATAH;

            }else if(wazan.equals("S")){
                wazan=BSAMIA;

            }else if(wazan.equals("K")){

                wazan=BKARUMU;
            }else if(wazan.equals("H")){

                wazan=BHASIBA;
            }

            return wazan;
        }
     */
    public static String getWazan() {
        if (wazan.equals("N")) {
            wazan = "1";
        } else if (wazan.equals("Z")) {
            wazan = "2";
        } else if (wazan.equals("F")) {
            wazan = "3";

        } else if (wazan.equals("S")) {
            wazan = "4";

        } else if (wazan.equals("K")) {
            wazan = "5";
        } else if (wazan.equals("H")) {
            wazan = "6";
        }
        return wazan;
    }

    public static String getMazeedWazan(String form) {
        String formdetails = "";
        switch (form) {
            case "IV":
                formdetails = "1";
                break;
            case "II":
                formdetails = "2";
                break;
            case "III":
                formdetails = "3";
                break;
            case "VII":
                formdetails = "4";
                break;
            case "VIII":
                formdetails = "5";
                break;
            case "VI":
                formdetails = "8";
                break;
            case "V":
                formdetails = "7";
                break;
            case "X":
                formdetails = "9";
                break;
            default:
                String s = "";

        }
        return formdetails;
    }
   public static String getVerbMood(String selected){
            String vmood="" ;
       switch (selected) {
           case "IND":
               vmood= "Indicative";
               break;
           case "SUBJ":
               vmood= "Subjunctive";
               break;
           case "JUS":
               vmood= "Jussive";
               break;
           default:
               vmood= "";
               break;
       }
return vmood;
   }


    public void setWazan(String wazan) {
        this.wazan = wazan;
    }

    public String getWazan(String wazan) {
        if (wazan.equals("N")) {
            wazan = BNASARA;
        } else if (wazan.equals("Z")) {
            wazan = BZARABA;
        } else if (wazan.equals("F")) {
            wazan = BFATAH;

        } else if (wazan.equals("S")) {
            wazan = BSAMIA;

        } else if (wazan.equals("K")) {
            wazan = BKARUMU;
        } else if (wazan.equals("H")) {
            wazan = BHASIBA;
        }
        return wazan;
    }
}
