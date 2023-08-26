package database;

import android.content.Context;


import database.entity.Mazeed;
import database.entity.MujarradVerbs;
import database.entity.QuranVerbsEntity;
import database.entity.kov;
import database.entity.verbcorpus;

import java.util.ArrayList;

public class VerbDatabaseUtils {


    private static VerbDatabase database;

    public VerbDatabaseUtils(Context context) {
        database = VerbDatabase.getInstance(context);

    }


    public ArrayList<kov> getKov() {
        return (ArrayList<kov>) database.kovDao().getRules();

    }

    public ArrayList<QuranVerbsEntity> getQuranVerbsbyFrequency(int sort) {
        return (ArrayList<QuranVerbsEntity>) database.QuranVerbsDao().getverbsbyFrequency(sort);

    }


    public ArrayList<MujarradVerbs> getMujarradVerbs(String root) {
        return (ArrayList<MujarradVerbs>) database.mujarradDao().getverbTri(root);

    }

    public ArrayList<MujarradVerbs> getMujarradAall( ) {
        return (ArrayList<MujarradVerbs>) database.mujarradDao().getverbTriAll();
    }
    public ArrayList<MujarradVerbs> getMujarradBYWeakness(String kov ) {
        return (ArrayList<MujarradVerbs>) database.mujarradDao().getMujarradWeakness(kov);
    }
    public ArrayList<Mazeed> getMazeedWeakness(String kov ) {
        return (ArrayList<Mazeed>) database.mazeedDao().getMazeedWeakness(kov);
    }



    public ArrayList<Mazeed> getMazeedRoot(String root) {
        return (ArrayList<Mazeed>) database.mazeedDao().getMazeedRoot(root);

    }



    public ArrayList<verbcorpus> verbcorpuses() {


        return (ArrayList<verbcorpus>) database.verbcorpusDao().getmazeedform("I");
    }
}










